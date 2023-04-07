import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class combines all the functionality of the files, methods, and classes into a menu that the user can navigate
 * by creating and account as a customer or seller. This program allows the user to manipulate their account details,
 * product listings, shopping cart, etc. as they so choose.
 *
 * @author Dylan, Nathanael, James, Vaibhav, Jordan
 * @version November 13, 2022
 */


public class Market {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        File accountsFile = new File("accounts.txt");
        ui.checkFileCreated(accountsFile);
        File marketFile = new File("marketProducts.txt");
        ui.checkFileCreated(marketFile);
        File boughtFile = new File("boughtFile.txt");
        ui.checkFileCreated(boughtFile);
        File shoppingCart = new File("shoppingCart.txt");
        ui.checkFileCreated(shoppingCart);

        Scanner scan = new Scanner(System.in);
        String userType = null;
        String currentUS = "";
        while (true) {
            System.out.println("Do you have an account? \n1. Yes \n2. No");
            int acc;
            try {
                acc = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                continue;
            }
            boolean verify = false;

            if (acc == 1) {
                System.out.println("Enter your username: ");
                String un = scan.nextLine();
                System.out.println("Enter your password: ");
                String passwd = scan.nextLine();
                try {
                    FileReader fr = new FileReader(accountsFile);
                    BufferedReader bfr = new BufferedReader(fr);
                    String line = bfr.readLine();
                    while (line != null) {
                        if (line.contains(un)) {
                            String actpasswd = line.split(",")[1];

                            if (passwd.equals(actpasswd)) {
                                userType = line.split(",")[2];
                                currentUS = line.split(",")[0];
                                verify = true;
                            }
                        }
                        line = bfr.readLine();
                    }

                    if (!verify) {
                        System.out.println("Failed to log you in!\n" +
                                "Make sure you enter the correct login information. ");
                        continue;
                    } else {
                        System.out.println("------");
                        System.out.println("Welcome to the Bookstore");
                        System.out.println("------");
                    }
                    int accOpt = -1;
                    while (accOpt == -1) {
                        try {
                            System.out.println("What would you like to do?\n1. Edit your account" +
                                    "\n2. Delete your account\n3. Continue");
                            accOpt = Integer.parseInt(scan.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid input!");
                            accOpt = -1;
                        }
                    }
                    if (accOpt == 1) {
                        while (true) {
                            System.out.println("Enter new username: ");
                            String newUserName = scan.nextLine();
                            if (!ui.checkUserName(newUserName, "accounts.txt") &&
                                    !currentUS.equals(newUserName)) {
                                System.out.println("That user name is already in username!");
                                continue;
                            }
                            System.out.println("Enter new password: ");
                            String newPassWd = scan.nextLine();

                            String newType = "";
                            while (!newType.equals("customer") && !newType.equals("seller")) {
                                System.out.println("Are you a customer or a seller? ");
                                newType = scan.nextLine();
                            }
                            ui.readWriteFile("edit", currentUS, newUserName, newPassWd, newType);
                            break;
                        }
                    } else if (accOpt == 2) {
                        ui.readWriteFile("delete", currentUS, "", "", "");
                        System.out.println("Your account was deleted!");
                    } else if (accOpt == 3) {
                        break;
                    } else {
                        System.out.println("Invalid input!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (acc == 2) {

                System.out.println("Enter your new user name: ");
                String newUN = scan.nextLine();
                //this was the line i was talking about
                currentUS = newUN;

                if (!ui.checkUserName(newUN, String.valueOf(accountsFile))) {
                    System.out.println("You already have an account!");
                    continue;
                }

                System.out.println("Enter your new password: ");
                String newPW = scan.nextLine();

                userType = "";
                while (!userType.equals("customer") && !userType.equals("seller")) {
                    System.out.println("Are you a customer or a seller? ");
                    userType = scan.nextLine();
                    if (!userType.equals("customer") && !userType.equals("seller")) {
                        System.out.println("Invalid Input");
                    }
                }

                String account1 = newUN + "," + newPW + "," + userType;

                try {
                    FileOutputStream fos = new FileOutputStream(accountsFile, true);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.println(account1);
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }


        if (userType.equals("customer")) {
            String input = "";
            while (!input.equals("8")) {
                //System.out.println("Welcome to the marketplace!");
                String[] products = ui.display();
                for (int i = 0; i < products.length; i++) {
                    System.out.println("------");
                    ui.show(products[i], i, 3);
                }
                System.out.println("------");
                System.out.println("What would you like to do?\n1. View and buy a book\n2. Search the bookstore\n" +
                        "3. Bookstore sort \n4. Go to cart\n5. View store sales statistics\n" +
                        "6. View purchase history\n7. Export purchase history\n8. Log out");
                input = scan.nextLine();
                if (input.equals("1")) {
                    if (products.length > 0) {
                        boolean exit = false;
                        do {
                            exit = ui.viewItemCustomer(scan, products, ui, currentUS);
                        } while (!exit);
                    } else {
                        System.out.println("Sorry, there are no products for you to view!");
                    }
                } else if (input.equals("2")) {
                    boolean search = true;
                    do {
                        System.out.println("Enter your search keyword: ");
                        String searchQuery = scan.nextLine();

                        System.out.println("Results:");
                        String[] results = ui.search(searchQuery);

                        if (results == null || results.length == 0) {
                            System.out.println("No books matched your search.");
                        }

                        for (int i = 0; i < results.length; i++) {

                            System.out.println("------");
                            ui.show(results[i], i, 3);
                        }
                        System.out.println("------");


                        System.out.println("Would you like to:\n1. View item from search\n" +
                                "2. Search again\n3. Go back");
                        input = scan.nextLine();

                        if (input.equals("1")) {
                            boolean exit;
                            do {
                                exit = ui.viewItemCustomer(scan, results, ui, currentUS);
                            } while (!exit);
                        } else if (input.equals("3")) {
                            search = false;
                        }
                    } while (search);
                } else if (input.equals("3")) {
                    // May want to organize this menu more.
                    System.out.println("How would you like to sort the books or stores?\n1. Sort books by Price" +
                            "\n2. Sort books by Quantity");

                    int sortBy;
                    while (true) {
                        try {
                            sortBy = Integer.parseInt(scan.nextLine());
                            if (1 <= sortBy && sortBy <= 4) {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                        }
                        System.out.println("Invalid input!");
                    }

                    String[] sortedProducts;
                    if (sortBy == 1) {
                        sortedProducts = ui.sortPrice();
                        ui.sortMenu(currentUS, sortedProducts, ui, scan);
                    } else if (sortBy == 2) {
                        sortedProducts = ui.sortQuantity();
                        ui.sortMenu(currentUS, sortedProducts, ui, scan);
                    }
                } else if (input.equals("4")) {
                    while (true) {
                        String[] cartItems = CustomerCart.show(currentUS);
                        if (cartItems == null) {
                            System.out.println("Your cart is empty");
                            break;
                        } else {
                            System.out.println("Here is your cart:");
                        }
                        ui.showArray(cartItems, 1);
                        System.out.println("What would you like to do?\n1. Remove book\n2. Buy books\n3. Go back");
                        input = scan.nextLine();
                        if (input.equals("1")) {
                            System.out.println("Which book would you like to remove?");
                            int removeItemIndex = Integer.parseInt(scan.nextLine()) - 1;
                            Product p = ui.buildProduct(cartItems, removeItemIndex);
                            CustomerCart.remove(currentUS, p);
                        } else if (input.equals("2")) {
                            for (int i = 0; i < cartItems.length; i++) {
                                Product p = ui.buildProduct(cartItems, i);
                                ui.buy(p, currentUS);
                            }
                            // leave cart because it will be empty
                            break;
                        } else if (input.equals("3")) {
                            break;
                        }
                    }
                } else if (input.equals("5")) {
                    var productsSold = ui.customerStoreStatistics(currentUS);
                    while (true) {
                        if (productsSold.length > 0) {
                            for (int i = 0; i < productsSold.length; i++) {
                                var line = productsSold[i].split(",");
                                System.out.printf(
                                        "%d. %s\tYou have bought: %s\tOut of %s Total sales\n",
                                        i + 1, line[0], line[1], line[2]);
                            }
                        } else {
                            System.out.println("It appears there are no stores open!");
                            break;
                        }

                        System.out.println("Would you like to:");
                        System.out.println("1. Sort by Your purchases");
                        System.out.println("2. Sort by Total purchases");
                        System.out.println("3. Exit");
                        int choice;
                        while (true) {
                            try {
                                choice = Integer.parseInt(scan.nextLine());
                                if (1 <= choice && choice <= 3) {
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid input!");
                            }
                            System.out.println("Invalid input!");
                        }
                        if (choice == 1 || choice == 2) {
                            productsSold = ui.sortStoresByProductsSold(choice == 1, currentUS);
                        } else {
                            break;
                        }
                    }
                } else if (input.equals("6")) {
                    System.out.println("You have bought: ");
                    String[] purchaseHistory = ui.getPurchaseHistory(currentUS);
                    for (int i = 0; i < purchaseHistory.length; i++) {
                        var purchaseFields = purchaseHistory[i].split(",");
                        var product = Arrays.copyOfRange(purchaseFields, 0, purchaseFields.length - 1);
                        purchaseHistory[i] = String.join(",", product);
                    }
                    ui.showArray(purchaseHistory, 2);
                    System.out.println("Press enter to continue.");
                    scan.nextLine();
                } else if (input.equals("7")) {
                    ui.importExportMenu(scan, "customer", currentUS);
                }
            }
        } else if (userType.equals("seller")) {
            while (true) {
                System.out.println("What would you like to do?\n1. View bookstore\n2. Sell a new book" +
                        "\n3. Sort Books\n4. Search for a book\n5. See your books in customer carts" +
                        "\n6. Calculate Income\n7. Show sales by Stores\n" +
                        "8. View statistics for each of your stores\n9. Import/export sales\n10.Log out ");
                int opt;
                try {
                    opt = Integer.parseInt(scan.nextLine());
                } catch (Exception e) {
                    opt = 0;
                    System.out.println("Invalid Input");
                }

                if (opt == 1) {
                    String[] statsDisplay = ui.display();
                    if (statsDisplay.length == 0) {
                        System.out.println("There are no books available!");
                        break;
                    }
                    for (int i = 0; i < statsDisplay.length; i++) {
                        System.out.println("------");
                        ui.show(statsDisplay[i], i, 3);
                    }
                    System.out.println("------");
                    boolean exit;
                    do {
                        exit = ui.viewItemSeller(scan, statsDisplay, currentUS);
                    } while (!exit);

                } else if (opt == 2) {
                    String name = null;
                    String store = null;
                    String desc = null;

                    while (name == null) {
                        System.out.println("Enter the name of the book: ");
                        name = scan.nextLine();
                        if (name.contains(",")) {
                            System.out.println("A book name cannot include any commas! " +
                                    "Please enter a new name.");
                            name = null;
                        }
                    }
                    while (store == null) {
                        System.out.println("Enter the store name: ");
                        store = scan.nextLine();
                        if (store.contains(",")) {
                            System.out.println("A store name cannot include any commas! " +
                                    "Please enter a new store name.");
                            store = null;
                        }
                    }
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
                            System.out.println("An item's sale percentage has to be between 0 and 1!");
                            work = true;
                        }
                    } while (work);

                    Product newProduct = new Product(name, currentUS, store, desc, qt, price, sale,
                            new ArrayList<>());
                    ui.addProduct(newProduct);

                } else if (opt == 3) {
                    int sortOpt = 1;
                    do {
                        System.out.println("How would you like to sort books?\n1.Based on Price\n" +
                                "2. Based on Quantity");
                        try {
                            sortOpt = Integer.parseInt(scan.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid Input!");
                        }
                    } while (!(sortOpt == 1 || sortOpt == 2));
                    if (sortOpt == 1) {
                        ui.showArray((ui.sortPrice()), 3);
                    } else {
                        ui.showArray(ui.sortQuantity(), 3);
                    }
                } else if (opt == 4) {
                    boolean search = true;
                    do {
                        System.out.println("Enter your search keyword: ");
                        String searchQuery = scan.nextLine();

                        System.out.println("Results: ");
                        String[] results = ui.search(searchQuery);

                        ui.showArray(results, 3);

                        System.out.println("Would you like to:\n1. View book from search\n2. Search again\n3. Go back");
                        String input = scan.nextLine();

                        if (input.equals("1")) {
                            boolean exit;
                            do {
                                exit = ui.viewItemSeller(scan, results, currentUS);
                            } while (!exit);
                        } else if (input.equals("3")) {
                            search = false;
                        }
                    } while (search);
                } else if (opt == 5) {
                    String[] productsInCarts = ui.getProductsInCart(currentUS);
                    if (productsInCarts.length == 0) {
                        System.out.println("None of your books are in customer carts");
                    } else {
                        for (String i : productsInCarts) {
                            String[] productInfo = i.split(",");

                            System.out.printf("there are %s %s's \"%s\" in store %s selling for $%s " +
                                            "currently in customer shopping carts\n",
                                    productInfo[4], productInfo[0], productInfo[3], productInfo[2], productInfo[5]);
                            //quantity, productName, description, storeName, price
                        }
                    }

                } else if (opt == 6) {
                    System.out.printf("%.2f\n", ui.calculateIncome(currentUS));
                } else if (opt == 7) {
                    ui.sellerSales(currentUS);
                } else if (opt == 8) {
                    int sort;
                    while (true) {
                        System.out.println("Do you want your stores' data to be sorted?\n1. Yes\n2. No");
                        sort = Integer.parseInt(scan.nextLine());
                        if ((sort != 1 && sort == 2) || (sort != 2 && sort == 1)) {
                            break;
                        }
                    }
                    ui.sellerStatistics(currentUS, sort);
                } else if (opt == 9) {
                    ui.importExportMenu(scan, "seller", currentUS);
                } else if (opt == 10) {
                    break;
                } else {
                    System.out.println("Enter a valid option");
                }
            }
        }
    }
}