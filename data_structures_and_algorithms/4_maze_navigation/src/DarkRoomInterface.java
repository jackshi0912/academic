/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

/**
 * Interface for the DarkRoom object
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public interface DarkRoomInterface {

    /**
     * Read the dark room file
     * @param filename name of the file
     */
    public void readFromFile(String filename);

    /**
     * Find the location of 'S' within the 2d array
     * 
     * @return The location of the start character
     */
    public Location findStart();

    /**
     * Check if the door is adjacent to the current location
     * 
     * @param loc
     *            The current location
     */
    public boolean isDoor(Location loc);

    /**
     * Check if the cursor can move
     * 
     * @param loc
     *            The current location
     * @return Can the cursor move
     */
    public boolean canMove(Location loc);

    /**
     * Mark the location with a '.'
     * 
     * @param loc
     *            The location to be marked
     */
    public void markVisited(Location loc);

    /**
     * Remove all occurrences of '.'
     */
    public void clear();

    /**
     * Search for the door by using stack and queue
     * 
     * @param choice
     *            Check if the user want to use stack or queue
     */
    public void escapeDarkRoom(String choice);

    /**
     * Count the number of locations visited in total
     * 
     * @return The count of visited Locations
     */
    public int countVisited();

    /**
     * Print the room in console
     */
    public void printRoom();

}
