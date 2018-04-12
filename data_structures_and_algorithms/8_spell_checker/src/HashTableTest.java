package hw8;

import static org.junit.Assert.*;

import org.junit.*;

public class HashTableTest 
{
	private IHashTable hashTable = new HashTable();
    
    @Before
    public void setUp() {
    	hashTable = new HashTable();
    }
    
	@Test
	public void testAdd() 
	{
		for(int i=0;i<50;i++)
			assertTrue(hashTable.insert(""+i));
		for(int i=0;i<50;i++)
			assertFalse(hashTable.insert(""+i));
	}
	@Test
	public void testLookup() 
	{
		for(int i=0;i<50;i++)
			assertFalse(hashTable.lookup(""+i));
		for(int i=0;i<50;i++)
			hashTable.insert(""+i);
		for(int i=0;i<50;i++)
			assertTrue(hashTable.lookup(""+i));
	}
	@Test
	public void testDelete() 
	{
		for(int i=0;i<50;i++)
			assertFalse(hashTable.delete(""+i));
		for(int i=0;i<50;i++)
			hashTable.insert(""+i);
		for(int i=0;i<50;i++)
			assertTrue(hashTable.delete(""+i));
	}
}
