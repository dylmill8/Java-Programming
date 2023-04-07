import java.io.*;

/**
 * This program simulates stock trades for users. The stock price and number of available shares are determined
 * by the buy and sell orders from traders
 *
 * @author Dylan Miller
 * @version November 1, 2022
 */

public class StockGame extends Thread {
    private static double stockPrice;
    private static int availableShares;
    private static int tradeCount;
    private String name;
    private int numShares;
    private String filename;
    public static final Object OBJ = new Object();

    public StockGame(String name, String fileName) {
        this.name = name;
        this.filename = fileName;
        numShares = 0;
        stockPrice = 100.00;
        availableShares = 100;
        /*
        default value for integers is 0, so tradeCount is initialized to 0 unless it already was assigned a value
        by another instance in which case we don't need to initialize it because the variable is static and will be
        assigned to all instances of the class
        */
    }

    public void run() {
        try {
            File file = new File(filename);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            String option;
            int value;

            while (line != null) {
                option = line.split(",")[0];
                value = Integer.parseInt(line.split(",")[1]);

                synchronized (OBJ) {
                    System.out.println("----------");
                    if (value < 0) {
                        System.out.println("Error, invalid input!");
                    } else {
                        System.out.println("Stock Price: " + stockPrice + "\n" +
                                "Available Shares: " + availableShares + "\n" +
                                "Trade number: " + (tradeCount + 1) + "\n" +
                                "Name: " + name);
                        if (option.equalsIgnoreCase("BUY")) {
                            System.out.printf("Purchasing %d shares at %.2f...%n", value, stockPrice);
                            if (value <= availableShares) {
                                numShares += value;
                                availableShares -= value;
                                stockPrice = stockPrice + (value * 1.5);
                                tradeCount++;
                            } else {
                                System.out.println("Insufficient shares available, cancelling order...");
                            }
                        } else if (option.equalsIgnoreCase("SELL")) {
                            System.out.printf("Selling %d shares at %.2f...%n", value, stockPrice);
                            if (value <= numShares) {
                                numShares -= value;
                                availableShares += value;
                                stockPrice = stockPrice - (value * 2.0);
                                tradeCount++;
                            } else {
                                System.out.println("Insufficient owned shares, cancelling order...");
                            }
                        }
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            StockGame[] stockTraders = {new StockGame("Xander", "TraderOneMoves.txt"),
                new StockGame("Afua", "TraderTwoMoves.txt")};

            for (int i = 0; i < stockTraders.length; i++) {
                stockTraders[i].start();
            }
            for (int i = 0; i < stockTraders.length; i++) {
                stockTraders[i].join();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}
