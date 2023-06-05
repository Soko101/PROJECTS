public abstract class Order implements Comparable<Order> {
    private long id;
    private long timestamp;
    private int traderId;
    private int stockId;
    private boolean isSell;
    private boolean isBuy;
    private int price;
    private int quantity;

    public Order(long l,long ts, int tId, int sId, int p, int q) {
        // unique number to describe the order
        this.id = l;
        timestamp = ts;
        traderId = tId;
        stockId = sId;
        price = p;
        quantity = q;
    }
    public long getId() { return id; }
    public long getTimestamp() { return timestamp; }
    public int getTraderId() { return traderId; }
    public int getStockId() { return stockId; }
    public int getPrice() { return price; }
    public  int getQuantity() { return quantity; }

    public abstract boolean isSell();


    @Override
    public abstract  int compareTo(Order o);

    /**
     * Reduce the quantity of shares in this order
     * @param shares quantity
     */
    public void removeShares(int shares) {
        quantity -= shares;
    }
}
