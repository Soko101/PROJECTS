
public class Trader {
    private int traderId;

    private int stocksBought;

    private int stocksSold;

    private int netSales;

    public Trader(int id) {
        traderId = id;
        stocksBought = 0;
        stocksSold = 0;
        netSales = 0;

    }
    //The total number bought by all traders should equal the total number sold by all
    //traders, and the total   of   all   the   net   transfers   should   be   0.
    //Hence we implement 2 methods; buy and sell where we also calculate netSales

    public void buy(int shares, int price) {
        stocksBought += shares;
        netSales = netSales - (shares * price);
    }

    public void sell(int shares, int price) {
        stocksSold += shares;
        netSales = netSales + (shares * price);

    }

    // getters for netSales, stocksSold, stocksBought and TraderId
    public int getNetSales() {
        return netSales;
    }

    public int getStocksSold() {
        return stocksSold;
    }

    public int getStocksBought() {
        return stocksBought;
    }

    public int getTraderId() {
        return traderId;
    }

}
