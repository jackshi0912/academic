/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import java.util.NoSuchElementException;

/**
 * Implement a queue ADT using circular arrays
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class MyQueue<T> implements Stack_QueueInterface<T> {

    // Declare all variables
    private T[] array;
    private int initialIndex;
    private int finalIndex;
    private int size;
    private boolean isEmpty;
    public static final int INITIAL_SIZE = 20;

    /**
     * Constructor for the Queue ADT
     * 
     * @param size
     *            The initial size of the array
     */
    @SuppressWarnings("unchecked")
    public MyQueue() {
        // Initialize all the values
        this.size = INITIAL_SIZE;
        array = (T[]) new Object[INITIAL_SIZE];
        initialIndex = 0;
        finalIndex = 0;
        isEmpty = true;
    }

    /**
     * Return if the list is empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Add an element to the end of the queue
     * 
     * @param newElement
     *            The element to be added to the end of the queue
     */
    public void addElement(T newElement) {
        // Check if the array is filled up
        if (this.getInitialIndex() == this.getFinalIndex() && !isEmpty) {
            this.enlargeArray();
        }
        // Add the element and change the appropriate flages and variables
        isEmpty = false;
        array[this.getFinalIndex()] = newElement;
        finalIndex++;
    }

    /**
     * Remove a element from the front of the queue
     * 
     * @return The element newly removed
     */
    public T removeElement() {
        // Check if the list is empty
        if (this.isEmpty) {
            throw new NoSuchElementException();
        }

        // Set the empty flag to true after
        if (this.size() == 1) {
            this.isEmpty = true;
        }

        // Record the element to be returned and move the initial cursor
        T element = array[this.getInitialIndex()];
        initialIndex++;
        return element;
    }

    /**
     * Return the size of the data
     */
    public int size() {
        // return the size of the data
        return finalIndex - initialIndex;
    }

    /**
     * Return the final index
     * 
     * @return The final index of the data
     */
    private int getFinalIndex() {
        // Return the accumulative final index moded by the array's size
        return finalIndex % size;
    }

    /**
     * Return the initial index
     * 
     * @return The initial index of the data
     */
    private int getInitialIndex() {
        // Return the accumulative initial index moded by the array's size
        return initialIndex % size;
    }

    /**
     * Enlarge the array to twice it's size
     */
    private void enlargeArray() {
        // Initialize the larger array
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[array.length * 2];
        // Copy all the data
        for (int i = 0; i < this.size(); i++) {
            newArray[i] = array[(this.getInitialIndex() + i) % size];
        }
        // Resetting variables
        initialIndex = 0;
        finalIndex = array.length;
        // Swapping references
        array = newArray;
        size = array.length;
    }
}