import java.util.Scanner;

public class ReverseString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        do {
            System.out.println("Enter a string:");
            String input = scanner.nextLine();

            for (int i = 1; i <= input.length(); i++) {
                System.out.print(input.charAt(input.length()-i));
            }

            System.out.println("\nAgain?");
            String again = scanner.nextLine();
            if (again.equalsIgnoreCase("n")) {
                stop = true;
            }

        } while (!stop);
    }
}