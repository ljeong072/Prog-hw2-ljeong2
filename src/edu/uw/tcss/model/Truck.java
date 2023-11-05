package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


/**
 * This is the Truck class which extends AbstractVehicle class. It can only traverse streets and through lights and
 * crosswalks. It will randomly drive straight, left, or right and will only reverese if necessary. If ignores all
 * traffic lights but will stop for red crosswalk lights. It's death time is 0 meaning it is the only vehicle which
 * cannot die.
 */
public class Truck extends AbstractVehicle {
    /**
     * Constant which represents the amount of pokes required to revive the vehicle if dead.
     */
    private static final int DEATH_TIME = 0;

    /**
    * This truck constructor creates a vehicle which passes the X, Y positions and the current direction along with
    * the death time constant.
    * @param theX is the current position of the car.
    * @param theY is the current position of the car.
    * @param theDir is the current position of the car.
    */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * This method chooses a direction which is randomized. The following directions, if viable, are straight
     * left, and right. It will reverse only if necessary.
     * @param theNeighbors The map of neighboring terrain.
     * @return a direction that the car can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return randomizerset(theNeighbors);
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
        }
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            stoplight = true;
        }
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW)
        {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * This helper method contains a set of viable directions (straight, left, or right) and then randomizes them.
     * @param theNeighbors is a map containing adjacent terrain of the truck.
     * @return a random direction and reverse if necessary.
     */
    private Direction randomizerset(final Map<Direction, Terrain> theNeighbors) {
        ArrayList<Direction> moveset = new ArrayList<>();

        if ((theNeighbors.get(getDirection()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection()) == Terrain.LIGHT
                || theNeighbors.get(getDirection()) == Terrain.STREET)) {
            moveset.add(getDirection());
        }
        if ((theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().right()) == Terrain.STREET)) {
            moveset.add(getDirection().right());
        }
        if ((theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                || theNeighbors.get(getDirection().left()) == Terrain.STREET)) {
            moveset.add(getDirection().left());
        }
        if (moveset.isEmpty()) {
            moveset.add(getDirection().reverse());
        }
        Collections.shuffle(moveset);
        return moveset.get(0);
    }
}

