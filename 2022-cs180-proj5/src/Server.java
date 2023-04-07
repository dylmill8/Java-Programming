import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class Server implements Runnable {
    private static ArrayList<String> accounts = null;
    private static ArrayList<String> marketFile;
    private static ArrayList<String> boughtFile;
    private static ArrayList<String> shoppingCart;

    private final ServerSocket socket;
    public static final CyclicBarrier barrier = new CyclicBarrier(2);

    public Server(ServerSocket socket) {
        this.socket = socket;
        if (accounts == null) {
            // lock order: accounts > marketFile > boughtFile > shoppingCart
            accounts = ServerFunctions.readFile(new File("accounts.txt"));
            boughtFile = ServerFunctions.readFile(new File("boughtFile.txt"));
            marketFile = ServerFunctions.readFile(new File("marketFile.txt"));
            shoppingCart = ServerFunctions.readFile(new File("shoppingCart.txt"));
        }
    }

    public void run() {
        try (
                var client = socket.accept();
                var bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                var printWriter = new PrintWriter(client.getOutputStream(), true);
        ) {
            barrier.await(); // tell ThreadSpawner to wait for next client

            String line;
            String email;
            String password;
            String userType;
            while (true) {
                while (true) {
                    line = bufferedReader.readLine();
                    email = line.split(",")[0];
                    password = line.split(",")[1];

                    // Server function which takes in a email and username and checks the user file array list to make
                    // certain it exists.
                    int userValid = ServerFunctions.validateUser(email, password, accounts);
                    printWriter.println(userValid);
                    if (userValid == 1) {
                        break;
                    } else if (userValid == 2) {
                        continue;
                    }

                    line = bufferedReader.readLine();

                    if (line.equals("1")) {
                        while (true) {
                            line = bufferedReader.readLine();
                            email = line.split(",")[0];
                            password = line.split(",")[1];
                            line = line.split(",")[2];

                            userValid = ServerFunctions.validateUser(email, accounts);

                            printWriter.println(userValid);

                            if (userValid == 1) {
                                if (line.equals("0")) {
                                    synchronized (accounts) {
                                        accounts.add(email + "," + password + ",customer");
                                    }
                                } else {
                                    synchronized (accounts) {
                                        accounts.add(email + "," + password + ",seller");
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                userType = ServerFunctions.getUserType(email, password, accounts);
                printWriter.println(userType);

                line = bufferedReader.readLine();
                if (line.equals("edit")) {
                    while (true) {
                        String[] newData = bufferedReader.readLine().split(",");
                        int userValid = ServerFunctions.validateUser(newData[0], accounts);
                        printWriter.println(userValid);
                        if (userValid == 1) {
                            if (newData[2].equals("0")) {
                                userType = "customer";
                            } else {
                                userType = "seller";
                            }
                            ClientFunctions.editUser(email, newData[0], newData[1], userType, accounts);
                            break;
                        }
                    }
                } else if (line.equals("delete")) {
                    ClientFunctions.deleteUser(email, accounts);
                } else if (line.equals("continue")) {
                    break;
                }
            }

            while (true) {
                String[] market = ClientFunctions.displayMarket(marketFile);
                for (String s : market) {
                    printWriter.println(s);
                }
                printWriter.println("<END>");

                printWriter.println(marketFile.size());

                String option = bufferedReader.readLine();

                switch (option) {
                    case "1. View a book" -> {
                        if (!(marketFile.size() == 0)) {
                            // not threadsafe
                            int itemToView = Integer.parseInt(bufferedReader.readLine()) - 1;
                            if (itemToView == -1) {
                                continue;
                            }
                            viewItemServer(bufferedReader, printWriter, userType, email, Product.fromString(marketFile.get(itemToView)));
                        }
                    }
                    case "2. Search the bookstore" -> {
                        line = bufferedReader.readLine();

                        ArrayList<String> searchLines = new ArrayList<>();
                        synchronized (marketFile) {
                            int[] searchResults = ServerFunctions.getSearchResults(line, marketFile);

                            printWriter.println(searchResults.length);

                            for (int i : searchResults) {
                                searchLines.add(marketFile.get(i));
                            }
                            String[] searchDisplay = ClientFunctions.displayMarket(searchLines);

                            for (String s : searchDisplay) {
                                printWriter.println(s);
                            }
                            printWriter.println("<END>");
                        }
                        int index = Integer.parseInt(bufferedReader.readLine()) - 1;
                        if (index == -1) {
                            continue;
                        }
                        String itemToView = searchLines.get(index);
                        viewItemServer(bufferedReader, printWriter, userType, email, Product.fromString(itemToView));
                        break;
                    }
                    case "3. Sort the bookstore" -> {
                        line = bufferedReader.readLine();

                        if (line.equals("0")) {
                            ServerFunctions.sortByNumber(marketFile, 4);
                        } else {
                            ServerFunctions.sortByNumber(marketFile, 5);
                        }
                    }
                    case "4. View cart" -> {
                        while (true) {
                            String[] userCartTemp = new String[0];
                            synchronized (shoppingCart) {
                                for (String i : shoppingCart) {
                                    if (i.contains(email) && i.split(";")[0].equals(email)) {
                                        userCartTemp = i.split(";");
                                        break;
                                    }
                                }
                            }
                            ArrayList<String> userCart = new ArrayList<>(Arrays.asList(userCartTemp));
                            var sublist = userCart.subList(1, userCart.size());
                            String[] cart = ClientFunctions.displayMarket(sublist);

                            printWriter.println(userCart.size() - 1);

                            for (String s : cart) {
                                printWriter.println(s);
                            }
                            printWriter.println("<END>");

                            line = bufferedReader.readLine();

                            if (line.equals("0")) {
                                for (int i = 1; i < userCart.size(); i++) {
                                    var product = Product.fromString(userCart.get(i));
                                    int quantity = product.getQuantity();

                                    ServerFunctions.buyItem(email, product, quantity, marketFile, boughtFile);
                                    CartFunctions.remove(email, 1, shoppingCart);
                                }
                                break;
                            } else if (line.equals("1")) {
                                int i = Integer.parseInt(bufferedReader.readLine());
                                CartFunctions.remove(email, i, shoppingCart);
                            } else {
                                break;
                            }
                        }
                    }
                    case "5. View store sales statistics" -> {
                        if (userType.equals("customer")) {
                            ArrayList<String> stats = ServerFunctions.customerData(email, boughtFile);
                            do {
                                line = bufferedReader.readLine();

                                ServerFunctions.sortByNumber(stats, Integer.parseInt(line) + 1);

                                String salesDisplay = ClientFunctions.displayStats(stats);

                                printWriter.println(salesDisplay + "\n<END>");

                                line = bufferedReader.readLine();
                            } while (line.equals("0"));
                        } else {
                            Dictionary<String, ArrayList<String>[]> stats = ServerFunctions.sellerData(email,
                                    boughtFile);
                            Enumeration<String> statsKeys = stats.keys();
                            for (int i = 0; i < stats.size(); i++) {
                                ArrayList<String>[] nextStat = stats.get(statsKeys.nextElement());
                                ArrayList<String> statPurchases = nextStat[0];
                                ServerFunctions.sortByNumber(statPurchases, 1);
                                ArrayList<String> statCustomers = nextStat[1];
                                ServerFunctions.sortByNumber(statCustomers, 1);
                            }


                            do {
                                line = bufferedReader.readLine();

                                String salesDisplay = ClientFunctions.displayStats(stats, line);

                                printWriter.println(salesDisplay + "\n<END>");

                                line = bufferedReader.readLine();
                            } while (line.equals("0"));
                        }
                    }
                    case "6. View purchase history", "7. Export purchase history", "7. Transaction history" -> {
                        if (option.equals("7. Export purchase history")) {
                            var input = bufferedReader.readLine();
                            if (Integer.parseInt(input) != 0) {
                                continue;
                            }
                        }
                        String[] history = ClientFunctions.displayPurchaseHistory(email, boughtFile);

                        for (String s : history) {
                            printWriter.println(s);
                        }
                        printWriter.println("<END>");
                    }
                    case "4. Products in carts" -> {
                        printWriter.println(CartFunctions.getCarts(email, shoppingCart) + "\n<END>");
                    }
                    case "6. Sell a book" -> {
                        String name = bufferedReader.readLine();
                        String store = bufferedReader.readLine();
                        String description = bufferedReader.readLine();
                        String quantity = bufferedReader.readLine();
                        String price = bufferedReader.readLine();
                        String sale = bufferedReader.readLine();

                        marketFile.add(name + "," + email + "," + store + "," + description + "," + quantity + ","
                                + price + "," + sale + ",");
                    }
                    case "8. Calculate income" -> {
                        printWriter.println(ClientFunctions.calculateIncome(email, boughtFile));
                    }
                    case "9. Import/export sales" -> {
                        line = bufferedReader.readLine();
                        int c = Integer.parseInt(bufferedReader.readLine());
                        if (c != 0) {
                            continue;
                        }

                        if (line.equals("0")) { // Import
                            ArrayList<String> newProducts = new ArrayList<>();
                            line = bufferedReader.readLine();
                            while (!line.equals("<END>")) {
                                newProducts.add(line);
                                line = bufferedReader.readLine();
                            }

                            ServerFunctions.importProduct(newProducts, marketFile);
                        } else if (line.equals("1")) { // Export
                            for (String s : marketFile) {
                                if (s.split(",")[1].equals(email)) {
                                    printWriter.println(s);
                                }
                            }
                            printWriter.println("<END>");
                        }
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // maybe a better way to do this
            synchronized (accounts) {
                synchronized (marketFile) {
                    synchronized (boughtFile) {
                        synchronized (shoppingCart) {
                            ServerFunctions.saveFile("accounts.txt", accounts);
                            ServerFunctions.saveFile("marketFile.txt", marketFile);
                            ServerFunctions.saveFile("boughtFile.txt", boughtFile);
                            ServerFunctions.saveFile("shoppingCart.txt", shoppingCart);
                        }
                    }
                }
            }
        } //If the socket is closed (user disconnects) then the thread is terminated
    }

    public static void viewItemServer(BufferedReader bufferedReader, PrintWriter printWriter, String userType,
                                      String email, Product product) throws IOException {

        String item = ClientFunctions.displayItem(product, marketFile);
        printWriter.println(item + "\n<END>");

        if (userType.equals("seller")) {
            String line = bufferedReader.readLine();

            switch (line) {
                case "edit" -> {
                    if (product.getSellerName().equals(email)) {
                        printWriter.println("true");
                    } else {
                        printWriter.println("false");
                        return;
                    }

                    String description = bufferedReader.readLine();

                    String quantity = bufferedReader.readLine();

                    String price = bufferedReader.readLine();

                    String sale = bufferedReader.readLine();

                    ClientFunctions.edit(product, description, quantity, price, sale, marketFile);
                }
                case "remove" -> {
                    synchronized (marketFile) {
                        int index = ServerFunctions.productIndex(marketFile, product.toString());
                        marketFile.remove(index);
                    }
                }
            }
        } else {
            String line = bufferedReader.readLine();

            switch (line) {
                case "review" -> {
                    line = bufferedReader.readLine();
                    ClientFunctions.addReview(product, line, marketFile);
                }
                case "buy" -> {
                    line = bufferedReader.readLine();
                    ServerFunctions.buyItem(email, product, Integer.parseInt(line), marketFile, boughtFile);
                }
                case "cart" -> {
                    line = bufferedReader.readLine();
                    CartFunctions.add(email, product, Integer.parseInt(line), marketFile, shoppingCart);
                }
            }
        }
    }
}
