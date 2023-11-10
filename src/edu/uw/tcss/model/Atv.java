package edu.uw.tcss.model;

import java.util.Map;
import edu.uw.tcss.util.Bag.MyBag;
/*
 * TCSS 305
 * File Name: Atv.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This is the ATV class which extends AbstractVehicle class. It randomly moves
 * straight, left, or right on any terrain that is not a while and ignores all light
 * cycles for crosswalks and lights. It's death time is 25.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public class Atv extends AbstractVehicle{
    /**
     * This constant holds the time of death for the Atv class.
     */
    private static final int DEATH_TIME = 25;

    /**
     * constructor which passes the X and Y position with the direction and
     * the death timer to the abstract class.
     *
     * @param theX is the current X position of the Atv.
     * @param theY is the current Y position of the Atv.
     * @param theDir is the current direction of the Atv.
     */
    public Atv(final int theX, final int theY, final Direction theDir)
    {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * randomMoveSet is the possible directions that could be chosen by the ATV which
     * it randomly chooses straight, right, and left depending on it's terrain.
     * @param theNeighbors The map of neighboring terrain.
     * @return a valid direction for the ATV to travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
            final MyBag<Direction> thebag = new MyBag<>();
            if ((theNeighbors.get(getDirection()) != Terrain.WALL)){
                thebag.putBag(getDirection());
            }
            if ((theNeighbors.get(getDirection().right()) != Terrain.WALL)){
                thebag.putBag(getDirection().right());
            }
            if ((theNeighbors.get(getDirection().left()) != Terrain.WALL)) {
                thebag.putBag(getDirection().left());
            }
            return thebag.grabBag();
    }

    /**
     * This method is called and checks if the Atv can pass the terrain.
     * The Atv can pass across all terrains regardless of light color,
     * but cannot traverse through walls.
     * @param theTerrain The terrain the vehicle wants to pass.
     * @param theLight The light color.
     * @return a boolean true if it can pass or false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight){
        return theTerrain != Terrain.WALL;
    }
}
