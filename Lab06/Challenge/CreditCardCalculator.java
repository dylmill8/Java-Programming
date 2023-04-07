import java.util.Scanner;

/**
 * Lab06Challenge -- Credit Card Calculator
 * <p>
 * This program provides and interface for the user to instantiate a credit card with an annual interest rate, balance,
 * and monthly payment and gives them the option to manage their credit card, calculate the payoff, or quit.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version September 30, 2022
 *
 */

public class CreditCardCalculator {
    public static final String WELCOME_MESSAGE = "Welcome to the Credit Card Calculator!";
    public static final String MENU = "Menu";
    public static final String QUIT_OPTION = "0. Quit";
    public static final String ADD_OPTION = "1. Add a card";
    public static final String RATE_PROMPT = "Enter the annual interest rate:";
    public static final String BALANCE_PROMPT = "Enter the balance:";
    public static final String PAYMENT_PROMPT = "Enter the monthly payment:";
    public static final String MODIFY_OPTION = "1. Modify card";
    public static final String PAYOFF_OPTION = "2. Calculate Payoff";
    public static final String CALCULATION_OPTION = "Would you like to print the payoff calculations?";
    public static final String CALCULATION_DECISION = "1. Yes\n2. No";
    public static final String CALCULATION_RESULT = "Months until payoff: %d";
    public static final String ERROR_MESSAGE = "Error! Invalid input.";
    public static final String GOODBYE_MESSAGE = "Thank you!";

    //manages the two ongoing menus
    public static int menu(int option, Scanner scanner) {
        int optionSelection = 0;

        if (option == 1) {
            do {
                System.out.println(MENU);
                System.out.println(QUIT_OPTION);
                System.out.println(ADD_OPTION);
                optionSelection = scanner.nextInt();
                scanner.nextLine();
                if (optionSelection != 0 && optionSelection != 1) System.out.println(ERROR_MESSAGE);
            } while (optionSelection != 0 && optionSelection != 1);
        }

        if (option == 2) {
            do {
                System.out.println(MENU);
                System.out.println(QUIT_OPTION);
                System.out.println(MODIFY_OPTION);
                System.out.println(PAYOFF_OPTION);
                optionSelection = scanner.nextInt();
                scanner.nextLine();
                if (optionSelection != 0 && optionSelection != 1 && optionSelection != 2) {
                    System.out.println(ERROR_MESSAGE);
                }
            } while (optionSelection != 0 && optionSelection != 1 && optionSelection != 2);
        }

        if (option == 3) {
            do {
                System.out.println(CALCULATION_OPTION);
                System.out.println(CALCULATION_DECISION);
                optionSelection = scanner.nextInt();
                scanner.nextLine();
                if (optionSelection != 1 && optionSelection != 2) {
                    System.out.println(ERROR_MESSAGE);
                }
            } while (optionSelection != 1 && optionSelection != 2);
        }

        return optionSelection;
    }

    public static void main(String[] args) {
        System.out.println(WELCOME_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        int optionSelection;

        do {
            optionSelection = menu(1, scanner); //calls the first menu option
            if (optionSelection == 0) break;

            //prompts for credit card information
            System.out.println(RATE_PROMPT);
            double rate = scanner.nextDouble();
            scanner.nextLine();
            System.out.println(BALANCE_PROMPT);
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println(PAYMENT_PROMPT);
            double monthlyPayment = scanner.nextDouble();
            scanner.nextLine();

            Card creditCard = new Card(rate, balance, monthlyPayment);
            System.out.print(creditCard.toString()); //prints card info

            do {
                optionSelection = menu(2, scanner);

                if (optionSelection == 1) {
                    System.out.println(RATE_PROMPT);
                    rate = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println(BALANCE_PROMPT);
                    balance = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println(PAYMENT_PROMPT);
                    monthlyPayment = scanner.nextDouble();
                    scanner.nextLine();

                    //sets the new values for the existing credit card
                    creditCard.setRate(rate);
                    creditCard.setBalance(balance);
                    creditCard.setMonthlyPayment(monthlyPayment);

                    System.out.println(creditCard.toString());
                }

                if (optionSelection == 2) {
                    optionSelection = menu(3, scanner);
                    int months;

                    if (optionSelection == 1) months = creditCard.calculatePayoff(true);
                    else months = creditCard.calculatePayoff(false);

                    if (months != -1) System.out.printf(CALCULATION_RESULT + "%n", months);
                }
            } while (optionSelection != 0);
        } while (optionSelection != 0);
        System.out.println(GOODBYE_MESSAGE);
    }
}