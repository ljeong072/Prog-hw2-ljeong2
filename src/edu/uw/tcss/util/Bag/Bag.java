/*
 * TCSS 305 - Bag interface
 */
package edu.uw.tcss.util.Bag;

/**
 * An interface for the Bag ADT
 *
 * @author Lucas Jeong
 * @version November 6
 * @param <T> is a generic type.
 */
public interface Bag<T> {
    /**
     * Allows you to put a generic type T into the Bag ADT.
     * @param theItem is a generic type item to be put in the bag.
     */
    void putBag(T theItem);

    /**
     * Allows you to grab a randomized generic type T based on what is already added in the bag.
     * if the bag is empty and this method is called, it will call a NullPointerException.
     * @return a generic type T that is randomized in the bag.
     */
    T grabBag();

    /**
     * checks if the bag is empty.
     * @return true if the bag is empty and false if the bag is not empty
     */
    boolean getBagEmpty();
}