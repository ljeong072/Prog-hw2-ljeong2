package edu.uw.tcss.app;

import edu.uw.tcss.model.Direction;
import edu.uw.tcss.model.Light;
import edu.uw.tcss.model.Terrain;
import edu.uw.tcss.util.Bag.Bag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import edu.uw.tcss.util.Bag.MyBag;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Bag ADT
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public class BagTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;

    /**
     * An instance field storing a bag for testing (string object types).
     */
    private Bag<String> TEST_BAG_STRING;

    /**
     * An instance field storing a bag for testing (Direction object types).
     */
    private Bag<Direction> TEST_BAG_DIRECTION;
    /**
     * An instance field storing a bag for testing (Terrain object types).
     */
    private Bag<Terrain> TEST_BAG_TERRAIN;
    /**
     * An instance field storing a bag for testing (Light object types).
     */
    private Bag<Light> TEST_BAG_LIGHT;

    /**
     * Sets multiple testbags to ensure that it works with a wide variety of ADTs
     */
    @BeforeEach
    public void setUp() {
        TEST_BAG_STRING = new MyBag<>();
        TEST_BAG_DIRECTION = new MyBag<>();
        TEST_BAG_TERRAIN = new MyBag<>();
        TEST_BAG_LIGHT = new MyBag<>();
    }

    /**
     * Test method for Bag constructor.
     */
    @Test
    public void testBagConstructor() {
        setUp();

        assertTrue(TEST_BAG_STRING.getBagEmpty(), "The Bag was not initalized properly, the bag" +
                "should exist and should be empty (type String).");
        assertTrue(TEST_BAG_DIRECTION.getBagEmpty(), "The Bag was not initalized properly, the bag" +
                "should exist and should be empty (type Direction).");
        assertTrue(TEST_BAG_TERRAIN.getBagEmpty(), "The Bag was not initalized properly, the bag" +
                "should exist and should be empty (type Terrain).");
        assertTrue(TEST_BAG_LIGHT.getBagEmpty(), "The Bag was not initalized properly, the bag" +
                "should exist and should be empty (type Light).");
    }


    /**
     * Test method for Bag put Method
     */
    @Test
    public void testBagPutMethod() {
        setUp();
        TEST_BAG_LIGHT.putBag(Light.RED);
        TEST_BAG_TERRAIN.putBag(Terrain.CROSSWALK);
        TEST_BAG_DIRECTION.putBag(Direction.NORTH);
        TEST_BAG_STRING.putBag("Hello");

        assertFalse(TEST_BAG_STRING.getBagEmpty(), "The Bag has at least one object so it" +
                "is not empty");
        assertFalse(TEST_BAG_DIRECTION.getBagEmpty(), "The Bag has at least one object so it" +
                "is not empty.");
        assertFalse(TEST_BAG_TERRAIN.getBagEmpty(), "The Bag has at least one object so it " +
                "is not empty.");
        assertFalse(TEST_BAG_LIGHT.getBagEmpty(), "The Bag has at least one object so it " +
                "is not empty.");
    }

    /**
     * Test method for Bag put method (avoid duplicates)
     */
    @Test
    public void testBagPutMethodAvoidDuplicates() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int minoritysum;

        setUp();
        TEST_BAG_STRING.putBag("A");
        TEST_BAG_STRING.putBag("B");
        TEST_BAG_STRING.putBag("C");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");
        TEST_BAG_STRING.putBag("D");

        /**
         * we if we have one A, one B, one C, and 60 D strings, if grabBag is called
         * randomly at least fifty times, it should be called dissproportionatly and should
         * outnumber all the others. However, since the bag cannot allow duplicates, if the
         * combination of the other "smaller size" elements is greater than D it implies that
         * D is only put in once.
         */
        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++)
        {
            String str = TEST_BAG_STRING.grabBag();

            switch(str) {
                case ("A"):
                    a++;
                case ("B"):
                    b++;
                case ("C"):
                    c++;
                case ("D"):
                    d++;
            }
        }
        minoritysum = a + b + c;

        assertTrue(d < minoritysum, "Since all probabilites are statistically equal" +
                "among 4 string objects (assuming that duplicates are removed, the sum of all" +
                        "the other String objects should always be greater than d.");
    }

    /**
     * Test method for Bag grabBag method for string types.
     */
    @Test
    public void testBagGetGrabBagMethodString() {
        setUp();
        TEST_BAG_STRING.putBag("ONE");
        TEST_BAG_STRING.putBag("TWO");
        TEST_BAG_STRING.putBag("THREE");

        boolean seenone = false;
        boolean seentwo = false;
        boolean seenthree = false;

        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++)
        {
            String str = TEST_BAG_STRING.grabBag();

            if (str.equals("ONE")) {
                seenone = true;
            } else if (str.equals("TWO")) {
                seentwo = true;
            } else if (str.equals("THREE")) {
                seenthree = true;
            }
        }
        assertTrue(seenone && seentwo && seenthree, "The grabbag method should " +
                "grab a random String from the bag");
    }

    /**
     * Test method for Bag grabBag method for direction types.
     */
    @Test
    public void testBagGetGrabBagMethodDirection() {
        setUp();
        TEST_BAG_DIRECTION.putBag(Direction.NORTH);
        TEST_BAG_DIRECTION.putBag(Direction.EAST);
        TEST_BAG_DIRECTION.putBag(Direction.WEST);
        TEST_BAG_DIRECTION.putBag(Direction.SOUTH);

        boolean seennorth = false;
        boolean seenwest = false;
        boolean seeneast = false;
        boolean seensouth = false;

        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++)
        {
            Direction dir = TEST_BAG_DIRECTION.grabBag();

            if (dir.equals(Direction.NORTH)) {
                seennorth = true;
            } else if (dir.equals(Direction.EAST)) {
                seenwest = true;
            } else if (dir.equals(Direction.WEST)) {
                seeneast = true;
            } else if (dir.equals(Direction.SOUTH)) {
                seensouth = true;
            }
        }
        assertTrue(seennorth && seenwest && seeneast && seensouth,
                "The grabbag method properly grabs a random Direction from the bag");
    }

    /**
     * Test method for Bag grabBag method for Light types.
     */
    @Test
    public void testBagGetGrabBagMethodLight() {
        setUp();
        TEST_BAG_LIGHT.putBag(Light.RED);
        TEST_BAG_LIGHT.putBag(Light.YELLOW);
        TEST_BAG_LIGHT.putBag(Light.GREEN);

        boolean seenred = false;
        boolean seenyellow = false;
        boolean seengreen = false;

        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++) {
            Light light = TEST_BAG_LIGHT.grabBag();

            if (light.equals(Light.RED)) {
                seenred = true;
            } else if (light.equals(Light.YELLOW)) {
                seenyellow = true;
            } else if (light.equals(Light.GREEN)) {
                seengreen = true;
            }
        }
        assertTrue(seenred && seenyellow && seengreen,
                "The grabbag method properly grabs a random Light from the bag");
    }

    /**
     * Test method for Bag grabBag method for Terrain types.
     */
    @Test
    public void testBagGetGrabBagMethodTerrain() {
        setUp();
        TEST_BAG_TERRAIN.putBag(Terrain.CROSSWALK);
        TEST_BAG_TERRAIN.putBag(Terrain.LIGHT);
        TEST_BAG_TERRAIN.putBag(Terrain.WALL);
        TEST_BAG_TERRAIN.putBag(Terrain.STREET);
        TEST_BAG_TERRAIN.putBag(Terrain.TRAIL);
        TEST_BAG_TERRAIN.putBag(Terrain.GRASS);

        boolean seencrosswalk = false;
        boolean seenlight = false;
        boolean seenwall = false;
        boolean seenstreet = false;
        boolean seentrail = false;
        boolean seengrass = false;

        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++)
        {
            Terrain ter = TEST_BAG_TERRAIN.grabBag();

            if (ter.equals(Terrain.CROSSWALK)) {
                seencrosswalk = true;
            } else if (ter.equals(Terrain.STREET)) {
                seenstreet = true;
            } else if (ter.equals(Terrain.WALL)) {
                seenwall = true;
            } else if (ter.equals(Terrain.LIGHT)) {
                seenlight = true;
            } else if (ter.equals(Terrain.TRAIL)) {
                seentrail = true;
            } else if (ter.equals(Terrain.GRASS)) {
                seengrass = true;
            }
        }
        assertTrue(seencrosswalk && seenstreet && seenwall && seenlight &&
                        seentrail && seengrass,
                "The grabbag method properly grabs a random Terrain from the bag");
    }

    /**
     * Test method for Bag getEmpty method.
     */
    @Test
    public void testBagGetEmptyMethod() {
        setUp();
        TEST_BAG_STRING = new MyBag<>();
        TEST_BAG_DIRECTION = new MyBag<>();
        TEST_BAG_TERRAIN = new MyBag<>();
        TEST_BAG_LIGHT = new MyBag<>();

        assertTrue(TEST_BAG_STRING.getBagEmpty(), "The Bag should be empty (type String).");
        assertTrue(TEST_BAG_DIRECTION.getBagEmpty(), "The Bag should be empty (type Direction).");
        assertTrue(TEST_BAG_TERRAIN.getBagEmpty(), "The Bag should be empty (type Terrain).");
        assertTrue(TEST_BAG_LIGHT.getBagEmpty(), "The Bag should be empty (type Light).");
    }
}

