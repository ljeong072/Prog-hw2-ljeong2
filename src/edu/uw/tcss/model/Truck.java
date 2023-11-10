package edu.uw.tcss.model;

import java.util.Map;
import edu.uw.tcss.util.Bag.MyBag;

/*
 * TCSS 305
 * File Name: Truck.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This is the Truck class which extends AbstractVehicle class. It can only traverse
 * streets and through lights and crosswalks. It will randomly drive straight,
 * left, or right and will only reverese if necessary. If ignores all
 * traffic lights but will stop for red crosswalk lights. It's death
 * time is 0 meaning it is the only vehicle which
 * cannot die.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public class Truck extends AbstractVehicle {
    /**
     * Constant which represents the amount of pokes required to revive the vehicle if dead.
     */
    private static final int DEATH_TIME = 0;

    /**
    * This truck constructor creates a vehicle which passes the X, Y positions
     * and current direction and death timer.
    * @param theX is the current position of the truck.
    * @param theY is the current position of the truck.
    * @param theDir is the current position of the truck.
    */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * This method chooses a direction which is randomized.
     * The following directions, if viable, are straight
     * left, and right. It will reverse only if necessary.
     * @param theNeighbors The map of neighboring terrain.
     * @return a direction that the car can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final MyBag<Direction> thebag = new MyBag<>();

        if (isViableOption(theNeighbors, getDirection())) {
            thebag.putBag(getDirection());
        }
        if (isViableOption(theNeighbors, getDirection().right())) {
            thebag.putBag(getDirection().right());
        }
        if (isViableOption(theNeighbors, getDirection().left())) {
            thebag.putBag(getDirection().left());
        }
        if (thebag.getBagEmpty()) {
            thebag.putBag(getDirection().reverse());
        }
        return thebag.grabBag();
    }

    /**
     * This method checks if the truck can pass the terrain it is on given the option to check for terrain and the
     * light cycle.
     * @param theTerrain is the passed in terrain the truck wants to pass.
     * @param theLight The current light cycle.
     * @return a boolean true if the truck can pass, and false if the vehicle cannot pass.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = false;

        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT)
        {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW)
        {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * helper method to determine whether the direction is a viable direction.
     * @param theNeighbors a map containing the terrain adjacent to the truck.
     * @param theDirection is the direction the truck is checking.
     * @return a boolean true if the direction is possible,
     * and false if the direction is not possible.
     */
    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors, final Direction theDirection)
    {
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }
}

