/*
 * NAME: Huize Shi
 * ID: A92122910
 * LOGIN: cs12wjz
 */

package hw1;

import java.util.ArrayList;

/**  
 * @author Your Name
 * @date The date
 *
 */

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

/**
 * The class simulates the card game of war. The use and computer both get A
 * card for each round. The higher number wins.
 * 
 * @author Jack Shi
 */
public class SimpleWar {
	
	public static final int INPUT_OFFSET = 2;
	public static final int KIND_NUMBER = 13;
	public static final int SUIT_NUMBER = 4;
	public static final int PERCENTAGE = 100;
	private static int[] NumberOfUsedKinds = new int[13]; // Use it to count the
															// number of used
	private static Scanner input = new Scanner(System.in); // Scanner object
															// that takes an
	private static ArrayList<String> cardsUsed = new ArrayList<String>();
	// input from a user
	private static String[] Suits = { "Hearts", "Diamonds", "Spades", "Clubs" }; // 0,1,2,3
	private static String[] Kinds = { "Two", "Three", "Four", "Five", "Six", // 0,
																				// 1,
																				// 2,
																				// 3,
																				// 4
			"Seven", "Eight", "Nine", "Ten", "Jack", // 5,6,7,8,9
			"Queen", "King", "Ace" }; // 10, 11, 12
	// stores computer moves
	private static LinkedList<String> computerMoves = new LinkedList<String>();
	// stores users moves
	private static LinkedList<String> userMoves = new LinkedList<String>();
	private static int computerKindIndex, kindIndex; // suit and kind chosen
														// randomly by
	// a computer
	private static Random rand = new Random();
	private static int userWin, computerWin, ties;

	/**
	 * The main method of this program
	 * 
	 * @param args
	 *            An array of strings
	 */
	public static void main(String[] args) {

		// Initializing every element of the array
		for (int i = 0; i < NumberOfUsedKinds.length; i++) {
			NumberOfUsedKinds[i] = 0;
		}

		// Infinite loop that keeps the game running until the user decides the
		// quit
		while (true) {
			System.out.println("My card is: " + generateComputerCard());
			prompForCard();
			// Ending a game and decide whether to continue looping after -1 is
			// entered
			if (kindIndex == -1) {
				if (endGame()) {
					continue;
				} else {
					break;
				}
			}
			// Print out the user's cards
			System.out.println(generateUserCard(kindIndex));
			checkWin(); // Check for any winners
		}
	}

	/**
	 * Check for any potential winners
	 */
	private static void checkWin() {
		// TODO Auto-generated method stub
		// Check for winners and record the result
		if (kindIndex > computerKindIndex) {
			System.out.println("You won\n");
			userWin++;
		} else if (kindIndex < computerKindIndex) {
			System.out.println("I won\n");
			computerWin++;
		} else {
			System.out.println("A tie\n");
			ties++;
		}
	}

	/**
	 * Generate the card that the computer plays
	 * 
	 * @return The card that the computer plays
	 */
	private static String generateComputerCard() {
		// Keep looping until a acceptable card is generated
		while (true) {
			// Generating cards
			computerKindIndex = rand.nextInt(Kinds.length) + 2;
			String computerCard = generateCard(computerKindIndex);
			// Check if the card is acceptable to use
			if (cardsUsed.contains(computerCard)) {
				continue;
			}
			// Record the information about this card
			cardsUsed.add(computerCard);
			computerMoves.add(computerCard);
			NumberOfUsedKinds[computerKindIndex - INPUT_OFFSET]++;
			return computerCard;
		}
	}

	/**
	 * Generate a card for the user
	 * 
	 * @param kind
	 *            The kind of card selected by the user
	 * @return The user's card with a suit
	 */
	private static String generateUserCard(int kind) {
		kindIndex = kind;
		// Loops until a acceptable card is made
		// Or when the user have changed it's selection to a acceptable one
		while (true) {
			String playerCard = generateCard(kindIndex);
			if (NumberOfUsedKinds[kindIndex - INPUT_OFFSET] >= Suits.length) {
				prompForReplacement();
				continue;
			}
			// Generate the acceptable suit for the card
			while (cardsUsed.contains(playerCard)) {
				playerCard = generateCard(kindIndex);
			}
			// Record the information for the card
			userMoves.add(playerCard);
			cardsUsed.add(playerCard);
			NumberOfUsedKinds[kindIndex - INPUT_OFFSET]++;
			return playerCard;
		}
	}

	/**
	 * Prompt the user for a replacement card
	 */
	private static void prompForReplacement() {
		// TODO Auto-generated method stub
		System.out.print("All cards of this this type have been played. Pick another one: ");
		kindIndex = input.nextInt();
	}

	/**
	 * Generate a card with a suit
	 * 
	 * @param kind
	 *            The index of the kind of card
	 * @return The final card with a suit generated
	 */
	private static String generateCard(int kind) {
		return Kinds[kind - INPUT_OFFSET] + " of " + Suits[rand.nextInt(Suits.length)];
	}

	/**
	 * Prompt the user for a card
	 */
	private static void prompForCard() {
		// TODO Auto-generated method stub
		System.out.print("What is your card (kind)? (2-14, -1 to finish the game): ");
		kindIndex = input.nextInt();
	}

	/**
	 * At the end of the game, consider the user's input and decide whether to
	 * run again
	 * 
	 * @return
	 */
	private static boolean endGame() {
		// TODO Auto-generated method stub
		System.out.println("I won: " + calcAverage(computerWin) + "%  You won: " + calcAverage(userWin) + "%  We tied: "
				+ calcAverage(ties) + "%");
		// Print out the history of the match
		for (int i = 0; i < userMoves.size() - 1; i++) {
			System.out.println("My Moves: " + userMoves.get(i) + " Your Moves: " + userMoves.get(i));
		}
		// Ask whether to play again
		System.out.print("Play again(y/n)?");
		char userChoice = input.next().charAt(0);
		// Process that request and return the result
		if (userChoice == 'y' || userChoice == 'Y') {
			reset();
			return true;
		} else if (userChoice == 'n' || userChoice == 'N') {
			System.out.println("Bye, see you later!");
			return false;
		} else {
			System.out.println("Wrong input, see you later!");
			return false;
		}
	}

	/**
	 * Calculate the average of the win amount passed in as the parameter
	 * @param n The win amount
	 * @return The percentage of win
	 */
	private static int calcAverage(int n) {
		return (int) Math.round((float) n * PERCENTAGE / (float) (userWin + computerWin + ties));
	}

	/**
	 * Reset the game
	 */
	private static void reset() {
		// Reset all variables
		userWin = 0;
		computerWin = 0;
		ties = 0;
		for (int i = 0; i < NumberOfUsedKinds.length; i++) {
			NumberOfUsedKinds[i] = 0;
		}
	}
}
