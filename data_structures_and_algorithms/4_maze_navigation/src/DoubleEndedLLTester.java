/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for the link DoubleEndedSinglyLL class
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class DoubleEndedLLTester {

    // Declare the list to be tested
    private DoubleEndedLL<Integer> list;

    @Before
    public void setup() {
        list = new DoubleEndedLL<>(); // Initialize the list
    }

    @Test
    public void testIsEmpty() {
        // Test the emptiness of the fresh list
        assertTrue(list.isEmpty());

        // Test the emptiness after the list has been modified
        list.addFirst(new Integer(1));
        assertTrue(!list.isEmpty());
        list.removeFirst();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSize() {
        // Check initial size of the fresh list
        assertEquals(0, list.size());

        // Check the size after the list has been modified
        list.addFirst(new Integer(0));
        assertEquals(1, list.size());
    }

    @Test
    public void testAddRemoveFirst() {
        // Check if adding and removing first are working by adding some integer
        // into the list then removing them to check the values
        list.addFirst(new Integer(0));
        list.addFirst(new Integer(1));
        assertEquals(new Integer(1), list.removeFirst());
        assertEquals(new Integer(0), list.removeFirst());
    }

    @Test
    public void testAddRemoveLast() {
        // Check if adding and removing last are working by adding some integer
        // into the list then removing them to check the values
        list.addLast(new Integer(0));
        list.addLast(new Integer(1));
        assertEquals(new Integer(1), list.removeLast());
        assertEquals(new Integer(0), list.removeLast());
    }
}
