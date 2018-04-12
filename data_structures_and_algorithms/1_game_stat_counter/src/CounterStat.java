/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */
package hw1;

/**
 * A class that implements a simple statistic tracking array
 * 
 * @version 1.5.2016
 * @author Huize Shi
 */
public class CounterStat {

	// private array of size 3
	private int[] Stat = new int[3];
	public static final int PERCENT = 100;

	/**
	 * creates an empty array
	 */
	public CounterStat() {
		// Nothing to do here
	}

	/**
	 * Creates an array with specified values
	 * 
	 * @param first:
	 *            assigned to the first cell
	 * @param second:
	 *            assigned to the second cell
	 * @param third:
	 *            assigned to the third cell
	 */
	public CounterStat(int first, int second, int third) {
		Stat[0] = first;
		Stat[1] = second;
		Stat[2] = third;
	}

	/**
	 * Calculates the number of games played
	 * 
	 * @return The total number of played games
	 */
	public int getTotalGames() {
		return Stat[0] + Stat[1] + Stat[2];
	}

	/**
	 * Increments the number of games won by a user
	 */
	public void incrementUserWins() {
		Stat[0]++;
	}

	/**
	 * Increments the number of games won by a computer
	 */
	public void incrementComputerWins() {
		Stat[1]++;
	}

	/**
	 * Increments the number of ties between a user and a computer
	 */
	public void incrementTies() {
		Stat[2]++;
	}

	/**
	 * 
	 * @param choice:
	 *            depending on the value of choice the corresponding average
	 *            (percent) is returned: if choice is 0, return the average for
	 *            a user if choice is 1, return the average for a computer if
	 *            choice is 2, return the average for ties otherwise return -1
	 * @return percentage of won games or ties, depending on the parameter
	 *         choice.
	 */
	public int averageGames(int choice) {
		if (choice >= Stat.length || choice < 0) {
			System.out.println("Choice was not between 0-2");
			return -1;
		}

		return Math.round(((float) (Stat[choice])) * 100 / ((float) this.getTotalGames()));
	}

	/**
	 * Prints the statistics message
	 */
	public void printStats() {
		System.out.println("I won: " + averageGames(0) + "%   You won: " + averageGames(1) + "%    We tied: "
				+ averageGames(2) + "%\n");
	}

	/**
	 * Resets the statistic array if the player wants to play again
	 */
	public void reset() {
		Stat[0] = 0;
		Stat[1] = 0;
		Stat[2] = 0;

	}

	/**
	 * Reset all the values to wrong values
	 */
	public void resetWrong() {
		for (int i = 0; i < Stat.length; i++) {
			Stat[i] = i;
		}
	}

}
