public class Main {
    public static void main(String[] args) {

        Config c = new Config(args);

        StockMarketSimulation s = new StockMarketSimulation(c);

        s.simulate();
    }
}
