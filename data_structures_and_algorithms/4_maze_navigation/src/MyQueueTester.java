/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Tester for the myQueue Class
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class MyQueueTester {

    // Declare the appropriate variables
    public final int SIZE = 20;
    private MyQueue<Integer> queue;

    /**
     * Set up the preconditions by initiating all the appropriate values
     */
    @Before
    public void setup() {
        queue = new MyQueue<>();
    }

    /**
     * Test the isEmpty method of the queue class
     */
    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.addElement(new Integer(0));
        queue.removeElement();
        assertTrue(queue.isEmpty());
    }

    /**
     * Check the Size method of the queue class
     */
    @Test
    public void testSize() {
        assertEquals(0, queue.size());
        queue.addElement(new Integer(0));
        assertEquals(1, queue.size());
    }

    /**
     * Check the add and remove element methods of the queue
     */
    @Test
    public void testAddRemoveElement() {
        // Add a lot of stuff
        for (int i = 0; i < SIZE; i++) {
            queue.addElement(new Integer(i));
        }
        // Remove them while checking the values
        for (int i = 0; i < SIZE; i++) {
            assertEquals(new Integer(i), queue.removeElement());
        }
    }

    /**
     * Test the exception throwing of the removeElement method
     */
    @Test
    public void testRemoveException() {
        try {
            queue.removeElement();
            fail();
        } catch (NoSuchElementException e) {

        }
    }
}
