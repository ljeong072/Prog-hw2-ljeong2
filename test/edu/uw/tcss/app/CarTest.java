package edu.uw.tcss.app;
/*
 * TCSS 305 - Road Rage
 */
import edu.uw.tcss.model.Direction;
import edu.uw.tcss.model.Light;
import edu.uw.tcss.model.Terrain;
import edu.uw.tcss.model.Car;
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
 * Unit tests for class Car.
 *
 * @author Lucas Jeong
 * @version 2023 November 4
 */

public class CarTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;

    /**
     * The number of pokes it takes for an atv to revive.
     */
    private static final int DEATH_TIME = 15;

    private Car TEST_CAR;

    /**
     * Sets a Car object for testing with X and Y fields set to 1 and the direction set to East.
     */
    @BeforeEach
    public void setUp() {
        TEST_CAR = new Car(1, 1, Direction.EAST);
    }

    /** Test method for Atv constructor. */
    @Test
    public void testATVConstructor() {
        final Car c = new Car(4, 10, Direction.EAST);

        assertEquals(4, c.getX(), "Atv X coordinate not initialized correctly!");
        assertEquals(10, c.getY(), "Atv coordinate not initialized correctly!");
        assertEquals(Direction.EAST, c.getDirection(),
                "Atv direction not initialized correctly!");
        assertEquals(DEATH_TIME, c.getDeathTime(), "Atv death time not initialized correctly!");
        assertTrue(c.isAlive(), "Atv isAlive() fails initially!");
    }

    /** Test method for Car setters. */
    @Test
    public void testAtvSetters() {
        final Car c = new Car(2, 9, Direction.NORTH);

        c.setX(2);
        assertEquals(2, c.getX(), "Car setX failed!");
        c.setY(2);
        assertEquals(2, c.getY(), "Car setY failed!");
        c.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, c.getDirection(), "Atv setDirection failed!");
    }

    /**
     * Test method for {@link Car#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Cars can move on STREETS, LIGHTS, or to CROSSWALKS.
        // so we need to test all of these.

        // Cars should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Cars should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);

        final Car car = new Car(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {

                    // Cars  can pass STREET under any light condition
                    assertTrue(car.canPass(destinationTerrain, currentLightCondition),
                            "Car should be able to pass STREET"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.LIGHT) {
                    // Cars cannot pass LIGHT
                    // if the light is RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse(car.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Truck should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else { //Light is yellow or green
                        assertTrue(car.canPass(destinationTerrain, currentLightCondition),
                                "Car should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // Cars cannot pass CROSSWALK
                    // if the light is RED or YELLOW

                    if (currentLightCondition == Light.RED) {
                        assertFalse(car.canPass(destinationTerrain, currentLightCondition),
                                "Car should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else if (currentLightCondition == Light.YELLOW) {
                        assertFalse(car.canPass(destinationTerrain, currentLightCondition),
                                "Car should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else {
                        assertTrue(car.canPass(destinationTerrain, currentLightCondition),
                                "Car should be able to pass " + destinationTerrain
                                + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(car.canPass(destinationTerrain, currentLightCondition),
                            "Car should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }
    /**
     * Test method for {@link Car#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionStraight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.LIGHT);
        neighbors1.put(Direction.EAST, Terrain.LIGHT);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);


        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors1.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);


        assertTrue(TEST_CAR.chooseDirection(neighbors1) == TEST_CAR.getDirection(),
                "The car should go striaght if given the option.");
    }

    /**
     * Test method for {@link Car#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionLeft() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.LIGHT);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);


        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);

        assertTrue(TEST_CAR.chooseDirection(neighbors1) == TEST_CAR.getDirection().left(),
                "The car should go left if going straight is not possible.");
    }

    /**
     * Test method for {@link Car#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionRight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);

        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.LIGHT);


        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.CROSSWALK);


        assertTrue(TEST_CAR.chooseDirection(neighbors1) == TEST_CAR.getDirection().right(),
                "The car should go right given straight and left are not viable directions.");
    }

    /**
     * Test method for {@link Car#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionReverse() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);

        neighbors1.put(Direction.WEST, Terrain.LIGHT);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        neighbors1.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.WALL);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_CAR.chooseDirection(neighbors1) == TEST_CAR.getDirection().reverse(),
                "The car should reverse since there are no viable directions.");
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

        final Car car = new Car(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = car.chooseDirection(neighbors);

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

        assertTrue(seenNorth,
                "Car chooseDirection() should only go straight if possible");

        assertFalse(seenWest && seenEast && seenSouth,
                "Truck chooseDirection() reversed direction when not necessary!");
    }

    /**
     * Test the setter method for the car's x value.
     */
    @Test
    public void testSetterMethodCarX() {
        setUp();
        TEST_CAR.setX(11);
        assertEquals(11, TEST_CAR.getX(), "Car's X field should be 5");

        TEST_CAR.setX(14);
        assertEquals(14, TEST_CAR.getX(), "Car's X field should be 22");
    }

    /**
     * Test the setter method for the car's y value.
     */
    @Test
    public void testSetterMethodAtv() {
        setUp();
        TEST_CAR.setY(10);
        assertEquals(10, TEST_CAR.getY(), "Car's y field should be 10");

        TEST_CAR.setY(28);
        assertEquals(28, TEST_CAR.getY(), "Car's Y field should be 28.");
    }

    /**
     * Test the getter method for the car's x value.
     */
    @Test
    public void testGetterMethodCarX() {
        setUp();
        assertEquals(1, TEST_CAR.getX(), "Car's X field should be initialized to 1.");

        setUp();
        TEST_CAR.setX(9);
        assertEquals(9, TEST_CAR.getX(), "Car's X field should be 9");
    }

    /**
     * Test the getter method for the car's y value.
     */
    @Test
    public void testGetterMethodCarY() {
        setUp();
        assertEquals(1, TEST_CAR.getY(), "Car's Y field should be initiaialized to 1 for " +
                "this car test object");

        setUp();
        TEST_CAR.setY(6);
        assertEquals(6, TEST_CAR.getY(), "Car's Y field should be 6.");
    }

    /**
     * Test the getter method for the atv's current direction.
     */
    @Test
    public void testCarDirection() {
        setUp();
        assertEquals(Direction.EAST, TEST_CAR.getDirection(), "Car's get direction field should return east.");

        setUp();
        TEST_CAR.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, TEST_CAR.getDirection(), "Car's get direction field should return east.");

        setUp();
        TEST_CAR.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, TEST_CAR.getDirection(), "Car's get direction field should return south");
        setUp();
        TEST_CAR.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, TEST_CAR.getDirection(), "Car's get direction field should return north");

        setUp();
        TEST_CAR.setDirection(Direction.WEST);
        assertEquals(Direction.WEST, TEST_CAR.getDirection(), "Car's get direction field should return west.");

    }

    /**
     * Test the reset method for the atv's.
     */
    @Test
    public void testCarResetMethod(){
        Car c = new Car(3, 4, Direction.EAST);

        c.setX(10);
        c.setY(10);
        c.setDirection(Direction.EAST);

        c.reset();

        assertEquals(3, c.getX(), "The car should have rest to a value of 7 in the X field");
        assertEquals(4, c.getY(), "The car should have rest to a value of 9 in the Y field");
        assertEquals(Direction.EAST, c.getDirection(), "The car should have direction set to south.");
        assertTrue(c.isAlive(), "The car should be alive at the start.");
    }

    /**
     * Tests whether this method gets the image file of the
     * class depending on whether its dead or alive.
     */
    @Test
    public void testGetImageFileNameMethod(){
        setUp();
        assertEquals("car.gif", TEST_CAR.getImageFileName(), "The returned String should be" +
                "a car.gif and not atv_dead.gif since the truck can never die (in this program).");

        setUp();
        Truck testtruck = new Truck(2,2, Direction.WEST);
        TEST_CAR.collide(testtruck);
        assertEquals("car_dead.gif", TEST_CAR.getImageFileName(), "The returned String should be" +
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
        Car othera = new Car(2,2,Direction.WEST);
        Truck truck = new Truck(2,2,Direction.WEST);
        TEST_CAR.collide(othera);
        assertTrue(TEST_CAR.isAlive(), "This Car collided with the same vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        othera.collide(TEST_CAR);
        assertTrue(othera.isAlive(), "The other Car collided with the this atv vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        TEST_CAR.collide(truck);
        assertFalse(TEST_CAR.isAlive(), "This Car collided with a surperior vehicle (truck)" +
                " so it should be dead, return false");

        setUp();
        truck.collide(TEST_CAR);
        assertTrue(TEST_CAR.isAlive(), "A truck collided with an Car object, however the collide method" +
                "only updates the status of the vehicle that is being compared to the other vehicle. Thus, truck" +
                "remains alive and so should atv");
    }

    /**
     * Tests the Getter method for the death time parameter.
     */
    @Test
    public void testGetDeathTime(){
        setUp();
        Car atv1 = new Car(3,2,Direction.SOUTH);
        Car atv2 = new Car(9,1,Direction.WEST);
        Car atv3 = new Car(11,7,Direction.EAST);
        Car atv4 = new Car(14,4,Direction.NORTH);

        assertEquals(DEATH_TIME, TEST_CAR.getDeathTime(), "The test car object should have a " +
                "death time of 15.");

        assertEquals(DEATH_TIME, atv1.getDeathTime(), "The test atv1 should have a death time of 15.");

        assertEquals(DEATH_TIME, atv2.getDeathTime(), "The test atv2 should have a death time of 15.");

        assertEquals(DEATH_TIME, atv3.getDeathTime(), "The test atv3 should have a death time of 15.");

        assertEquals(DEATH_TIME, atv4.getDeathTime(), "The test atv4 should have a death time of 15");
    }

    /**
     * Tests whether the vehicle is alive.
     */
    @Test
    public void testIsAlive() {
        setUp();
        Truck truck = new Truck(1,1,Direction.EAST);
        TEST_CAR.collide(truck);
        assertFalse(TEST_CAR.isAlive(), "This atv should be dead");

        setUp();
        assertTrue(TEST_CAR.isAlive(), "This atv should be alive");
    }

    /**
     * Tests if it accurately pokes the vehicle the amount of times it needs to revive. It also
     * must check if it is in random direction.
     */
    @Test
    public void poke(){
        setUp();
        Truck truck = new Truck(1,1,Direction.EAST);
        TEST_CAR.collide(truck);

        setUp();
        TEST_CAR.collide(truck);

        for(int i = 0; i <= DEATH_TIME; i++)
        {
            TEST_CAR.poke();
        }
        assertTrue(TEST_CAR.isAlive(), "The vehicle should revive after 15 ticks, thus on the 26th tick" +
                "it will revive.");

        setUp();
        TEST_CAR.collide(truck);

        for(int i = 1; i <= 14; i++)
        {
            TEST_CAR.poke();
        }
        assertFalse(TEST_CAR.isAlive(), "The vehicle should not revive after 14 ticks (pokes)");

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

        final Car atv = new Car(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            setUp();
            TEST_CAR.collide(truck);

            for(int i = 0; i <= DEATH_TIME; i++) {
                TEST_CAR.poke();
            }

            if (TEST_CAR.getDirection() == Direction.WEST) {
                seenWest = true;
            } else if (TEST_CAR.getDirection() == Direction.NORTH) {
                seenNorth = true;
            } else if (TEST_CAR.getDirection() == Direction.EAST) {
                seenEast = true;
            } else if (TEST_CAR.getDirection() == Direction.SOUTH) {
                seenSouth = true;
            }
        }

        assertTrue(seenWest && seenNorth && seenEast && seenSouth,
                "Atv upon revival failed to randomly select a direction.");
    }
}
