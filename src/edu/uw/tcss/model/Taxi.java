package edu.uw.tcss.model;

import java.util.Map;

/*
 * TCSS 305
 * File Name: Taxi.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This is the taxi class which extends AbstractVehicle class. It prefers to drive
 * straight. If that direction is not possible, left, right, and reverse.
 * It can only traverse streets and through lights and crosswalks. It ignores yellow
 * and green lights but stops for red and yellow lights. If it stops at a light it
 * will pass if it has been 3 ticks or whether the light turns green.
 * It's death time is 15.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public class Taxi extends AbstractVehicle{
    /**
     * constant which represents the number of pokes required to revive a taxi.
     */
    private static final int DEATH_TIME = 15;
    /**
     * constant whic represents the number of cycles required to pass automatically
     * (when stoppped at a light).
     */
    private static final int CYCLE_THRESH_HOLD = 3;

    /**
     * instance field which holds the current cycle the taxi is at.
     */
    private int myCycle;

    /**
     * This taxi constructor creates a vehicle which passes the X, Y positions
     * and the current direction and the death timer. The taxi also instantializes
     * a field myCycle which represents how many light cycles a taxi must
     * wait before passing a crosswalk light (unless the light turns green first).
     *
     * @param theX is the current position of the taxi.
     * @param theY is the current position of the taxi.
     * @param theDir is the current position of the taxi.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
        myCycle = 0;
    }

    /**
     * This method chooses a direction according to the taxi's "preference" to go
     * straight if possible, left if possible, right if possible, and then
     * reverse if necessary.
     * @param theNeighbors The map of neighboring terrain.
     * @return a direction that the car can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction findirection;

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
     * This method checks if the taxi can pass the terrain it is on given the
     * option to check for terrain and the light cycle. The taxi can pass
     * regardless of light status if it is a street and the cross
     * walks and lights are both yellow and green. If three light
     * cycles has been called and the canPass method is false, on the fourth
     * call (because it will wait at three cycles) it will automatically
     * move and will reset the cycle.
     * @param theTerrain is the passed in terrain the taxi wants to pass.
     * @param theLight The current light cycle.
     * @return a boolean true if the car can pass, and false if the vehicle cannot
     * pass.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = ViableCanPass(theTerrain, theLight);

        if ((theTerrain == Terrain.CROSSWALK) && (theLight == Light.RED))
        {
            stoplight = false;
            myCycle++;
        }

        if (myCycle > CYCLE_THRESH_HOLD || stoplight)
        {
            stoplight = true;
            myCycle = 0;
        }
        return stoplight;
    }

    /**
     * This helper method determines if the given direction is one of the viable
     * terrains the taxi can traverse. This terrain includes streets, crosswalks,
     * and lights.
     * @param theNeighbors is a map of neighboring terrain.
     * @param theDirection is the direction the vehicle wants to check if it is a
     * viable terrain to traverse.
     * @return a boolean true if the vehicle can pass on this terrain, or false if
     * it cannot travel on this type of terrain.
     */
    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors,
                                   final Direction theDirection)
    {
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }

    /**
     * This helper method determines if the vehicle can pass this terrain given the
     * terrain and light status. The taxi can pass the terrain if it is a street
     * regardless of light status. It can cross lights and crosswalks if both light
     * statuses are yellow and green.
     * @param theTerrain is the terrain the vehicle wants to pass.
     * @param theLight is the light color in the cycle.
     * @return a boolean true if the vehicle can pass and false if the vehicle cannot
     * pass.
     */
    private boolean ViableCanPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = theTerrain == Terrain.STREET;

        if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN) {
            stoplight = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.YELLOW) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW) {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * This toString is used for testing the vehicle classes which inheret from abstract
     * vehicle However, this toString is overriden specifically for the taxi class
     * because it needs to document the cycle. Thus, the tostring returns a string
     * containing the class name, its position, direction, status, and the current cycle.
     */
    @Override
    public String toString() {
        return super.toString() + ", Cycle: " + myCycle;
    }
}
