/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 26th, 2016
 * LOGIN: cs12wjz
 */
package hw7;

import java.io.*;
import java.util.*;

/**
 * This program takes a text file from command line and change the postfix
 * expression to infix
 * 
 * @author Huize Shi
 * @version Feb. 27, 2016
 */
public class Postfix {

    /**
     * The main method of this program that takes a command line input
     * 
     * @param args
     *            Command line input of a string array
     */
    public static void main(String[] args) {
        // Declaring and initializing variables
        Scanner in;
        Stack<BSTree> stack = new Stack<>();

        // Feed the command line file to the scanner and check for potential
        // errors regarding file not found
        try {
            in = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.print("File Not Found!");
            return;
        }

        while (in.hasNextLine()) { // Keeps iteration while the files has more
                                   // lines
            stack = new Stack<>(); // Initialize a stack to hold the operands
            String line = in.nextLine(); // One line of the file

            // Iterate through the line to push operands onto the stack
            for (int i = 0; i < line.length(); i++) {
                // Push operands onto the stack
                if (Character.isLetter(line.charAt(i))) {
                    stack.push(new BSTree(line.charAt(i)));
                } else { // If an operator is discovered, pop two operands from
                         // the stack then construct a new tree, then push the
                         // new tree onto the stack
                    BSTree newTree = new BSTree(line.charAt(i));
                    newTree.setRightChild(stack.pop().getRoot());
                    newTree.setLChild(stack.pop().getRoot());
                    stack.push(newTree);

                }
            }
            System.out.println(stack.pop().printTree()); // Print out the tree
        }
        in.close();
    }

    /**
     * The binary search tree that is a inner class of Postfix
     * 
     * @author Huize Shi
     * @version Feb. 27, 2016
     */
    private static class BSTree {

        // Initialize appropriate instance variables
        private LinkedList<Character> temp;
        private BSTNode root;

        /**
         * Constructor for the Binary Search tree
         * 
         * @param c
         *            The character to be stored in the root
         */
        public BSTree(Character c) {
            root = new BSTNode(null, c, null);
        }

        /**
         * Set the right child of the root to a BSTNode
         * 
         * @param rChild
         *            The new right child for the root
         */
        public void setRightChild(BSTNode rChild) {
            root.setRChild(rChild);
        }

        /**
         * Set the left child of the root to a BSTNode
         * 
         * @param lChild
         *            The new left child for the root
         */
        public void setLChild(BSTNode lChild) {
            root.setLChild(lChild);
        }

        /**
         * Print the tree to a string
         * 
         * @return The tree as a string
         */
        public String printTree() {
            // initialize the helper linked list
            temp = new LinkedList<Character>();
            // Print the tree using recursive helper method
            printToListRecursive(root);
            // Setting the initial string to "" then add elements of the tree to
            // the string
            String sTemp = "";
            for (Character c : temp) {
                sTemp += c;
            }
            return sTemp;
        }

        /**
         * Helper method to store each value of the tree to a linked list
         * 
         * @param root
         *            The current node for the element searching algorithm
         */
        private void printToListRecursive(BSTNode root) {
            if (root != null) { // Collapse the stack frame when the current
                                // node is null

                // recursively search each child of the current node
                this.printToListRecursive(root.getLChild());
                temp.add(root.getElement()); // Add the element to the list
                this.printToListRecursive(root.getRChild());
            }
        }

        /**
         * Accessor for the root of the tree
         * 
         * @return The root BSTNode of the tree
         */
        public BSTNode getRoot() {
            return root;
        }

        /**
         * The inner class of BSTree that describe each node in the tree
         * 
         * @author JackShi
         * @version Feb. 27, 2016
         */
        private class BSTNode {

            private Character c; // data
            private BSTNode lChild; // Left Node
            private BSTNode rChild; // Right Node

            /**
             * Constructor for the Node
             * 
             * @param lChild
             *            The left node of the current node
             * @param c
             *            Data for the node to hold
             * @param rChild
             *            The right node of the current node
             */
            public BSTNode(BSTNode lChild, Character c, BSTNode rChild) {
                // Initialize appropriate data
                this.c = c;
                this.rChild = rChild;
                this.lChild = lChild;
            }

            /**
             * Return the data of the node
             * 
             * @return Data encapsulated in the node
             */
            public Character getElement() {
                return c;
            }

            /**
             * Accesor for the left child node of the current node
             * 
             * @return Left child node
             */
            public BSTNode getLChild() {
                return this.lChild;
            }

            /**
             * Accesor for the right child node of the current node
             * 
             * @return The right child nod of the current node
             */
            public BSTNode getRChild() {
                return this.rChild;
            }

            /**
             * Mutator for the left child node of the current node
             * 
             * @param newChild
             *            The new child node to be the left child node of the
             *            current node
             */
            public void setLChild(BSTNode newChild) {
                this.lChild = newChild;
            }

            /**
             * Mutator for the right child node of the current node
             * 
             * @param newChild
             *            The new child node to be the right child node of the
             *            current node
             */
            public void setRChild(BSTNode newChild) {
                this.rChild = newChild;
            }
        }
    }
}
