/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

/**
 * The class that construct the dark room and escape it
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class Escape {
    /**
     * The main method of this class
     * 
     * @param args
     *            Command line input for the name of the dark room file
     */
    public static void main(String[] args) {
        DarkRoom dr = new DarkRoom(); // Initialize dark room
        //dr.readFromFile(args[0]); // Read the data from the file
        dr.readFromFile("room3.txt"); // Eclipse Diagnostic
        dr.escapeDarkRoom("Stack"); // Escape the room with a selected ADT
        dr.clear(); // Clear the visited locations in the room
        dr.escapeDarkRoom("Queue"); // Escape the room with a selected ADT
    }
}