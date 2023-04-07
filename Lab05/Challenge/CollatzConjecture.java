import java.util.Scanner;

/**
 * Lab05Challenge -- Collatz Conjecture
 * <p>
 * This program lets the user test a famous unsolved math problem the Collatz conjecture. Given a number if it is even
 * divide it by 2 and if it is odd multiply it by 3 and add 1. Repeating these two operations will always result in
 * eventually reaching a value of 1. The program asks the user for a starting number and (optional) terminating
 * limit then performs the series of calculations while printing the resulting values.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version September 22, 2022
 *
 */

public class CollatzConjecture {
    public static void main(String[] args) {
        String welcome = "Welcome to Collatz Conjecture Calculator!";
        String optionOne = "1. Calculate with upper limit";
        String optionTwo = "2. Calculate until the end";
        String initialNumPrompt = "Enter the starting number:";
        String upperLimitPrompt = "Enter the upper limit:";
        String result = "Operation performed %d times, and the resulting number is %d.";
        String unfinishedPrompt = "Do you want to continue the calculations %d more times? (yes/no)";
        String againPrompt = "Do you want to use the calculator again? (yes/no)";
        String farewellPrompt = "Thanks for using Collatz Conjecture Calculator!";

        Scanner scanner = new Scanner(System.in);

        System.out.println(welcome);

        while (true) {
            int selection;

            do {
                System.out.println(optionOne + "\n" + optionTwo);
                selection = scanner.nextInt();
                scanner.nextLine(); //prevent scanner skipping line
            } while (selection != 1 && selection != 2); //input validation

            System.out.println(initialNumPrompt);
            int num = scanner.nextInt();
            scanner.nextLine(); //prevent scanner skipping line
            int counter = 0;

            switch (selection) {
                case 1 -> { //calculate wth upper limit
                    System.out.println(upperLimitPrompt);
                    int limit = scanner.nextInt();
                    scanner.nextLine(); //prevent scanner skipping line

                    while (true) {
                        System.out.print(num);
                        for (int i = 1; i < limit; i++) {
                            if (num == 1) break; //end if number reaches 1
                            else if (num % 2 == 0) num = num / 2; //if even divide 2
                            else num = (num * 3) + 1; //if odd multiply 3 add 1
                            System.out.print("..."+num);
                            counter++;
                        }

                        System.out.println("!");
                        System.out.printf(result, counter, num);
                        System.out.println();

                        if (num == 1) break;
                        else { //prompts the user to continue if the calculation didn't reach 1
                            System.out.printf(unfinishedPrompt, limit);
                            System.out.println();
                            String unfinished = scanner.nextLine();

                            if (unfinished.equalsIgnoreCase("no")) break;
                        }
                    }
                }

                case 2 -> { //calculate until end
                    System.out.print(num);

                    while (num != 1) {
                        if (num % 2 == 0) num = num / 2; //if even divide 2
                        else num = (num * 3) + 1; //if odd multiply 3 add 1
                        System.out.print("..."+num);
                        counter++;
                    }

                    System.out.println("!");
                    System.out.printf(result, counter, num);
                    System.out.println();
                }
            }

            System.out.println(againPrompt);
            String again = scanner.nextLine();

            if (again.equalsIgnoreCase("no")) {
                break;
            }
        }
        System.out.println(farewellPrompt);
    }
}