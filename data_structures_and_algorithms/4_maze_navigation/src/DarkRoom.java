/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * This class makes a dark room for the algorithm to use queue and stack to find
 * the location of the door and record all of the steps and the amount of
 * instructions left in the collection.
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class DarkRoom implements DarkRoomInterface {

    // Declare various variables
    protected char[][] darkRoom;
    protected int numRows = 0;
    protected int numCols;
    private Stack_QueueInterface<Location> storage;

    /**
     * Read and decipher the dark room and store all its information into the 2
     * dimensional array
     * 
     * @param fname
     *            The name of the file
     */
    public void readFromFile(String fname) {

        String line; // A line in the text file
        BufferedReader inputStrem; // The reader of the file
        StringTokenizer st; // This allow strings to be broken into tokens

        try { // Try code with potential error
            int currentRow = 0; // Initialize the row count
            // Initializing the BufferReader
            inputStrem = new BufferedReader(new FileReader(fname));

            // Iterate through each line of code
            while ((line = inputStrem.readLine()) != null) {
                if (numRows == 0) { // Check if this is the first iteration
                    st = new StringTokenizer(line); // Store the first line to
                                                    // be analyzed
                    // Store row count
                    numRows = Integer.parseInt(st.nextToken());
                    // Store column count
                    numCols = Integer.parseInt(st.nextToken());
                    // Initialize the 2 dimensional array based on these values
                    darkRoom = new char[numRows][numCols];
                } else if (line.length() == 1) // Check for the end of the file
                    break;
                else {
                    // Iterate through each character of the line
                    for (int c = 0; c < numCols; c++) {
                        // Add the values into the 2d array
                        darkRoom[currentRow][c] = line.charAt(c);
                    }
                    // Increment count
                    currentRow++;
                }
            }
        } catch (IOException e) { // Catch the error
            // Print out the error code
            System.out.println(e.toString());
            // Print out the problem in English
            System.out.println("Could not find file " + fname);
        }

    }

    // Helper methods:

    /**
     * Find the location of 'S' within the 2d array
     * 
     * @return The location of the start character
     */
    public Location findStart() {

        // Nested for loop to go through the entire 2d array
        for (int i = 0; i < darkRoom.length; i++) {
            for (int j = 0; j < darkRoom[0].length; j++) {

                if (darkRoom[i][j] == 'S') { // Check if S is found
                    return new Location(i, j); // Return the location of S
                }

            }
        }
        // If noting found, return null
        return null;
    }

    /**
     * Check if the door is adjacent to the current location
     * 
     * @param loc
     *            The current location
     */
    public boolean isDoor(Location loc) {
        // Return true if any of the adjacent block contain 'D'
        return darkRoom[loc.getRow()][loc.getColumn() - 1] == 'D'
                || darkRoom[loc.getRow() - 1][loc.getColumn()] == 'D'
                || darkRoom[loc.getRow()][loc.getColumn() + 1] == 'D'
                || darkRoom[loc.getRow() + 1][loc.getColumn()] == 'D';
    }

    /**
     * Check if the cursor can move
     * 
     * @param loc
     *            The current location
     * @return Can the cursor move
     */
    public boolean canMove(Location loc) {
        // Return true if there are empty spaces around the cursor
        return darkRoom[loc.getRow()][loc.getColumn() - 1] == ' '
                || darkRoom[loc.getRow() - 1][loc.getColumn()] == ' '
                || darkRoom[loc.getRow()][loc.getColumn() + 1] == ' '
                || darkRoom[loc.getRow() + 1][loc.getColumn()] == ' ';
    }

    /**
     * Mark the location with a '.'
     * 
     * @param loc
     *            The location to be marked
     */
    public void markVisited(Location loc) {
        darkRoom[loc.getRow()][loc.getColumn()] = '.';
    }

    /**
     * Count the number of locations visited in total
     * 
     * @return The count of visited Locations
     */
    public int countVisited() {
        int visited = 0; // Initialize count to 0
        // Nested for loop to go through the entire array
        for (int i = 0; i < darkRoom.length; i++) {
            for (int j = 0; j < darkRoom[0].length; j++) {
                if (darkRoom[i][j] == '.') { // Find the visited spots
                    visited++; // Increment the visited count
                }
            }
        }
        return visited; // Return the final count
    }

    /**
     * Remove all occurrences of '.'
     */
    public void clear() {
        // Nested for loop to go through the entire array
        for (int i = 0; i < darkRoom.length; i++) {
            for (int j = 0; j < darkRoom[0].length; j++) {
                if (darkRoom[i][j] == '.') { // Check if the location is visited
                    darkRoom[i][j] = ' '; // Replace the '.' with ' '
                }
            }
        }
    }

    /**
     * Print the room in console
     */
    public void printRoom() {
        // Nested for loop to go through the entire array
        for (int i = 0; i < darkRoom.length; i++) {
            for (int j = 0; j < darkRoom[0].length; j++) {
                System.out.print(darkRoom[i][j]); // Print out the element in
                                                  // that line
            }
            System.out.print("\n"); // switch to another line
        }
    }

    /**
     * Search for the door by using stack and queue
     * 
     * @param choice
     *            Check if the user want to use stack or queue
     */
    public void escapeDarkRoom(String choice) {

        int step = 0; // reset the count to 0
        if (choice.equals("Stack")) { // Check for the user's choice
            storage = new MyStack<Location>(); // Initialize the storage with
                                               // appropriate object
        } else {
            // Initialize the storage with
            storage = new MyQueue<Location>();
        }
        Location curLoc = this.findStart(); // Record the location of the start
        while (!this.isDoor(curLoc)) { // Check for door every revolution
            // Mark the visited spot if it's empty
            if (darkRoom[curLoc.getRow()][curLoc.getColumn()] == ' ') {
                this.markVisited(curLoc);
                step++;
            }
            // Store the valid locations in storage
            this.storeValidLocations(curLoc);
            // remove a location from the storage
            curLoc = storage.removeElement();
        }
        // Mark the current location as visited after door is found
        this.markVisited(curLoc);
        step++;
        // Print the appropriate contents
        this.printGoal(choice, step, storage.size());
        this.printRoom();
    }

    /**
     * Store the location around the cursor that are unvisited
     * 
     * @param curLoc
     *            The current location
     */
    private void storeValidLocations(Location curLoc) {
        // Check if LEFT, UP, RIGHT and DOWN are empty and store the location
        if (darkRoom[curLoc.getRow()][curLoc.getColumn() - 1] == ' ') {
            storage.addElement(
                    new Location(curLoc.getRow(), curLoc.getColumn() - 1));
        }
        if (darkRoom[curLoc.getRow() - 1][curLoc.getColumn()] == ' ') {
            storage.addElement(
                    new Location(curLoc.getRow() - 1, curLoc.getColumn()));
        }
        if (darkRoom[curLoc.getRow()][curLoc.getColumn() + 1] == ' ') {
            storage.addElement(
                    new Location(curLoc.getRow(), curLoc.getColumn() + 1));
        }
        if (darkRoom[curLoc.getRow() + 1][curLoc.getColumn()] == ' ') {
            storage.addElement(
                    new Location(curLoc.getRow() + 1, curLoc.getColumn()));
        }
    }

    /**
     * Print the accumulative data collected from the escaping process
     * 
     * @param choice
     *            The ADT choice of the storage
     * @param stepsTaken
     *            The steps taken to complete the search for the door
     * @param positionsLeft
     */
    public void printGoal(String choice, int stepsTaken, int positionsLeft) {
        // Print the number of step taken and the number of instructions left
        // in the ADT
        System.out.println("Goal found (with " + choice + "): It took "
                + stepsTaken + " explored positions");
        System.out.println("There is (are) " + positionsLeft
                + " position(s) left to explore in " + choice);

    }
}
