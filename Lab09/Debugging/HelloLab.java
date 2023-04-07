import java.util.Scanner;

public class HelloLab {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello there! Please enter your Lab number in the form LXX:");
        String labNumber = scanner.nextLine();
        System.out.printf("Welcome to %s!",labNumber);
    }
}