/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import java.util.LinkedList;

/**
 * Class for the double ended singly linked list
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class DoubleEndedLL<T> implements DoubleEndedLLInterface<T> {
    // The list to contain all the data
    LinkedList<T> list;

    /**
     * Constructor that initializes the data storage list
     */
    public DoubleEndedLL() {
        list = new LinkedList<T>();
    }
 
    /**
     * Check if the list is empty
     * 
     * @return Is the list empty
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Accessor for the list's size
     * 
     * @return size of the list
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Add the element to the head of the list
     * 
     * @param The new element to be added
     */
    @Override
    public void addFirst(T newItem) {
        list.addFirst(newItem);
    }

    /**
     * Add element to the end of the list
     * 
     * @param The element to be added to the end of the list
     */
    @Override
    public void addLast(T newItem) {
        list.addLast(newItem);
    }

    /**
     * Remove the first element of the list
     * 
     * @return The element removed from the front of the list
     */
    @Override
    public T removeFirst() {
        return list.removeFirst();
    }

    /**
     * Remove the last element from the list
     * 
     * @return The element removed from the tail of the list
     */
    @Override
    public T removeLast() {
        return list.removeLast();
    }
}
