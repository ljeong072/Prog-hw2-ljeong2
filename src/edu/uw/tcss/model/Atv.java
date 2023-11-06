package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * This is the ATV class which extends AbstractVehicle class. It randomly moves
 * straight, left, or right on any terrain that is not a while and ignores all light
 * cycles for crosswalks and lights. It's death time is 25.
 */
public class Atv extends AbstractVehicle{
    /**
     * constant which stores the death time.
     */
    private static final int DEATH_TIME = 25;
    /**
     * constructor which passes the x and y position with the direction to the AbstractVehicle
     * class.
     *
     * @param theX is the current x position of the Atv.
     * @param theY is the current y position of the Atv.
     * @param theDir is the current direction of the Atv.
     */
    public Atv(final int theX, final int theY, final Direction theDir)
    {
        super(theX, theY, theDir);
    }

    /**
     * randomMoveSet is the possible directions that could be chosen by the ATV which
     * it randomly chooses stright, right, and left depending on it's terrain.
     * @param theNeighbors The map of neighboring terrain.
     * @return a valid direction for the ATV to travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return randomMoveSet(theNeighbors);
    }

    /**
     * This method is called and checks if the Atv can pass the terrain.
     * @param theTerrain The terrain the vehicle wants to pass.
     * @param theLight The light color.
     * @return a boolean true if it can pass or false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight){
        return theTerrain != Terrain.WALL;
    }

    /**
     * randomMoveSet is a helper method which stores all the possible choices in an arraylsit
     * and then shuffles it (random) and returns the first direction in the list.
     * @param theNeighbors is a map which contains the surrounding terrain.
     * @return a valid direction for the ATV to travel in.
     */
    private Direction randomMoveSet(final Map<Direction, Terrain> theNeighbors) {
        final ArrayList<Direction> possiblemoves = new ArrayList<>(3);

        if ((theNeighbors.get(getDirection()) != Terrain.WALL)){
            possiblemoves.add(getDirection());
        }
        if ((theNeighbors.get(getDirection().right()) != Terrain.WALL)){
            possiblemoves.add(getDirection().right());
        }
        if ((theNeighbors.get(getDirection().left()) != Terrain.WALL)) {
            possiblemoves.add(getDirection().left());
        }
        Collections.shuffle(possiblemoves);

        return possiblemoves.remove(0);
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
