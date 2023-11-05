/*
 * TCSS 305 - Road Rage
 */

package edu.uw.tcss.app;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.tcss.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for class Truck.
 *
 * @author Lucas Jeong
 * @version 2023 November 4
 */
public class TruckTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * constant for the death timer of a truck vehicle.
     */
    private static final int DEATH_TIME = 0;

    private Truck TEST_TRUCK;

    /**
     * Sets a truck object for testing with X and Y fields set to 1 and the direction set to north.
     */
    @BeforeEach
    public void setUp() {
        TEST_TRUCK = new Truck(1, 1, Direction.NORTH);
    }

    /** Test method for Truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(10, 11, Direction.NORTH);

        assertEquals(10, t.getX(), "Truck x coordinate not initialized correctly!");
        assertEquals(11, t.getY(), "Truck y coordinate not initialized correctly!");
        assertEquals(Direction.NORTH, t.getDirection(),
                "Truck direction not initialized correctly!");
        assertEquals(DEATH_TIME, t.getDeathTime(), "Truck death time not initialized correctly!");
        assertTrue(t.isAlive(), "Truck isAlive() fails initially!");
    }

    /** Test method for Truck setters. */
    @Test
    public void testHumanSetters() {
        final Truck t = new Truck(10, 11, Direction.NORTH);

        t.setX(12);
        assertEquals(12, t.getX(), "Truck setX failed!");
        t.setY(13);
        assertEquals(13, t.getY(), "Truck setY failed!");
        t.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, t.getDirection(), "Atv setDirection failed!");
    }

    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Truck can move on STREETS, LIGHTS, or to CROSSWALKS.
        // so we need to test all of these.

        // Trucks should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Truck should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);

        final Truck truck = new Truck(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET || destinationTerrain == Terrain.LIGHT) {

                    // Trucks can pass STREET and LIGHT under any light condition
                    assertTrue(truck.canPass(destinationTerrain, currentLightCondition),
                            "Truck should be able to pass STREET and LIGHT"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // TRUCKS cannot pass CROSSWALK
                    // if the light is RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse(truck.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Truck should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else { //Light is yellow or green
                        assertTrue(truck.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Truck should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(truck.canPass(destinationTerrain, currentLightCondition),
                            "Truck should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionSurroundedByValidOptions() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);

        neighbors.put(Direction.WEST, Terrain.LIGHT);
        neighbors.put(Direction.NORTH, Terrain.LIGHT);
        neighbors.put(Direction.EAST, Terrain.LIGHT);
        neighbors.put(Direction.SOUTH, Terrain.LIGHT);


        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);

        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;

        final Truck truck = new Truck(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);

            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }

        assertTrue(seenWest && seenNorth && seenEast,
                "Truck chooseDirection() fails to select randomly "
                        + "among all possible valid choices!");

        assertFalse(seenSouth,
                "Truck chooseDirection() reversed direction when not necessary!");
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnTruckTerrainMustReverse() {

        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t !=Terrain.LIGHT) {

                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.GRASS);

                final Truck human = new Truck(0, 0, Direction.NORTH);

                // the Human must reverse and go SOUTH
                assertEquals(Direction.SOUTH, human.chooseDirection(neighbors),
                        "Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!");
            }
        }
    }

    /**
     * Test the setter method for the truck's x value.
     */
    @Test
    public void testSetterMethodTruckX() {
        setUp();
        TEST_TRUCK.setX(5);
        assertEquals(5, TEST_TRUCK.getX(), "Truck's X field should be 5");

        TEST_TRUCK.setX(22);
        assertEquals(22, TEST_TRUCK.getX(), "Truck's X field should be 22");
    }

    /**
     * Test the setter method for the truck's y value.
     */
    @Test
    public void testSetterMethodTruckY() {
        setUp();
        TEST_TRUCK.setY(7);
        assertEquals(7, TEST_TRUCK.getY(), "Truck's y field should be 7");

        TEST_TRUCK.setY(26);
        assertEquals(26, TEST_TRUCK.getY(), "Truck's Y field should be 26.");
    }

    /**
     * Test the getter method for the truck's x value.
     */
    @Test
    public void testGetterMethodTruckX() {
        setUp();
        assertEquals(1, TEST_TRUCK.getX(), "Truck's X field should be initialized to 1 for this Truck" +
                "test object");

        setUp();
        TEST_TRUCK.setX(10);
        assertEquals(10, TEST_TRUCK.getX(), "Truck's X field should be 10");
    }

    /**
     * Test the getter method for the truck's y value.
     */
    @Test
    public void testGetterMethodTruckY() {
        setUp();
        assertEquals(1, TEST_TRUCK.getY(), "Truck's Y field should be initiaialized to 1 for this Truck" +
                "test object");

        setUp();
        TEST_TRUCK.setY(15);
        assertEquals(15, TEST_TRUCK.getY(), "Truck's Y field should be 15.");
    }

    /**
     * Test the getter method for the truck's current direction.
     */
    @Test
    public void testTruckDirection() {
        setUp();
        assertEquals(Direction.NORTH, TEST_TRUCK.getDirection(), "Truck's get direction field should return north.");

        setUp();
        TEST_TRUCK.setDirection(Direction.WEST);
        assertEquals(Direction.WEST, TEST_TRUCK.getDirection(), "Truck's get direction field should return west.");

        setUp();
        TEST_TRUCK.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, TEST_TRUCK.getDirection(), "Truck's get direction field should return east.");

        setUp();
        TEST_TRUCK.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, TEST_TRUCK.getDirection(), "Truck's get direction field should return south");
        setUp();
        TEST_TRUCK.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, TEST_TRUCK.getDirection(), "Truck's get direction field should return north");
    }

    /**
     * Test the reset method for the truck's.
     */
    @Test
    public void testTruckResetMethod(){
        Truck t = new Truck(7, 9, Direction.SOUTH);

        t.setX(5);
        t.setY(2);
        t.setDirection(Direction.NORTH);

        t.reset();

        assertEquals(7, t.getX(), "The truck should have rest to a value of 7 in the X field");
        assertEquals(9, t.getY(), "The truck should have rest to a value of 9 in the Y field");
        assertEquals(Direction.SOUTH, t.getDirection(), "The truck should have direction set to south.");
        assertTrue(t.isAlive(), "The truck should be alive at the start.");
    }

    /**
     * Tests whether this method gets the image file of the
     * class depending on whether its dead or alive.
     */
    @Test
    public void testGetImageFileNameMethod(){
        setUp();
        assertEquals("truck.gif", TEST_TRUCK.getImageFileName(), "The returned String should be" +
                "a truck.gif and not truck_dead.gif since the truck can never die (in this program).");
    }

    /**
     * Tests whether this method accurately updates the status of the vehicle when it collides with another object.
     * If the death time of this vehicle is greater than the death time of the other object,
     * what is returned is the status set to false (the vehicle is dead).
     */
    @Test
    public void testCollideMethod() {
        setUp();
        Truck othert = new Truck(1,1,Direction.NORTH);
        TEST_TRUCK.collide(othert);
        assertTrue(TEST_TRUCK.isAlive(), "These vehicles are both trucks so this truck vehicle should not die");

        othert.collide(TEST_TRUCK);
        assertTrue(othert.isAlive(), "These vehicles are both trucks so the other truck vehicle should not be die.");
    }

    /**
     * Tests the Getter method for the death time parameter.
     */
    @Test
    public void testGetDeathTime(){
        setUp();
        Truck truck1 = new Truck(3,2,Direction.SOUTH);
        Truck truck2 = new Truck(9,1,Direction.WEST);
        Truck truck3 = new Truck(11,7,Direction.EAST);
        Truck truck4 = new Truck(14,4,Direction.NORTH);

        assertEquals(DEATH_TIME, TEST_TRUCK.getDeathTime(), "The test truck should have a death time of 0.");

        assertEquals(DEATH_TIME, truck1.getDeathTime(), "The test truck1 should have a death time of 0.");

        assertEquals(DEATH_TIME, truck2.getDeathTime(), "The test truck2 should have a death time of 0.");

        assertEquals(DEATH_TIME, truck3.getDeathTime(), "The test truck3 should have a death time of 0.");

        assertEquals(DEATH_TIME, truck4.getDeathTime(), "The test truck4 should have a death time of 0");
    }

    /**
     * Tests whether the vehicle is alive. Since Trucks cannot die within the context of this program,
     * it should always return true.
     */
    @Test
    public void testIsAlive() {
        setUp();
        Truck othert = new Truck(1,1,Direction.NORTH);
        TEST_TRUCK.collide(othert);
        assertTrue(TEST_TRUCK.isAlive(), "These vehicles are both trucks so this truck vehicle should not die");

        othert.collide(TEST_TRUCK);
        assertTrue(othert.isAlive(), "These vehicles are both trucks so the other truck vehicle should not be die.");
    }
}
