// NOTE: If this import is giving you issues, make sure you
// hover over it and either click the red lightbulb, or press
// ALT + ENTER to "Add Junit4 to Classpath". This will allow
// you to run the test cases!

import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestCases {

    @Test
    public void testFindMax() {
        assertEquals(90, Questions.findMax(new int[]{1,2,3,90,83,23}));
        assertEquals(12345, Questions.findMax(new int[]{999,23,4321,12345,32,1,2,39,2,4,21}));
        assertEquals(1, Questions.findMax(new int[]{0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0}));
    }

    @Test
    public void testFindMin() {
        assertEquals(1, Questions.findMin(new int[]{1,2,3,4,5,6,7}));
        assertEquals(Integer.MIN_VALUE, Questions.findMin(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 1,2,3,4}));
        assertEquals(100, Questions.findMin(new int[]{111111,2333,432432,100,432143}));
    }

    @Test
    public void testFindSum() {
        assertEquals(15, Questions.findSum(new int[]{1,2,3,4,5}));
        assertEquals(25, Questions.findSum(new int[]{3,5,7,10}));
        assertEquals(1, Questions.findSum(new int[]{0,1,0,0,0,0,0,0,0}));
    }

    @Test
    public void testFindAverage() {
        assertEquals(3, Questions.findAverage(new int[]{1,2,3,4,5}));
        assertEquals(5, Questions.findAverage(new int[]{3,5,7,10,0}));
        assertEquals(10, Questions.findAverage(new int[]{2,4,6,8,10,12,14,16,18}));
    }

    @Test
    public void testFizzBuzz() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz"));
        assertEquals(input, Questions.fizzBuzz(15));
        ArrayList<String> inputAgain = new ArrayList<>(Arrays.asList("1","2","fizz","4","buzz"));
        assertEquals(inputAgain, Questions.fizzBuzz(5));
    }

    @Test
    public void testBinarySearch() {
        assertEquals(7, Questions.binarySearch(new int[]{1,2,3,4,5,6,7,8}, 8));
        assertEquals(1, Questions.binarySearch(new int[]{3,4,5,6,7,8,9,10}, 4));
        assertEquals(4, Questions.binarySearch(new int[]{2,4,6,8,10,12,14,16,18}, 10));
    }

    @Test
    public void testCountDuplicates() {
        assertEquals(3, Questions.countDuplicates("Welcome to cs"));
        assertEquals(10, Questions.countDuplicates("This is another homework assignment"));
        assertEquals(3, Questions.countDuplicates("Cereal before milk"));
    }

    @Test
    public void testSumBetween193() {
        assertEquals(5, Questions.sumBetween193(new int[]{1,2,3,4,5,193,1,3,1,193,12}));
        assertEquals(15, Questions.sumBetween193(new int[]{123,432,198,193,15,193}));
        assertEquals(0, Questions.sumBetween193(new int[]{193,193,1,2,3}));
    }

    @Test
    public void testReverseNumber() {
        assertEquals(54321, Questions.reverseNumber(12345));
        assertEquals(12345, Questions.reverseNumber(54321));
        assertEquals(121, Questions.reverseNumber(121));
    }

    @Test
    public void testFindSubstring() {
        assertEquals(true, Questions.findSubstring("needleinhaystack","needle"));
        assertEquals(false, Questions.findSubstring("LurkingEverywhere","spy"));
        assertEquals(true, Questions.findSubstring("ImADuplicate","ImADuplicate"));
        assertEquals(false, Questions.findSubstring("hullabaloo", "love"));
    }

}
