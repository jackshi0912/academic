/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: March. 4th, 2016
 * LOGIN: cs12wjz
 */

package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Custom HashTable ADT that stores Strings by double hashing
 * 
 * @author Huize Shi
 * @version March. 4, 2016
 */
public class HashTable implements IHashTable {

    public static final int INITIAL_SIZE = 13; // Initial size of the array
    public static final int BASE = 27; // The base of the alphabet plus space
    // Constant to convert char ASCII char value to alphabet index
    public static final int ASCII_VALUE_CHAR = 96;
    // Constant to convert char ASCII digit value to alphabet index
    public static final int ASCII_VALUE_DIGIT = 48;
    public static final int HALF = 2;
    public static final double MAX_ALPHA = 2.0 / 3.0; // Max load factor
    public static final int KEY_WORD_LENGTH = 6; // The length of the action
    // The extra length to the data string after the action
    public static final int ELEMENT_START_INDEX = 2;

    private String[] storage; // Hash Table array
    private int size; // The size of the table
    private int r; // The number of resizing needed
    private int c; // The number of times collision happened
    private boolean collision = false; // Collision flag

    /**
     * Constructor for the Hash Table
     */
    public HashTable() {
        // Initialize all the appropriate variables
        size = 0;
        r = 0;
        c = 0;
        storage = new String[INITIAL_SIZE];
    }

    /**
     * Insert the value into the Hash Table
     * 
     * @param value
     *            The value to be inserted into the Hash Table
     * @return Is the insertion successful
     */
    @Override
    public boolean insert(String value) {
        // Check if the load factor is too large
        if ((double) this.size / (double) storage.length > MAX_ALPHA) {
            this.enlargeArray(); // Double the size of array
            r++; // Count the times enlarged
        }

        if (value == null) { // Check if the value to be inserted is null
            return true;
        }

        // Generate index for the second hashing function
        for (int i = 0; i < storage.length; i++) {

            // Check if an empty space is found
            if (storage[(this.hashFunction1(value) + i * this.hashFunction2())
                    % storage.length] == null) {

                storage[(this.hashFunction1(value) + i * this.hashFunction2())
                        % storage.length] = value; // Store the data into array
                size++;
                collision = false; // Reset collision
                return true;

                // Check if an deleted space is found
            } else if (storage[(this.hashFunction1(value)
                    + i * this.hashFunction2()) % storage.length]
                            .equals("ddeelleetteedd")) {

                storage[(this.hashFunction1(value) + i * this.hashFunction2())
                        % storage.length] = value; // Store the data into array
                size++;
                collision = false; // Reset collision
                return true;

                // Check if the value is found
            } else if (storage[(this.hashFunction1(value)
                    + i * this.hashFunction2()) % storage.length]
                            .equals(value)) {

                size++;
                collision = false; // Reset collision
                return false;

                // Check if a collision is present
            } else if (storage[(this.hashFunction1(value)
                    + i * this.hashFunction2()) % storage.length] != null
                    && !storage[(this.hashFunction1(value)
                            + i * this.hashFunction2()) % storage.length]
                                    .equals(value)
                    && !collision) {

                collision = true; // Set collision flag to true
                c++;
            }
        }

        collision = false;
        return false;
    }

    /**
     * This method search for the data and psudo delete it when found
     * 
     * @param value
     *            The value to be deleted from the Hash Table
     * @return Is the deleting successful
     */
    @Override
    public boolean delete(String value) {
        // Generate index values for the double hashing searching algorithm
        for (int i = 0; i < storage.length; i++) {
            // Check if the value exist
            if (storage[(this.hashFunction1(value) + i * this.hashFunction2())
                    % storage.length] == null) {
                return false;
            }

            // Check if the value match
            if (storage[(this.hashFunction1(value) + i * this.hashFunction2())
                    % storage.length].equals(value)) {

                // Set the value to lazy delete string
                storage[(this.hashFunction1(value) + i * this.hashFunction2())
                        % storage.length] = "ddeelleetteedd";
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Look up a value in the hash table
     * 
     * @param value
     *            The target value to find in the table
     * @return Is the value found in the Hash Table
     */
    @Override
    public boolean lookup(String value) {

        // Generate index for the double hashing function
        for (int i = 0; i < storage.length; i++) {
            // Check if the value is not there
            if (storage[(this.hashFunction1(value) + i * this.hashFunction2())
                    % storage.length] == null) {
                return false;
            }
            // Check if the value is found
            if (storage[(this.hashFunction1(value) + i * this.hashFunction2())
                    % storage.length].equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print the table based on established specs
     */
    @Override
    public void print() {
        for (int i = 0; i < storage.length; i++) {
            // Check if psudo-delete and empty space is present
            if (storage[i] == null || storage[i].equals("ddeelleetteedd")) {
                System.out.println(i + ":");
            } else { // Check if there are suggestions
                System.out.println(i + ": " + storage[i]);
            }
        }
    }

    /**
     * Accessor for the number of resizes needed
     * 
     * @return The number of resizes
     */
    public int getNumResizes() {
        return r;
    }

    /**
     * Accessor for the number of collisions
     * 
     * @return The collision counts
     */
    public int getCollisionCounts() {
        return c;
    }

    /**
     * Accessor for the load factor
     * 
     * @return The load factor of this Hash Table
     */
    public double getAlpha() {
        return (double) this.size / this.storage.length;
    }

    /**
     * First Hash Function that converts a string to a key index
     * 
     * @param key
     *            The input string
     * @return The index calculated from the key String
     */
    private int hashFunction1(String key) {
        int value = 0;
        // Loop through each character of the key
        for (int i = 0; i < key.length(); i++) {
            // Check if the character is a digit
            if (Character.isDigit(key.charAt(i))) {
                // Add it's calculated ascii value to the total value
                value += (value * BASE + (int) key.charAt(i)
                        - ASCII_VALUE_DIGIT) % storage.length;
            } else {
                // Add it's calculated ascii value to the total value
                value += (value * BASE + (int) key.charAt(i) - ASCII_VALUE_CHAR)
                        % storage.length;
            }
        }
        return value;
    }

    /**
     * The second function that generates a secondary index by raising 2 to the
     * power of the size of the array
     * 
     * @return The secondary index generated from the hash function
     */
    private int hashFunction2() {
        return (int) Math.pow(HALF, storage.length) % storage.length + 1;
    }

    /**
     * Enlarge the array to twice it's current size
     */
    private void enlargeArray() {
        String[] temp = this.storage; // Keep the original values
        storage = new String[temp.length * HALF]; // Double the hash table
        // Rehash everything
        for (int i = 0; i < temp.length; i++) {
            this.insert(temp[i]);
        }
    }

    /**
     * Main method of the Hash Table that performs a set of instructions
     * 
     * @param args
     *            Array of command line instructions
     */
    public static void main(String[] args) {
        // Declare and initialize appropriate local variables
        HashTable table = new HashTable();
        Scanner in = null;

        try {
            // Get the list of commands
            in = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Loop through the file to analyze each line
        while (in.hasNext()) {
            String line = in.nextLine();
            String value = "";
            // Check if the line has a instruction other than print
            if (line.length() > KEY_WORD_LENGTH) {
                // Record the argument for the instruction
                value = line.substring(KEY_WORD_LENGTH + ELEMENT_START_INDEX,
                        line.length() - 1);
            }
            if (line.equals("print")) { // Check if the instruction is print
                table.print(); // Print the table

                // Check if the instruction is insert
            } else if (line.substring(0, KEY_WORD_LENGTH).equals("insert")) {
                if (table.insert(value)) { // Check if inserting is successful
                    System.out.println(
                            "item " + value + " successfully inserted");
                } else {
                    System.out.println("item " + value + " already present");
                }

                // Check if the instruction is lookup
            } else if (line.substring(0, KEY_WORD_LENGTH).equals("lookup")) {
                if (table.lookup(value)) { // Check if the value is found
                    System.out.println("item " + value + " found");
                } else {
                    System.out.println("item " + value + " not found");
                }

                // Check if the instruction is delete
            } else if (line.substring(0, KEY_WORD_LENGTH).equals("delete")) {
                // Check if the deletion is successful
                if (table.delete(value)) {
                    System.out
                            .println("item " + value + " successfully deleted");
                } else {
                    System.out.println("item " + value + " not found");
                }
            }
        }

        in.close();
    }
}
