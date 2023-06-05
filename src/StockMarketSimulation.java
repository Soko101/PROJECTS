import java.util.ArrayList;
import java.util.Scanner;

public class StockMarketSimulation {

    private ArrayList<Stock> stocks;
    private ArrayList<Trader> traders;

    private Config config;

    private Scanner in;
    private int numTraders;
    private int numStocks;

    private long curr_id = 0;

    public StockMarketSimulation(Config c) {
        config = c;

        // read in an initial configuration
        in = new Scanner(System.in);

        // COMMENT: <COMMENT>
        // MODE: <INPUT_MODE>
        // NUM_STOCKS: <NUM_STOCKS>

        // skip over the comment
        in.nextLine();

        // throw away header
        in.next();
        String mode = in.next();

        in.next();
        numTraders = in.nextInt();

        in.next();
        numStocks = in.nextInt();


        // construct ALs with the correct capacity
        traders = new ArrayList<>(numTraders);
        for (int i = 0; i < numTraders; i++) {
            traders.add(new Trader(i));

        }
        stocks = new ArrayList<>(numStocks);
        for (int i = 0; i < numStocks; i++) {
            stocks.add(new Stock());
        }


        // check for PR mode
        if (mode.equals("PR")) {
            // RANDOM_SEED: <SEED>
            // NUMBER_OF_ORDERS: <NUM_ORDERS>
            // ARRIVAL_RATE: <ARRIVAL_RATE>

            in.next();
            int seed = in.nextInt();

            in.next();
            int numOrders = in.nextInt();

            in.next();
            int arrivalRate = in.nextInt();

            in = P2Random.PRInit(seed, numTraders, numStocks, numOrders, arrivalRate);
        }


    }

    public void simulate() {

        System.out.println("Processing orders...");

        long currentTime = 0;
        // Our main processing loop for simulation
        while (in.hasNextLong()) {

            Order nextOrder = getNextOrder();

            // Error check for the timestamp
            if (nextOrder.getTimestamp() < currentTime || nextOrder.getTimestamp() < 0) {
                System.err.println("Timestamps should not be decreasing");
                System.exit(1);

            }

            if (nextOrder.getTimestamp() > currentTime) {
                //when the timestamp changes
                //If and only if the --median/-m option is specified on the command line, at the times detailed in
                //the Market Logic section, the program will print the current median price for all stocks in
                //ascending order by stock ID.
                if (config.median) {
                    for (int i = 0; i < stocks.size(); i++) {
                        // i is the stock ID
                        Stock s = stocks.get(i);
                        if (s.numTransactions > 0) {
                            // TODO printout median output
                            System.out.println("Median match price of Stock " +
                                    i + " at time " + currentTime + " is " + "$" + s.getMedian());
                        }

                    }
                }
                currentTime = nextOrder.getTimestamp();
            }

            Stock s = stocks.get(nextOrder.getStockId());

            // add the order to the stock
            s.addOrder(nextOrder);
            // perform matches
            s.performMatches(config.verbose, traders);
            //s.performMatches(traders);


        }
        // Median printed out again
        if (config.median) {
            for (int i = 0; i < stocks.size(); i++) {
                // i is the stock ID
                Stock s = stocks.get(i);
                if (s.numTransactions > 0) {
                    int median = s.getMedian();
                    //printing out median output
                    System.out.println("Median match price of Stock " +
                            i + " at time " + currentTime + " is " + "$" + s.getMedian());
                }

            }
        }

        //After all input has been read and all possible trades have been completed, the following output
        //will always be printed without any preceding newlines before any optional end of day output:
        System.out.println("---End of Day---");
        System.out.println("Orders Processed: " + Stock.count);


        //If and only if the --trader-info/-i option is specified on the command line, the following
        //output will be printed without preceding lines
        if (config.traderInfo) {
            System.out.println("---Trader Info---");
            for (int j = 0; j < traders.size(); j++) {
                System.out.println("Trader " + j + " bought " + traders.get(j).getStocksBought() +
                        " and sold " + traders.get(j).getStocksSold() + " for a net " +
                        "transfer of " + "$" + traders.get(j).getNetSales());

            }
        }
    }


    /**
     * Read and return the next order from in
     *
     * @return
     */
    private Order getNextOrder() {
        // <TIMESTAMP> <BUY/SELL> T<TRADER_ID> S<STOCK_NUM> $<PRICE> #<QUANTITY>
        long ts = in.nextLong();
        String intent = in.next();
        int traderId = Integer.parseInt(in.next().substring(1));
        int stockId = Integer.parseInt(in.next().substring(1));
        int price = Integer.parseInt(in.next().substring(1));
        int qty = Integer.parseInt(in.next().substring(1));

        //Error check for the timestamp
        if (ts < 0) {
            System.err.println("TimeStamp cannot be less than Zero");
            System.exit(1);
        }
        // Error check for the traderId and numTraders
        if (!((traderId >= 0 && traderId < numTraders))) {
            System.err.println("Trader ID is out of range");
            System.exit(1);
        }
        // Error check for the stockId and numStocks
        if (!((stockId >= 0 && stockId < numStocks))) {
            System.err.println("Stock ID is out of range");
            System.exit(1);
        }
        // Error check if price entered is negative
        if (price <= 0) {
            System.err.println("price should not be negative");
            System.exit(1);
        }
        // Error check if quantity entered is negative
        if (qty <= 0) {
            System.err.println("Quantity should not be negative");
            System.exit(1);
        }
        //Depending on whether the trader wants to sell or buy:
        if (intent.equals("SELL")) {
            return new SellOrder(curr_id++, ts, traderId, stockId, price, qty);
        } else {
            return new BuyOrder(curr_id++, ts, traderId, stockId, price, qty);
        }

    }


}
