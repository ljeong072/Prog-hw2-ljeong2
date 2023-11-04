package edu.uw.tcss.model;

/*
 * TCSS 305
 * File Name: AbstractVehicle.java
 * Instructor: Charles Bryan
 * Assignment: Programming Assignment 2
 * Due Date: 11/10/2023
 */

/**
 * This abstract class declares and stores instance variable all subclasses
 * must have, and a protected constructor to initialize the instance variable
 * holding the name of the type of of vehicle.
 *
 * @author Lucas Jeong
 * @version 2023 January 30
 */
public abstract class AbstractVehicle implements Vehicle {
    /**
     * This instance field stores the death time of the vehicle.
     */
    private final int DEATH_TIME;
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
     * This instance field stores the intiial direction of the vehicle.
     */
    private final Direction myInitialDir;
    /**
     * This instance field stores the initial status of the vehicle.
     */
    private final boolean myInitialStatus;

    /**
     * This protected constructor instantiates the common fields for vehicles and includes the position, intiial position,
     * direction, initial direction, pokes, death time, the status, and the initial status.
     *
     * @param theX is the x position vehicle.
     * @param theY is the y position of the vehicle.
     * @param theDir is the direction of the vehicle.
     * @param theDeathTime is time the vehicle takes to die.
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDir, final int theDeathTime){
        super();
        myX = theX;
        myY = theY;
        myDir = theDir;
        myStatus = true;
        myPokes = 0;

        DEATH_TIME = theDeathTime;
        myInitialX = theX;
        myInitialY = theY;
        myInitialDir = theDir;
        myInitialStatus = true;
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
        myStatus = myInitialStatus;
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
        if ((this.isAlive() && theOther.isAlive()) && this.getDeathTime() > theOther.getDeathTime())
        {
            myStatus = false;
        }
    }

    /**
     * Getter method for the death time parameter.
     * @return the time it takes to revive.
     */
    @Override
    public int getDeathTime(){
        return DEATH_TIME;
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
        if (myPokes > DEATH_TIME && !myStatus)
        {
            myStatus = myInitialStatus;
            myPokes = 0;
            setDirection(Direction.random());
        }
    }
}
