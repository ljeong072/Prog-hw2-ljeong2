/*
 * TCSS 305 - Road Rage
 */
package edu.uw.tcss.app;

import edu.uw.tcss.model.Direction;
import edu.uw.tcss.model.Light;
import edu.uw.tcss.model.Terrain;
import edu.uw.tcss.model.Atv;
import edu.uw.tcss.model.Truck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for class Atv.
 *
 * @author Lucas Jeong
 * @version 2023 November 4
 */
public class AtvTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 500;

    /**
     * The number of pokes it takes for an atv to revive.
     */
    private static final int DEATH_TIME = 25;

    private Atv TEST_ATV;

    /**
     * Sets a Atb object for testing with X and Y fields set to 2 and the direction set to WEST.
     */
    @BeforeEach
    public void setUp() {
        TEST_ATV = new Atv(2, 2, Direction.WEST);
    }

    /** Test method for Atv constructor. */
    @Test
    public void testATVConstructor() {
        final Atv a = new Atv(8, 5, Direction.SOUTH);

        assertEquals(8, a.getX(), "Atv X coordinate not initialized correctly!");
        assertEquals(5, a.getY(), "Atv coordinate not initialized correctly!");
        assertEquals(Direction.SOUTH, a.getDirection(),
                "Atv direction not initialized correctly!");
        assertEquals(DEATH_TIME, a.getDeathTime(), "Atv death time not initialized correctly!");
        assertTrue(a.isAlive(), "Atv isAlive() fails initially!");
    }

    /** Test method for Atv setters. */
    @Test
    public void testAtvSetters() {
        final Atv a = new Atv(4, 5, Direction.SOUTH);

        a.setX(10);
        assertEquals(10, a.getX(), "Atv setX failed!");
        a.setY(2);
        assertEquals(2, a.getY(), "Atv setY failed!");
        a.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, a.getDirection(), "Atv setDirection failed!");
    }

    /**
     * Test method for {@link Atv#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Atv can move on any terrain that is not a wall.
        // so we need to test all of these.

        // The Atv should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Atv should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.GRASS);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.TRAIL);

        final Atv atv = new Atv(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain != Terrain.WALL) {

                    // Atv can pass any terrain under any light condition
                    assertTrue(atv.canPass(destinationTerrain, currentLightCondition),
                            "Atv should be able to pass STREET and LIGHT"
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }

    /**
     * Test method for {@link Atv#chooseDirection(java.util.Map)}.
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

        final Atv atv = new Atv(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = atv.chooseDirection(neighbors);

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
                "Atv chooseDirection() fails to select randomly "
                        + "among all possible valid choices!");

        assertFalse(seenSouth,
                "Atv chooseDirection() reversed direction when not necessary!");
    }

    /**
     * Test the setter method for the atv's x value.
     */
    @Test
    public void testSetterMethodAtvX() {
        setUp();
        TEST_ATV.setX(11);
        assertEquals(11, TEST_ATV.getX(), "Atv's X field should be 5");

        TEST_ATV.setX(14);
        assertEquals(14, TEST_ATV.getX(), "Atv's X field should be 22");
    }

    /**
     * Test the setter method for the truck's y value.
     */
    @Test
    public void testSetterMethodAtv() {
        setUp();
        TEST_ATV.setY(10);
        assertEquals(10, TEST_ATV.getY(), "Atv's y field should be 10");

        TEST_ATV.setY(28);
        assertEquals(28, TEST_ATV.getY(), "Atv's Y field should be 28.");
    }

    /**
     * Test the getter method for the truck's x value.
     */
    @Test
    public void testGetterMethodAtvX() {
        setUp();
        assertEquals(2, TEST_ATV.getX(), "Atv's X field should be initialized to 1 for this Truck" +
                "test object");

        setUp();
        TEST_ATV.setX(9);
        assertEquals(9, TEST_ATV.getX(), "Atv's X field should be 9");
    }

    /**
     * Test the getter method for the atv's y value.
     */
    @Test
    public void testGetterMethodAtvY() {
        setUp();
        assertEquals(2, TEST_ATV.getY(), "Atv's Y field should be initiaialized to 2 for " +
                "this ATV test object");

        setUp();
        TEST_ATV.setY(6);
        assertEquals(6, TEST_ATV.getY(), "Atv's Y field should be 6.");
    }

    /**
     * Test the getter method for the atv's current direction.
     */
    @Test
    public void testAtvDirection() {
        setUp();
        assertEquals(Direction.WEST, TEST_ATV.getDirection(), "ATV's get direction field should return west.");

        setUp();
        TEST_ATV.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, TEST_ATV.getDirection(), "Atv's get direction field should return east.");

        setUp();
        TEST_ATV.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, TEST_ATV.getDirection(), "Atv's get direction field should return south");
        setUp();
        TEST_ATV.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, TEST_ATV.getDirection(), "Atv's get direction field should return north");

        setUp();
        TEST_ATV.setDirection(Direction.WEST);
        assertEquals(Direction.WEST, TEST_ATV.getDirection(), "Atv's get direction field should return west.");

    }

    /**
     * Test the reset method for the atv's.
     */
    @Test
    public void testAtvResetMethod(){
        Atv a = new Atv(3, 4, Direction.EAST);

        a.setX(10);
        a.setY(10);
        a.setDirection(Direction.EAST);

        a.reset();

        assertEquals(3, a.getX(), "The Atv should have rest to a value of 7 in the X field");
        assertEquals(4, a.getY(), "The Atv should have rest to a value of 9 in the Y field");
        assertEquals(Direction.EAST, a.getDirection(), "The Atv should have direction set to south.");
        assertTrue(a.isAlive(), "The atv should be alive at the start.");
    }

    /**
     * Tests whether this method gets the image file of the
     * class depending on whether its dead or alive.
     */
    @Test
    public void testGetImageFileNameMethod(){
        setUp();
        assertEquals("atv.gif", TEST_ATV.getImageFileName(), "The returned String should be" +
                "a atv.gif and not atv_dead.gif since the truck can never die (in this program).");

        setUp();
        Truck testtruck = new Truck(2,2, Direction.WEST);
        TEST_ATV.collide(testtruck);
        assertEquals("atv_dead.gif", TEST_ATV.getImageFileName(), "The returned String should be" +
                "a atv_dead.gift and not atv.gif or some other gif since the atv is killed.");
    }

    /**
     * Tests whether this method accurately updates the status of the vehicle when it collides with another object.
     * If the death time of this vehicle is greater than the death time of the other object,
     * what is returned is the status set to false (the vehicle is dead).
     */
    @Test
    public void testCollideMethod() {
        setUp();
        Atv othera = new Atv(2,2,Direction.WEST);
        Truck truck = new Truck(2,2,Direction.WEST);
        TEST_ATV.collide(othera);
        assertTrue(TEST_ATV.isAlive(), "This ATV collided with the same vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        othera.collide(TEST_ATV);
        assertTrue(othera.isAlive(), "The other ATV collided with the this atv vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        TEST_ATV.collide(truck);
        assertFalse(TEST_ATV.isAlive(), "This ATV collided with a surperior vehicle (truck)" +
                " so it should be dead, return false");

        setUp();
        truck.collide(TEST_ATV);
        assertTrue(TEST_ATV.isAlive(), "A truck collided with an ATV object, however the collide method" +
                "only updates the status of the vehicle that is being compared to the other vehicle. Thus, truck" +
                "remains alive and so should atv");
    }

    /**
     * Tests the Getter method for the death time parameter.
     */
    @Test
    public void testGetDeathTime(){
        setUp();
        Atv atv1 = new Atv(3,2,Direction.SOUTH);
        Atv atv2 = new Atv(9,1,Direction.WEST);
        Atv atv3 = new Atv(11,7,Direction.EAST);
        Atv atv4 = new Atv(14,4,Direction.NORTH);

        assertEquals(DEATH_TIME, TEST_ATV.getDeathTime(), "The test atv object should have a " +
                "death time of 25.");

        assertEquals(DEATH_TIME, atv1.getDeathTime(), "The test atv1 should have a death time of 25.");

        assertEquals(DEATH_TIME, atv2.getDeathTime(), "The test atv2 should have a death time of 25.");

        assertEquals(DEATH_TIME, atv3.getDeathTime(), "The test atv3 should have a death time of 25.");

        assertEquals(DEATH_TIME, atv4.getDeathTime(), "The test atv4 should have a death time of 25");
    }

    /**
     * Tests whether the vehicle is alive.
     */
    @Test
    public void testIsAlive() {
        setUp();
        Truck truck = new Truck(2,2,Direction.WEST);
        TEST_ATV.collide(truck);
        assertFalse(TEST_ATV.isAlive(), "This atv should be dead");

        setUp();
        assertTrue(TEST_ATV.isAlive(), "This atv should be alive");
    }

    /**
     * Tests if it accurately pokes the vehicle the amount of times it needs to revive. It also
     * must check if it is in random direction.
     */
    @Test
    public void poke(){
        setUp();
        Truck truck = new Truck(2,2,Direction.WEST);
        TEST_ATV.collide(truck);

        setUp();
        TEST_ATV.collide(truck);

        for(int i = 0; i <= DEATH_TIME; i++)
        {
            TEST_ATV.poke();
        }
        assertTrue(TEST_ATV.isAlive(), "The vehicle should revive after 25 ticks, thus on the 26th tick" +
                "it will revive.");

        setUp();
        TEST_ATV.collide(truck);

        for(int i = 1; i <= 24; i++)
        {
            TEST_ATV.poke();
        }
        assertFalse(TEST_ATV.isAlive(), "The vehicle should not revive after 25 ticks (pokes)");

        setUp();
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

        final Atv atv = new Atv(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            setUp();
            TEST_ATV.collide(truck);

            for(int i = 0; i <= DEATH_TIME; i++) {
                TEST_ATV.poke();
            }

            if (TEST_ATV.getDirection() == Direction.WEST) {
                seenWest = true;
            } else if (TEST_ATV.getDirection() == Direction.NORTH) {
                seenNorth = true;
            } else if (TEST_ATV.getDirection() == Direction.EAST) {
                seenEast = true;
            } else if (TEST_ATV.getDirection() == Direction.SOUTH) {
                seenSouth = true;
            }
        }

        assertTrue(seenWest && seenNorth && seenEast && seenSouth,
                "Atv upon revival failed to randomly select a direction.");
    }

    /**
     * tests if the toString returns the toString for Atv properly
     */
    @Test
    public void testAtvToString(){
        setUp();
        String teststring = TEST_ATV.toString();

        assertEquals(teststring, TEST_ATV.toString(), "This string should return the class " +
                "followed by position, direction, and status.");
    }
}
