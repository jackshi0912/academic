/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 26th, 2016
 * LOGIN: cs12wjz
 */

package hw7;

import java.util.LinkedList;

/**
 * The binary search tree
 * 
 * @author Huize Shi
 * @version Feb. 26, 2016
 */
public class BSTree {

    // Declaring and initializing appropriate values
    private BSTNode root;
    private BSTNode curNode;
    private int size;
    private int level;
    private int depth;
    private int leafCount;
    private LinkedList<Person> personList;
    private int levelCount;

    /**
     * Empty argument constructor to initialize all the appropriate values
     */
    public BSTree() {

        // Initialize all the appropriate values
        curNode = root;
        depth = 0;
        size = 0;
        leafCount = 0;
        level = 0;
        levelCount = 0;
    }

    /**
     * Insert another person into the tree by providing his name and key
     * 
     * @param name
     *            The new person's name
     * @param key
     *            The new person's key
     */
    public void insert(String name, int key) {

        if (name == "" || key < 1 || key > 200) { // Check the parameters
            throw new IllegalArgumentException();
        }

        if (size == 0) { // Base case 1, if tree is empty, simply make the root
            root = new BSTNode(null, new Person(name, key), null);
            curNode = root;
            size++;
            return;
        }

        // Base case 2, check if the key is greater than the key of the current
        // person and if it's left child is empty
        if (curNode.getPerson().getKey() > key && curNode.getLChild() == null) {
            // Set the current nodes left child to the new Node
            curNode.setLChild(new BSTNode(null, new Person(name, key), null));
            curNode = root;
            size++;
            return;
        } else if (curNode.getPerson().getKey() <= key
                && curNode.getRChild() == null) { // Check if the the key is
                                                  // greater than that of the
                                                  // current person and if it's
                                                  // right child is empty
            // Set the right child of the current node to the new Node
            curNode.setRChild(new BSTNode(null, new Person(name, key), null));
            curNode = root;
            size++;
            return;
        }

        // Check if the key is less than the key of the current Person
        if (key < curNode.getPerson().getKey()) {
            // The next stack frame will use the left child
            curNode = curNode.getLChild();
        } else {
            // The next stack frame will use the right child
            curNode = curNode.getRChild();
        }

        // Construct another stack frame
        this.insert(name, key);
    }

    /**
     * Find a person in the tree based on the key and the name
     * 
     * @param key
     *            The target key to find
     * @param name
     *            The target name to find
     * @return Is the person found in the tree
     */
    public boolean findPerson(int key, String name) {

        // Check if the arguments are legal
        if (name == "" || key < 1 || key > 200) {
            throw new IllegalArgumentException();
        }

        if (curNode == null) { // Base case 1, person not found
            curNode = root;
            return false;
        }

        // Base case 2, person is found
        if (curNode.getPerson().getKey() == key
                && curNode.getPerson().getName().equals(name)) {
            curNode = root;
            return true;
        }

        // Check if the key is less than the current key
        if (key < curNode.getPerson().getKey()) {
            // Next stack frame will use the left child as the current node
            curNode = curNode.getLChild();
        } else {
            // Next stack frame will use the right child as the current node
            curNode = curNode.getRChild();
        }

        // Build the next stack frame
        return findPerson(key, name);
    }

    /**
     * Produce an array of person contained in this tree
     * 
     * @param root
     *            The root of the tree
     * @return List of person in array form
     */
    public Person[] printToArray(BSTNode root) {

        if (root == null) { // Check if the tree is empty
            throw new NullPointerException();
        }

        // Initialize the temporary linked list
        personList = new LinkedList<>();
        this.printToArrayRecursive(root); // Call the recursive method
        Person[] p = new Person[personList.size()]; // Initialize the array
        // Copy all the data from the helper linked list to the array
        for (int i = 0; i < personList.size(); i++) {
            p[i] = personList.get(i);
        }

        return p;
    }

    /**
     * Recursive method that visit every Node of the tree
     * 
     * @param root
     *            The root of the tree
     */
    private void printToArrayRecursive(BSTNode root) {
        if (root != null) { // Check if the end of the branch is reached

            // In order implementation of the tree visiting method
            this.printToArrayRecursive(root.getLChild());
            personList.add(root.getPerson());
            this.printToArrayRecursive(root.getRChild());
        }
    }

    /**
     * Delete a Node based on the name and the key of the person
     * 
     * @param key
     *            The key of the person
     * @param name
     *            The name of the person
     * @return The person deleted
     */
    public Person delete(int key, String name) {

        // Check if the inputs are legal
        if (name == "" || key < 1 || key > 200) {
            throw new IllegalArgumentException();
        }

        return this.recursiveDelete(root, key);
    }

    /**
     * Delete a node by visiting the tree recursively
     * 
     * @param root
     *            The current node of the tree
     * @param key
     *            The target key to delete
     * @return The person deleted
     */
    private Person recursiveDelete(BSTNode root, int key) {

        // Check if the root and the left child are present and if the left
        // child have the matching key
        if (root != null && root.getLChild() != null
                && root.getLChild().getPerson().getKey() == key) {
            // If the key is found, save the information in temp variable
            Person temp = root.getLChild().getPerson();

            // If the left child of the left child is a leaf simply replace it
            // to null
            if (root.getLChild().getLChild() == null
                    && root.getLChild().getRChild() == null) {
                root.setLChild(null);

                // If the left child has one left child
            } else if (root.getLChild().getLChild() != null
                    && root.getLChild().getRChild() == null) {
                // Set the left child to it's left child
                root.setLChild(root.getLChild().getLChild());

                // If the left child has one right child
            } else if (root.getLChild().getLChild() == null
                    && root.getLChild().getRChild() != null) {
                // Set the left child to the right child
                root.setLChild(root.getLChild().getRChild());

            } else { // If the left child has two children

                // If the left child's right child has a child, it's then needed
                // to find the minimum of the right subtree
                if (root.getLChild().getRChild().getLChild() != null) {
                    BSTNode right = root.getLChild().getRChild();
                    BSTNode left = root.getLChild().getLChild();
                    // Let the deleted Node to the minimum of the right subtree
                    root.setLChild(this
                            .minimumRightSubtree(root.getLChild().getRChild()));
                    // Fixing the tree
                    root.getLChild().setRChild(right);
                    root.getLChild().setLChild(left);

                } else { // If the left child's right child has no child, simply
                         // set the removed node to the left child's right child
                    BSTNode left = root.getLChild().getLChild();
                    // Swapping the Node
                    root.setLChild(root.getLChild().getRChild());
                    // Fixing the tree
                    root.getLChild().setLChild(left);
                }
            }
            size--;

            return temp;

            // Check if the root and the right child are present and if the
            // right child have the matching key
        } else if (root != null && root.getRChild() != null
                && root.getRChild().getPerson().getKey() == key) {
            Person temp = root.getRChild().getPerson();

            // If the right child has no children
            if (root.getRChild().getLChild() == null
                    && root.getRChild().getRChild() == null) {
                root.setRChild(null); // Set the leaf to null

                // If the right child has a left child
            } else if (root.getRChild().getLChild() != null
                    && root.getRChild().getRChild() == null) {
                // Replace the right child with the left child
                root.setRChild(root.getRChild().getLChild());

                // If the right child has a right child
            } else if (root.getRChild().getLChild() == null
                    && root.getRChild().getRChild() != null) {
                // Replace the right child with a right child
                root.setRChild(root.getRChild().getRChild());

            } else { // If the right child has no children

                // If the right child's right child has a left child, there will
                // be a need to find minimum right subtree element
                if (root.getRChild().getRChild().getLChild() != null) {
                    BSTNode right = root.getRChild().getRChild();
                    BSTNode left = root.getRChild().getLChild();
                    // Set right child with the minimum element from the right
                    // subtree
                    root.setRChild(this
                            .minimumRightSubtree(root.getRChild().getRChild()));
                    // Fix the tree by adding the missing branches
                    root.getRChild().setRChild(right);
                    root.getRChild().setLChild(left);

                    // If the right child's right child has no left child
                } else {
                    // Set the deleted Node the right child's right child's left
                    // child
                    BSTNode left = root.getRChild().getLChild();
                    root.setRChild(root.getRChild().getRChild());
                    // Fixing the tree by adding the missing branches
                    root.getRChild().setLChild(left);
                }
            }
            size--;

            return temp; // Return the deleted node
        }

        if (root != null) { // Build the next stack frame if root is not null
            recursiveDelete(root.getLChild(), key);
            recursiveDelete(root.getRChild(), key);
        }

        return null; // Collapse the current stack frame. If at the bottom,
                     // return null to indicate the target node is not found
    }

    /**
     * Helper method to find the minimum right subtree
     * 
     * @param current
     *            The current node to find the minimum node of the right subtree
     * @return The minimum value of the right subtree
     */
    private BSTNode minimumRightSubtree(BSTNode current) {

        // Check if the left child has a left child
        if (current.getLChild().getLChild() == null) {
            // If it's null, the end is reached.
            // store the deleted minimum element
            BSTNode temp = current.getLChild();
            current.setLChild(null); // Remove the min elements
            return temp;
        }

        return minimumRightSubtree(current.getLChild());
    }

    /**
     * Find the depth of the tree
     * 
     * @param root
     *            The current node visiting
     * @return The depth of the tree
     */
    public int findDepth(BSTNode root) {

        if (root != null) { // Check if the end of the branch is reached
            level++;
            if (level > depth) { // Check if a longer branch is found
                depth = level;
            }

            // Recursively call the next level
            findDepth(root.getLChild());
            findDepth(root.getRChild());
            level--; // Removing levels as moving back up (collapsing stack
                     // frame)
        }
        return depth - 1; // Return the depth - the root level
    }

    /**
     * Count the number of leaves in the tree
     * 
     * @return The number of leaves in the tree
     */
    public int leafCount() {
        leafCount = 0;
        return recursiveLeafCounter(root);
    }

    /**
     * Recursively visit all the nodes of the tree and keep the count of leaves
     * 
     * @param root
     *            The current node
     * @return The total leaf count
     */
    private int recursiveLeafCounter(BSTNode root) {

        if (root != null) { // Check if the end of the branch is leaved
            // If the current node is a leaf
            if (root.getLChild() == null && root.getRChild() == null) {
                leafCount++;
            }
            // Build stack frames
            recursiveLeafCounter(root.getLChild());
            recursiveLeafCounter(root.getRChild());
        }

        return leafCount; // Return the leaf count
    }

    /**
     * Count the number of nodes in a certain level
     * 
     * @param level
     *            the level to count nodes
     * @return The number of nodes in a certain level
     */
    public int levelCount(int level) {

        levelCount = 0;
        this.level = 0;

        // Check if the level is not within the tree
        if (level > this.findDepth(root) || level < 0) {
            return -1;
        }

        return recursiveLevelCount(root, level);
    }

    /**
     * Recursive level counter method
     * 
     * @param root
     *            The current node
     * @param target
     *            The target level
     * @return The number of nodes in the level
     */
    public int recursiveLevelCount(BSTNode root, int target) {

        if (root != null) { // Check if the end of the branch is reached
            level++;

            if (level == target + 1) { // Check if the node is in the level
                levelCount++;
            }

            // Build more stack frames with the right or left children
            recursiveLevelCount(root.getLChild(), target);
            recursiveLevelCount(root.getRChild(), target);
            level--;
        }

        return levelCount;
    }

    /**
     * Accesor for the root of the tree
     * 
     * @return The root of the tree
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * Inner class for BSTNode
     * 
     * @author Huize Shi
     * @version Feb. 27, 2016
     */
    protected class BSTNode {

        private Person p; // data of the person stored in node
        private BSTNode lChild; // Left child of the current node
        private BSTNode rChild; // RIght child of the current node

        /**
         * The constructor for the node containing data and children
         * 
         * @param lChild // The left child of the node
         * @param p // Data to be stored in the node
         * @param rChild // THe right child of the node
         */
        public BSTNode(BSTNode lChild, Person p, BSTNode rChild) {
            
            // Storing all the data in the appropriate instance variables
            this.p = p;
            this.lChild = lChild;
            this.rChild = rChild;
        }

        /**
         * Accesor for the person
         * 
         * @return the person encapsulated in this node
         */
        public Person getPerson() {
            return p;
        }

        /**
         * Accesor for the left child
         * 
         * @return The left child of this node
         */
        public BSTNode getLChild() {
            return this.lChild;
        }

        /**
         * Accesor for the right child
         * 
         * @return The right child of this node
         */
        public BSTNode getRChild() {
            return this.rChild;
        }

        /**
         * Mutator for the left child
         * 
         * @param newLChild The new child to replace the left child
         */
        public void setLChild(BSTNode newLChild) {
            this.lChild = newLChild;
        }

        /**
         * Mutator for the right child
         * 
         * @param newRChild The new child to replace the right child
         */
        public void setRChild(BSTNode newRChild) {
            this.rChild = newRChild;
        }
    }

}
