package edu.uw.tcss.util.Bag;

import java.util.ArrayList;
import java.util.Collections;
/**
 * The Bag class which is an ADT which accepts Generic types. You can put generic types objects in the
 * bag, see if the bag is empty, or grab from the bag.
 *
 * @author Lucas Jeong
 * @version November 6
 * @param <T> is a generic data type for the bag class.
 */
public class MyBag<T> implements Bag<T>{
    private final ArrayList<T> myList;

    /**
     * This constructor creates a Bag ADT so the user can put generic type objects in the bag.
     */
    public MyBag() {
        super();
        myList = new ArrayList<>();
    }

    @Override
    public void putBag(final T theItem)
    {
        myList.removeIf(t -> t.equals(theItem));
        myList.add(theItem);
    }

    @Override
    public T grabBag()
    {
        if (myList.isEmpty()) {
            throw new NullPointerException("The bag is empty so you cannot grab any element from it.");
        }
        Collections.shuffle(myList);
        return myList.get(0);
    }

    @Override
    public boolean getBagEmpty(){
        return myList.isEmpty();
    }
}