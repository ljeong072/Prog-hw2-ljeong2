/*
 * TCSS 305 - Road Rage
 */
package edu.uw.tcss.app;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.tcss.model.Direction;
import edu.uw.tcss.model.Truck;
import edu.uw.tcss.model.Taxi;
import edu.uw.tcss.model.Light;
import edu.uw.tcss.model.Terrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for class Taxi.
 *
 * @author Lucas Jeong
 * @version 2023 November 8
 */
public class TaxiTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * The number of pokes it takes for an Taxi to revive.
     */
    private static final int DEATH_TIME = 15;

    /**
     * a private taxi object for testing.
     */
    private Taxi TEST_TAXI;

    /**
     * Sets a Taxi object for testing with X and Y fields set to 1 and the direction set to East.
     */
    @BeforeEach
    public void setUp() {
        TEST_TAXI = new Taxi(1, 1, Direction.EAST);
    }

    /** Test method for Taxi constructor. */
    @Test
    public void testTaxiConstructor() {
        final Taxi t = new Taxi(4, 10, Direction.EAST);

        assertEquals(4, t.getX(), "Taxi X coordinate not initialized correctly!");
        assertEquals(10, t.getY(), "Taxi coordinate not initialized correctly!");
        assertEquals(Direction.EAST, t.getDirection(),
                "Taxi direction not initialized correctly!");
        assertEquals(DEATH_TIME, t.getDeathTime(), "Taxi death time not initialized correctly!");
        assertTrue(t.isAlive(), "Taxi isAlive() fails initially!");
    }

    /** Test method for Taxi setters. */
    @Test
    public void testTaxiSetters() {
        final Taxi t = new Taxi(7, 9, Direction.NORTH);

        t.setX(2);
        assertEquals(2, t.getX(), "Taxi setX failed!");
        t.setY(2);
        assertEquals(2, t.getY(), "Taxi setY failed!");
        t.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, t.getDirection(), "Taxi setDirection failed!");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionStraightStreet() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection(),
                "The Taxi should go striaght if given the option (Street).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionLeftStreet() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().left(),
                "The Taxi should go left if going straight is not possible (Street).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionRightStreet() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().right(),
                "The Taxi should go right given straight and left are not viable directions (Street).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionReverseStreet() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();

        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().reverse(),
                "The Taxi should reverse since there are no viable directions (Street).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionStraightCrossWalk() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<>();
        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors1.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection(),
                "The Taxi should go striaght if given the option (CrossWalk).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionLeftCrossWalk() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().left(),
                "The Taxi should go left if going straight is not possible (CrossWalk).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionRightCrossWalk() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().right(),
                "The Taxi should go right given straight and left are not viable directions (CrossWalk).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionReverseCrossWalk() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();

        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().reverse(),
                "The Taxi should reverse since there are no viable directions (CrossWalk).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionStraightLight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<>();
        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.LIGHT);
        neighbors1.put(Direction.EAST, Terrain.LIGHT);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection(),
                "The Taxi should go striaght if given the option (straight).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionLeftLight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.LIGHT);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);

        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().left(),
                "The Taxi should go left if going straight is not possible (light).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionRightLIGHT() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().right(),
                "The Taxi should go right given straight and left are not viable directions (light).");
    }

    /**
     * Test method for {@link Taxi#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionReverseLight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();

        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_TAXI.chooseDirection(neighbors1) == TEST_TAXI.getDirection().reverse(),
                "The Taxi should reverse since there are no viable directions (light).");
    }

    /**
     * Test the setter method for the Taxi's x value.
     */
    @Test
    public void testSetterMethodCarX() {
        setUp();
        TEST_TAXI.setX(11);
        assertEquals(11, TEST_TAXI.getX(), "Car's X field should be 5");

        TEST_TAXI.setX(14);
        assertEquals(14, TEST_TAXI.getX(), "Car's X field should be 22");
    }

    /**
     * Test the setter method for the Taxi's y value.
     */
    @Test
    public void testSetterMethodCar() {
        setUp();
        TEST_TAXI.setY(10);
        assertEquals(10, TEST_TAXI.getY(), "Car's y field should be 10");

        TEST_TAXI.setY(28);
        assertEquals(28, TEST_TAXI.getY(), "Car's Y field should be 28.");
    }

    /**
     * Test the getter method for the Taxi's x value.
     */
    @Test
    public void testGetterMethodCarX() {
        setUp();
        assertEquals(1, TEST_TAXI.getX(), "Car's X field should be initialized to 1.");

        setUp();
        TEST_TAXI.setX(9);
        assertEquals(9, TEST_TAXI.getX(), "Car's X field should be 9");
    }

    /**
     * Test the getter method for the Taxi's y value.
     */
    @Test
    public void testGetterMethodCarY() {
        setUp();
        assertEquals(1, TEST_TAXI.getY(), "Taxi's Y field should be initiaialized to 1 for " +
                "this Taxi test object");

        setUp();
        TEST_TAXI.setY(6);
        assertEquals(6, TEST_TAXI.getY(), "Taxi's Y field should be 6.");
    }

    /**
     * Test the getter method for the Taxi's current direction.
     */
    @Test
    public void testCarGetDirection() {
        setUp();
        assertEquals(Direction.EAST, TEST_TAXI.getDirection(), "Taxi's get direction field should return east.");

        setUp();
        TEST_TAXI.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, TEST_TAXI.getDirection(), "Taxis get direction field should return east.");

        setUp();
        TEST_TAXI.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, TEST_TAXI.getDirection(), "Taxi's get direction field should return south");
        setUp();
        TEST_TAXI.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, TEST_TAXI.getDirection(), "Taxi's get direction field should return north");

        setUp();
        TEST_TAXI.setDirection(Direction.WEST);
        assertEquals(Direction.WEST, TEST_TAXI.getDirection(), "Taxi's get direction field should return west.");
    }

    /**
     * Test the reset method for the Taxi's.
     */
    @Test
    public void testCarResetMethod(){
        final Taxi t = new Taxi(3, 4, Direction.EAST);

        t.setX(10);
        t.setY(10);
        t.setDirection(Direction.EAST);

        t.reset();

        assertEquals(3, t.getX(), "The Taxi should have rest to a value of 7 in the X field");
        assertEquals(4, t.getY(), "The Taxi should have rest to a value of 9 in the Y field");
        assertEquals(Direction.EAST, t.getDirection(), "The Taxi should have direction set to south.");
        assertTrue(t.isAlive(), "The Taxishould be alive at the start.");
    }

    /**
     * Tests whether this method gets the image file of the
     * class depending on whether its dead or alive.
     */
    @Test
    public void testGetImageFileNameMethod(){
        setUp();
        assertEquals("taxi.gif", TEST_TAXI.getImageFileName(), "The returned String should be" +
                "a Taxi.gif and not atv_dead.gif since the truck can never die (in this program).");

        setUp();
        final Truck testtruck = new Truck(2,2, Direction.WEST);
        TEST_TAXI.collide(testtruck);
        assertEquals("taxi_dead.gif", TEST_TAXI.getImageFileName(), "The returned String should be" +
                "a Taxiv_dead.gif.");
    }

    /**
     * Tests whether this method accurately updates the status of the vehicle when it collides with another object.
     * If the death time of this vehicle is greater than the death time of the other object,
     * what is returned is the status set to false (the vehicle is dead).
     */
    @Test
    public void testCollideMethod() {
        setUp();
        final Taxi othera = new Taxi(2,2,Direction.WEST);
        final Truck truck = new Truck(2,2,Direction.WEST);
        TEST_TAXI.collide(othera);
        assertTrue(TEST_TAXI.isAlive(), "This Taxi collided with the same vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        othera.collide(TEST_TAXI);
        assertTrue(othera.isAlive(), "The other Taxi collided with the this atv vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        TEST_TAXI.collide(truck);
        assertFalse(TEST_TAXI.isAlive(), "This Taxi collided with a surperior vehicle (truck)" +
                " so it should be dead, return false");

        setUp();
        truck.collide(TEST_TAXI);
        assertTrue(TEST_TAXI.isAlive(), "A truck collided with an Taxi object, however the collide method" +
                "only updates the status of the vehicle that is being compared to the other vehicle. Thus, truck" +
                "remains alive and so should atv");
    }

    /**
     * Tests the Getter method for the death time parameter.
     */
    @Test
    public void testGetDeathTime(){
        setUp();
        final Taxi taxi1 = new Taxi(3,2,Direction.SOUTH);
        final Taxi taxi2 = new Taxi(9,1,Direction.WEST);
        final Taxi taxi3 = new Taxi(11,7,Direction.EAST);
        final Taxi taxi4 = new Taxi(14,4,Direction.NORTH);

        assertEquals(DEATH_TIME, TEST_TAXI.getDeathTime(), "The test Taxi object should have a " +
                "death time of 15.");

        assertEquals(DEATH_TIME, taxi1.getDeathTime(), "The test taxi1 should have a death time of 15.");

        assertEquals(DEATH_TIME, taxi2.getDeathTime(), "The test taxi2 should have a death time of 15.");

        assertEquals(DEATH_TIME, taxi3.getDeathTime(), "The test taxi3 should have a death time of 15.");

        assertEquals(DEATH_TIME, taxi4.getDeathTime(), "The test taxi4 should have a death time of 15");
    }

    /**
     * Tests whether the vehicle is alive.
     */
    @Test
    public void testIsAlive() {
        setUp();
        final Truck truck = new Truck(1,1,Direction.EAST);
        TEST_TAXI.collide(truck);
        assertFalse(TEST_TAXI.isAlive(), "This Taxi should be dead");

        setUp();
        assertTrue(TEST_TAXI.isAlive(), "This Taxi should be alive");
    }

    /**
     * Tests if it accurately pokes the vehicle the amount of times it needs to revive. It also
     * must check if it is in random direction.
     */
    @Test
    public void poke(){
        setUp();
        final Truck truck = new Truck(1,1,Direction.EAST);
        TEST_TAXI.collide(truck);

        setUp();
        TEST_TAXI.collide(truck);

        for(int i = 0; i <= DEATH_TIME; i++)
        {
            TEST_TAXI.poke();
        }
        assertTrue(TEST_TAXI.isAlive(), "The vehicle should revive after 15 ticks, thus on the 26th tick" +
                "it will revive.");

        setUp();
        TEST_TAXI.collide(truck);

        for(int i = 1; i <= 14; i++)
        {
            TEST_TAXI.poke();
        }
        assertFalse(TEST_TAXI.isAlive(), "The vehicle should not revive after 14 ticks (pokes)");

        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);

        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;

        final Taxi atv = new Taxi(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            setUp();
            TEST_TAXI.collide(truck);

            for(int i = 0; i <= DEATH_TIME; i++) {
                TEST_TAXI.poke();
            }

            if (TEST_TAXI.getDirection() == Direction.WEST) {
                seenWest = true;
            } else if (TEST_TAXI.getDirection() == Direction.NORTH) {
                seenNorth = true;
            } else if (TEST_TAXI.getDirection() == Direction.EAST) {
                seenEast = true;
            } else if (TEST_TAXI.getDirection() == Direction.SOUTH) {
                seenSouth = true;
            }
        }

        assertTrue(seenWest && seenNorth && seenEast && seenSouth,
                "Taxi upon revival failed to randomly select a direction.");
    }

    /**
     * tests if the toString returns the toString for taxi properly
     */
    @Test
    public void testTaxiToString(){
        setUp();
        final String teststring = TEST_TAXI.getClass().getSimpleName().toLowerCase() + ", X position:"
                + TEST_TAXI.getX() + ", Y position:" + TEST_TAXI.getY()
                + ", Direction:" + TEST_TAXI.getDirection() + ", Status: " + TEST_TAXI.isAlive()
                + ", Pokes: 0" + ", Cycle: 0";

        assertEquals(teststring, TEST_TAXI.toString(), "This string should return the class " +
                "followed by position, direction, status, pokes, and cycles.");
    }

    /**
     * Test method for {@link Taxi#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Taxis can move on STREETS, LIGHTS, or to CROSSWALKS.
        // so we need to test all of these.

        // Taxis should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Taxis should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);

        final Taxi taxi = new Taxi(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {

                    // Taxi  can pass STREET under any light condition
                    assertTrue(taxi.canPass(destinationTerrain, currentLightCondition),
                            "Taxi should be able to pass STREET"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.LIGHT) {
                    // Taxi cannot pass LIGHT
                    // if the light is RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse(taxi.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Taxi should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else { //Light is yellow or green
                        assertTrue(taxi.canPass(destinationTerrain, currentLightCondition),
                                "Taxi should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // Taxi cannot pass CROSSWALK
                    // if the light is RED or YELLOW

                    if (currentLightCondition == Light.RED) {
                        assertFalse(taxi.canPass(destinationTerrain, currentLightCondition),
                                "Taxi should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else {
                        assertTrue(taxi.canPass(destinationTerrain, currentLightCondition),
                                "Taxi should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(taxi.canPass(destinationTerrain, currentLightCondition),
                            "Taxi should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }

    @Test
    public void testTaxiCycle(){
        setUp();
        TEST_TAXI.canPass(Terrain.CROSSWALK, Light.RED);
        TEST_TAXI.canPass(Terrain.CROSSWALK, Light.RED);
        TEST_TAXI.canPass(Terrain.CROSSWALK, Light.RED);

        final String teststringcycle3 = TEST_TAXI.getClass().getSimpleName().toLowerCase() + ", X position:"
                + TEST_TAXI.getX() + ", Y position:" + TEST_TAXI.getY()
                + ", Direction:" + TEST_TAXI.getDirection() + ", Status: "  + TEST_TAXI.isAlive()
                + ", Pokes: 0, Cycle: 3";

        final String teststringcycle0 = TEST_TAXI.getClass().getSimpleName().toLowerCase() + ", X position:"
                + TEST_TAXI.getX() + ", Y position:" + TEST_TAXI.getY()
                + ", Direction:" + TEST_TAXI.getDirection() + ", Status: "
                + TEST_TAXI.isAlive() + ", Pokes: 0, Cycle: 0";

        assertEquals(teststringcycle3, TEST_TAXI.toString(), "This string should return the class " +
                "followed by position, direction, status, pokes, and the cycle at 3.");

        TEST_TAXI.canPass(Terrain.CROSSWALK, Light.RED);

        assertEquals(teststringcycle0, TEST_TAXI.toString(), "This string should return the class " +
                "followed by position, direction, status, pokes, and the cycle at 0.");

    }
}
















































































































