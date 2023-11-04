package edu.uw.tcss.model;

import java.util.Map;

/**
 * This is the Bicycle class which extends AbstractVehicle class. It prioritizes going straight
 * if there is a trail, and then chooses to turn right of left if there is a trail. Finally, if there
 * is no adjacent trail, it will go straight on a crosswalk (or light or crosswalk light) if it can.
 * Then it turns left if possible, or right. If none of these directions is legal, the bicycle turns around.
 */
public class Bicycle extends AbstractVehicle{
    /**
     * This constant holds the time of death for the bicycle class.
     */
    private static final int DEATH_TIME = 35;

    /**
     * The bicycle constructor creates a bicycle object via the abstract vehicle constructor
     * and passes the X and Y position along with the direction.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction findirection;
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

    private Direction secondaryBicycleMoveSet(final Map<Direction, Terrain> theNeighbors) {
        Direction possiblemove;

        if ((theNeighbors.get(getDirection()) == Terrain.STREET ||
                theNeighbors.get(getDirection()) == Terrain.LIGHT || theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            possiblemove = getDirection();
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET) || (theNeighbors.get(getDirection().right()) == Terrain.LIGHT)
                || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            possiblemove = getDirection().left();
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET) || (theNeighbors.get(getDirection().left()) == Terrain.LIGHT)
                || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
            possiblemove = getDirection().right();
        } else {
            possiblemove = getDirection().reverse();
        }
        return possiblemove;
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = theTerrain != Terrain.CROSSWALK || theLight != Light.GREEN;

        if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN)
        {
            stoplight = false;
        }
        return stoplight;
    }
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
}
