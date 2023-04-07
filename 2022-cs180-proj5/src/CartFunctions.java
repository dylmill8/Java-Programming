import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class CartFunctions {
    public static void remove(String email, int index, ArrayList<String> shoppingCart) {
        synchronized (shoppingCart) {
            for (var i = 0; i < shoppingCart.size(); i++) {
                var fields = new ArrayList<String>(Arrays.asList(shoppingCart.get(i).split(";")));
                if (fields.get(0).equals(email)) {
                    fields.remove(index);
                    shoppingCart.set(i, String.join(";", fields));
                    return;
                }
            }
        }
    }

    public static void add(
            String email, Product product, int quantity,
            ArrayList<String> marketFile, ArrayList<String> shoppingCart
    ) {
        if (quantity > product.getQuantity()) {
            return;
        }
        boolean delete = quantity == product.getQuantity();
        product.setQuantity(quantity);
        synchronized (shoppingCart) {
            int ind;
            for (ind = 0; ind < shoppingCart.size(); ind++) {
                var fields = Arrays.asList(shoppingCart.get(ind).split(";"));
                if (fields.get(0).equals(email)) {
                    break;
                }
            }
            if (ind == shoppingCart.size()) {
                shoppingCart.add(email);
            }
            var fields = new ArrayList<>(Arrays.asList(shoppingCart.get(ind).split(";")));
            int productInd;
            for (productInd = 1; productInd < fields.size(); productInd++) {
                var iproduct = Product.fromString(fields.get(productInd));
                if (product.same(iproduct)) {
                    break;
                }
            }
            Product oldProduct;
            if (productInd == fields.size()) {
                oldProduct = new Product();
            } else {
                oldProduct = Product.fromString(fields.get(productInd));
                fields.remove(productInd);
            }
            product.setQuantity(product.getQuantity() + oldProduct.getQuantity());
            fields.add(product.toString());
            shoppingCart.set(ind, String.join(";", fields));
        }
        if (delete) {
            synchronized (marketFile) {
                int index = ServerFunctions.productIndex(marketFile, product.toString());
                marketFile.remove(index);
            }
        }
    }

    public static String getCarts(String email, ArrayList<String> shoppingCart) {
        var sb = new StringBuilder();
        var products = new HashMap<String, Product>();
        var counts = new HashMap<String, Integer>();
        synchronized (shoppingCart) {
            for (var cart : shoppingCart) {
                var fields = cart.split(";");
                for (int i = 1; i < fields.length; i++) {
                    var product = Product.fromString(fields[i]);
                    var key = String.format(
                            "%s,%s,%s",
                            product.getSellerName(),
                            product.getStore(),
                            product.getName());
                    if (products.containsKey(key)) {
                        var iproduct = products.get(key);
                        iproduct.setQuantity(iproduct.getQuantity() + product.getQuantity());
                    } else {
                        products.put(key, product);
                    }
                    counts.put(key, counts.getOrDefault(key, 0) + 1);
                }
            }
        }
        for (var key : products.keySet()) {
            var product = products.get(key);
            var count = counts.get(key);

            sb.append("There are ").append(product.getQuantity()).append(' ');
            sb.append(product.getName()).append(", ").append(product.getDescription());
            sb.append(" in store ").append(product.getStore());
            sb.append(" selling for $").append(product.getPrice());
            sb.append(" at a ").append(product.getSale()).append("% discount currently in ");
            sb.append(count).append(" carts.\n");
        }
        return sb.toString();
    }
}
