/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw4;

/**
 * Object Location that encapsulates the column and row values
 * 
 * @author Huize Shi
 * @version Feb. 3rd, 2016
 */
public class Location {

    // Declare row and column values
    protected int row;
    protected int column;

    /**
     * Constructor for Location
     * 
     * @param currRow
     *            The row value of the Location object
     * @param currCol
     *            The column value of the Location object
     */
    public Location(int currRow, int currCol) {
        // Initialize the values
        row = currRow;
        column = currCol;
    }

    /**
     * Accessor for the row value
     * 
     * @return The row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Accessor for the column value
     * 
     * @return The column value
     */
    public int getColumn() {
        return column;
    }

    /**
     * Return the location left of this one
     * 
     * @return The location left of this one
     */
    public Location left() {
        return new Location(row, column - 1);
    }

    /**
     * Return the location top of this one
     * 
     * @return The location top of this one
     */
    public Location up() {
        return new Location(row - 1, column);
    }

    /**
     * Return the location right of this one
     * 
     * @return The location right of this one
     */
    public Location right() {
        return new Location(row, column + 1);

    }

    /**
     * Return the location under this one
     * 
     * @return The location under this one
     */
    public Location down() {
        return new Location(row + 1, column);
    }
}
