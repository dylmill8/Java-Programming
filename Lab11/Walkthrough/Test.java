import javax.swing.JOptionPane;

public class Test {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to Homework 11!", "Welcome",
                JOptionPane.INFORMATION_MESSAGE);

        int reply = JOptionPane.showConfirmDialog(null, "Would you like to exit?",
                "Exit", JOptionPane.YES_NO_OPTION);

        String favoriteColor = JOptionPane.showInputDialog(null, "What is your favorite color?",
                "Color", JOptionPane.QUESTION_MESSAGE);

        Integer[] choices = {1,2,3,4,5,6,7,8,9,10};
        int userChoice;
        userChoice = (int) JOptionPane.showInputDialog(null, "What is your choice?",
                "Choice?", JOptionPane.QUESTION_MESSAGE,
                null, choices, choices[0]);

        String[] options = {"Lets go!", "Not right now."};
        int result = JOptionPane.showOptionDialog(null, "Are you ready to continue?", "Ready?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);
    }
}
