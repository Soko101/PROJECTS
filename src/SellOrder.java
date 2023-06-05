public class SellOrder extends Order{
    public SellOrder(long id, long ts, int tId, int sId, int p, int q) {
        super(id, ts, tId, sId, p, q);
    }

    @Override
    public boolean isSell() {
        return true;
    }

    @Override
    public int compareTo(Order o) {
        //this
        // o -- other object
        // return a -ve int when this is higher priority than o

        // break ties for same price
        if (this.getPrice() == o.getPrice()) {
            // the order that came first gets priority
            // smaller  ID means came earlier
            // same for a buy order
            return (int) (this.getId() - o.getId());
        }
        // FIXME price comparison will have to be different for a buy order
        return this.getPrice() - o.getPrice();
    }
}
