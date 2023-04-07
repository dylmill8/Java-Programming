package CS180.Week03.Lab03.src.Walkthrough;

import java.util.Scanner;

public class IntroduceMe {
    public static void main(String[] args) {
        //gathering user input using Scanner
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scan.nextLine();
        System.out.println("Enter your major: ");
        String major = scan.nextLine();
        System.out.println("Enter the number of credits you are taking: ");
        int numbCredits = scan.nextInt();
        System.out.println("Enter your GPA: ");
        double GPA = scan.nextDouble();
        //Since we are scanning a string after this double we call nextLine() to prevent scanner from skipping an input
        scan.nextLine();
        System.out.println("Enter your previous experience: ");
        String experience = scan.nextLine();
        System.out.println("Enter your hobby: ");
        String hobby = scan.nextLine();

        //Outputting the user's input
        System.out.println("Hello! My name is "+name+".");
        System.out.println("I am majoring in "+major+".");
        System.out.println("I am currently taking "+numbCredits+" credits.");
        System.out.print("My GPA is ");
        //Outputting 2 decimal places using formatting string
        System.out.printf("%.2f",GPA);
        System.out.print(".\n");
        System.out.println("Before coming to Purdue, I was "+experience+".");
        System.out.println("I like to spend my free time "+hobby+".");
    }
}