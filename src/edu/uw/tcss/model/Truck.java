package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Truck extends AbstractVehicle {
    private static final int DEATH_TIME = 0;

    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return randomizerset(theNeighbors);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = true;

        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            stoplight = false;
        }
        return stoplight;
    }
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