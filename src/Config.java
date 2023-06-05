import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

public class Config {

    boolean verbose;
    boolean traderInfo;
    boolean median;

    public Config(String[] args) {
        // implement getopt processing on args to set verbose, traderInfo, and median
        // Get-opt processing
        LongOpt[] longOptions = {
                new LongOpt("verbose", LongOpt.OPTIONAL_ARGUMENT, null, 'v'),
                new LongOpt("median", LongOpt.OPTIONAL_ARGUMENT, null, 'm'),
                new LongOpt("trader-info", LongOpt.OPTIONAL_ARGUMENT, null, 'i')

        };

        // constructing the get-opt object
        Getopt g = new Getopt("StockMarket", args, "vmi", longOptions);
        g.setOpterr(true);

        int choice;

        // looping through the arguments
        while ((choice = g.getopt()) != -1) {

            switch (choice) {
                case 'v':
                    verbose = true;
                    break;

                case 'm':
                    median = true;
                    break;

                case 'i':
                    traderInfo = true;
                    break;

                default:
                    // prints if the user enters an option or command not specified in the program
                    System.err.println("Unknown command line argument option:" + choice);
                    System.exit(1);
            }
        }


    }
}
