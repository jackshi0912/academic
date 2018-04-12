/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 19th, 2016
 * LOGIN: cs12wjz
 */
package hw6;

/**
 * This class is a heap capable of having various dimensions
 * 
 * @author Jack Shi
 * @version Feb.19, 2016
 * @param <T>
 *            The input must extends comparable which is capable of comparing
 *            with it's own super class.
 */
class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {

    private T[] storage; // The array in which the heap is going to be stored
    private int dimension; // The dimension of the heap structure
    private int heapSize = 0; // The size of the heap
    private final int TWICE = 2;
    private int minIndex; // The index of the minimum child

    /**
     * The constructor takes one argument: an initial capacity
     * 
     * @param heapSize
     *            The initial capacity of the heap
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {

        dimension = TWICE; // Set the default dimension of the heap to binary
        storage = (T[]) new Comparable[heapSize]; // Initialize the heap
    }

    /**
     * The constructor takes two arguments: an initial capacity and the
     * dimension of the heap
     * 
     * @param d
     *            The dimension of the heap
     * @param heapSize
     *            The size of the heap
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize) {
        // Handle the exception throwing
        if (d < 1) {
            throw new IllegalArgumentException();
        }
        // Initialize the appropriate values
        dimension = d;
        storage = (T[]) new Comparable[heapSize];
    }

    /**
     * Getter for the size of the heap
     * 
     * @return The size of the heap
     */
    public int size() {
        return heapSize;
    }

    /**
     * Add a element to the heap
     * 
     * @param The
     *            new data to be added to the heap
     */
    public void add(T data) {
        // If the size is 0, simply set the root to the new element and
        // increment size by 1
        if (heapSize == 0) {
            storage[0] = data;
            heapSize++;
            return;
        }

        // If the array is not big enough to have a new member added, enlarge it
        // to twice it's current size
        if (heapSize + 1 >= storage.length) {
            this.enlargeArray();
        }

        // Put the data to the end of the array
        int dataLoc = heapSize;
        storage[dataLoc] = data;

        // Bubble the data upstream until it's smaller than it's children
        while (storage[dataLoc]
                .compareTo(storage[(dataLoc - 1) / dimension]) < 0) {
            T temp = storage[(dataLoc - 1) / dimension]; // Temp is now the
                                                         // parent
            // Swap the position with the data's parent
            storage[(dataLoc - 1) / dimension] = storage[dataLoc];
            storage[dataLoc] = temp;
            dataLoc = (dataLoc - 1) / dimension; // The data location is now the
                                                 // parent's location
        }
        heapSize++;
    }

    /**
     * Remove the root of the heap and fix the heap structure
     * 
     * @return The root of the heap
     */
    public T removeSmallest() {
        // Check if remove is possible
        if (heapSize == 0) {
            return null;
        }

        T first = storage[0]; // Pull off the root element
        storage[0] = storage[heapSize - 1]; // Set the root to the last element
                                            // of the heap
        storage[heapSize - 1] = null; // Set the last element to null
        heapSize--;
        int dataLoc = 0; // The location of the new root
        // Loop until the data is trickled down to where it's children are all
        // larger
        while ((this.smallestChild(dataLoc) != null) && (storage[dataLoc]
                .compareTo(this.smallestChild(dataLoc)) > 0)) {
            T temp = this.smallestChild(dataLoc); // Find the smallest child
                                                  // under the misplaced root
            // Swap the positions of the root and the smallest child
            storage[minIndex] = storage[dataLoc];
            storage[dataLoc] = temp;
            dataLoc = minIndex; // Set the location of the misplaced root t othe
                                // minimum Index
        }
        return first;
    }

    /**
     * Return the root element without removing it from the heap
     * 
     * @return The root element
     */
    public T peek() {
        T temp = this.removeSmallest(); // Store the root element in temp
        this.add(temp); // Add the temp back into the queue
        return temp; // return the root element
    }

    /**
     * Find the smallest child of the element at a position of the heap
     * 
     * @param dataLoc
     *            The location of the data for finding the minimum child
     * @return The smallest child
     */
    private T smallestChild(int dataLoc) {
        // Check for potential index out of bound
        if (dimension * dataLoc + 1 >= storage.length) {
            return null;
        }
        // Check if there is no child
        if (storage[dimension * dataLoc + 1] == null) {
            return null;
        }

        minIndex = dimension * dataLoc + 1; // Set the index of the minimum
                                            // child to the first child
        T minVal = storage[dimension * dataLoc + 1]; // Set the minimum value to
                                                     // the first child
        // Loop through the child of the data
        for (int i = dimension * dataLoc + 1; i <= dimension * dataLoc
                + dimension; i++) {
            // Check if there is no more child to run through
            if (storage[i] == null) {
                break;
            }
            // Check if the current value is smaller than the minVal
            if (storage[i].compareTo(minVal) < 0) {
                // Change the min value and the index to the new value
                minVal = storage[i]; // Setting the new min value
                minIndex = i; // Setting the new min index
            }
        }
        return minVal;
    }

    /**
     * Enlarge the array to twice it's current size
     */
    private void enlargeArray() {
        @SuppressWarnings("unchecked")
        // Initialize the new array to twice the size of the current one
        T[] newStoage = (T[]) new Comparable[storage.length * TWICE];
        // Loop through the array to copy all the information to the new array
        for (int i = 0; i < storage.length; i++) {
            newStoage[i] = storage[i];
        }
        storage = newStoage; // Swap the references
    }

}
