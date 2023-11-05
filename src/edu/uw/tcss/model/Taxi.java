package edu.uw.tcss.model;

import java.util.Map;
/**
 * This is the Taxi class which extends AbstractVehicle class. It prefers to drive straight. If that direction
 * is not possible, left, right, and reverse. It can only traverse streets and through lights and
 * crosswalks. It ignores yellow and green lights but stops for red and yellow lights. If it
 * stops at a light it will pass if it has been 3 ticks or whether the light turns green. It's death time is 15.
 */
public class Taxi extends AbstractVehicle{
    /**
     * constant which represents the number of pokes required to revive a taxi.
     */
    private static final int DEATH_TIME = 15;
    /**
     * constant whic represents the number of cycles required to pass automatically (when stoppped at a light).
     */
    private static final int CYCLE_THRESH_HOLD = 3;

    /**
     * instance field which holds the current cycle the taxi is at.
     */
    private int myCycle = 0;

    /**
     * This Taxi constructor creates a vehicle which passes the X, Y positions and the current direction along with
     * the death time constant.
     * @param theX is the current position of the taxi.
     * @param theY is the current position of the taxi.
     * @param theDir is the current position of the taxi.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * This method chooses a direction according to the taxi's "preference" to go straight if possible,
     * left if possible, right if possible, and then reverse if necessary.
     * @param theNeighbors The map of neighboring terrain.
     * @return a direction that the car can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction findirection;

        if (isViableOption(theNeighbors, getDirection()))
        {
            findirection = getDirection();
        } else if (isViableOption(theNeighbors, getDirection().left())) {
            findirection = getDirection().left();
        } else if (isViableOption(theNeighbors, getDirection().right())) {
            findirection = getDirection().right();
        } else {
            findirection = getDirection().reverse();
        }
        return findirection;
    }

    /**
     * This method checks if the taxi can pass the terrain it is on given the option to check for terrain and the
     * light cycle.
     * @param theTerrain is the passed in terrain the taxi wants to pass.
     * @param theLight The current light cycle.
     * @return a boolean true if the car can pass, and false if the vehicle cannot pass.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight){
        boolean stoplight = theTerrain != Terrain.LIGHT || theLight != Light.RED;

        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED || (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW)) {
            stoplight = false;
            myCycle++;
        }

        if (myCycle == CYCLE_THRESH_HOLD || stoplight) {
            stoplight = true;
            myCycle = 0;
        }
        return stoplight;
    }

    /**
     * This helper method determines if the given direction is one of the viable terrains the taxi can traverse.
     * @param theNeighbors is a map of neighboring terrain.
     * @param theDirection is the direction the vehicle wants to check if it is a viable terrain to traverse.
     * @return a boolean true if the vehicle can pass on this terrain, or false if it cannot travel on this
     * type of terrain.
     */
    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors, final Direction theDirection)
    {
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }
}
