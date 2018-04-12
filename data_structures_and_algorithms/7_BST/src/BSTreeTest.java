package hw7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BSTreeTest {

    private BSTree tree;

    @Before
    public void setUp() {
        tree = new BSTree();
    }

    @Test
    public void testInsertRemove() {
        // Inserting some elements then check if the elements are correctly
        // inserted
        tree.insert("Jack3", 3);
        tree.insert("Jack5", 5);
        tree.insert("Jack6", 6);
        tree.insert("Jack1", 1);
        tree.insert("Jack4", 4);
        assertEquals(new Person("Jack1", 1),
                tree.getRoot().getLChild().getPerson());
        assertEquals(new Person("Jack3", 3), tree.getRoot().getPerson());
        assertEquals(new Person("Jack6", 6),
                tree.getRoot().getRChild().getRChild().getPerson());
        assertEquals(new Person("Jack5", 5),
                tree.getRoot().getRChild().getPerson());
        assertEquals(new Person("Jack4", 4),
                tree.getRoot().getRChild().getLChild().getPerson());

        // Removing Node with 2 children, right child has no children
        tree.delete(5, "Jack5");
        assertEquals(new Person("Jack6", 6),
                tree.getRoot().getRChild().getPerson());
        assertEquals(new Person("Jack4", 4),
                tree.getRoot().getRChild().getLChild().getPerson());

        // Removing Node with 2 children, the right child has 2 children
        tree.insert("Jack9", 9);
        tree.insert("Jack8", 8);
        tree.insert("Jack10", 10);
        tree.delete(6, "Jack6");
        assertEquals(new Person("Jack8", 8),
                tree.getRoot().getRChild().getPerson());
        assertEquals(new Person("Jack4", 4),
                tree.getRoot().getRChild().getLChild().getPerson());
        assertEquals(new Person("Jack9", 9),
                tree.getRoot().getRChild().getRChild().getPerson());
        assertEquals(new Person("Jack10", 10),
                tree.getRoot().getRChild().getRChild().getRChild().getPerson());

        // Removing a element without any child
        tree.delete(10, "Jack10");
        try {
            System.out.print(tree.getRoot().getRChild().getRChild().getRChild()
                    .getPerson()); // Check if the element is properly removed
            fail();
        } catch (NullPointerException e) {

        }

        // Trying insert's exception handling
        try {
            tree.insert("", 20);
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.insert("Jack", 201);
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.insert("", 201);
            fail();
        } catch (IllegalArgumentException e) {

        }
        
     // Trying delete's exception handling
        try {
            tree.delete(20, "");
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.delete(201, "Jack");
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.delete(201, "");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testToArray() {
        Person[] personArray;
        try {
            personArray = tree.printToArray(tree.getRoot());
            fail();
        } catch (NullPointerException e) {
            tree.insert("Jack3", 3);
            tree.insert("Jack5", 5);
            tree.insert("Jack6", 6);
            tree.insert("Jack1", 1);
            tree.insert("Jack4", 4);
            personArray = tree.printToArray(tree.getRoot());
            assertEquals(new Person("Jack1", 1), personArray[0]);
            assertEquals(new Person("Jack3", 3), personArray[1]);
            assertEquals(new Person("Jack4", 4), personArray[2]);
            assertEquals(new Person("Jack5", 5), personArray[3]);
            assertEquals(new Person("Jack6", 6), personArray[4]);
        }
    }

    @Test
    public void testFindPerson() {
        tree.insert("Jack5", 5);
        tree.insert("Jack6", 6);
        tree.insert("Jack4", 4);
        tree.insert("Jack7", 7);
        tree.insert("Jack1", 1);

        assertTrue(tree.findPerson(5, "Jack5"));
        assertTrue(tree.findPerson(6, "Jack6"));
        assertTrue(tree.findPerson(4, "Jack4"));
        assertTrue(tree.findPerson(7, "Jack7"));
        assertTrue(tree.findPerson(1, "Jack1"));
        assertTrue(!tree.findPerson(10, "Jack10"));
        
        try {
            tree.findPerson(20, "");
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.findPerson(201, "Jack");
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            tree.findPerson(201, "");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testFindDepth() {
        tree.insert("Jack5", 5);
        tree.insert("Jack6", 6);
        tree.insert("Jack4", 4);
        tree.insert("Jack7", 7);
        tree.insert("Jack1", 1);
        tree.insert("Jack1", 9);

        assertEquals(3, tree.findDepth(tree.getRoot()));
    }

    @Test
    public void testLeafCount() {
        tree.insert("Jack10", 10);
        tree.insert("Jack15", 15);
        tree.insert("Jack5", 5);
        tree.insert("Jack4", 4);
        tree.insert("Jack7", 7);
        tree.insert("Jack14", 14);
        tree.insert("Jack20", 20);

        assertEquals(4, tree.leafCount());
    }

    @Test
    public void testLevelCount() {
        tree.insert("Jack10", 10);
        tree.insert("Jack15", 15);
        tree.insert("Jack5", 5);
        tree.insert("Jack4", 4);
        tree.insert("Jack7", 7);
        tree.insert("Jack14", 14);
        tree.insert("Jack20", 20);

        assertEquals(4, tree.levelCount(2));
        assertEquals(2, tree.levelCount(1));
        assertEquals(1, tree.levelCount(0));
        assertEquals(-1, tree.levelCount(-3));
        assertEquals(-1, tree.levelCount(10));
    }

}