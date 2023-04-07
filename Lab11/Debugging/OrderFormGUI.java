import javax.swing.*;

public class OrderFormGUI {
    public static void main(String[] args) {
        while (true) {
            /** DO NOT CHANGE VALUES BELOW **/
            boolean hoodieInStock = true;
            boolean tshirtInStock = false;
            boolean longsleeveInStock = true;
            String item = "";
            int quantity = 0;
            String name = "";
            /** DO NOT CHANGE VALUES ABOVE **/

            String[] options = {"Hoodie", "T-shirt", "Long sleeve"};
            boolean outOfStock = true;
            do {
                item = (String) JOptionPane.showInputDialog(null, "Select item style ", "Order Form",
                        JOptionPane.PLAIN_MESSAGE, null, options, null);

                if (item.equalsIgnoreCase("Hoodie") && !hoodieInStock) {
                    JOptionPane.showMessageDialog(null, "Out of Stock",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                } else if (item.equalsIgnoreCase("T-shirt") && !tshirtInStock) {
                    JOptionPane.showMessageDialog(null, "Out of Stock",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                } else if (item.equalsIgnoreCase("Long sleeve") && !longsleeveInStock) {
                    JOptionPane.showMessageDialog(null, "Out of Stock",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                } else {
                    outOfStock = false;
                }
            } while (outOfStock);

            do {
                try {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter quantity",
                            "Order Form", JOptionPane.QUESTION_MESSAGE));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Enter an integer",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                    quantity = -1;
                }

                if (quantity < 1) {
                    JOptionPane.showMessageDialog(null, "Enter an integer",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                }
            } while (quantity < 1);

            boolean condition = true;
            do {
                name = JOptionPane.showInputDialog(null, "Enter your Name", "Order Form",
                        JOptionPane.QUESTION_MESSAGE);
                if (name.contains(" ")) {
                    condition = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Enter first and last name",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                }
            } while (condition);


            /** Order Confirmation Message **/
            String resultMessage = "Name: " + name + "\nItem: " + item + "\nQuantity: " + quantity;
            JOptionPane.showMessageDialog(null, resultMessage, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);

            int again = JOptionPane.showConfirmDialog(null, "Would you like to place another order?",
                    "Order Form", JOptionPane.YES_NO_OPTION);
            if (again == 1) {
                break;
            }
        }
    }
}
