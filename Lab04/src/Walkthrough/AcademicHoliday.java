import java.util.Scanner;

/**
 * A program that prints the holiday associated with the selected month in the 2021-2022 academic year.
 *
 * Purdue University -- CS18000 -- Spring 2022 -- Homework 04 -- Walkthrough
 *
 * @author Purdue CS
 * @version January 10, 2021
 */

public class AcademicHoliday {
    public static void main(String[] args) {
        String holiday = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Pick a number between 1-6 to select a holiday!");
        System.out.println("1. July");
        System.out.println("2. September");
        System.out.println("3. October");
        System.out.println("4. November");
        System.out.println("5. January");
        System.out.println("6. March");

        // TODO: Implement your program below. Use the given print statements as output.
        int input = scanner.nextInt();
        boolean valid = true;
        switch (input) {
            case 1 -> holiday = "Independence Day Holiday!";
            case 2 -> holiday = "Labor Day!";
            case 3 -> holiday = "October Break!";
            case 4 -> holiday = "Thanksgiving Vacation!";
            case 5 -> holiday = "Martin Luther King Jr. Day!";
            case 6 -> holiday = "Spring Vacation!";
            default -> valid = false;
            }

        if (valid) {
            System.out.printf("The holiday is: %s!\n", holiday);
        } else {
            System.out.println("That's not a valid number!");
        }
    }
}
