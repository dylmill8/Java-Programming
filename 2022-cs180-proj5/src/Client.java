import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket = new Socket("localhost", 10101);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not establish connection to the server!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            String email;
            String password;
            String userType;
            while (true) {
                while (true) {
                    String[] accountInfo = accountInfo();
                    if (accountInfo == null) {
                        socket.close();
                        break;
                    }
                    email = accountInfo[0];
                    password = accountInfo[1];

                    printWriter.println(email + "," + password);

                    int result = Integer.parseInt(bufferedReader.readLine());
                    if (result == 0) {
                        int input = JOptionPane.showConfirmDialog(null,
                                "Your account does not exist, would you like to make a new account?",
                                "Bookstore", JOptionPane.YES_NO_OPTION);
                        if (input == JOptionPane.YES_OPTION) {
                            printWriter.println("1");
                            while (true) {
                                accountInfo = accountInfo();
                                if (accountInfo == null) {
                                    socket.close();
                                    break;
                                }
                                email = accountInfo[0];
                                password = accountInfo[1];
                                String[] options = {"Customer", "Seller"};
                                input = JOptionPane.showOptionDialog(null,
                                        "Are you a customer or bookseller?", "Bookstore",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                        null, options, options[0]);
                                printWriter.println(email + "," + password + "," + input);
                                String line = bufferedReader.readLine();
                                if (line.equals("1")) {
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "This email is already taken!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else if (input == JOptionPane.NO_OPTION) {
                            continue;
                        } else {
                            socket.close();
                            break;
                        }
                    } else if (result == 2) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid credentials, please try again.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    break;
                }

                userType = bufferedReader.readLine();

                String[] accountOptions = {"Continue", "Edit", "Delete"};
                int accountMenu = JOptionPane.showOptionDialog(null,
                        "Would you like to edit or delete your account?", "Bookstore",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, accountOptions,
                        accountOptions[0]);
                if (accountMenu == 0) { // continue
                    printWriter.println("continue");
                    break;
                } else if (accountMenu == 1) { // edit
                    printWriter.println("edit");
                    boolean exit = false;
                    while (true) {
                        String[] accountInfo = accountInfo();
                        if (accountInfo == null) {
                            socket.close();
                            exit = true;
                            break;
                        }
                        email = accountInfo[0];
                        password = accountInfo[1];

                        String[] options = {"Customer", "Seller"};
                        int input = JOptionPane.showOptionDialog(null,
                                "Are you a customer or bookseller?", "Bookstore",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);
                        printWriter.println(email + "," + password + "," + input);
                        String line = bufferedReader.readLine();
                        if (line.equals("1")) {
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "This email is already taken!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if (exit) {
                        break;
                    }
                } else if (accountMenu == 2) { // delete
                    printWriter.println("delete");
                    JOptionPane.showMessageDialog(null, "Your account was deleted",
                            "Bookstore", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    socket.close();
                    break;
                }
            }
            boolean loop = true;
            main: while (loop) {
                String marketText = "";
                String line = bufferedReader.readLine();
                while (!line.equals("<END>")) {
                    marketText = marketText + "\n" + line;
                    line = bufferedReader.readLine();
                }

                int numItems = Integer.parseInt(bufferedReader.readLine());

                JTextArea textArea = new JTextArea(marketText);
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setLineWrap(false);
                textArea.setEditable(false);
                scrollPane.setPreferredSize((new Dimension(500, 500)));

                String[] options;
                if (userType.equals("customer")) {
                    options = new String[]{"1. View a book", "2. Search the bookstore", "3. Sort the bookstore",
                            "4. View cart", "5. View store sales statistics", "6. View purchase history",
                            "7. Export purchase history"};
                } else {
                    options = new String[]{"1. View a book", "2. Search the bookstore", "3. Sort the bookstore",
                            "4. Products in carts", "5. View store sales statistics", "6. Sell a book",
                            "7. Transaction history", "8. Calculate income", "9. Import/export sales"};
                }

                Object option = JOptionPane.showInputDialog(null, scrollPane, "Bookstore",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == null) {
                    socket.close();
                    break;
                }
                String optionString = option.toString();

                printWriter.println(optionString);

                switch (optionString) {
                    case "1. View a book" -> {
                        if (numItems == 0) {
                            JOptionPane.showMessageDialog(null,
                                    "The marketplace is empty, there are no items to view!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            viewItemMenu(scrollPane, numItems, printWriter, bufferedReader, userType);
                        }
                    }
                    case "2. Search the bookstore" -> {
                        String search = JOptionPane.showInputDialog(null, "Enter your search:",
                                "Bookstore", JOptionPane.QUESTION_MESSAGE);
                        printWriter.println(search);

                        search = "";
                        numItems = Integer.parseInt(bufferedReader.readLine());
                        line = bufferedReader.readLine();
                        while (!line.equals("<END>")) {
                            search = search + "\n" + line;
                            line = bufferedReader.readLine();
                        }

                        textArea = new JTextArea(search);
                        scrollPane = new JScrollPane(textArea);
                        textArea.setLineWrap(false);
                        textArea.setEditable(false);
                        scrollPane.setPreferredSize((new Dimension(500, 500)));

                        viewItemMenu(scrollPane, numItems, printWriter, bufferedReader, userType);
                    }
                    case "3. Sort the bookstore" -> {

                        String[] sortOptions = {"Quantity", "Price"};
                        int sort = JOptionPane.showOptionDialog(null,
                                "How would you like to sort the market?", "Bookstore",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, sortOptions,
                                sortOptions[0]);
                        printWriter.println(sort); // 0 = quantity, 1 = price
                    }
                    case "4. View cart" -> {
                        while (true) {
                            String cart = "";
                            numItems = Integer.parseInt(bufferedReader.readLine());
                            line = bufferedReader.readLine();
                            while (!line.equals("<END>")) {
                                cart = cart + "\n" + line;
                                line = bufferedReader.readLine();
                            }

                            textArea = new JTextArea(cart);
                            scrollPane = new JScrollPane(textArea);
                            textArea.setLineWrap(false);
                            textArea.setEditable(false);
                            scrollPane.setPreferredSize((new Dimension(500, 500)));

                            String[] cartOptions = {"Buy all items", "Remove", "Go back"};
                            int cartMenu = JOptionPane.showOptionDialog(null, scrollPane,
                                    "Bookstore", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                    null, cartOptions, cartOptions[0]);
                            printWriter.println(cartMenu); // 0 = Buy, 1 = Remove, 2 = Go Back
                            if (cartMenu == 1) { // if remove
                                int itemToRemove;
                                while (true) {
                                    try {
                                        itemToRemove = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                scrollPane, "Which item would you like to remove?",
                                                JOptionPane.QUESTION_MESSAGE));
                                        if (numItems < itemToRemove || itemToRemove < 1) {
                                            JOptionPane.showMessageDialog(null,
                                                    "There is not a valid item!", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                        } else { break; }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a number!", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                printWriter.println(itemToRemove);
                            } else { break; }
                        }
                    }
                    case "5. View store sales statistics" -> {
                        String[] sortOptions;
                        while (true) {
                            if (userType.equals("customer")) {
                                sortOptions = new String[]{"Store sales", "Purchases"};
                            } else {
                                sortOptions = new String[]{"Products sold", "Customer purchases"};
                            }
                            int sortMenu = JOptionPane.showOptionDialog(null, "Sort by:",
                                    "Bookstore", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                    null, sortOptions, sortOptions[0]);

                            printWriter.println(sortMenu);

                            String stats = "";
                            line = bufferedReader.readLine();
                            while (!line.equals("<END>")) {
                                stats = stats + "\n" + line;
                                line = bufferedReader.readLine();
                            }

                            textArea = new JTextArea(stats);
                            scrollPane = new JScrollPane(textArea);
                            textArea.setLineWrap(false);
                            textArea.setEditable(false);
                            scrollPane.setPreferredSize((new Dimension(500, 500)));

                            String[] statsOptions = new String[]{"Sort", "Continue"};
                            int statsMenu = JOptionPane.showOptionDialog(null, scrollPane,
                                    "Bookstore", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                    null, statsOptions, statsOptions[0]);

                            if (statsMenu == 1 || statsMenu == JOptionPane.CLOSED_OPTION) { break; }
                        }
                    }
                    case "6. View purchase history" -> {
                        String historyText = "";
                        line = bufferedReader.readLine();
                        while (!line.equals("<END>")) {
                            historyText = historyText + "\n" + line;
                            line = bufferedReader.readLine();
                        }

                        textArea = new JTextArea(historyText);
                        scrollPane = new JScrollPane(textArea);
                        textArea.setLineWrap(false);
                        textArea.setEditable(false);
                        scrollPane.setPreferredSize((new Dimension(500, 500)));

                        JOptionPane.showMessageDialog(null, scrollPane, "Bookstore",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    case "7. Export purchase history" -> {
                        String fileName;
                        do {
                            fileName = JOptionPane.showInputDialog(null, "Enter a file name.",
                                    "Bookstore", JOptionPane.QUESTION_MESSAGE);
                            if (fileName == null) {
                                printWriter.println(1);
                                continue main;
                            }
                            if (fileName.length() != 0) {
                                break;
                            }

                            JOptionPane.showMessageDialog(null, "Invalid file name!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } while (fileName.isBlank());

                        printWriter.println(0);

                        PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName, true));
                        line = bufferedReader.readLine();
                        while (!line.equals("<END>")) {
                            fileWriter.println(line);
                            line = bufferedReader.readLine();
                        }
                        fileWriter.close();
                    }
                    case "4. Products in carts" -> {
                        String productsInCarts = "";
                        line = bufferedReader.readLine();
                        while (!line.equals("<END>")) {
                            productsInCarts = productsInCarts + "\n" + line;
                            line = bufferedReader.readLine();
                        }

                        textArea = new JTextArea(productsInCarts);
                        scrollPane = new JScrollPane(textArea);
                        textArea.setLineWrap(false);
                        textArea.setEditable(false);
                        scrollPane.setPreferredSize((new Dimension(500, 500)));

                        JOptionPane.showMessageDialog(null, scrollPane, "Bookstore",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    case "6. Sell a book" -> {
                        JTextField productName = new JTextField();
                        JTextField storeName = new JTextField();
                        JTextField description = new JTextField();
                        JTextField quantity = new JTextField();
                        JTextField price = new JTextField();
                        JTextField sale = new JTextField();
                        Object[] message = {
                                "Product name", productName,
                                "Store name:", storeName,
                                "Description:", description,
                                "Quantity:", quantity,
                                "Price:", price,
                                "Sale:", sale
                        };
                        boolean valid = false;
                        while (!valid) {
                            valid = true;
                            int confirm = JOptionPane.showConfirmDialog(null, message, "Bookstore",
                                    JOptionPane.OK_CANCEL_OPTION);
                            if (confirm == JOptionPane.OK_OPTION) {
                                if (description.getText().contains(",")) {
                                    JOptionPane.showMessageDialog(null,
                                            "A book description cannot include any commas!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                } if (productName.getText().contains(",")) {
                                    JOptionPane.showMessageDialog(null,
                                            "A product name cannot include any commas!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                } if (storeName.getText().contains(",")) {
                                    JOptionPane.showMessageDialog(null,
                                            "A store name cannot include any commas!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }

                                try {
                                    int quantityInt = Integer.parseInt(quantity.getText());
                                    if (quantityInt <= 0) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a valid integer!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid integer!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }

                                try {
                                    double priceFloat = Float.parseFloat(price.getText());
                                    if (priceFloat <= 0) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a valid price!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid price!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }

                                try {
                                    double saleFloat = Float.parseFloat(sale.getText());
                                    if (saleFloat < 0 || saleFloat > 1) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a valid sale percentage (between 0 and 1)!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    }
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid sale percentage (between 0 and 1)!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            }
                        }
                        printWriter.println(productName.getText() + "\n" + storeName.getText() + "\n"
                                + description.getText() + "\n" + quantity.getText() + '\n' + price.getText()
                                + "\n" + sale.getText());
                    }
                    case "7. Transaction history" -> {
                        String transactionHistory = "";
                        line = bufferedReader.readLine();
                        while (!line.equals("<END>")) {
                            transactionHistory = transactionHistory + "\n" + line;
                            line = bufferedReader.readLine();
                        }

                        textArea = new JTextArea(transactionHistory);
                        scrollPane = new JScrollPane(textArea);
                        textArea.setLineWrap(false);
                        textArea.setEditable(false);
                        scrollPane.setPreferredSize((new Dimension(500, 500)));

                        JOptionPane.showMessageDialog(null, scrollPane, "Bookstore",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    case "8. Calculate income" -> {
                        String calculatedIncome = String.format("%.2f", Float.parseFloat(bufferedReader.readLine()));
                        JOptionPane.showMessageDialog(null, "Calculated income: $" +
                                calculatedIncome, "Bookstore", JOptionPane.INFORMATION_MESSAGE);
                    }
                    case "9. Import/export sales" -> {
                        String[] fileOptions = {"Import", "Export"};
                        int input = JOptionPane.showOptionDialog(null,
                                "Would you like to import or export your sales?", "Bookstore",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, fileOptions, fileOptions[0]);
                        printWriter.println(input); // 0 = import, 1 = export

                        String fileName;
                        do {
                            fileName = JOptionPane.showInputDialog(null, "Enter a file name.",
                                    "Bookstore", JOptionPane.QUESTION_MESSAGE);

                            if (fileName == null) {
                                printWriter.println(1);
                                continue main;
                            }

                            JOptionPane.showMessageDialog(null, "Invalid file name!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } while (fileName.isBlank());
                        printWriter.println(0);


                        if (input == 0) { // import
                            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
                            line = fileReader.readLine();
                            while (line != null) {
                                printWriter.println(line);
                                line = fileReader.readLine();
                            }
                            printWriter.println("<END>");
                        } else { // export
                            PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName, true));
                            line = bufferedReader.readLine();
                            while (!line.equals("<END>")) {
                                fileWriter.println(line);
                                line = bufferedReader.readLine();
                            }
                            fileWriter.close();
                        }
                    }
                    default -> {
                        socket.close();
                        loop = false;
                    }
                }
            }

        } catch (IOException Ignored) {
            JOptionPane.showMessageDialog(null, "Thanks for visiting the Bookstore!",
                    "Bookstore", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static String[] accountInfo() {
        String email;
        String password;

        while (true) {
            email = JOptionPane.showInputDialog(null, "Enter your email.",
                    "Bookstore", JOptionPane.QUESTION_MESSAGE);
            if (email == null) {
                return null;
            }
            password = JOptionPane.showInputDialog(null, "Enter your password.",
                    "Bookstore", JOptionPane.QUESTION_MESSAGE);
            if (password ==null) {
                return null;
            } else if (!email.contains("@") || email.contains(",") || password.contains(",") || email.isBlank() ||
                    password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Enter a valid email and password!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else { break; }
        }
        return new String[]{email, password};
    }
    public static void viewItemMenu(JScrollPane scrollPane, int numItems, PrintWriter printWriter,
                                    BufferedReader bufferedReader, String userType) throws IOException {
        int itemToView;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, scrollPane,
                        "Which item would you like to view?", JOptionPane.QUESTION_MESSAGE);
                if (input == null) {
                    printWriter.println(0);
                    return;
                }
                itemToView = Integer.parseInt(input);
                if (numItems < itemToView || itemToView < 1) {
                    JOptionPane.showMessageDialog(null, "There is not a valid item!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else { break; }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a number!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        printWriter.println(itemToView);

        String itemText = "";
        String line = bufferedReader.readLine();
        while (!line.equals("<END>")) {
            itemText = itemText + "\n" + line;
            line = bufferedReader.readLine();
        }

        JTextArea textArea = new JTextArea(itemText);
        scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(false);
        textArea.setEditable(false);
        scrollPane.setPreferredSize((new Dimension(500, 500)));

        // Have to make client know what type of user the client is
        if (userType.equals("seller")) {
            String[] itemMenuOptions = {"Edit Product", "Remove Product", "Go back"};
            int menuOption = JOptionPane.showOptionDialog(null, scrollPane,
                    "Bookstore", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, itemMenuOptions, itemMenuOptions[0]);

            switch (menuOption) {
                case 0 -> { // edit product
                    printWriter.println("edit");
                    if (bufferedReader.readLine().equals("false")) {
                        JOptionPane.showMessageDialog(null,
                                "You can only edit your own books!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JTextField description = new JTextField();
                    JTextField quantity = new JTextField();
                    JTextField price = new JTextField();
                    JTextField sale = new JTextField();
                    Object[] message = {
                            "Description:", description,
                            "Quantity:", quantity,
                            "Price:", price,
                            "Sale:", sale
                    };
                    boolean valid = false;
                    while (!valid) {
                        valid = true;
                        int confirm = JOptionPane.showConfirmDialog(null, message, "Bookstore",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (confirm == JOptionPane.OK_OPTION) {
                            if (description.getText().contains(",")) {
                                JOptionPane.showMessageDialog(null,
                                        "A book description cannot include any commas!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                            try {
                                int quantityInt = Integer.parseInt(quantity.getText());
                                if (quantityInt <= 0) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid integer!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null,
                                        "Please enter a valid integer!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                            try {
                                double priceFloat = Float.parseFloat(price.getText());
                                if (priceFloat <= 0) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid price!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null,
                                        "Please enter a valid price!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                            try {
                                double saleFloat = Float.parseFloat(sale.getText());
                                if (saleFloat < 0 || saleFloat > 1) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid sale percentage (between 0 and 1)!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null,
                                        "Please enter a valid sale percentage (between 0 and 1)!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    }
                    printWriter.println(description.getText() + "\n" + quantity.getText() + '\n' + price.getText()
                            + "\n" + sale.getText());
                }
                case 1 -> printWriter.write("remove"); // remove product
                case 2 -> printWriter.write("back"); // go back to main menu
            }
        } else {
            String[] itemMenuOptions = {"Add review", "Buy item", "Add item to cart", "Go back"};
            int menuOption = JOptionPane.showOptionDialog(null, scrollPane,
                    "Bookstore", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, itemMenuOptions, itemMenuOptions[0]);
            switch (menuOption) {
                case 0 -> { // add review
                    printWriter.println("review");
                    String review = null;
                    boolean valid = false;
                    while (!valid) {
                        valid = true;
                        review = JOptionPane.showInputDialog(null,
                                "Enter review:");
                        if (review.contains(",")) {
                            JOptionPane.showMessageDialog(null,
                                    "A book review cannot include any commas!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            valid = false;
                        }
                    }
                    printWriter.println(review);
                }
                case 1 -> {// buy item
                    printWriter.println("buy");
                    int quantity;
                    while (true) {
                        try {
                            quantity = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Enter the quantity you would like to buy:"));
                            if (quantity <= 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Enter a valid quantity!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else { break; }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "Enter a valid quantity!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    printWriter.println(quantity);
                }
                case 2 -> { // add item to cart
                    printWriter.println("cart");
                    int quantity;
                    while (true) {
                        try {
                            quantity = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Enter the quantity you would like to add:"));
                            if (quantity <= 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Enter a valid quantity!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else { break; }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "Enter a valid quantity!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    printWriter.println(quantity);
                }
                case 3 -> printWriter.write("back"); // go back to main menu
            }
        }
    }
}