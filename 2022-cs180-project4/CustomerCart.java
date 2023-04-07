import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class holds all the functionality and helper methods for managing the customer cart files. It handles displaying
 * a cart, adding products to a user's cart, and removing products from a user's cart.
 *
 * @author Dylan, Nathanael, James, Vaibhav, Jordan
 * @version November 13, 2022
 */

public class CustomerCart {
    private static File shoppingCart = new File("shoppingCart.txt");

    public CustomerCart() {
    }

    public static String[] show(String customer) {
        String[] customerCart = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(shoppingCart));
            String line = reader.readLine();
            while (line != null) {
                String[] userAndCart = line.split(";");
                if (userAndCart[0].equalsIgnoreCase(customer)) {
                    customerCart = Arrays.copyOfRange(userAndCart, 1, userAndCart.length);
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
            return customerCart;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void add(String customer, Product p) {
        var lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(shoppingCart))) {
            boolean foundCustomer = false;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userCart = line.split(";");
                if (userCart[0].equals(customer)) {
                    foundCustomer = true;
                    boolean foundProduct = false;
                    for (int i = 1; i < userCart.length; i++) {
                        String[] fields = userCart[i].split(",");
                        boolean match = (fields[0].equals(p.getName())
                                && fields[1].equals(p.getSellerName())
                                && fields[2].equals(p.getStore()));
                        if (match) {
                            foundProduct = true;
                        }
                    }
                    if (!foundProduct) {
                        line += ';' + p.toString();
                    }
                }
                lines.add(line);
            }
            if (!foundCustomer) {
                lines.add(customer + ";" + p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(shoppingCart))) {
            for (var line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void remove(String customer, Product p) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(shoppingCart));

            ArrayList<String> lines = new ArrayList<>();
            String edit = "";
            String line = reader.readLine();
            while (line != null) {
                // buyer name, productname; product
                String[] userCart = line.split(";");
                if (userCart[0].equalsIgnoreCase(customer)) {
                    String[] products = Arrays.copyOfRange(userCart, 1, userCart.length);
                    edit += customer;
                    for (String product : products) {
                        String[] fields = product.split(",");

                        boolean match = (fields[0].equals(p.getName())
                                && fields[1].equals(p.getSellerName())
                                && fields[2].equals(p.getStore()));
                        if (!match) {
                            edit = edit + ";" + product;
                        }
                    }
                    line = edit.substring(0, edit.length() - 1);
                }
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(shoppingCart));
            for (String i : lines) {
                writer.println(i);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
