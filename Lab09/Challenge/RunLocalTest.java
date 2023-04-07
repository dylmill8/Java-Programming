import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Purdue CS
     * @version June 13, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testAmusement() {
            try {
                ArrayList<Ride> expectedRides = new ArrayList<Ride>();
                expectedRides.add(new Rollercoaster("Space Dome", "Black", 48, 20, false));
                expectedRides.add(new Rollercoaster("Speed Racing", "Black", 48, 16, false));
                expectedRides.add(new Rollercoaster("Mover of People", "Blue", 36, 32, true));
                AmusementPark testAmusement = new AmusementPark("The Valley Adventure", 10.5, 100,
                        expectedRides, true, true, false, false, new boolean[]{true, true, true, true});
                String actName = testAmusement.getName();
                double actAdmissionCost = testAmusement.getAdmissionCost();
                double actLand = testAmusement.getLand();
                ArrayList<Ride> actRides = testAmusement.getRides();
                boolean actIndoor = testAmusement.isIndoor();
                boolean actOutdoor = testAmusement.isOutdoor();
                boolean actArcade = testAmusement.isArcade();
                boolean actBowling = testAmusement.isBowling();
                boolean[] actSeasons = testAmusement.getSeasons();
                assertEquals("Ensure your getName() method in AmusementPark.java returns the correct value!", "The Valley Adventure", actName);
                assertEquals("Ensure your getAdmissionCost() method in AmusementPark.java returns the correct value!", 10.5, actAdmissionCost, 0.01);
                assertEquals("Ensure your getLand() method in AmusementPark.java returns the correct value!", 100, actLand, 0.01);
                ArrayList<Ride> expectedRidesCopy = new ArrayList<Ride>();
                for (Ride r : expectedRides) {
                    expectedRidesCopy.add(r);
                }
                assertEquals("Ensure your isIndoor() method in AmusementPark.java returns the correct value!", true, actIndoor);
                assertEquals("Ensure your isOutdoor() method in AmusementPark.java returns the correct value!", true, actOutdoor);
                assertEquals("Ensure your isArcade() method in AmusementPark.java returns the correct value!", false, actArcade);
                assertEquals("Ensure your isBowling() method in AmusementPark.java returns the correct value!", false, actBowling);
                for (int i = 0; i < 4; i++) {
                    assertEquals("Ensure your getSeasons() method in AmusementPark.java returns the correct value!", true, actSeasons[i]);
                }
                testAmusement.setName("The Super Valley Adventure");
                testAmusement.setAdmissionCost(12.80);
                testAmusement.setArcade(true);
                testAmusement.setBowling(true);
                testAmusement.setSeasons(new boolean[]{true, true, true, false});
                assertEquals("Ensure your setName() method in AmusementPark.java sets name to the correct value!", "The Super Valley Adventure", testAmusement.getName());
                assertEquals("Ensure your setAdmissionCost() method in AmusementPark.java sets admission cost to the correct value!", 12.80, testAmusement.getAdmissionCost(), 0.01);
                assertEquals("Ensure your setArcade() method in AmusementPark.java sets arcade to the correct value!", true, testAmusement.isArcade());
                assertEquals("Ensure your setBowling() method in AmusementPark.java sets bowling to the correct value!", true, testAmusement.isBowling());
                for (int i = 0; i < 4; i++) {
                    boolean expectedSeasons = true;
                    if (i == 3) {
                        expectedSeasons = false;
                    }
                    assertEquals("Ensure your setSeasons() method in AmusementPark.java sets seasons to the correct value!", expectedSeasons, testAmusement.getSeasons()[i]);
                }
                Ride newRide = new Rollercoaster("Testing Track", "Orange", 42, 16, false);
                expectedRidesCopy.add(newRide);
                try {
                    testAmusement.addRide(newRide);
                } catch (WrongRideException e) {
                    fail("Ensure your addRide() method throws a WrongRideException in the correct situations!");
                }
                actRides = testAmusement.getRides();
                for (int i = 0; i < expectedRidesCopy.size(); i++) {
                    String expectedName = expectedRidesCopy.get(i).getName();
                    String expectedColor = expectedRidesCopy.get(i).getColor();
                    int expectedMinHeight = expectedRidesCopy.get(i).getMinHeight();
                    int expectedMaxRiders = expectedRidesCopy.get(i).getMaxRiders();
                    actName = actRides.get(i).getName();
                    String actColor = actRides.get(i).getColor();
                    int actMinHeight = actRides.get(i).getMinHeight();
                    int actMaxRiders = actRides.get(i).getMaxRiders();
                    assertEquals("Ensure your addRide() method in AmusementPark.java updates the rides instance variable to be a list of rides with the proper names!", expectedName, actName);
                    assertEquals("Ensure your addRide() method in AmusementPark.java updates the rides instance variable to be a list of rides with the proper colors!", expectedColor, actColor);
                    assertEquals("Ensure your addRide() method in AmusementPark.java updates the rides instance variable to be a list of rides with the proper minHeight values!", expectedMinHeight, actMinHeight);
                    assertEquals("Ensure your addRide() method in AmusementPark.java updates the rides instance variable to be a list of rides with the proper maxRiders values!", expectedMaxRiders, actMaxRiders);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }
}

