import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class holds all the functionality and helper methods for managing customers and sellers in the marketplace. This
 * class utilizes a market, transaction, and shopping cart to handle everything from displaying products bought and sold
 * to file handling. This class also manages importing and exporting files from customers and sellers.
 *
 * @author Dylan, Nathanael, James, Vaibhav, Jordan
 * @version November 13, 2022
 */

public class UserInterface {
    private final File marketFile = new File("marketProducts.txt"); //file 1
    private final File boughtFile = new File("boughtFile.txt"); //file 2
    private final File shoppingCart = new File("shoppingCart.txt"); //file 4

    //UI Constructor
    public UserInterface() {
    }

    //rg
    public String[] getProductsInCart(String sellerName) {
        ArrayList<String> productsInCart = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(shoppingCart));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains(sellerName)) { //checking if the seller has a product in the customer's cart
                    String[] cart = line.split(";"); //getting list of all products in customer's cart

                    for (int i = 1; i < cart.length; i++) {
                        String[] item = cart[i].split(","); //splitting up the toString of each product

                        if (item[1].equalsIgnoreCase(sellerName)) { //checks if the sellerName of the product matches

                            if (productsInCart.contains(cart[i])) { //checks if the product is not unique
                                int index = productsInCart.indexOf(cart[i]);
                                int quantity = Integer.parseInt(productsInCart.get(index).split(",")[4]);
                                item[4] = String.valueOf((Integer.parseInt(item[4])) + quantity);
                                String edit = String.join(",", item); //sets quantity to the sum of quantities

                                productsInCart.set(index, edit); //replaces the product with the new toString
                            } else {
                                productsInCart.add(cart[i]);
                            }
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] returnProducts = new String[productsInCart.size()];
        for (int i = 0; i < returnProducts.length; i++) {
            returnProducts[i] = productsInCart.get(i);
        }

        return returnProducts; //will return an empty list if there are no products in customer carts
    }

    public void importExportMenu(Scanner scan, String userType, String username) {
        int choice;
        while (true) {
            System.out.println("Would you like to:");
            System.out.println("1. Export data");
            if (userType.equals("seller")) {
                System.out.println("2. Import data");
                System.out.println("3. Go back");
            } else {
                System.out.println("2. Go back");
            }

            int max = userType.equals("seller") ? 3 : 2;

            try {
                choice = Integer.parseInt(scan.nextLine());
                if (1 <= choice && choice <= max) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
            System.out.println("Invalid input!");
        }

        String fileName = null;
        while (fileName == null || fileName.length() == 0) {
            System.out.println("Please provide a filename.");
            fileName = scan.nextLine();
        }

        if (choice == 1) {
            exportFile(userType, fileName, username);
        } else if (choice == 2 && userType.equals("seller")) {
            importProducts(fileName);
        }
    }

    //productName, sellerName, store, desc, qt, price, sale, reviews, order limit.
    public void importProducts(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> importProducts = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                importProducts.add(line);
                line = reader.readLine();
            }
            reader.close();

            reader = new BufferedReader(new FileReader(marketFile));
            ArrayList<String> rewriteFile = new ArrayList<>();
            line = reader.readLine();
            while (line != null) {
                //If seller is already selling the product, just increase the market quantity by the imported quantity
                if (importProducts.contains(line)) {
                    int index = importProducts.indexOf(line);
                    int quantity = Integer.parseInt(importProducts.get(index).split(",")[4]);
                    String[] editLine = line.split(",");
                    editLine[4] = String.valueOf((Integer.parseInt(editLine[4])) + quantity);
                    line = String.join(",", editLine);

                    importProducts.remove(index);
                }
                rewriteFile.add(line);
                line = reader.readLine();
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(marketFile));

            for (String i : rewriteFile) {
                writer.println(i);
            }

            for (String i : importProducts) {
                writer.println(i);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //productName, sellerName, store, desc, qt, price, sale, reviews, order limit, buyerName
    public void exportFile(String userType, String fileName, String username) {
        try {
            if (userType.equalsIgnoreCase("customer")) {
                BufferedReader reader = new BufferedReader(new FileReader(boughtFile));
                PrintWriter writer = new PrintWriter(new FileWriter(fileName));
                ArrayList<String> boughtProducts = new ArrayList<>();
                String line = reader.readLine();
                while (line != null) {
                    var fields = line.split(",");
                    if (fields[fields.length - 1].equalsIgnoreCase(username)) {
                        boughtProducts.add(line);
                    }
                    line = reader.readLine();

                }
                reader.close();

                for (String i : boughtProducts) {
                    writer.println(i);
                }
                writer.close();
            } else if (userType.equalsIgnoreCase("seller")) {
                BufferedReader reader = new BufferedReader(new FileReader(marketFile));
                PrintWriter writer = new PrintWriter(new FileWriter(fileName));
                ArrayList<String> marketProducts = new ArrayList<>();
                String line = reader.readLine();

                while (line != null) {
                    if (line.split(",")[1].equalsIgnoreCase(username)) {
                        marketProducts.add(line);
                    }
                    line = reader.readLine();

                }
                reader.close();

                for (String i : marketProducts) {
                    writer.println(i);
                }
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Adds a product to the market file when the customer creates a new one
    public void addProduct(Product p) {
        String toWrite = p.toString();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(marketFile, true));
            bw.write(toWrite);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Removes a product from the market file
    public void removeProduct(Product p) {
        ArrayList<String> fileContents = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(marketFile));
            String line = br.readLine();
            while (line != null) {
                String[] lineContents = line.split(",");
                if (!lineContents[0].equals(p.getName()) || !lineContents[1].equals(p.getSellerName())
                        || !lineContents[2].equals(p.getStore())) {
                    fileContents.add(line);
                }
                line = br.readLine();
            }
            br.close();

            FileWriter fw = new FileWriter(marketFile, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < fileContents.size(); i++) {
                bw.write(fileContents.get(i));
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(Product p) {
        String seller = p.getSellerName();
        String name = p.getName();
        String store = p.getStore();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(marketFile));
            ArrayList<String> writeFile = new ArrayList<>();
            String line = reader.readLine();

            while (line != null) {
                String[] item = line.split(",");
                if (item[0].equalsIgnoreCase(name) &&
                        item[2].equalsIgnoreCase(store) &&
                        item[1].equalsIgnoreCase(seller)) {
                    line = p.toString();
                }
                writeFile.add(line);
                line = reader.readLine();
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(marketFile));
            for (String i : writeFile) {
                writer.println(i);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buy(Product p, String buyerName) {
        String seller = p.getSellerName();
        String name = p.getName();
        String store = p.getStore();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(marketFile));
            String line = reader.readLine();
            ArrayList<String> writeFile = new ArrayList<>();
            while (line != null) {
                String[] item = line.split(",");
                if (item[0].equalsIgnoreCase(name) &&
                        item[2].equalsIgnoreCase(store) &&
                        item[1].equalsIgnoreCase(seller)) {
                    int remaining = Integer.parseInt(item[4]) - p.getQuantity();
                    if (remaining > 0) {
                        item[4] = String.valueOf(remaining);
                        line = String.join(",", item);
                    } else if (remaining == 0) {
                        // ignore invalid purchases
                        line = null;
                    }
                }
                if (line != null) {
                    writeFile.add(line);
                }
                line = reader.readLine();
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(marketFile));
            for (String i : writeFile) {
                writer.println(i);
            }
            writer.close();

            PrintWriter writer2 = new PrintWriter(new FileWriter(boughtFile, true));
            writer2.println(p + "," + buyerName);
            writer2.close();

            CustomerCart.remove(buyerName, p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String[] display() {
        try {
            return Files
                    .readAllLines(marketFile.toPath())
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //returns a sorted list by price of the current contents in market file
    public String[] sortPrice() { //sorts by price or string
        ArrayList<String> itemsArray = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(marketFile));
            String line = br.readLine();
            while (line != null) {
                itemsArray.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemsArray.stream().sorted((a, b) -> {
            var aFields = a.split(",");
            var bFields = b.split(",");
            var aPrice = Double.parseDouble(aFields[5]) * (1 - Double.parseDouble(aFields[6]));
            var bPrice = Double.parseDouble(bFields[5]) * (1 - Double.parseDouble(bFields[6]));
            return (int) Math.signum(bPrice - aPrice);
        } ).toArray(String[]::new);
    }

    //returns a sorted list by quantity of the current contents in market file
    public String[] sortQuantity() {
        ArrayList<String> itemsArray = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(marketFile));
            String line = br.readLine();
            while (line != null) {
                itemsArray.add(line);
                line = br.readLine();
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemsArray.stream()
                .sorted((a, b) -> Integer.parseInt(b.split(",")[4]) -
                        Integer.parseInt(a.split(",")[4]))
                .toArray(String[]::new);
    }

    public String[] search(String query) {
        return Arrays.stream(display())
                .filter(l -> l.contains(query))
                .toArray(String[]::new);
    }

    public String[] sales() {
        ArrayList<String> tempProductsBought = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(boughtFile));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                tempProductsBought.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] productsBought = new String[tempProductsBought.size()];
        for (int i = 0; i < productsBought.length; i++) {
            productsBought[i] = tempProductsBought.get(i);
        }
        return productsBought;
    }

    public double calculateIncome(String sellerName) {
        double income = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(boughtFile));
            String line = br.readLine();
            while (line != null) {
                String[] lineContents = line.split(",");
                if (lineContents[1].equals(sellerName)) {
                    income += (
                            Double.parseDouble(lineContents[4])
                                    * Double.parseDouble(lineContents[5])
                                    * (1 - Double.parseDouble(lineContents[6])));
                }
                line = br.readLine();
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return income;
    }

    public void sellerSales(String sellerName) {
        String[] sales = sales();

        ArrayList<String> uniqueStores = new ArrayList<>();
        for (int i = 0; i < sales.length; i++) {
            if (!uniqueStores.contains(sales[i].split(",")[2]) && sales[i].split(",")[1].equals(sellerName)) {
                uniqueStores.add(sales[i].split(",")[2]);
            }
        }
        if (uniqueStores.size() == 0) {
            System.out.println("Your stores did not sell any books yet.");
        } else {
            for (int a = 0; a < uniqueStores.size(); a++) {
                System.out.println("For " + uniqueStores.get(a) + " here are the sales & revenue: ");
                ArrayList<String> salesBySeller = new ArrayList<>();
                for (int i = 0; i < sales.length; i++) {
                    if (sales[i].split(",")[1].equals(sellerName) &&
                            sales[i].split(",")[2].equals(uniqueStores.get(a))) {
                        salesBySeller.add(sales[i]);
                    }
                }
                String[] returner = new String[salesBySeller.size()];
                for (int j = 0; j < salesBySeller.size(); j++) {
                    String[] sale = salesBySeller.get(j).split(",");
                    System.out.println("Book Name: " + sale[0]);
                    System.out.println("Store: " + sale[2]);
                    System.out.println("Book description: " + sale[3]);
                    System.out.println("Price: " + sale[5]);
                    System.out.println("Discount %: " + sale[6]);
                    System.out.println("Buyer: " + sale[9]);
                    returner[j] = String.format(
                            "Revenue from this sale: %.2f",
                            Float.parseFloat(sale[4]) *
                                    Float.parseFloat(sale[5]) *
                                    (1 - (Float.parseFloat(sale[6]) / 100)));
                    System.out.println(returner[j]);
                    System.out.println("------");
                }
            }
        }
    }

    public void sellerStatistics(String sellerName, int sort) {
        String[] sales = sales();

        ArrayList<String> uniqueStores = new ArrayList<>();
        for (int i = 0; i < sales.length; i++) {
            if (!uniqueStores.contains(sales[i].split(",")[2]) && sales[i].contains(sellerName)) {
                uniqueStores.add(sales[i].split(",")[2]);
            }
        }

        ArrayList<String> uniqueCustomers = new ArrayList<>();
        for (int i = 0; i < sales.length; i++) {
            if (!uniqueCustomers.contains(sales[i].split(",")[sales[i].split(",").length - 1]) &&
                    sales[i].contains(sellerName)) {
                uniqueCustomers.add(sales[i].split(",")[sales[i].split(",").length - 1]);
            }
        }

        ArrayList<String> uniqueProducts = new ArrayList<>();
        for (int i = 0; i < sales.length; i++) {
            if (!uniqueProducts.contains(sales[i].split(",")[0]) && sales[i].contains(sellerName)) {
                uniqueProducts.add(sales[i].split(",")[0]);
            }
        }

        if (uniqueStores.size() == 0) {
            System.out.println("Your stores did not sell any books yet.");
        } else {

            for (int a = 0; a < uniqueStores.size(); a++) {
                System.out.println("For store: " + uniqueStores.get(a));

                ArrayList<String> customerSalesPerStore = new ArrayList<>();
                ArrayList<Integer> boughtCount = new ArrayList<>();
                for (int b = 0; b < uniqueCustomers.size(); b++) {
                    int customerBoughtCount = 0;
                    for (int c = 0; c < sales.length; c++) {
                        if ((sales[c].contains(sellerName)) && (sales[c].contains(uniqueStores.get(a))) &&
                                (sales[c].contains(uniqueCustomers.get(b)))) {
                            customerBoughtCount += 1;
                        }
                    }

                    if (customerBoughtCount != 0) {
                        customerSalesPerStore.add(uniqueCustomers.get(b) + " bought " + customerBoughtCount + " items");
                        boughtCount.add(customerBoughtCount);
                    }

                }
                Integer[] boughtCountList = new Integer[boughtCount.size()];
                for (int x = 0; x < boughtCount.size(); x++) {
                    boughtCountList[x] = boughtCount.get(x);
                }
                Arrays.sort(boughtCountList);

                String[] customerList = new String[customerSalesPerStore.size()];
                ArrayList<String> sortedCustomerList = new ArrayList<>();

                for (int u = 0; u < boughtCountList.length; u++) {
                    for (int i = 0; i < customerSalesPerStore.size(); i++) {
                        customerList[i] = customerSalesPerStore.get(i);
                        if ((Integer.parseInt(customerList[i].split(" ")[2]) == boughtCountList[u]) &&
                                !sortedCustomerList.contains(customerList[i])) {
                            sortedCustomerList.add(customerList[i]);
                        }
                    }
                }

                ArrayList<String> productSalesPerStore = new ArrayList<>();
                ArrayList<Integer> productCount = new ArrayList<>();
                for (int d = 0; d < uniqueProducts.size(); d++) {

                    int productBoughtCount = 0;
                    for (int e = 0; e < sales.length; e++) {
                        if ((sales[e].contains(sellerName)) && (sales[e].contains(uniqueStores.get(a))) &&
                                (sales[e].contains(uniqueProducts.get(d)))) {
                            productBoughtCount += 1;
                        }
                    }
                    if (productBoughtCount != 0) {
                        productSalesPerStore.add(uniqueProducts.get(d) + " was bought " +
                                productBoughtCount + " times");
                        productCount.add(productBoughtCount);
                    }
                }

                Integer[] productCountList = new Integer[productCount.size()];
                for (int y = 0; y < productCount.size(); y++) {
                    productCountList[y] = productCount.get(y);
                }
                Arrays.sort(productCountList);
                String[] productList = new String[productSalesPerStore.size()];
                ArrayList<String> sortedProductList = new ArrayList<>();


                for (int t = 0; t < productCountList.length; t++) {
                    for (int j = 0; j < productSalesPerStore.size(); j++) {
                        productList[j] = productSalesPerStore.get(j);
                        if ((Integer.parseInt(productList[j].split(" ")[3]) == productCountList[t]) &&
                                !(sortedProductList.contains(productList[j]))) {
                            sortedProductList.add(productList[j]);
                        }
                    }
                }

                if (sort == 1) {
                    for (int m = 0; m < sortedCustomerList.size(); m++) {
                        System.out.println(sortedCustomerList.get(m));
                    }

                    for (int l = 0; l < sortedProductList.size(); l++) {
                        System.out.println(sortedProductList.get(l));
                    }
                } else {
                    System.out.println(Arrays.toString(customerList));
                    System.out.println(Arrays.toString(productList));
                }
            }
        }
    }

    public String[] customerStoreStatistics(String username) {
        ArrayList<String> tempProductsBought = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(boughtFile));

            String line = reader.readLine();
            while (line != null) {
                tempProductsBought.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var storeProductMap = new HashMap<String, Integer[]>();
        for (int i = 0; i < tempProductsBought.size(); i++) {
            String[] productData = tempProductsBought.get(i).split(",");
            var quantity = Integer.parseInt(productData[4]);
            if (!storeProductMap.containsKey(productData[2])) {
                storeProductMap.put(productData[2], new Integer[]{0, 0});
            }
            var arr = storeProductMap.get(productData[2]);
            if (productData[productData.length - 1].equals(username)) {
                arr[0] += quantity;
            }
            arr[1] += quantity;
        }

        var result = storeProductMap.keySet().stream()
                .map(l -> {
                    var arr = storeProductMap.get(l);
                    return String.format("%s,%d,%d", l, arr[0], arr[1]);
                } )
                .toArray(String[]::new);

        return result;
    }

    public String[] sortStoresByProductsSold(boolean onlyConsumersBought, String username) {
        var information = customerStoreStatistics(username);

        var sortingField = onlyConsumersBought ? 1 : 2;

        var result = Arrays.stream(information)
                .sorted((a, b) -> {
                    var aFields = a.split(",");
                    var bFields = b.split(",");
                    return Integer.parseInt(bFields[sortingField]) - Integer.parseInt(aFields[sortingField]);
                } )
                .toArray(String[]::new);

        return result;
    }

    public void readWriteFile(String method, String accountName,
                              String newusername, String newpasswd, String newtype) {
        ArrayList<String> printLines = new ArrayList<>();
        String x = newusername + "," + newpasswd + "," + newtype;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (accountName.equals(line.split(",")[0]) && method.equalsIgnoreCase("edit")) {
                    line = x;
                } else if (accountName.equals(line.split(",")[0])
                        && method.equalsIgnoreCase("delete")) {
                    line = null;
                }
                if (line != null) {
                    printLines.add(line);
                }
                line = reader.readLine();
            }

            PrintWriter writer = new PrintWriter(new FileWriter("accounts.txt"));
            for (String i : printLines) {
                writer.println(i);
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserName(String username, String accfilename) {
        boolean verify = true;
        try {
            File f = new File(accfilename);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.split(",")[0].contains(username)) {
                    verify = false;
                }
                line = bfr.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verify;
    }

    public void showArray(String[] products, int quantityStatement) {
        for (int i = 0; i < products.length; i++) {
            System.out.println("--------");
            show(products[i], i, quantityStatement);
        }
        System.out.println("--------");
    }

    // Prints out a product in a nice way. Right now, just printing out data, need to do more.
    // Takes in the product string and its index in the list. If the num is -1, it prints it without the index.

    public void show(String product, int num, int quantityStatement) {
        String[] p = product.split(",");
        if (num >= 0) {
            System.out.printf("%d. " + p[0] + "\n", num + 1);
        } else {
            System.out.println(p[0]);
        }
        float cost = Float.parseFloat(p[5]);
        float sale = Float.parseFloat(p[6]);
        if (sale > 0) {
            System.out.printf("$%.2f - there is a %d%% sale!\n", cost * (1 - sale), (int) (sale * 100));
        } else {
            System.out.printf("$%.2f\n", cost);
        }
        System.out.println(p[3]);
        System.out.println("Store: " + p[2]);
        System.out.println("Seller: " + p[1]);
        if (quantityStatement == 1) {
            System.out.println("You have " + p[4] + " in your cart.");
        } else if (quantityStatement == 2) {
            System.out.println("You have bought " + p[4] + " books.");
        } else {
            System.out.println("There are " + p[4] + " left in stock.");
        }
    }

    public boolean viewItemSeller(Scanner scan, String[] products, String currentUS) {
        boolean exit = false;
        String input;
        int index = 0;
        do {
            try {
                System.out.println("Which book would you like to view?");
                input = scan.nextLine();
                index = Integer.parseInt(input) - 1;
                if (index < 0 || index >= products.length) {
                    System.out.println("Please give a valid book number!");
                    input = null;
                }
            } catch (Exception e) {
                System.out.println("Please give a valid book number!");
                input = null;
            }
        } while (input == null);
        System.out.println("You are looking at...");
        System.out.println("------");
        show(products[index], -1, 3);
        System.out.println("------");
        Product p = buildProduct(products, index);
        System.out.println("Would you like to:\n1. View reviews\n2. Edit Product\n3. Remove Book\n4. Go back");
        input = scan.nextLine();
        if (input.equals("1")) {
            if (p.getReviewCount() == 0) {
                System.out.println("This item has no reviews!");
            } else {
                for (int i = 0; i < p.getReviewCount(); i++) {
                    System.out.println("-----");
                    System.out.println(p.getReview(i));
                }
            }
        } else if (input.equals("2")) {
            exit = true;

            if (!p.getSellerName().equals(currentUS)) {
                System.out.println("You can only edit your own books!");
            } else {

                String name = p.getName();
                String store = p.getStore();

                String desc = null;
                while (desc == null) {
                    System.out.println("Describe the book: ");
                    desc = scan.nextLine();
                    if (desc.contains(",")) {
                        System.out.println("A book description cannot include any commas! " +
                                "Please enter a new description.");
                        desc = null;
                    }
                }

                boolean work = true;
                int qt = 0;
                do {
                    System.out.println("Enter the quantity: ");
                    try {
                        qt = Integer.parseInt(scan.nextLine());
                        work = false;
                    } catch (Exception e) {
                        System.out.println("Invalid Input!");
                    }
                } while (work);

                work = true;
                float price = 0;
                do {
                    System.out.println("Enter the price of this book: ");
                    try {
                        price = Float.parseFloat(scan.nextLine());
                        work = false;
                    } catch (Exception e) {
                        System.out.println("Invalid Input!");
                    }
                } while (work);

                work = true;
                float sale = 0;
                do {
                    System.out.println("Enter the sale percentage: ");
                    try {
                        sale = Float.parseFloat(scan.nextLine());
                        work = false;
                    } catch (Exception e) {
                        System.out.println("Invalid Input!");
                    }
                    if (sale > 1 || sale < 0) {
                        System.out.println("An book's sale percentage has to be between 0 and 1!");
                        work = true;
                    }
                } while (work);

                Product ep = new Product(name, currentUS, store, desc, qt, price, sale,
                        new ArrayList<>());

                edit(ep);
            }
        } else if (input.equals("3")) {
            exit = true;
            Product ep2 = buildProduct(products, index);
            if (ep2.getSellerName().equals(currentUS)) {
                removeProduct(ep2);
            } else {
                System.out.println("You can only remove your own books from the marketplace!");
            }
        } else {
            exit = true;
        }
        return exit;
    }

    public Product buildProduct(String[] statsDisplay, int index) {
        String[] itemData = statsDisplay[index].split(",");

        ArrayList<String> reviews = new ArrayList<>();
        for (int i = 7; i < itemData.length; i++) {
            reviews.add(itemData[i]);
        }

        return new Product(itemData[0], itemData[1], itemData[2], itemData[3], Integer.parseInt(itemData[4]),
                Float.parseFloat(itemData[5]), Float.parseFloat(itemData[6]), reviews);
    }

    public void checkFileCreated(File f) {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean viewItemCustomer(Scanner scan, String[] products, UserInterface ui, String buyer) {
        boolean exit = false;
        int option;
        int index;
        String input;
        while (true) {
            try {
                System.out.println("Which book would you like to view?");
                option = scan.nextInt();
                scan.nextLine();
                // previously 1 was not being subtracted here, but the user input is indexed starting from 1
                index = option - 1;
                if (0 <= index && index < products.length) {
                    input = String.valueOf(option);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
        System.out.println("------");
        ui.show(products[index], -1, 3);
        System.out.println("------");
        Product p = ui.buildProduct(products, index);
        System.out.println("Would you like to:\n1. View reviews\n2. Add review" +
                "\n3. Buy book\n4. Add book to cart\n5. Go back");
        input = scan.nextLine();
        if (input.equals("1")) {
            if (p.getReviewCount() == 0) {
                System.out.println("This item has no reviews!");
            } else {
                for (int i = 0; i < p.getReviewCount(); i++) {
                    System.out.println("-----");
                    System.out.println(p.getReview(i));
                }
            }
        } else if (input.equals("2")) {
            input = null;
            while (input == null) {
                System.out.println("Write your review below:");
                input = scan.nextLine();
                if (input.contains(",")) {
                    System.out.println("A book review cannot include any commas! " +
                            "Please enter a new review.");
                    input = null;
                }
            }
            ui.removeProduct(p);
            p.makeReview(input);
            products[index] = p.toString();
            ui.addProduct(p);
            System.out.println("Thank you for reviewing this book!");
        } else if (input.equals("3")) {
            boolean again = true;
            int amt = 0;

            do {
                System.out.println("How much would you like to buy?");
                try {
                    amt = Integer.parseInt(scan.nextLine());
                    again = false;
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                }
            } while (again);

            if (amt <= p.getQuantity()) {
                p.setQuantity(amt);
                ui.buy(p, buyer);
            } else {
                System.out.println("There is not enough of this book in stock!");
            }

            exit = true;
        } else if (input.equals("4")) {
            while (true) {
                boolean again = true;
                int amt = 0;
                do {
                    System.out.println("How much would you like to add to your cart?");
                    try {
                        amt = Integer.parseInt(scan.nextLine());
                        again = false;
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                } while (again);

                if (amt <= p.getQuantity()) {
                    p.setQuantity(amt);
                    CustomerCart.add(buyer, p); //current us taken in as input
                    break;
                } else {
                    System.out.println("There is not enough of this book in stock!");
                    System.out.println("Would you like to try again?\n1. Yes\n2. No");
                    try {
                        int output = Integer.parseInt(scan.nextLine());
                        if (output == 2) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                }
            }

            exit = true;
        } else if (input.equals("5")) {
            exit = true;
        }
        return exit;
    }

    public void sortMenu(String currentUS, String[] sortedProducts, UserInterface ui, Scanner scan) {
        ui.showArray(sortedProducts, 3);

        System.out.println("Would you like to:\n1. View book from sort\n2. Go back to the bookstore");
        String input = scan.nextLine();

        if (input.equals("1")) {
            boolean exit;
            do {
                exit = ui.viewItemCustomer(scan, sortedProducts, ui, currentUS);
            } while (!exit);
        }
    }

    public String[] getPurchaseHistory(String username) {
        String[] itemsBought = sales();
        ArrayList<String> tempItemsBoughtByUser = new ArrayList<>();

        for (int i = 0; i < itemsBought.length; i++) {
            String[] tempData = itemsBought[i].split(",");
            if (tempData[tempData.length - 1].equals(username)) {
                tempItemsBoughtByUser.add(itemsBought[i]);
            }
        }

        String[] purchaseHistory = new String[tempItemsBoughtByUser.size()];
        for (int i = 0; i < purchaseHistory.length; i++) {
            purchaseHistory[i] = tempItemsBoughtByUser.get(i);
        }

        return purchaseHistory;
    }
}
