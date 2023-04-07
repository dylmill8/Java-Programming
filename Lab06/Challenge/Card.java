/**
 * Lab06Challenge -- Credit Card
 * <p>
 * This program lets the user input the annual interest rate, balance, and monthly payment for a credit card and can
 * calculate the number of months it will take to pay off the card.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version September 30, 2022
 *
 */

public class Card {
    private double rate; //annual percent interest of the credit card
    private double balance; //total balance of the credit card
    private double monthlyPayment; //monthly payment of the credit card

    public Card(double rate, double balance, double monthlyPayment) {
        this.rate = rate;
        this.balance = balance;
        this.monthlyPayment = monthlyPayment;
    }

    //accessor methods
    public double getRate() { return rate; }
    public double getBalance() { return balance; }
    public double getMonthlyPayment() { return monthlyPayment; }

    //mutator methods
    public void setRate(double rate) { this.rate = rate; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    //Calculate and return the number of months required to pay off the credit card
    public int calculatePayoff(boolean output) {
        double monthlyInterestRate;
        double monthlyInterestCharge;
        double monthlyPrincipalPayment;
        int months = 0;
        double totalInterestPaid = 0;
        double startingBalance = balance;

        while (balance > 0) {
            if (balance > startingBalance) {
                System.out.println("Error: Impossible to payoff balance under these conditions");
                return -1;
            }

            monthlyInterestRate = rate / 12;
            monthlyInterestCharge = monthlyInterestRate * balance;
            monthlyPrincipalPayment = monthlyPayment - monthlyInterestCharge;

            if (monthlyPrincipalPayment > balance) {
                monthlyPrincipalPayment = balance;
                balance = 0;
            } else {
                balance = balance - monthlyPrincipalPayment;
            }

            totalInterestPaid = totalInterestPaid + monthlyInterestCharge;
            months += 1;

            if (output) {
                System.out.printf("Payment: %d - Principal: %.2f - Interest: %.2f - Remaining Balance: %.2f%n",
                        months, monthlyPrincipalPayment, monthlyInterestCharge, balance);
            }
        }

        if (output) System.out.printf("Total Interest Paid: %.2f%n", totalInterestPaid);

        return months;
    }

    public String toString() {
        return String.format("Rate: %.2f - Balance: %.2f - Payment: %.2f%n", rate, balance, monthlyPayment);
    }

    //testing
    /*
    public static void main(String[] args) {
        Card creditCard = new Card(0.12,1000,88.85);
        System.out.println(creditCard.toString());
        creditCard.calculatePayoff(true);
    }
    */
}
