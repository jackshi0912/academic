package hw8;


public interface IHashTable {

	/** Insert the string value into the hash table
	 * 
	 * @param value value to insert
	 * @return true if the value was inserted, false if the value was already present
	 */
	boolean insert(String value);
	/** Delete the given value from the hash table
	 * 
	 * @param value value to delete
	 * @return true if the value was deleted, false if the value was not found
	 */
	boolean delete(String value);
	/** Check if the given value is present in the hash table
	 * 
	 * @param value value to look up
	 * @return true if the value was found, false if the value was not found
	 */
	boolean lookup(String value);
	
	/** Print the contents of the hash table
	 * 
	 *
	 * 
	 * Example output for this function:
	 * 
	 * 0: fify
	 * 1:
	 * 2: marina
	 * 3: table
	 * 4:
	 * 
	 * 
	 */
	void print();
}
