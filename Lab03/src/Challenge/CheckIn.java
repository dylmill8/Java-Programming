package CS180.Week03.Lab03.src.Challenge;

import java.util.Scanner;

/**
 * Lab03Challenge -- PUSH Patient Check-in
 *
 * This program inputs the user's name, mobile number,
 * ID number, insurance provider, body temperature,
 * blood pressure, heart rate, and the time of day.
 * The program outputs strings describing the patients'
 * information.
 *
 * @author Dylan Miller, lab sec L11
 *
 * @version September 9, 2022
 *
 */

public class CheckIn {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Student Full Name:");
        String name = scan.nextLine();
        name = name.toUpperCase();
        String[] firstName = name.split(" ");

        System.out.println("Enter Student Mobile Number:");
        String phone = scan.nextLine();
        //splits the phone number by the dashes
        String[] phoneArray = phone.split("-");
        //converts the 3 segments of the phone number into integers, sums them, and stores the sum
        int phoneCode;
        phoneCode = Integer.parseInt(phoneArray[0]) + Integer.parseInt(phoneArray[1]) + Integer.parseInt(phoneArray[2]);

        System.out.println("Enter Student ID Number (10 digits):");
        String studentID = scan.nextLine();
        //keeps only the last 3 digits of the student's ID number
        studentID = studentID.substring(studentID.length() - 3);

        System.out.println("Enter Insurance Provider Name:");
        String insurance = scan.nextLine();
        //returns the ascii value of each character in the string and converts them to an array of integers
        int[] insuranceArray = insurance.chars().toArray();
        //takes the first two characters' ascii values, converts to strings, and concatenates them together
        String insuranceCode = insuranceArray[0] + Integer.toString(insuranceArray[1]);

        System.out.println("Enter Time of Day:");
        int time24 = scan.nextInt();
        //subtracts 1200 (12 hours) if the military time is 1300 or greater (1:00)
        if (time24 >= 1300 ) {
            time24 = time24 - 1200;
        }
        String time12 = Integer.toString(time24);
        if (time12.length() < 4) {
            //loops until there are 4 digits in the time
            for (int i = time12.length() ; i < 4 ; i++) {
                //adds "0" as a placeholder for times with less than 4 digits
                time12 = "0" + time12;
            }
        }
        //adds a ":" after the second digit
        time12 = time12.substring(0 , 2) + ":" + time12.substring(2);

        System.out.println("Enter Body Temperature (In F):");
        double tempF = scan.nextDouble();
        //converts from Fahrenheit to Celsius
        double tempC = (tempF - 32) * (5 / 9.0);
        //prevents scanner from skipping a line
        scan.nextLine();

        System.out.println("Enter Blood Pressure:");
        String bloodPressure = scan.nextLine();

        System.out.println("Enter Heart Rate:");
        String heartRate = scan.nextLine();

        System.out.println("Code: " + firstName[0] + "|" + phoneCode + "|" + studentID + "|" + insuranceCode);
        System.out.println("Time: " + time12);
        System.out.print("Vitals: ");
        System.out.printf("%.2f" , tempF);
        System.out.print("F|");
        System.out.printf("%.2f" , tempC);
        System.out.print("C|" + bloodPressure + "|" + heartRate);
    }
}