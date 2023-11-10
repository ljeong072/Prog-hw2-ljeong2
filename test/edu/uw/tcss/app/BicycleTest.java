/*
 * TCSS 305 - Road Rage
 */
package edu.uw.tcss.app;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.tcss.model.Direction;
import edu.uw.tcss.model.Truck;
import edu.uw.tcss.model.Bicycle;
import edu.uw.tcss.model.Light;
import edu.uw.tcss.model.Terrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit tests for class Bicycle.
 *
 * @author Lucas Jeong
 * @version 2023 November 8
 */
public class BicycleTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    /**
     * The number of pokes it takes for an bicycle to revive.
     */
    private static final int DEATH_TIME = 35;

    private Bicycle TEST_BICYCLE;

    /**
     * Sets a Bicycle object for testing with X and Y fields set to 2 and the direction set to WEST.
     */
    @BeforeEach
    public void setUp() {
        TEST_BICYCLE = new Bicycle(2, 2, Direction.WEST);
    }

    /** Test method for Bicycle constructor. */
    @Test
    public void testBicycleConstructor() {
        final Bicycle b = new Bicycle(8, 5, Direction.SOUTH);

        assertEquals(8, b.getX(), "Bicycle X coordinate not initialized correctly!");
        assertEquals(5, b.getY(), "Bicycle coordinate not initialized correctly!");
        assertEquals(Direction.SOUTH, b.getDirection(),
                "Bicycle direction not initialized correctly!");
        assertEquals(DEATH_TIME, b.getDeathTime(), "Bicycle death time not initialized correctly!");
        assertTrue(b.isAlive(), "Bicycle isAlive() fails initially!");
    }

    /** Test method for Bicycle setters. */
    @Test
    public void testBicycleSetters() {
        final Bicycle b = new Bicycle(4, 5, Direction.SOUTH);

        b.setX(10);
        assertEquals(10, b.getX(), "Bicycle setX failed!");
        b.setY(2);
        assertEquals(2, b.getY(), "Bicycle setY failed!");
        b.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, b.getDirection(), "Bicycle setDirection failed!");
    }

    /**
     * Test method for {@link Bicycle#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        // Bicycles can move on STREETS, LIGHTS, TRAILS, or to CROSSWALKS.
        // so we need to test all of these.

        // Bicycles should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types

        // Bicycle should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.TRAIL);

        final Bicycle bicycle = new Bicycle(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.TRAIL) {
                    //Bicycle can pass TRAIL under any light condition
                    assertTrue(bicycle.canPass(destinationTerrain, currentLightCondition),
                            "Bicycle should be able to pass Trail"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.STREET) {
                    //Bicycle can pass TRAIL under any light condition
                    assertTrue(bicycle.canPass(destinationTerrain, currentLightCondition),
                            "Bicycle should be able to pass STREET"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.LIGHT) {
                    // Bicycle cannot pass LIGHT
                    // if the light is RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse(bicycle.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Bicycle should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else if (currentLightCondition == Light.YELLOW) {
                        assertFalse(bicycle .canPass(destinationTerrain,
                                        currentLightCondition),
                                    "Bicycle should NOT be able to pass " + destinationTerrain
                                            + ", with light " + currentLightCondition);
                    } else { //Light is green
                        assertTrue(bicycle.canPass(destinationTerrain, currentLightCondition),
                                "Bicycle should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // Bicycle cannot pass CROSSWALK
                    // if the light is RED or YELLOW

                    if (currentLightCondition == Light.RED) {
                        assertFalse(bicycle.canPass(destinationTerrain, currentLightCondition),
                                "Bicycle should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else if (currentLightCondition == Light.YELLOW) {
                        assertFalse(bicycle.canPass(destinationTerrain, currentLightCondition),
                                "Bicycle should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else {
                        assertTrue(bicycle.canPass(destinationTerrain, currentLightCondition),
                                "Bicycle should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(bicycle.canPass(destinationTerrain, currentLightCondition),
                            "Bicycle should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionStraight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<>();
        neighbors1.put(Direction.WEST, Terrain.STREET);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        assertTrue(TEST_BICYCLE.chooseDirection(neighbors1) == TEST_BICYCLE.getDirection(),
                "The bicycle should go straight if given the option.");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionLeft() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.WALL);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.STREET);

        assertTrue(TEST_BICYCLE.chooseDirection(neighbors1) == TEST_BICYCLE.getDirection().left(),
                "The bicycle should go left if going straight is not possible.");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionRight() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();
        neighbors1.put(Direction.WEST, Terrain.WALL);
        neighbors1.put(Direction.NORTH, Terrain.STREET);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_BICYCLE.chooseDirection(neighbors1) == TEST_BICYCLE.getDirection().right(),
                "The Bicycle should go right given left and right are not viable directions.");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionValidOptionReverse() {
        setUp();
        final Map<Direction, Terrain> neighbors1 = new HashMap<Direction, Terrain>();

        neighbors1.put(Direction.WEST, Terrain.WALL);
        neighbors1.put(Direction.NORTH, Terrain.WALL);
        neighbors1.put(Direction.EAST, Terrain.STREET);
        neighbors1.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(TEST_BICYCLE.chooseDirection(neighbors1) == TEST_BICYCLE.getDirection().reverse(),
                "The bicycle should reverse since there are no viable directions.");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionTrailStraight() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.TRAIL);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);

        assertTrue(Direction.WEST == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west so it should face west, straight.");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionTrailLeft() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.TRAIL);

        assertTrue(Direction.SOUTH == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west so it should face south (turn left to the path).");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionTrailRight() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.TRAIL);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.STREET);

        assertTrue(Direction.NORTH == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west so it should face north (turn right to the path).");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionTrailBehindCheck() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.TRAIL);
        neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);

        assertFalse(Direction.EAST == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west, but the trail is behind it. It should not reverse and should continue" +
                "faceing west)");
    }

    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionCannotPassGrass() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.GRASS);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.GRASS);


        assertTrue(Direction.NORTH == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west, but it is surrounded by grass except for its reverse and to its" +
                "right. So logically it should turn right, but not reverse.");
        }
    /**
     * Test method for {@link Bicycle#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionCannotPassWall() {
        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.WALL);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.WALL);


        assertTrue(Direction.NORTH == TEST_BICYCLE.chooseDirection(neighbors), "The bicycle is " +
                "facing west, but it is surrounded by walls except for its reverse and to its" +
                "right. So logically it should turn right, but not reverse.");
    }

    /**
     * Test the setter method for the bicycle's x value.
     */
    @Test
    public void testSetterMethodBicycleX() {
        setUp();
        TEST_BICYCLE.setX(11);
        assertEquals(11, TEST_BICYCLE.getX(), "Bicycle's X field should be 5");

        TEST_BICYCLE.setX(14);
        assertEquals(14, TEST_BICYCLE.getX(), "Bicycle's X field should be 22");
    }

    /**
     * Test the setter method for the bicycle's y value.
     */
    @Test
    public void testSetterMethodBicycle() {
        setUp();
        TEST_BICYCLE.setY(10);
        assertEquals(10, TEST_BICYCLE.getY(), "Bicycle's y field should be 10");

        TEST_BICYCLE.setY(28);
        assertEquals(28, TEST_BICYCLE.getY(), "Bicycle's Y field should be 28.");
    }

    /**
     * Test the getter method for the bicycle's x value.
     */
    @Test
    public void testGetterMethodBicycleX() {
        setUp();
        assertEquals(2, TEST_BICYCLE.getX(), "Bicycle's X field should be initialized to 1 for this Truck" +
                "test object");

        setUp();
        TEST_BICYCLE.setX(9);
        assertEquals(9, TEST_BICYCLE.getX(), "Bicycle's X field should be 9");
    }

    /**
     * Test the getter method for the bicycle's y value.
     */
    @Test
    public void testGetterMethodBicycleY() {
        setUp();
        assertEquals(2, TEST_BICYCLE.getY(), "Bicycle's Y field should be initiaialized to 2 for " +
                "this ATV test object");

        setUp();
        TEST_BICYCLE.setY(6);
        assertEquals(6, TEST_BICYCLE.getY(), "Bicycle's Y field should be 6.");
    }

    /**
     * Test the getter method for the bicycle's current direction.
     */
    @Test
    public void testBicycleDirection() {
        setUp();
        assertEquals(Direction.WEST, TEST_BICYCLE.getDirection(), "Bicycle's get direction field should return west.");

        setUp();
        TEST_BICYCLE.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, TEST_BICYCLE.getDirection(), "Bicycle's get direction field should return east.");

        setUp();
        TEST_BICYCLE.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, TEST_BICYCLE.getDirection(), "Bicycle's get direction field should return south");
        setUp();
        TEST_BICYCLE.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, TEST_BICYCLE.getDirection(), "Bicycle's get direction field should return north");

        setUp();
        TEST_BICYCLE.setDirection(Direction.WEST);
        assertEquals(Direction.WEST, TEST_BICYCLE.getDirection(), "Bicycle's get direction field should return west.");

    }

    /**
     * Test the reset method for the Bicycle.
     */
    @Test
    public void testAtvResetMethod(){
        final Bicycle b = new Bicycle(3, 4, Direction.EAST);

        b.setX(10);
        b.setY(10);
        b.setDirection(Direction.EAST);

        b.reset();

        assertEquals(3, b.getX(), "The Bicycle should have rest to a value of 7 in the X field");
        assertEquals(4, b.getY(), "The Bicycle should have rest to a value of 9 in the Y field");
        assertEquals(Direction.EAST, b.getDirection(), "The Bicycle should have direction set to south.");
        assertTrue(b.isAlive(), "The atv should be alive at the start.");
    }

    /**
     * Tests whether this method gets the image file of the
     * class depending on whether its dead or alive.
     */
    @Test
    public void testGetImageFileNameMethod(){
        setUp();
        assertEquals("bicycle.gif", TEST_BICYCLE.getImageFileName(), "The returned String should be" +
                "a bicycle.gif and not bicycle_dead.gif since the truck can never die (in this program).");

        setUp();
        final Truck testtruck = new Truck(2,2, Direction.WEST);
        TEST_BICYCLE.collide(testtruck);
        assertEquals("bicycle_dead.gif", TEST_BICYCLE.getImageFileName(), "The returned String should be" +
                "a bicycle_dead.gift and not atv.gif or some other gif since the bicycle is killed.");
    }

    /**
     * Tests whether this method accurately updates the status of the vehicle when it collides with another object.
     * If the death time of this vehicle is greater than the death time of the other object,
     * what is returned is the status set to false (the vehicle is dead).
     */
    @Test
    public void testCollideMethod() {
        setUp();
        final Bicycle othera = new Bicycle(2,2,Direction.WEST);
        final Truck truck = new Truck(2,2,Direction.WEST);
        TEST_BICYCLE.collide(othera);
        assertTrue(TEST_BICYCLE.isAlive(), "This Bicycle collided with the same vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        othera.collide(TEST_BICYCLE);
        assertTrue(othera.isAlive(), "The other Bicycle collided with the this bicycle vehicle (same death time) so " +
                "it should be alive.");

        setUp();
        TEST_BICYCLE.collide(truck);
        assertFalse(TEST_BICYCLE.isAlive(), "This Bicycle collided with a surperior vehicle (truck)" +
                " so it should be dead, return false");

        setUp();
        truck.collide(TEST_BICYCLE);
        assertTrue(TEST_BICYCLE.isAlive(), "A truck collided with an Bicycle object, however the collide method" +
                "only updates the status of the vehicle that is being compared to the other vehicle. Thus, truck" +
                "remains alive and so should atv");
    }

    /**
     * Tests the Getter method for the death time parameter.
     */
    @Test
    public void testGetDeathTime(){
        setUp();
        final Bicycle bicycle1= new Bicycle(3,2,Direction.SOUTH);
        final Bicycle bicycle2 = new Bicycle(9,1,Direction.WEST);
        final Bicycle bicycle3 = new Bicycle(11,7,Direction.EAST);
        final Bicycle bicycle4 = new Bicycle(14,4,Direction.NORTH);

        assertEquals(DEATH_TIME, TEST_BICYCLE.getDeathTime(), "The test atv object should have a " +
                "death time of 25.");

        assertEquals(DEATH_TIME, bicycle1.getDeathTime(), "The test bicycle1 should have a death time of 25.");

        assertEquals(DEATH_TIME, bicycle2.getDeathTime(), "The test bicycle2 should have a death time of 25.");

        assertEquals(DEATH_TIME, bicycle3.getDeathTime(), "The test bicycle3 should have a death time of 25.");

        assertEquals(DEATH_TIME, bicycle4.getDeathTime(), "The test bicycle4 should have a death time of 25");
    }

    /**
     * Tests whether the vehicle is alive.
     */
    @Test
    public void testIsAlive() {
        setUp();
        final Truck truck = new Truck(2,2,Direction.WEST);
        TEST_BICYCLE.collide(truck);
        assertFalse(TEST_BICYCLE.isAlive(), "This bicycle should be dead");

        setUp();
        assertTrue(TEST_BICYCLE.isAlive(), "This bicycle should be alive");
    }

    /**
     * Tests if it accurately pokes the vehicle the amount of times it needs to revive. It also
     * must check if it is in random direction.
     */
    @Test
    public void poke(){
        setUp();
        final Truck truck = new Truck(2,2,Direction.WEST);
        TEST_BICYCLE.collide(truck);

        setUp();
        TEST_BICYCLE.collide(truck);

        for(int i = 0; i <= DEATH_TIME; i++)
        {
            TEST_BICYCLE.poke();
        }
        assertTrue(TEST_BICYCLE.isAlive(), "The vehicle should revive after 35 ticks, thus on the 36th tick" +
                "it will revive.");

        setUp();
        TEST_BICYCLE.collide(truck);

        for(int i = 1; i <= 24; i++)
        {
            TEST_BICYCLE.poke();
        }
        assertFalse(TEST_BICYCLE.isAlive(), "The vehicle should not revive after 35 ticks (pokes)");

        setUp();
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);

        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;

        final Bicycle bicycle = new Bicycle(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            setUp();
            TEST_BICYCLE.collide(truck);

            for(int i = 0; i <= DEATH_TIME; i++) {
                TEST_BICYCLE.poke();
            }

            if (TEST_BICYCLE.getDirection() == Direction.WEST) {
                seenWest = true;
            } else if (TEST_BICYCLE.getDirection() == Direction.NORTH) {
                seenNorth = true;
            } else if (TEST_BICYCLE.getDirection() == Direction.EAST) {
                seenEast = true;
            } else if (TEST_BICYCLE.getDirection() == Direction.SOUTH) {
                seenSouth = true;
            }
        }
        assertTrue(seenWest && seenNorth && seenEast && seenSouth,
                "Bicycle upon revival failed to randomly select a direction.");
    }

    /**
     * tests if the toString returns the toString for Bicycle properly
     */
    @Test
    public void testBicycleToString(){
        setUp();
        final String teststring = TEST_BICYCLE.getClass().getSimpleName().toLowerCase() + ", X position:"
                + TEST_BICYCLE.getX() + ", Y position:" + TEST_BICYCLE.getY()
                + ", Direction:" + TEST_BICYCLE.getDirection() + ", Status: " + TEST_BICYCLE.isAlive()
                + ", Pokes: 0";

        assertEquals(teststring, TEST_BICYCLE.toString(), "This string should return the class " +
                "followed by position, direction, status, and pokes.");
    }
}
