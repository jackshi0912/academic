/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import java.util.NoSuchElementException;

/**
 * The stack ADT implemented using Doubled Ended Singly Linked List
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class MyStack<T> implements Stack_QueueInterface<T> {

    // The list used to implement ADT
    private DoubleEndedLL<T> list;
    
    MyStack() {
        list = new DoubleEndedLL<>();
    }

    /**
     * Check if the storage is empty
     * 
     * @return Is the stack empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Remove an element from the top of the stack
     * 
     * @return The element that is removed from the top of the stack
     */
    public T removeElement() {
        if (list.size() == 0) {
            throw new NoSuchElementException();
        }
        T topElement = list.removeLast();
        return topElement;
    }

    /**
     * Add an element to the top of the stack
     * 
     * @param The element to be added to the top of the stack
     */
    public void addElement(T newElement) {
        list.addLast(newElement);
    }

    /**
     * Return the size of the stack
     */
    public int size() {
        return list.size();
    }

}
