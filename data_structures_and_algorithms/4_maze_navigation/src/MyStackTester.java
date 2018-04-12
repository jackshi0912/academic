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
 * JUnit Tester for the myStack Class
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class MyStackTester {

    // Declare the appropriate variables
    public final int SIZE = 20;
    private MyStack<Integer> stack;

    /**
     * Set up the preconditions by initiating all the appropriate values
     */
    @Before
    public void setup() {
        stack = new MyStack<>();
    }

    /**
     * Test the isEmpty method of the stack class
     */
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        
        // Test the isEmpty method after the stack is tempered with
        stack.addElement(new Integer(0));
        stack.removeElement();
        assertTrue(stack.isEmpty());
    }

    /**
     * Check the Size method of the stack class
     */
    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.addElement(new Integer(0));
        assertEquals(1, stack.size());
    }

    /**
     * Check the add and remove element methods of the stack
     */
    @Test
    public void testAddRemoveElement() {
        // Add a lot of stuff
        for (int i = 0; i < SIZE; i++) {
            stack.addElement(new Integer(i));
        }
        // Remove them while checking the values
        for (int i = SIZE - 1; i >= 0; i--) {
            assertEquals(new Integer(i), stack.removeElement());
        }
    }

    /**
     * Test the exception throwing of the removeElement method
     */
    @Test
    public void testRemoveException() {
        try {
            // Try to remove an element from a empty list
            stack.removeElement();
            fail();
        } catch (NoSuchElementException e) {

        }
    }
}
