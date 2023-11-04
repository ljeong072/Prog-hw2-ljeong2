package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class Human extends AbstractVehicle {
    private static final int DEATH_TIME = 45;
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction findirection;
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
}