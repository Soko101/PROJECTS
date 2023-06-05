import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Stock {


    PriorityQueue<Order> buyOrders = new PriorityQueue<>();
    PriorityQueue<Order> sellOrders = new PriorityQueue<>();


    //2 more PQs that we can implement for tracking the median
    // min PQ
    PriorityQueue<Integer> topHalf = new PriorityQueue<>();

    // max PQ
    PriorityQueue<Integer> bottomHalf = new PriorityQueue<>(Collections.reverseOrder());

    // track median
    private int median;

    public int getMedian() {

        return median;
    }


    public static int count;
    public int numTransactions = 0;


    public void addOrder(Order o) {
        // TODO implement adding an order
        if (o.isSell()) {
            sellOrders.add(o);
        } else {
            buyOrders.add(o);
        }
    }

    public void performMatches(boolean verbose, List<Trader> traders) {

        //checking if there are orders that can be matched and perform those transactions
        while (canMatch()) {
            Order buy = buyOrders.peek();
            Order sell = sellOrders.peek();

            int price;
            if (buy.getId() < sell.getId()) {
                price = buy.getPrice();
            } else {
                price = sell.getPrice();
            }

            

            int shares = Math.min(buy.getQuantity(), sell.getQuantity());

            // perform the transaction
            buy.removeShares(shares);
            sell.removeShares(shares);

            if (buy.getQuantity() == 0) {
                buyOrders.remove();
            }

            if (sell.getQuantity() == 0) {
                sellOrders.remove();
            }

            //If and only if the --verbose/-v option is specified on the command line  whenever
            //a trade is completed then we will printout on a single line:
            if (verbose) {
                System.out.println("Trader " + buy.getTraderId() + " purchased "  + shares + " shares of Stock "  + buy.getStockId()
                        + " from Trader " + sell.getTraderId() + " for " +  "$" + price + "/share");
            }
            count++;
            updateMedian(price);
            numTransactions++;

            //updating trader information
            traders.get(buy.getTraderId()).buy(shares, price);
            traders.get(sell.getTraderId()).sell(shares, price);

            }
    }
    //Method to calculate median through the use of priority queues
    private void updateMedian(int price) {

        // add this price to the running median
        if (topHalf.isEmpty() && bottomHalf.isEmpty()) {
            // first half
            topHalf.add(price);
            median = price;
        } else {
            // decide if were inserting in either top or bottom half
            if (price < median) {
                bottomHalf.add(price);
            } else {
                topHalf.add(price);
            }

            // check for balanced sizes
            if (bottomHalf.size() - topHalf.size() == 2) {
                // shift an item from the bottom to the top
                topHalf.add(bottomHalf.remove());
            } else if (topHalf.size() - bottomHalf.size() == 2) {
                // shift from top to bottom
                bottomHalf.add(topHalf.remove());
            }

            // update the median value
            // sizes differ by at most 1
            if (bottomHalf.size() > topHalf.size()) {
                median = bottomHalf.element();
            } else if (topHalf.size() > bottomHalf.size()) {
                median = topHalf.element();
            } else {
                // integer division
                median = (topHalf.element() + bottomHalf.element()) / 2;
            }
        }
    }
    // matching sell orders and buy orders while also making sure
    // that they are not empty
    private boolean canMatch() {
        if (buyOrders.isEmpty() || sellOrders.isEmpty()) {
            // we cannot match
            return false;
        }

        return sellOrders.peek().getPrice() <= buyOrders.peek().getPrice();
    }
}
