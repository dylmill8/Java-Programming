import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class holds the data for a product including its name, the seller name, store name, description, quantity,
 * price, sale percentage, and reviews with accessor and mutator methods for each variable and a toString that is
 * used to print all relevant data about a product.
 *
 * @author Dylan, Nathanael, James, Vaibhav, Jordan
 * @version November 13, 2022
 */

public class Product {
    private String name;
    private String sellerName;
    private String store;
    private String description;
    private int quantity;
    private float price;
    private float sale;
    private ArrayList<String> reviews;

    public Product() {
        name = "";
        sellerName = "";
        store = "";
        description = "";
        quantity = 0;
        price = 0;
        sale = 0;
        reviews = new ArrayList<>();
    }

    public static Product fromString(String toString) {
        var fields = toString.split(",", 8);
        ArrayList<String> reviews;
        if (fields[7].length() != 0) {
            reviews = new ArrayList<String>(Arrays.asList(fields[7].split(",")));
        } else {
            reviews = new ArrayList<>();
        }
        return new Product(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]),
                Float.parseFloat(fields[5]), Float.parseFloat(fields[6]),
                reviews);
    }

    public Product(String name, String sellerName, String store, String description, int quantity,
                   float price, float sale, ArrayList<String> reviews) {
        this.name = name;
        this.sellerName = sellerName;
        this.store = store;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.sale = sale;
        this.reviews = reviews;
    }

    public Product(Product product) {
        name = product.getName();
        sellerName = product.getSellerName();
        store = product.getStore();
        description = product.getDescription();
        quantity = product.getQuantity();
        price = product.getPrice();
        sale = product.getSale();
        reviews = product.getReviews();
    }

    // ACCESSORS
    public float getSale() {
        return sale;
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public String getReview(int i) {
        return reviews.get(i);
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getStore() {
        return store;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    // MUTATORS
    public void setName(String name) {
        this.name = name;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public void makeReview(String review) {
        reviews.add(review);
    }

    // METHODS
    /*
    public void buyItem(int amount) {
        /*
        if (amount > quantity) {
            throw new InvalidPurchaseException("Not enough products in stock");
        }
        quantity -= amount;
    }
    */

    @Override
    public String toString() {
        String reviewString = "";
        // the old way broke if the length was 0, because it would substring until -1
        for (int i = 0; i < reviews.size(); i++) {
            if (i != 0) {
                reviewString += ',';
            }
            reviewString += reviews.get(i);
        }

        return String.format(
                "%s,%s,%s,%s,%d,%.2f,%.2f,%s",
                name,
                sellerName,
                store,
                description,
                quantity,
                price,
                sale,
                reviewString);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product p = (Product) obj;
            if (reviews.size() == p.getReviewCount()) {
                for (int i = 0; i < p.getReviewCount(); i++) {
                    if (!reviews.contains(p.getReview(i))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return name.equals(p.getName()) && store.equals(p.getStore()) && description.equals(p.getDescription())
                    && quantity == p.getQuantity() && price == p.getPrice() && sale == p.getSale();
        }
        return false;
    }

    public boolean same(Product other) {
        return name.equals(other.name) && store.equals(other.store) && sellerName.equals(other.sellerName);
    }
}
