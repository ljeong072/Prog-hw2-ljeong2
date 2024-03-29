package edu.uw.tcss.model;

import java.util.Map;

/*
 * TCSS 305
 * File Name: Car.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This is the Car class which extends AbstractVehicle class. It prefers to
 * drive straight. If that direction is not possible, left, right, and reverse.
 * It can only traverse streets and through lights and crosswalks. It ignores
 * yellow and green lights but stops for red and yellow lights. It's death time is 15.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */

public class Car extends AbstractVehicle{
    /**
     * This constant represents the amount of pokes required to revive the car.
     */
    private static final int DEATH_TIME = 15;

    /**
     * This car constructor creates a vehicle which passes the X, Y positions and the
     * current direction and death timer.
     * @param theX is the current X position of the Car.
     * @param theY is the current Y position of the Car.
     * @param theDir is the current direction of the Car.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    /**
     * This method chooses a direction according to the car's "preference" to go
     * straight if possible,
     * left if possible, right if possible, and then reverse if necessary.
     * @param theNeighbors The map of neighboring terrain.
     * @return a direction that the car can travel in.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction findirection;

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

    /**
     * This method checks if the car can pass the terrain it is on given the option
     * to check for terrain and the light cycle. It can pass streets regardless of
     * light conditions, but it can cross lights when it is green and yellow.
     * Otherwise, it can cross crosswalks only when the light is green.
     * @param theTerrain is the passed in terrain the car wants to pass.
     * @param theLight The current light cycle.
     * @return a boolean true if the car can pass, and false if the vehicle cannot pass.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean stoplight = false;

        if (theTerrain == Terrain.STREET) {
            stoplight = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN) {
            stoplight = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.YELLOW) {
            stoplight = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            stoplight = true;
        }
        return stoplight;
    }

    /**
     * This helper method checks if the following terrain is viable given a direction
     * and a map of the adjacent terrain for the car.
     * @param theNeighbors is a map of the terrain adjacent to the car.
     * @param theDirection is the direction that this method checks to see is viable
     * @return a boolean true if the given direction is a viable terrain to traverse.
     */
    private boolean isViableOption(final Map<Direction, Terrain> theNeighbors,
                                   final Direction theDirection){
        return (theNeighbors.get(theDirection) == Terrain.STREET) ||
                (theNeighbors.get(theDirection) == Terrain.CROSSWALK) ||
                (theNeighbors.get(theDirection) == Terrain.LIGHT);
    }
}