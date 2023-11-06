package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
/**
 * This is the Human class which extends AbstractVehicle class. It prefers to travel through crosswalks if it
 * is adjacent to it and will wait until the crosswalk light cycle is red or yellow. Otherwise it will travel
 * randomly on grass and will not reverse unless necessary. It's death time is 45.
 */
public class Human extends AbstractVehicle {
    /**
     * A constant int which represents the amount of pokes required to revive the human (vehicle).
     */
    private static final int DEATH_TIME = 45;

    /**
     * This Human constructor creates a vehicle which passes the X, Y positions and the current direction.
     * @param theX is the current position of the human.
     * @param theY is the current position of the human.
     * @param theDir is the current position of the human.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }

    /**
     * This method chooses a direction according to the humans's "preference" to face an adjacent crosswalk. Otherwise
     * it will randomly change directions on grass, and will reverse only if necessary.
     * @param theNeighbors is a map of neighboring terrain.
     * @return a direction that the human can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction findirection;
        final String crosswalk = isCrossWalk(theNeighbors);

        if (!crosswalk.isEmpty()) {
            findirection = switch (crosswalk) {
                case ("STRAIGHT") -> getDirection();
                case ("RIGHT") -> getDirection().right();
                case ("LEFT") -> getDirection().left();
                default -> null;
            };
        } else {
            findirection = isViableWalk(theNeighbors);
        }
        return findirection;
    }
    /**
     * This method checks if the human can pass the terrain it is on given the option to check for terrain and the
     * light cycle.
     * @param theTerrain is the passed in terrain the human wants to pass.
     * @param theLight The current light cycle.
     * @return a boolean true if the human can pass, and false if the vehicle cannot pass.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = false;

        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW) {
            stoplight = true;
        } else if (theTerrain == Terrain.GRASS) {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * This helper method checks if there is a crosswalk adjacent to the human
     * @param theNeighbors is a map containing the adjacent terrain from the human.
     * @return a String if the cross walk is straight, right, or left. Otherwise it will pass
     * an empty string.
     */
    private String isCrossWalk(final Map<Direction, Terrain> theNeighbors) {
        String crosswalkpath = "";

        if ((theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            crosswalkpath = "STRAIGHT";
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
            crosswalkpath = "RIGHT";
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            crosswalkpath = "LEFT";
        }
        return crosswalkpath;
    }
    /**
     * This helper method checks if the grass adjacent to the human being is viable (straight, right, left) and
     * randomizes it. If it cannot travel in any of these randomized directions, the human reverses.
     * @param theNeighbors is a map containing the adjacent terrain from the human.
     * @return a Direction containing the randomized direction the human can travel
     */
    private Direction isViableWalk(final Map<Direction, Terrain> theNeighbors) {
        final ArrayList<Direction> moveset = new ArrayList<>();

        if (theNeighbors.get(getDirection()) == Terrain.GRASS) {
            moveset.add(getDirection());
        }
        if (theNeighbors.get(getDirection().right()) == Terrain.GRASS) {
            moveset.add(getDirection().right());
        }
        if (theNeighbors.get(getDirection().left()) == Terrain.GRASS) {
            moveset.add(getDirection().left());
        }
        if (moveset.isEmpty()) {
            moveset.add(getDirection().reverse());
        }

        Collections.shuffle(moveset);
        return moveset.get(0);
    }

    /**
     * Getter method for the death time parameter.
     * @return the time it takes to revive.
     */
    @Override
    public int getDeathTime(){
        return DEATH_TIME;
    }
}