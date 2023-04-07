package CS180.Week03.Lab03.src.Debugging;

import java.util.Scanner;

public class Debug {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String firstName = scanner.next();
        String lastName = scanner.next();
        System.out.println("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter your position: ");
        String job = scanner.nextLine();

        System.out.println(firstName + " " + lastName + "\nAge: " + age + "\nOccupation: " + job);
    }
}