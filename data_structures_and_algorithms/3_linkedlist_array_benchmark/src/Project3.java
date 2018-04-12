/*
 * Name: Huize Shi
 * Date: Jan. 24
 * PID: A92122910
 * CSE12 Project3.java
 */

import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class creates either an array or a Linked List and add elements to the 
 * front until the number matches the user's input. It then outputs the 
 * time it takes for each operation to complete.
 */
public class Project3 {

    // Declare all the variables and initialize scanner
    private static int[] arr;
    private static int[] newarr;
    private static LinkedList<Integer> list;
    private static long beforeTime;
    private static long afterTime;
    private static Scanner in = new Scanner(System.in);
    private static char choice;
    private static int size;
    public static final int ITERATION=10;

    /**
     * The main method of this program, it'll will get the user's input and 
     * execute the appropriate method.
     *
     * @param args A array of string
     */
    public static void main( String[] args ) {

        // Keep the program running until the user wants to quit
        while (choice != 'q' || choice != 'Q') {
            // Ask User for input and initialize values accoridngly
            System.out.print("Size of the array or list: ");
            size = in.nextInt();
            System.out.print("(L)inkedList or (A)rray: ");
            choice = in.next().charAt(0);
            list = new LinkedList<Integer>();
            arr = new int[0];

            // Analize the choice and execute commands accoridngly
            if (choice == 'L' || choice == 'l') {
                //Running the test 10 times and output the duration
                for ( int i = 0; i < ITERATION; i++ ) {
                    System.out.println( "The running time for LinkedList of size " 
                            + size + " is: " + addManyFrontList( list, size ) );
                } 
            }
            else if (choice == 'A' || choice == 'a') {
                //Running the test 10 times and output the duration
                for ( int i = 0; i < ITERATION; i++ ) {
                    System.out.println( "The running time for Array of size " 
                            + size + " is: " + addManyFrontArray( arr, size ) ); 
                }
            } else {
                // Handles bad input
                System.out.println("Illegal input.");
            }

            // Ask if user wish to quit
            System.out.println("(Q)uit? (Any other character to continue)");
            choice = in.next().charAt(0);
            if ( choice == 'q' || choice == 'Q' ) {
                break;
            }
        }

    }

    /**
     * "Add" element to the front of the array by creating a one size larger 
     * array, add the element to the new array, then copy all the old values
     * over and reassign the references.
     *
     * @param arr The array to be tested
     * @param size The number of increment to be made to the array
     * @return The time took for the operation to complete
     */
    private static long addManyFrontArray(int[] arr, int size) {
        // Record time before execution
        beforeTime = System.currentTimeMillis();
        // "Adding" element to the front of the array
        for( int i = 0; i < size; i++ ) {
            newarr = new int[ arr.length + 1 ];
            newarr[0] = i;
            // Copying all the old data
            for ( int j = 1; j < arr.length+1; j++ ) {
                newarr[i] = arr[i-1];
            }
            // Re-assign references
            arr = newarr;
        }
        // Record final time
        afterTime = System.currentTimeMillis();
        return afterTime-beforeTime;
    }


    /**
     * "Add" element to the front of the Linked List. This is handeled by 
     * changing the refernce of the head and the new node containing the data.
     *
     * @param list The LinkedList to be tested
     * @param size The number of increment to be made to the LinkedList
     * @return The time took for the operation to complete
     */
    @SuppressWarnings("unchecked")
        private static long addManyFrontList( LinkedList list, int size ) {

            // Record time before execution
            beforeTime = System.currentTimeMillis();
            // Adding element to the front of the LinkedList
            for (int i = 0; i < size; i++) {
                list.add(new Integer(i));
            }
            // Record final time
            afterTime = System.currentTimeMillis();
            return afterTime-beforeTime;
        }
}
