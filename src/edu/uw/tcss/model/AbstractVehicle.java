package edu.uw.tcss.model;
/*
 * TCSS 305
 * File Name: AbstractVehicle.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This abstract class declares and stores instance variable that are common across all subclasses
 * but specific to a particular instance of an object. It has a protected constructor to store
 * these common instance fields and methods for each vehicle.
 *
 * @author Lucas Jeong
 * @version 2023 November 9
 */
public abstract class AbstractVehicle implements Vehicle {
    /**
     * This constant stores the death timer of the vehicle.
     */
    private final int myDeathTime;
    /**
     * This instance field stores the X position of the vehicle.
     */
    private int myX;
    /**
     * This instance field stores the Y position of the vehicle.
     */
    private int myY;
    /**
     * This instance field stores the direction of the vehicle.
     */
    private Direction myDir;
    /**
     * This instance field stores the status of the vehicle.
     */
    private boolean myStatus;
    /**
     * This instance field stores the number of pokes for the vehicle.
     */
    private int myPokes;
    /**
     * This instance field stores the initial X position of the vehicle.
     */
    private final int myInitialX;
    /**
     * This instance field stores the initial Y position of the vehicle.
     */
    private final int myInitialY;
    /**
     * This instance field stores the intial direction of the vehicle.
     */
    private final Direction myInitialDir;

    /**
     * This protected constructor instantiates the common instance fields for vehicles and includes
     * the position, intiial position, direction, initial direction, pokes, death time,
     * the status, and the initial status.
     *
     * @param theX is the X position of the vehicle.
     * @param theY is the Y position of the vehicle.
     * @param theDir is the direction of the vehicle.
     * @param theDeathTime is the amount of pokes required to revive a vehicle when dead.
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir, final int theDeathTime){
        super();
        myX = theX;
        myY = theY;
        myDir = theDir;
        myStatus = true;
        myPokes = 0;
        myDeathTime = theDeathTime;

        myInitialX = theX;
        myInitialY = theY;
        myInitialDir = theDir;
    }

    /**
     * Setter method for the X value in position.
     */
    @Override
    public void setX(final int theX){
        myX = theX;
    }

    /**
     * Setter method for the Y value in position.
     */
    @Override
    public void setY(final int theY){
        myY = theY;
    }

    /**
     * Getter method for the X value in position.
     * @return the X position as an integer value.
     */
    @Override
    public int getX(){
        return myX;
    }

    /**
     * Getter method for the Y value in position.
     * @return the Y position as an integer value.
     */
    @Override
    public int getY(){
        return myY;
    }

    /**
     * Getter method for the current direction of the vehicle.
     * @return the current direction.
     */
    @Override
    public Direction getDirection(){
        return myDir;
    }

    /**
     * Setter method for the current direction of the vehicle.
     * @param theDir is the direction that is going to be set to the direction instance field.
     */
    @Override
    public void setDirection(final Direction theDir)
    {
        myDir = theDir;
    }

    /**
     * This method resets the vehicle to its initial state.
     */
    @Override
    public void reset(){
        setDirection(myInitialDir);
        setX(myInitialX);
        setY(myInitialY);
        myStatus = true;
    }

    /**
     * This method gets the image file of the class depending on whether its dead or alive.
     * @return the String corresponding to the image file which represents its dead or alive status.
     */
    @Override
    public String getImageFileName(){
        String classname = getClass().getSimpleName().toLowerCase();

        if (isAlive()) {
            classname += ".gif";
        } else {
            classname += "_dead.gif";
        }
        return classname;
    }

    /**
     * This method is called when the GUI detects that two vehicles are on the same position and
     * updates the vehicle's status (assuming that it is the one that is killed when it runs into
     * a "surperior" or larger vehicle object).
     *
     * @param theOther Is the other vehicle.
     */
    @Override
    public void collide(final Vehicle theOther){
        if ((this.isAlive() && theOther.isAlive()) && this.myDeathTime > theOther.getDeathTime())
        {
            myStatus = false;
        }
    }

    /**
     * Checks whether the vehicle is alive or dead.
     * @return a boolean true if the vehicle is alive and false if the vehicle is dead.
     */
    @Override
    public boolean isAlive() {
        return myStatus;
    }

    /**
     * This poke method is called by the GUI for every vehicle that is dead.
     */
    @Override
    public void poke(){
        myPokes++;
        if (myPokes > myDeathTime)
        {
            myStatus = true;
            myPokes = 0;
            setDirection(Direction.random());
        }
    }

    /**
     * This toString is used for testing the vehicle classes which inheret from abstract vehicle.
     * It returns a string containing the class name, its position, direction, and status.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase() + ", X position:" + getX() + ", Y position:" + getY()
                + ", Direction:" + getDirection() + ", Status: " + isAlive() + ", Pokes: " + myPokes;
    }

    /**
     * Getter method for the death time parameter.
     * @return the time it takes to revive.
     */
    @Override
    public int getDeathTime(){
        return myDeathTime;
    }
}
