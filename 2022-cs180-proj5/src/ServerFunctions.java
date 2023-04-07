import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class ServerFunctions {
    public ServerFunctions() { }

    public static void checkFileCreated(File f) {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> readFile(File f) {
        checkFileCreated(f);
        ArrayList<String> accounts = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line = bf.readLine();
            while (line != null) {
                accounts.add(line);
                line = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void saveFile(String fileName, ArrayList<String> updatedList) {
        File f = new File(fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.print("");
            fos = new FileOutputStream(f, true);
            pw = new PrintWriter(fos);
            for (int i = 0; i < updatedList.size(); i++) {
                pw.println(updatedList.get(i));
            }
            pw.close();
        } catch (IOException ignore) { }
    }

    public static int validateUser(String email, String password, ArrayList<String> accounts) {
        // 0 = email not found; 1 = successful login; 2 = existing email, but wrong password
        synchronized (accounts) {
            int validation = 0;
            for (String account : accounts) {
                String un = account.split(",")[0];
                String pass = account.split(",")[1];
                if (un.equals(email)) {
                    validation = pass.equals(password) ? 1 : 2;
                    break;
                }
            }
            return validation;
        }
    }

    public static int validateUser(String email, ArrayList<String> accounts) {
        int validation = 1;
        for (String account : accounts) {
            String un = account.split(",")[0];
            if (un.equals(email)) {
                validation = 0;
                break;
            }
        }
        return validation;
    }

    public static String getUserType(String email, String password, ArrayList<String> accounts) {
        synchronized (accounts) {
            String userType = "";
            for (String account : accounts) {
                String un = account.split(",")[0];
                String pass = account.split(",")[1];
                if (un.equals(email) && pass.equals(password)) {
                    userType = account.split(",")[2];
                    break;
                }
            }
            return userType;
        }
    }

    public static void buyItem(String email, Product product,
                               int quantityWanted,
                               ArrayList<String> marketFile,
                               ArrayList<String> boughtFile) {
        int quantityOnHand = product.getQuantity();
        if (quantityOnHand < quantityWanted) {
            quantityWanted = quantityOnHand;
        }
        int difference = quantityOnHand - quantityWanted;
        Product newProduct = new Product(product);
        synchronized (marketFile) {
            int itemIndex = ServerFunctions.productIndex(marketFile, product.toString());
            if (difference == 0) {
                marketFile.remove(itemIndex);
            } else {
                newProduct.setQuantity(difference);
                marketFile.set(itemIndex, newProduct.toString());
            }
        }
        product.setQuantity(quantityWanted);
        synchronized (boughtFile) {
            boughtFile.add(String.format("%s,%s", product, email));
        }
    }

    public static int[] getSearchResults(String search, ArrayList<String> marketFile) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < marketFile.size(); i++) {
            if (marketFile.get(i).contains(search)) {
                indexes.add(i);
            }
        }
        int[] indexesList = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            indexesList[i] = indexes.get(i);
        }
        return indexesList;
    }

    public static void sortByNumber(ArrayList<String> file, int indexOfProduct) {
        synchronized (file) {
            for (int i = 1; i < file.size(); i++) {
                float price = Float.parseFloat(file.get(i).split(",")[indexOfProduct]);
                if (price > Float.parseFloat(file.get(i - 1).split(",")[indexOfProduct])) {
                    for (int j = 0; j < file.size(); j++) {
                        if (price > Float.parseFloat(file.get(j).split(",")[indexOfProduct])) {
                            file.add(j, file.get(i));
                            file.remove(i + 1);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static int productIndex(ArrayList<String> marketFile, String product) {
        String[] givenProductSplit = product.split(",");
        for (int i = 0; i < marketFile.size(); i++) {
            String[] checkProductSplit = marketFile.get(i).split(",");
            if (givenProductSplit[0].equals(checkProductSplit[0])
                    && givenProductSplit[1].equals(checkProductSplit[1])
                    && givenProductSplit[2].equals(checkProductSplit[2])) {
                return i;
            }
        }
        return -1;
    }
    public static ArrayList<String> customerData(String email, ArrayList<String> boughtFile) {
        ArrayList<String> customerDataList = new ArrayList<>();
        for (int i = 0; i < boughtFile.size(); i++) {
            String[] line = boughtFile.get(i).split(",");
            String storeName = line[2];
            String customerName = line[line.length - 1];
            int quantityBought = Integer.parseInt(line[4]);

            boolean changed = false;
            for (int j = 0; j < customerDataList.size(); j++) {
                if (customerDataList.get(j).split(",")[0].equals(storeName)) {
                    if (customerName.equals(email)) {
                        customerDataList.set(j, storeName + "," + (quantityBought
                                + Integer.parseInt(customerDataList.get(j).split(",")[1])) + ","
                                + (quantityBought + Integer.parseInt(customerDataList.get(j).split(",")[2])));
                    } else {
                        customerDataList.set(j, storeName + "," + (quantityBought
                                + Integer.parseInt(customerDataList.get(j).split(",")[1])) + ","
                                + customerDataList.get(j).split(",")[2]);
                    }
                    changed = true;
                }
            }
            if (!changed) {
                if (customerName.equals(email)) {
                    customerDataList.add(storeName + "," + quantityBought + "," + quantityBought);
                } else {
                    customerDataList.add(storeName + "," + quantityBought + ",0");
                }
            }
        }
        return customerDataList;
    }

    public static Dictionary<String, ArrayList<String>[]> sellerData(String email, ArrayList<String> boughtFile) {
        Dictionary<String, ArrayList<String>[]>  dictionary = new Hashtable<>();

        ArrayList<String> storeNames = new ArrayList<>();
        for (String s : boughtFile) {
            String sellerName = s.split(",")[1];
            String storeName = s.split(",")[2];
            if (email.equals(sellerName) && !storeNames.contains(storeName)) {
                storeNames.add(storeName);
            }
        }

        for (String i : storeNames) {
            ArrayList<String>[] dataList = new ArrayList[2];
            dataList[0] = new ArrayList<>();
            dataList[1] = new ArrayList<>();
            dictionary.put(i, dataList);
        }

        for (int i = 0; i < boughtFile.size(); i++) {
            if (boughtFile.get(i).split(",")[1].equals(email)) {
                String[] line = boughtFile.get(i).split(",");
                String storeName = line[2];
                String productName = line[0];
                String customerName = line[line.length - 1];
                int quantity = Integer.parseInt(line[4]);

                ArrayList<String>[] dataList = dictionary.get(storeName);
                if (dataList != null) {
                    boolean changed = false;
                    for (int j = 0; j < dataList[0].size(); j++) {
                        if (dataList[0].get(j).split(",")[0].equals(productName)) {
                            dataList[0].set(j, productName + "," + (quantity
                                    + Integer.parseInt(dataList[0].get(j).split(",")[1])));
                            changed = true;
                        }
                    }
                    if (!changed) {
                        dataList[0].add(productName + "," + quantity);
                    }

                    changed = false;
                    for (int j = 0; j < dataList[1].size(); j++) {
                        if (dataList[1].get(j).split(",")[0].equals(customerName)) {
                            dataList[1].set(j, customerName + "," + (quantity
                                    + Integer.parseInt(dataList[1].get(j).split(",")[1])));
                            changed = true;
                        }
                    }
                    if (!changed) {
                        dataList[1].add(customerName + "," + quantity);
                    }
                }
            }
        }
        return dictionary;
    }

    public static void importProduct(ArrayList<String> newProducts, ArrayList<String> marketFile) {
        for (int i = 0; i < marketFile.size(); i++) {
            for (int j = 0; j < newProducts.size(); j++) {
                String[] list = newProducts.get(j).split(",");
                String productName = list[0];
                String sellerName = list[1];
                String storeName = list[2];
                int quantity = Integer.parseInt(list[3]);
                if (marketFile.get(i).contains(productName) &&
                        marketFile.get(i).contains(sellerName) &&
                        marketFile.get(i).contains(storeName)) {
                    quantity += Integer.parseInt(marketFile.get(i).split(",")[4]);
                    String[] marketSplit = marketFile.get(i).split(",");
                    String line = productName + "," +
                            sellerName + "," +
                            storeName + "," +
                            marketSplit[3] + "," +
                            quantity + "," +
                            marketSplit[5] + "," +
                            marketSplit[6] + "," +
                            marketSplit[7] + "," +
                            marketSplit[8];
                    marketFile.set(i, line);
                }
            }
        }
        marketFile.addAll(newProducts);
    }


}
