import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class ClientFunctions {
    public ClientFunctions() {
    }

    public static String[] displayMarket(List<String> marketFile) {
        synchronized (marketFile) {
            int marketLen = marketFile.size();
            String[] displayMarket = new String[marketLen];

            for (int i = 0; i < marketLen; i++) {
                Product p = Product.fromString(marketFile.get(i));
                String item[] = marketFile.get(i).split(",");
                String x = "<------------------------------->\n" + "Item number: " + (i + 1)
                        + "\nBook Title: " + item[0] + "\n"
                        + "Seller: " + item[1] + "\n"
                        + "Store: " + item[2] + "\n"
                        + "Description: " + item[3] + "\n"
                        + "Quantity:  " + item[4] + "\n" + "Price: $"
                        + String.format(
                            "%.2f",
                            Double.parseDouble(item[5]) * (1-Double.parseDouble(item[6])))
                        + "\n" + "Discount percentage: " + Double.parseDouble(item[6])*100 + "%";
                displayMarket[i] = x;
            }
            return displayMarket;
        }
    }

    public static String[] displayPurchaseHistory(String email, ArrayList<String> boughtFile) {
        ArrayList<String> returnerList = new ArrayList<>();
        synchronized (boughtFile) {
            for (int i = 0; i < boughtFile.size(); i++) {
                String[] boughtFileListi = boughtFile.get(i).split(",");
                if (boughtFileListi[boughtFileListi.length - 1].equals(email)) {
                    String x = "<------------------------------->\n" + "Book Title: " + boughtFileListi[0] + "\n"
                            + "Seller: " + boughtFileListi[1] + "\n"
                            + "Store: " + boughtFileListi[2] + "\n"
                            + "Description: " + boughtFileListi[3] + "\n"
                            + "Quantity Bought: " + boughtFileListi[4] + "\n" + "Price: $"
                            + String.format(
                                "%.2f",
                                Double.parseDouble(boughtFileListi[5]) * (1-Double.parseDouble(boughtFileListi[6])))
                            + "\n" + "Discount percentage: " + Double.parseDouble(boughtFileListi[6])*100 + "%";
                    returnerList.add(x);
                } else if (boughtFileListi[1].equals(email)) {
                    String x = "<------------------------------->\n" + "Book Title: " + boughtFileListi[0] + "\n"
                            + "Buyer: " + boughtFileListi[boughtFileListi.length - 1] + "\n"
                            + "Store: " + boughtFileListi[2] + "\n"
                            + "Description: " + boughtFileListi[3] + "\n"
                            + "Quantity Bought: " + boughtFileListi[4] + "\n" + "Price: $"
                            + String.format(
                                "%.2f",
                                Double.parseDouble(boughtFileListi[5]) * (1-Double.parseDouble(boughtFileListi[6])))
                            + "\n" + "Discount percentage: " + Double.parseDouble(boughtFileListi[6])*100 + "%";
                    returnerList.add(x);
                }
            }
        }
        int returnerSize = returnerList.size();
        String[] returner = new String[returnerSize];
        for (int a = 0; a < returnerSize; a++) {
            returner[a] = returnerList.get(a);
        }
        return returner;
    }

    public static String displayItem(Product product, ArrayList<String> marketFile) {
        String displayItem = "";

        displayItem = "Book Title: " + product.getName() + "\n"
                + "Seller: " + product.getSellerName() + "\n"
                + "Store: " + product.getStore() + "\n"
                + "Description: " + product.getDescription() + "\n"
                + "Quantity Available: " + product.getQuantity() + "\n" + "Price: $"
                + String.format("%.2f", product.getPrice() * (1-product.getSale())) + "\n"
                + "Discount percentage: " + (product.getSale()*100) + "%\n"
                + "Here are the reviews:" + "\n";

        var reviews = product.getReviews();
        if (reviews.size() != 0) {
            for (int i = 0; i < reviews.size(); i++) {
                displayItem += "Review #" + (i + 1) + "\n" + reviews.get(i) + "\n";
            }
        } else {
            displayItem += "There are no reviews yet!";
        }
        return displayItem;
    }

    public static void edit(Product product, String description, String quantity, String price, String salePercentage,
                            ArrayList<String> marketFile) {
        int index = ServerFunctions.productIndex(marketFile, product.toString());
        String[] item = marketFile.get(index).split(",");
        String updatedData = item[0] + "," + item[1] + "," + item[2] + ","  + description + ","  + quantity + ","
                + String.format("%.2f", price)  + "," + salePercentage + ",";
        int reviewCount = (item.length - 7);
        if (reviewCount != 0) {
            for (int j = 0; j < reviewCount; j++) {
                updatedData += item[7+j] + ",";
            }
        }
        marketFile.set(index, updatedData);
    }

    public static void addReview(Product product, String review, ArrayList<String> marketFile) {
        ArrayList<String> reviews = product.getReviews();
        reviews.add(review);
        synchronized (marketFile) {
            int index = ServerFunctions.productIndex(marketFile, product.toString());
            marketFile.set(index, (product.toString()+","));
        }
    }

    public static void editUser(String email, String newEmail, String newPassword,
                                String newType, ArrayList<String> accounts) {
        synchronized (accounts) {
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).split(",")[0].equals(email)) {
                    String newUserDetails = newEmail + "," + newPassword + "," + newType;
                    accounts.set(i, newUserDetails);
                    break;
                }
            }
        }
    }

    public static void deleteUser(String email, ArrayList<String> accounts) {
        synchronized (accounts) {
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).split(",")[0].equals(email)) {
                    accounts.remove(i);
                    break;
                }
            }
        }
    }

    public static String displayStats(ArrayList<String> customerData) {
        String x  = "";
        for (int i = 0;  i < customerData.size(); i++) {
            String reuse = "%s has sold %s books, you purchased %s books\n";
            String[] dataList = customerData.get(i).split(",");
            x += String.format(reuse, dataList[0], dataList[1], dataList[2]);
        }
        return x;
    }

    public static String displayStats(Dictionary<String, ArrayList<String>[]> sellerData, String displayMode) {
        String x = "";
        Enumeration<String> keys = sellerData.keys();
        while (keys.hasMoreElements()) {
            String nextEle = keys.nextElement();
            x += (String) nextEle + ": \n";
            ArrayList<String> array = null;
            String reuse = "";
            if (displayMode.equals("0")) {
                array = sellerData.get(nextEle)[0];
                reuse = "%s was bought %s times";
            } else {
                array = sellerData.get(nextEle)[1];
                reuse = "%s bought %s books";
            }
            for (int i = 0; i < array.size(); i++) {
                String[] productSplit = array.get(i).split(",");
                x += String.format(reuse, productSplit[0], productSplit[1]);
                x += "\n";
            }
        }
        return x;
    }

    public static double calculateIncome(String email, ArrayList<String> boughtFile) {
        synchronized (boughtFile) {
            int numberOfSales = boughtFile.size();
            double totalIncome = 0.00;
            for (int i = 0; i < numberOfSales; i++) {
                String[] sale = boughtFile.get(i).split(",");
                if (sale[1].equals(email)) {
                    totalIncome += Double.parseDouble(sale[4]) * Double.parseDouble(sale[5]) *
                            (1-Double.parseDouble(sale[6]));
                }
            }
            return totalIncome;
        }
    }

}
