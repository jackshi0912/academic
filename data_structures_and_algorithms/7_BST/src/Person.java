/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 26th, 2016
 * LOGIN: cs12wjz
 */
package hw7;

/**
 * Person object that encapsulate the name and the key number of a person
 * 
 * @author Huize Shi
 * @version Feb. 27, 2016
 */
public class Person {

    // Encapsulated information
    private String name;
    private int key;

    /**
     * Constructor that takes a name and a key number
     * 
     * @param name
     *            The name of the person
     * @param key
     *            The key number of the person
     */
    public Person(String name, int key) {
        // Assigning appropriate values to instance variables
        this.name = name;
        this.key = key;
    }

    /**
     * Mutator for the name of the person
     * 
     * @param name
     *            The new name for the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accesor for the name of the person
     * 
     * @return The name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Accesor for the key number of the person
     * 
     * @return The key of the person
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Override the toString method of the person object
     * 
     * @return return the String containing name and key number
     */
    @Override
    public String toString() {
        return this.getName() + ", " + this.getKey();
    }

    /**
     * Override the equals method of the Object superclass
     * 
     * @return Are the name and the key equal to that of the target
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { // Check if the object is null
            return false;
        }
        if (!(obj instanceof Person)) { // Check if the object is a Person
            return false;
        }
        return this.name.equals(((Person) obj).getName())
                && this.key == ((Person) obj).getKey();
    }

}
