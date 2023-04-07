/*
* CS 193 Homework 3
*
* TODO: READ THESE INSTRUCTIONS!
*
* The comments in the methods are for clarification. No tricks or riddles.
*
* The overall algorithm should not change. Don't focus on improving the runtime. 
*
* Debug in whichever way you feel is best.
*
* IT WILL BE AN AUTOMATIC 0, IF YOU MAKE CHANGES TO TESTCASES.JAVA
*
* Bonus: Understand these methods! They are helpful in interviews :)
*
*/

import java.util.*;

public class Questions {

    // Task 1
    public static int findMax(int[] input) {
        // find the max in the input array
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
        }
        return max;
    }

    // Task 2
    public static int findMin(int[] input) {
        // find the smallest element in the array
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (input[i] < min) {
                min = input[i];
            }
        }
        return min;
    }

    // Task 3
    public static int findSum(int[] input) {
        // find the sum of all the elements in the array
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum = sum + input[i];
        }
        return sum;
    }

    // Task 4
    public static int findAverage(int[] input) {
        // find the average of the input
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum = sum + input[i];
        }
        int average = sum / (input.length);
        return average;
    }

    // Task 5
    public static ArrayList<String> fizzBuzz(int n) {
        // EXPLANATION: RETURN A LIST 1 TO n BUT
        // Multiples OF 3 ARE REPLACED BY "fizz"
        // Multiples OF 5 ARE REPLACED BY "buzz"
        // Multiples OF 3 AND 5 ARE REPLACED BY "fizzbuzz"
        //
        // FROM 1 TO 15 THE LIST WOULD LOOK LIKE
        // {"1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz"}
        // Note: In java an array is different from a list
        // The data structure we will be using is an ArrayList

        ArrayList<String> answer = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                answer.add("fizzbuzz");
            } else if (i % 5 == 0) {
                answer.add("buzz");
            } else if (i % 3 == 0) {
                answer.add("fizz");
            } else {
                answer.add(Integer.toString(i));
            }
        }
        return answer;
    }
    
    // Task 6
    public static int reverseNumber(int input) {
        // reverse the number
        // 12345 should become 54321
        // Hint: How would you turn 9 into 95? Not by adding 86
        String answer = "";
        while (input != 0) {
            int digit = input % 10;
            answer = answer + digit;
            input = input / 10;
        }
        return Integer.parseInt(answer);
    }
    
    //EXTRA CREDIT BELOW HERE
    
    // Task 7
    // Look for a specific element in sorted array
    // keep in mind for this algorithm to work, array HAS to be sorted
    public static int binarySearch(int[] input, int target) {
        // look for the index of target in input
        int low = 0;
        int high = input.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (input[mid] == target) { // middle element is the target. Success!!!
                return mid;
            } else if (input[mid] > target) { // middle element is greater than the target
                high = mid - 1;
            } else { // middle element is smaller than the target
                low = mid + 1;
            }
        }
        return -1; // element is not found
    }
    
    // Task 8
    public static int countDuplicates(String input) {
        // Count the number of letters that are duplicated
        // The input string will always be lower case and contain no numbers
        // Example: Input: This is homework, Return: 3 (letters i, s, and o occur more than once

        input = input.toLowerCase(); // ensuring string is lower case
        int[] alphabetTemplate = new int[26];
        for (int i = 0; i < input.length(); i++) {// iterate over the string
            int index = input.charAt(i) - 'a'; // Math in ASCII tables.
                if (index >= 0) { //ignores spaces (negative index value)
                    alphabetTemplate[index] += 1;
                }
        }
        int counter = 0;
        for (int i = 0; i < alphabetTemplate.length; i++) {
            if (alphabetTemplate[i] > 1) {
                counter = counter + 1;
            }
        }
        return counter;
    }

    // Task 9
    public static int sumBetween193(int[] input) {
        // Sum up numbers between the first 193 and the next 193 non-inclusive
        int sum = 0;
        boolean startCounting = false;
        for (int i = 0; i < input.length; i++) {
            if (startCounting) {
                if (input[i] == 193) { // Checks for end condition
                    startCounting = false;
                    break;
                }
                sum = sum + input[i];
                if (input[i] == 193) {
                    startCounting = false;
                    break;
                }
            } else {
                if(input[i] == 193) {
                    startCounting = true;
                }
            }
        }
        return sum;
    }

    // Task 10
    public static boolean findSubstring(String theBigOne, String sub) {
        // checks to see if variable sub appears in theBigOne
        // highly recommended to write this one out on a notebook
        int counter = 0;
        for (int i = 0; i < theBigOne.length(); i++) {
            if (theBigOne.charAt(i) == sub.charAt(0)) {
                for (int j = 1; j < theBigOne.length(); j++) {
                    if (theBigOne.charAt(j) == sub.charAt(j)) {
                        counter += 1;
                    } else { // a character didn't match so break
                        break;
                    }
                    if (counter == sub.length()-1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // Main method is used for testing purposes
    public static void main(String[] args) {
    }
}
