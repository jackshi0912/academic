/**
 * Name: Huize Shi
 * PID: A92122910
 * Date Jan. 19, 2016
 */

package hw2;

import org.junit.*;
import static org.junit.Assert.*;

import java.nio.channels.IllegalSelectorException;
import java.util.ListIterator;

/**
 * Title: class LinkedListTester Description: JUnit test class for
 * DoublyLinkedList12 class
 */
public class DoublyLinkedList12Tester {
    // Declare 4 DoublyLinkedList12 for different contents
    private DoublyLinkedList12<Integer> empty;
    private DoublyLinkedList12<Integer> one;
    private DoublyLinkedList12<Integer> several;
    private DoublyLinkedList12<String>  slist;
    static final int                    DIM = 5;

    /**
     * Standard Test Fixture. An empty list, a list with one entry (0) and a
     * list with several entries (0,1,2)
     */
    @Before
    public void setUp() {
        // Initialize all DoublyLinkList12 to corresponding values
        empty = new DoublyLinkedList12<Integer>();
        one = new DoublyLinkedList12<Integer>();
        one.add(0, new Integer(0));
        several = new DoublyLinkedList12<Integer>();
        // List: 1,2,3,...,Dim
        for (int i = DIM; i > 0; i--) {
            several.add(0, new Integer(i));
        }

        // DoublyLinkedList12: "First","Last"
        slist = new DoublyLinkedList12<String>();
        slist.add(0, "First");
        slist.add(1, "Last");

    }

    /** Test if heads of the lists are correct */
    @Test
    public void testGetHead() {
        assertEquals("Check 0", new Integer(0), one.get(0));
        assertEquals("Check 0", new Integer(1), several.get(0));
        assertEquals("Check 0", new Integer(0), one.get(0));
        assertEquals("Check 0", new Integer(1), several.get(0));
    }

    /** Test if size of lists are correct */
    @Test
    public void testListSize() {
        assertEquals("Check Empty Size", 0, empty.size());
        assertEquals("Check One Size", 1, one.size());
        assertEquals("Check Several Size", DIM, several.size());
    }

    /** Test setting a specific entry */
    @Test
    public void testSet() {
        // Change the lists values by using the list's set method
        slist.set(1, "Final");
        several.set(0, 9000);
        empty.set(0, 9999);
        assertEquals("Setting specific value", "Final", slist.get(1));
        assertEquals("Setting specific value", new Integer(9000), several.get(0));
        assertEquals("Setting specific value", new Integer(9999), empty.get(0));
    }

    /** Test isEmpty */
    @Test
    public void testEmpty() {
        assertTrue("empty is empty", empty.isEmpty());
        assertTrue("one is not empty", !one.isEmpty());
        assertTrue("several is not empty", !several.isEmpty());
    }

    /** Test out of bounds exception on get */
    @Test
    public void testGetException() {
        // Test get exceptions
        try {
            empty.get(0);
            fail("Should have generated an exception");
        } catch (IndexOutOfBoundsException e) {
        }
        // Test add exceptions
        try {
            empty.add(2, 9000);
            fail("Should have generated an exception");
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            empty.add(null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {
        }
        try {
            empty.add(0, null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {
        }
        // Test set exceptions
        try {
            empty.set(0, null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {
        }
        try {
            empty.set(1, 9000);
            fail("Should have generated an exception");
        } catch (IndexOutOfBoundsException e) {
        }
        // Test remove exceptions
        try {
            empty.remove(1);
            fail("Should have generated an exception");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    /** Test iterator on empty list and several list */
    @Test
    public void testIteratorGeneral() {
        // LinkedList
        int counter = 0;
        ListIterator<Integer> iter;
        for (iter = empty.listIterator(); iter.hasNext();) {
            fail("Iterating empty list and found element");
        }
        counter = 0;
        for (iter = several.listIterator(); iter.hasNext(); iter.next())
            counter++;
        assertEquals("Iterator several count", counter, DIM);
    }

    /** Test iterator's add function */
    @Test
    public void testIteratorAdd() {
        ListIterator<String> iter = slist.listIterator();
        // Try the exception throwing
        try {
            iter.add(null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {
        }

        // Check adding functionality
        iter.add("Hello");
        assertEquals("Iterator value at 0", "Hello", slist.get(0));
    }

    /** Test iterator's previous function */
    @Test
    public void testIteratorPrevious() {
        ListIterator<String> iter = slist.listIterator();
        // Try the exception throwing
        try {
            iter.add(null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {
        }

        iter.next();
        // Checking the previous method
        assertEquals("Iterator value of previous", "First", iter.previous());
    }

    /** Test iterator's remove function */
    @Test
    public void testIteratorRemove() {
        ListIterator<String> iter = slist.listIterator();
        // Try the exception throwing
        try {
            iter.remove();
            fail("Should have generated an exception");
        } catch (IllegalStateException e) {

        }
        iter.next();
        // Check remove method of the iterator
        iter.remove();
        assertEquals("Iterator value of previous", "Last", iter.next());
    }

    /** Test iterator's set function */
    @Test
    public void testIteratorSet() {
        ListIterator<String> iter = slist.listIterator();
        // Try the exception throwing
        try {
            iter.set(null);
            fail("Should have generated an exception");
        } catch (NullPointerException e) {

        }
        try {
            iter.set("Hello");
            fail("Should have generated an exception");
        } catch (IllegalSelectorException e) {

        }
        iter.next();
        // Check set method of the iterator
        iter.set("Hello");
        assertEquals("Iterator value of set", "Hello", iter.previous());
    }
    
    @Test
    public void testReverseAndConcat() {
        several.reverseAndConcat(several);
        for (int i = 0; i < DIM; i++) {
            assertEquals("Check the original values", new Integer(i+1), 
                    several.get(i));
        }
        for (int i = DIM; i < several.size(); i++) {
            assertEquals("Check the flipped values", new Integer(DIM*2-i), 
                    several.get(i));
        }
    }
}
