# PROJECT
- Utilizes Priority Queue Algorithm to sort out orders
The provided code represents a simple stock market simulation in Java. Let's break down the architecture of the code:

1. **StockMarketSimulation Class:**
   - This is the main class responsible for running the stock market simulation.
   - It contains private data members such as `stocks` (a list of stocks), `traders` (a list of traders), `config` (configuration settings), and other variables to manage the simulation.
   - The constructor (`public StockMarketSimulation(Config c)`) initializes the simulation based on the provided configuration.
   - The `simulate()` method is the core of the simulation. It processes orders, matches buy and sell orders, and tracks various statistics.

2. **BuyOrder and SellOrder Classes:**
   - These are subclasses of the abstract `Order` class.
   - They represent buy and sell orders with specific behaviors for distinguishing between the two.
   - They implement the `compareTo` method to prioritize orders for matching.

3. **Stock Class:**
   - The `Stock` class manages buy and sell orders for a particular stock.
   - It uses priority queues (`buyOrders` and `sellOrders`) to maintain orders in priority order based on price.
   - It tracks the median price of transactions using two more priority queues (`topHalf` and `bottomHalf`).
   - The `performMatches` method matches buy and sell orders and updates the median and trader information.

4. **Trader Class:**
   - The `Trader` class represents individual traders in the simulation.
   - It keeps track of the trader's ID, the number of stocks bought and sold, and the net sales.
   - Traders can buy and sell stocks, and their statistics are updated accordingly.

5. **Main Class:**
   - The `Main` class contains the `main` method, which is the entry point of the program.
   - It reads command-line arguments and configuration settings, initializes the `StockMarketSimulation`, and starts the simulation.

6. **Order Abstract Class:**
   - This is an abstract class that defines the common properties and methods for both buy and sell orders.
   - It enforces the implementation of the `isSell` method and the `compareTo` method to prioritize orders for matching.

7. **Config Class:**
   - The `Config` class is not included in the provided code, but it's mentioned in the `StockMarketSimulation` constructor as a way to configure the simulation. It likely holds various simulation settings.

8. **Input Data:**
   - The testing files contain input data for the simulation, specifying the number of traders, stocks, and a sequence of buy and sell orders with timestamps, prices, and quantities.

The simulation's main flow involves reading and processing these input orders, matching buy and sell orders, updating statistics, and calculating the median prices. The program can be controlled with various command-line options, such as `--verbose` for detailed output and `--median` for printing median prices at specific times.

In summary, the architecture of the code represents a basic stock market simulation system, including traders, stocks, buy and sell orders, and tracking of trading statistics. It provides the foundation for simulating stock market behavior based on the input data and configuration settings.
