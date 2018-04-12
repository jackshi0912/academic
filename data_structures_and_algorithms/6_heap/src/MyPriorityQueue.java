/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 19th, 2016
 * LOGIN: cs12wjz
 */
package hw6;

import java.util.*;

/**
 * This class is a priority queue using a binary heap.
 * 
 * @author Jack Shi
 * @version Feb.19, 2016
 * @param <T>
 *            The input must extends comparable which is capable of comparing
 *            with it's own super class.
 */
public class MyPriorityQueue<T extends Comparable<? super T>> {

    public static final int INITIAL_SIZE = 11; // The initial size of the
                                               // priority queue
    dHeap<T> queue; // The priority queue using heap

    /**
     * No argument constructor for the priority queue. The initial size of the
     * priority queue is 11
     */
    MyPriorityQueue() {
        queue = new dHeap<>(INITIAL_SIZE); // Initialize the heap based queue
    }

    /**
     * The constructor of the priority queue that copies all of the elements of
     * the argument Collection as the starting elements of the priorty queue.
     * 
     * @param c
     *            The starting elements of the priority queue.
     */
    MyPriorityQueue(Collection<? extends T> c) {
        queue = new dHeap<>(c.size()); // Initialize the queue to the size of
                                       // the starting elements.
        // Loop through the queue to add all the elements from the collection.
        for (int i = 0; i < c.size(); i++) {
            queue.add(c.iterator().next());
        }
    }

    /**
     * The constructor for the priority queue that initialize the priority queue
     * with a initial size of choice
     * 
     * @param initialCapacity
     *            The initial size of the priority queue
     */
    MyPriorityQueue(int initialCapacity) {
        queue = new dHeap<>(initialCapacity); // Initialize the queue with a
                                              // initial size
    }

    /**
     * The cop constructor for the priority queue;
     * 
     * @param c
     *            The argument priority queue to be copied into the current
     *            queue
     */
    MyPriorityQueue(PriorityQueue<? extends T> c) {
        this.queue = new dHeap<>(c.size()); // Initialize the queue as the
                                            // argument queue's size
        // Loop through the queue to add all the appropriate values
        for (int i = 0; i < c.size(); i++) {
            this.queue.add(c.poll());
        }
    }

    /**
     * Add a element to the priority queue
     * 
     * @param element
     *            The element to be added to the priority queue
     */
    public void add(T element) {
        queue.add(element); // Add the element to the priority queue
    }

    /**
     * Clear the queue of all elements
     */
    void clear() {
        // Loop through the queue and remove all the elements
        for (int i = 0; i < queue.size(); i++) {
            queue.removeSmallest();
        }
    }

    /**
     * Check if the queue contains a certain element
     * 
     * @param o
     *            The target object to find in the queue
     * @return Is the object in the priority queue
     */
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        // Loop through the queue to find if the object is part of the queue
        for (int i = 0; i < queue.size(); i++) {
            // Return true on the first instance when the target object is found
            if (queue.peek().compareTo((T) o) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Offer to add a element. Return false if the adding process failed.
     * 
     * @param element
     *            The new element to be added
     * @return Is the adding process successful
     */
    public boolean offer(T element) {
        // Check if the input element is null
        if (element == null) {
            return false;
        }
        // Add the element.
        queue.add(element);
        return true;
    }

    /**
     * Get the root element without pulling it off the queue
     * 
     * @return The root element
     */
    public T peek() {
        T temp = queue.removeSmallest();
        queue.add(temp);
        return temp;
    }

    /**
     * Pull the root element off the priority queue and return it to the user
     * 
     * @return The root element of the priority queue
     */
    public T poll() {
        return queue.removeSmallest();
    }

    /**
     * Remove an element from the priority queue
     * 
     * @param o
     *            The target element to remove
     * @return Is the removal successful
     */
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        // Check if the priority queue is empty
        if (queue.size() == 0) {
            return false;
        }

        // Initialize a temporary heap to hold the removed data
        dHeap<T> temp = new dHeap<>(INITIAL_SIZE);
        
        // Loop through the queue by pulling root element and store it in temp
        for (int i = 0; i < queue.size(); i++) {
            temp.add(queue.removeSmallest());
            
            // If there is a match, remove the target element from the queue
            if (temp.peek().compareTo((T) o) == 0) {
                temp.removeSmallest();
                break;
            }
        }

        // Add all the element of the temp heap back to queue
        for (int i = 0; i < temp.size(); i++) {
            queue.add(temp.removeSmallest());
        }

        return true;
    }

    /**
     * Get the size of the queue
     * 
     * @return The size of the queue
     */
    public int size() {
        return queue.size();
    }

    /**
     * Return an array version of the priority queue
     * 
     * @return The array version of the priority queue
     */
    public Object[] toArray() {
        dHeap<T> temp = new dHeap<>(INITIAL_SIZE);
        Object[] tempArr = new Object[queue.size()];
        
        // Loop through the array to add each value from the queue
        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = queue.peek();
            temp.add(queue.removeSmallest()); // Store the pulled off elements in temp
        }
        queue = temp; // Swap the references
        return tempArr;
    }
}
