package edu.uw.tcss.model;
import java.util.Map;

public class Car extends AbstractVehicle{
    private static final int DEATH_TIME = 15;

    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

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

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight){
        boolean stoplight = true;

        if (theTerrain == Terrain.LIGHT && theLight == Light.RED)
        {
            stoplight = false;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            stoplight = false;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.YELLOW) {
            stoplight = false;
        }
        return stoplight;
    }

    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors, final Direction theDirection)
    {
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }
}