/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 19th, 2016
 * LOGIN: cs12wjz
 */
package hw6;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the JUnit tester class for the dHeap class
 * 
 * @version Feb. 19, 2016
 * @author Jack Shi
 */
public class dHeapTester {

    private dHeap<Integer> heap; // Heap to be tested

    /**
     * Setup the test by initializing all the appropriate values
     */
    @Before
    public void setUp() {
        heap = new dHeap<>(3, 5);
    }

    /**
     * Test the size getter method and make sure the size is handled correctly
     */
    @Test
    public void sizeTest() {
        assertEquals(0, heap.size()); // Checking if the initial size is 0
        // Add two elements
        heap.add(new Integer(5));
        heap.add(new Integer(6));
        assertEquals(2, heap.size()); // Check if the size is correct
    }

    /**
     * Checking if the adding and removing
     */
    @Test
    public void addRemoveTest() {
        // Adding a lot of numbers
        heap.add(new Integer(5));
        heap.add(new Integer(6));
        heap.add(new Integer(1));
        heap.add(new Integer(3));
        heap.add(new Integer(15));
        heap.add(new Integer(6));
        heap.add(new Integer(3));
        heap.add(new Integer(32));
        // Making sure the adding and removing both work by checking the
        // removeSmallest method
        assertEquals(new Integer(1), heap.removeSmallest());
        assertEquals(new Integer(3), heap.removeSmallest());
        assertEquals(new Integer(3), heap.removeSmallest());
        assertEquals(new Integer(5), heap.removeSmallest());
        assertEquals(new Integer(6), heap.removeSmallest());
        assertEquals(new Integer(6), heap.removeSmallest());
        assertEquals(new Integer(15), heap.removeSmallest());
        assertEquals(new Integer(32), heap.removeSmallest());
    }
}
