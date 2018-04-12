/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 10th, 2016
 * LOGIN: cs12wjz
 */
package hw5;
import java.util.Random;

/**
 * This is a object of a collection of sorting algorithms
 * 
 * @author JackShi
 * @version Feb. 3rd, 2016
 */
public class SortingAlgorithms {

    // The random number generator that is going to generate the pivot index for
    // quick sort
    private Random rand = new Random();
    private static int[] random;
    private static int[] reverse;
    private static int[] partSorted;
    private static int[] size = { 1000, 3000, 200000, 400000, 600000 };
    private static SortingAlgorithms alg = new SortingAlgorithms();
    private static int runningTime = 0;
    private static long beforeTime;
    private static final int ITERATION = 100;

    /**
     * The main method of the program, It handles all the testing and time
     * recording
     * 
     * @param args
     *            A string array for command line arguments
     */
    public static void main(String[] args) {
        // Insertion sort for random arrays
        for (int i = 0; i < size.length; i++) {
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                randomArrayGenerator(size[i]);
                random = alg.insertionSort(random);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Random Array insertionSort, with size "
                    + size[i] + ": " + runningTime / ITERATION);

            // Insertion sort for reversed arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                reverse = alg.reverseArrayGenerator(size[i], reverse);
                reverse = alg.insertionSort(reverse);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Reverse Array insertionSort, with size "
                    + size[i] + ": " + runningTime / ITERATION);

            // Insertion sort for partially sorted arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                partSorted = alg.partSortedArrayGenerator(size[i], partSorted);
                partSorted = alg.insertionSort(partSorted);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Partly sorted Array insertionSort, with size "
                    + size[i] + ": " + runningTime / ITERATION);

            // Merge sort for random arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                randomArrayGenerator(size[i]);
                random = alg.mergSort(random);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Random Array mergSort, with size " + size[i]
                    + ": " + runningTime / ITERATION);

            // Merge sort for reverse arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                reverse = alg.reverseArrayGenerator(size[i], reverse);
                reverse = alg.mergSort(reverse);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Reverse Array mergSort, with size " + size[i]
                    + ": " + runningTime / ITERATION);

            // Merge sort for partially sorted arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                partSorted = alg.partSortedArrayGenerator(size[i], partSorted);
                partSorted = alg.mergSort(partSorted);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Partly sorted Array mergSort, with size "
                    + size[i] + ": " + runningTime / ITERATION);

            // Quick sort for random arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                randomArrayGenerator(size[i]);
                alg.quickSortFromOnline(random, 0, random.length - 1);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Random Array quickSort, with size " + size[i]
                    + ": " + runningTime / ITERATION);

            // Quick sort for reversed arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                reverse = alg.reverseArrayGenerator(size[i], reverse);
                alg.quickSortFromOnline(reverse, 0, reverse.length - 1);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Reverse Array quickSort, with size " + size[i]
                    + ": " + runningTime / ITERATION);

            // Quick sort for partially sorted arrays
            for (int j = 0; j < ITERATION; j++) {
                beforeTime = System.nanoTime();
                partSorted = alg.partSortedArrayGenerator(size[i], partSorted);
                alg.quickSortFromOnline(partSorted, 0, partSorted.length - 1);
                runningTime += System.nanoTime() - beforeTime;
            }
            System.out.println("Partly sorted Array quickSort, with size "
                    + size[i] + ": " + runningTime / ITERATION);
        }
    }

    /**
     * Generate a array containing random numbers
     * 
     * @param size
     *            The size of the random array
     */
    private static void randomArrayGenerator(int size) {
        random = new int[size];
        Random rand = new Random();
        for (int i = 0; i < random.length; i++) {
            random[i] = rand.nextInt() % size;
        }
    }

    /**
     * Generate a array containing reversed number array
     * 
     * @param size
     *            The size of the reversed array
     */
    public int[] reverseArrayGenerator(int size, int[] a) {
        a = new int[size];
        int j = 0;
        for (int i = size - 1; i >= 0; i--) {
            a[j] = i;
            j++;
        }
        return a;
    }

    /**
     * Generate partially sorted array
     * 
     * @param size
     *            The size of the partially sorted array
     * @param a
     *            The array that contains all the data
     */
    public int[] partSortedArrayGenerator(int size, int[] a) {
        a = new int[size];
        // Create the sorted portion
        for (int i = 0; i < (int) (a.length * 0.2); i++) {
            a[i] = i;
        }
        // Create the unsorted portion
        for (int i = (int) (a.length * 0.2); i < a.length; i++) {
            a[i] = rand.nextInt() % size;
        }
        return a;
    }

    /**
     * The empty constructor
     */
    public SortingAlgorithms() {
    }

    /**
     * Sorting an integer array by using the insertion sort method
     * 
     * @param a
     *            The integer array to be sorted
     * @return Sorted array
     */
    public int[] insertionSort(int[] a) {
        // Loop through the array
        for (int i = 0; i < a.length; i++) {
            // Loop back through the sorted part
            for (int j = 0; j < i; j++) {
                // Check if insertion is needed
                if (a[i] < a[j]) {
                    // Insert the lesser value in front of the sorted larger
                    // value
                    a = arrayInserter(a, i, j);
                }
            }
        }
        return a;
    }

    /**
     * Helper method that take a value from the array and insert it into the
     * array at a given index
     * 
     * @param a
     *            The array to perform the operation on
     * @param originalIndex
     *            The index of the original element
     * @param finalIndex
     *            The index where the element should end up
     * @return The array with the element inserted into the desired spot
     */
    private int[] arrayInserter(int[] a, int originalIndex, int finalIndex) {
        // Create a new array to contain the values of an array that have the
        // values inserted
        int[] temp = new int[a.length];
        // Copy all the data before the index of insertion
        for (int i = 0; i < finalIndex; i++) {
            temp[i] = a[i];
        }
        temp[finalIndex] = a[originalIndex]; // Insert the data
        // Copy all the data before the index where the data was taken out
        for (int i = finalIndex + 1; i <= originalIndex; i++) {
            temp[i] = a[i - 1];
        }
        // Copy the rest of the array
        for (int i = originalIndex + 1; i < a.length; i++) {
            temp[i] = a[i];
        }
        return temp;
    }

    /**
     * This method sort the integer array using the merge sort technique
     * 
     * @param a
     *            The integer array to be sorted
     * @return Sorted integer array
     */
    public int[] mergSort(int[] a) {
        // Base case, if the array segment length is 1, return the array segment
        if (a.length == 1) {
            return a;
        }
        // Split the array in half
        int[] leftHalf = this.split(0, a.length / 2 - 1, a);
        int[] rightHalf = this.split(a.length / 2, a.length - 1, a);
        // Continuing to split by calling the method itself
        leftHalf = this.mergSort(leftHalf);
        rightHalf = this.mergSort(rightHalf);
        // Return the sorted, merged array
        return merge(leftHalf, rightHalf);
    }

    /**
     * Split the array in half
     * 
     * @param startIndex
     *            The starting index of the segmented array
     * @param endIndex
     *            The ending index of the segmented array
     * @param a
     *            The template array to create segments from
     * @return The array segments generated from copying data of a
     */
    private int[] split(int startIndex, int endIndex, int[] a) {
        // Create an array to house the data of the array segment
        int[] temp = new int[endIndex - startIndex + 1];
        int j = 0;
        // Copying the necessary data over
        for (int i = startIndex; i <= endIndex; i++) {
            temp[j] = a[i];
            j++;
        }
        return temp;
    }

    /**
     * Merge the arrays together into a single sorted array
     * 
     * @param a
     *            Array segment 1
     * @param b
     *            Array segment 2
     * @return Sorted merged array
     */
    private int[] merge(int[] a, int[] b) {
        // Initialize the new array to contain the merged product
        int[] c = new int[0];
        while (a.length > 0 && b.length > 0) {
            // Add the smaller value to the merged array then remove the old
            // value
            if (a[0] > b[0]) {
                c = this.addLast(c, b[0]);
                b = this.removeFirst(b);
            } else {
                c = this.addLast(c, a[0]);
                a = this.removeFirst(a);
            }
        }
        // Once one array is depleted of element, add the remaining element of
        // the other array to the merged array
        while (a.length > 0) {
            c = this.addLast(c, a[0]);
            a = this.removeFirst(a);
        }
        while (b.length > 0) {
            c = this.addLast(c, b[0]);
            b = this.removeFirst(b);
        }
        return c;
    }

    /**
     * Helper method that add the element to the end of the enlarged array
     * 
     * @param a
     *            The original array containing all the data
     * @param element
     *            The new element to be added to the end of the array
     * @return The enlarged array
     */
    private int[] addLast(int[] a, int element) {
        // Enlarge the array
        int[] temp = new int[a.length + 1];
        // Loop through the array to copy the old data
        for (int i = 0; i < a.length; i++) {
            temp[i] = a[i];
        }
        // Add the new data
        temp[a.length] = element;
        return temp;
    }

    /**
     * Helper method that remove the first element of the array and shrink the
     * array
     * 
     * @param a
     *            The original array that contain all the data
     * @return The array with the first element removed
     */
    private int[] removeFirst(int[] a) {
        int[] temp = new int[a.length - 1]; // Initialize the new array with one
                                            // size less
        // Copy the appropriate data
        for (int i = 0; i < temp.length; i++) {
            temp[i] = a[i + 1];
        }
        return temp;
    }

    /**
     * Sort the integer array by using quick sort
     * 
     * @param array
     *            The array to contain all the sorted data
     * @param lowerIndex
     *            The lower index of a segment of the array
     * @param higherIndex
     *            The higher index of a segment of the array
     */
    private void quickSortFromOnline(int[] array, int lowerIndex,
            int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        int pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a
             * number from right side which is less then the pivot value. Once
             * the search is done, then we exchange both numbers.
             */
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(array, i, j);
                // move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j) {
            quickSortFromOnline(array, lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSortFromOnline(array, i, higherIndex);
        }
    }

    /**
     * Swap the values at i and j
     * @param array The array to perform the swap on
     * @param i The index of the first value
     * @param j The index of the second value
     */
    private void exchangeNumbers(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Sort the integer array using the quick sort method
     * 
     * @param a
     *            The array to be sorted
     * @param start
     *            The starting index of the sort
     * @param end
     *            The ending index of the sort
     * @return The sorted array
     */
    public int[] quickSort(int[] a, int start, int end) {
        // Conditional to stop the recursion
        if (start < end) {
            // Perform one iteration of the quick sort operation
            int randomIndex = this.sortPivot(a, start, end);
            // Initiate the second iteration of the quick sort
            a = this.quickSort(a, start, randomIndex - 1);
            a = this.quickSort(a, randomIndex + 1, end);
        }
        return a;
    }

    /**
     * Helper method that perform one iteration of the quick sort
     * 
     * @param a
     *            The array to have the operation performed on
     * @param left
     *            The starting index
     * @param right
     *            The ending index
     * @return The resulting array after one iteration of the quick sort
     */
    private int sortPivot(int[] a, int left, int right) {
        rand = new Random(); // Initialize the random generator
        int randIndex = left + rand.nextInt(right - left + 1); // The pivot
                                                               // index
        int[] temp = new int[a.length];
        int i = 0;
        // Copying all that data that are less than the pivot entry
        for (int j = 0; j < a.length; j++) {
            if (a[j] < a[randIndex]) {
                temp[i] = a[j];
                i++;
            }
        }
        // Add the pivot entry
        temp[i] = a[randIndex];
        int pivotIndex = i;
        i++;
        // Add the rest of the data to the array
        for (int j = 0; j < a.length; j++) {
            if (a[j] >= a[randIndex] && j != randIndex) {
                temp[i] = a[j];
                i++;
            }
        }
        // Copying the resulting data to the original array
        for (int j = 0; j < temp.length; j++) {
            a[j] = temp[j];
        }
        return pivotIndex;
    }

}
