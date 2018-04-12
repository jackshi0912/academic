/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw1;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * A class that tests the correctness of CounterStat
 * @version 1.5.2016
 * @author Huize Shi
 */
public class CounterStatTester {

		private CounterStat stat;

		/* this sets up the test fixture. JUnit invokes this method before
	 	   every testXXX method.  The @Before tag tells JUnit to run this method 
		   before each test */
		@Before
		public void setUp() throws Exception {
			stat = new CounterStat(1,2,3);
		}
		
		/* The @Test tag tells JUnit this is a test to run */
		@Test
		public void testgetTotalGames() {
			System.out.println("Checking getTotalGames");
			assertEquals(6, stat.getTotalGames());
		}

		@Test
		public void testIncrements() {
			System.out.println("Checking Proper Increment");
			stat.incrementComputerWins();
			assertEquals(7, stat.getTotalGames());
			stat.incrementUserWins();
			assertEquals(8, stat.getTotalGames());
			stat.incrementTies();
			assertEquals(9, stat.getTotalGames()); 
		}
	

		@Test
		public void testReset() {
			System.out.println("Checking Reset");
			stat.reset();
			assertEquals(0, stat.getTotalGames());
		}

		/* TODO: write a test that verifies the proper calculation of average for all games  */
		
		@Test
		public void testAverageGames() {
			System.out.println( "Checking Proper Average Games" );
			assertEquals( 17, stat.averageGames(0) );
			assertEquals( 33, stat.averageGames(1) );
			assertEquals( 50, stat.averageGames(2) );
			assertEquals( -1, stat.averageGames(9000));
		}
		
		/* TODO: write a test that verifies the resetWrong method. This test must FAIL */

		@Test
		public void testWrongReset() {
			System.out.println("Checking Wrong Reset");
			stat.resetWrong();
			assertEquals(0, stat.getTotalGames());
		}
}
