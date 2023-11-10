package edu.uw.tcss.model;

import java.util.Map;

/*
 * TCSS 305
 * File Name: Bicycle.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This is the Bicycle class which extends AbstractVehicle class. It prioritizes
 * going straight if there is a trail, and then chooses to turn right of left if
 * there is a trail. Finally, if there is no adjacent trail, it will go straight
 * on a crosswalk (or light or crosswalk light) if it can. Then it turns left if
 * possible, or right. If none of these directions is legal, the bicycle turns around.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public class Bicycle extends AbstractVehicle{
    /**
     * This constant holds the time of death for the bicycle class.
     */
    private static final int DEATH_TIME = 35;

    /**
     * The bicycle constructor passes the X, Y, Direction,
     * and Death time to the parent constructor.
     * constructor and passes the X and Y position along with the direction.
     * @param theX is the current x position of the Bicycle.
     * @param theY is the current y position of the Bicycle.
     * @param theDir is the current direction of the Bicycle.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * This method chooses viable directions for the bicycle vehicle. It prioritizes
     * trails and then streets, crosswalks, and lights. It always prefers to
     * go straight, and if it cannot, it will try to turn left,
     * if not left, then right, if not right, then it will reverse..
     * @param theNeighbors The map of neighboring terrain.
     * @return a viable direction for the bicycle to traverse.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction findirection;
        final String path = isPath(theNeighbors);

        if (!path.isEmpty()) {
            findirection = switch (path) {
                case ("STRAIGHT") -> getDirection();
                case ("LEFT") -> getDirection().left();
                case ("RIGHT") -> getDirection().right();
                default -> null;
            };
        } else {
            findirection = secondaryBicycleMoveSet(theNeighbors);
        }
        return findirection;
    }

    /**
     * This helper method checks if the bicycle can move through the streets.
     * @param theNeighbors is a map which contains the surrounding terrain.
     * @return a viable direction for the bicycle to traverse.
     */
    private Direction secondaryBicycleMoveSet(final Map<Direction, Terrain> theNeighbors) {
        final Direction possiblemove;

        if (isViableOption(theNeighbors, getDirection())){
            possiblemove = getDirection();
        } else if (isViableOption(theNeighbors, getDirection().left())) {
            possiblemove = getDirection().left();
        } else if (isViableOption(theNeighbors, getDirection().right())) {
            possiblemove = getDirection().right();
        } else {
            possiblemove = getDirection().reverse();
        }
        return possiblemove;
    }
    /**
     * This method is called and checks if the bicycle can pass the terrain
     * which means it can pass through all trails and streets regardless of
     * light status. Otherwise it can pass lights and crosswalks only if the
     * light cycle is green.
     *
     * @param theTerrain is the terrain that the vehicle wants to pass.
     * @param theLight The light color.
     * @return a boolean true if it can pass or false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = false;

        if ((theTerrain == Terrain.TRAIL) || (theTerrain == Terrain.STREET))
        {
            stoplight = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * Helper method which chooses the most adjacent trail path (if it is not
     * straight ahead to the bicycle).
     * @param theNeighbors is a map which contains the surrounding terrain.
     * @return a String representing the path that the trail is located
     */
    private String isPath(final Map<Direction, Terrain> theNeighbors) {
        String path = "";

        if ((theNeighbors.get(getDirection()) == Terrain.TRAIL)) {
            path = "STRAIGHT";
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.TRAIL)) {
            path = "LEFT";
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.TRAIL)) {
            path = "RIGHT";
        }
        return path;
    }

    /**
     * helper method to determine whether the direction is a viable direction for
     * the bicycle (assuming a trail isn't nearby) which entails streets,
     * crosswalks, and lights.
     * @param theNeighbors a map containing the terrain adjacent to the bicycle.
     * @param theDirection is the direction the bicyclew is checking.
     * @return a boolean true if the direction is possible,
     * and false if the direction is not possible.
     */
    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors,
                                   final Direction theDirection)
    {
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }
}
