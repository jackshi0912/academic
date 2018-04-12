/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: March. 4th, 2016
 * LOGIN: cs12wjz
 */

package hw8;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Spell checker class that lookup words in a dictionary then give suggestions
 * 
 * @author Huize Shi
 * @version March. 4, 2016
 */
public class SpellChecker implements ISpellChecker {

    public static int ALPHABET = 26;
    private HashTable dictionary;

    // Constructor for the spell checker
    public SpellChecker() {
        dictionary = new HashTable();
    }

    /**
     * Read a dictionary and store all the entries to the Hash Table
     * 
     * @param reader
     *            The reader containing the dictionary
     */
    @Override
    public void readDictionary(Reader reader) {
        BufferedReader in;
        in = new BufferedReader(reader);
        String line;
        try {
            // Get the dictionary and insert each entry into the hash table
            while ((line = in.readLine()) != null) {
                dictionary.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a dictionary and store all the entries to the Hash Table using
     * Scanner
     * 
     * @param filePath
     *            Path of the target dictionary file
     * 
     */
    public void readDictionary(String filePath) {
        Scanner in = null;
        try {
            // Read the dictionary
            in = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNextLine()) { // Store all the entries to the dictionary
            dictionary.insert(in.nextLine());
        }
    }

    /**
     * Write the runtime data to stats.txt
     */
    public void writeRunTimeDataToFile() {
        PrintWriter writer = null;
        try {
            // Initialize the writer and create stats.txt
            writer = new PrintWriter(
                    new FileWriter(new File("stats.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.print("");
        writer.close();
        
        try {
            // Initialize the writer and create stats.txt
            writer = new PrintWriter(
                    new FileWriter(new File("stats.txt"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        writer.print(dictionary.getNumResizes() + " resizes, load factor "
                + dictionary.getAlpha() + ", " + dictionary.getCollisionCounts()
                + " collisions");

        writer.close();
    }

    /**
     * Check if the word has any suggestions
     * 
     * @return An array of suggestions
     */
    @Override
    public String[] checkWord(String word) {
        if (word == null) { // Check if the word is null
            return null;
        }
        word = word.toLowerCase(); // Change word to lower case
        if (dictionary.lookup(word)) { // No suggestions if it's in dictionary
            return null;
        }
        String[] alternative = { "" };
        LinkedList<String> temp = new LinkedList<>();

        // Loop through each letter of the word
        for (int i = 0; i < word.length(); i++) {
            // Check for each alphabet alternative
            for (int j = 0; j < ALPHABET; j++) {
                if (i == 0) { // Edge case: First letter alternative
                    if (dictionary.lookup( // Check if the new word is found
                            this.alphabetChart(i) + word.substring(1))) {
                        // Add the new word into the table
                        temp.add(this.alphabetChart(j) + word.substring(1));
                    }
                } else if (i == word.length() - 1) { // Edge case: last letter
                    if (dictionary.lookup(word.substring(0, word.length() - 1)
                            + this.alphabetChart(j))) { // Check if the new word
                                                        // is found
                        // Add the new word into the table
                        temp.add(word.substring(0, word.length() - 1)
                                + this.alphabetChart(j));
                    }
                    // Check if a alternative letter is found in the middle
                } else {
                    // Check if the new word is found
                    if (dictionary.lookup(word.substring(0, i)
                            + this.alphabetChart(j) + word.substring(i + 1))) {
                        // Add the new word into the table
                        temp.add(word.substring(0, i) + this.alphabetChart(j)
                                + word.substring(i + 1));
                    }
                }
            }
        }

        alternative = new String[temp.size()];
        for (int i = 0; i < alternative.length; i++) {
            alternative[i] = temp.remove(); // Copy all the data to the
                                            // returning String array
        }
        return alternative;
    }

    /**
     * Return a letter in the alphabet based on the argument index
     * 
     * @param index
     *            The index of a letter in the alphabet
     * @return The letter at the index
     */
    private char alphabetChart(int index) {
        return (char) (index + 1 + HashTable.ASCII_VALUE_CHAR);
    }

    /**
     * The main method of the program
     * 
     * @param args
     *            Command line inputs providing path to text files
     */
    public static void main(String[] args) // This is used for Part 2
    {
        Scanner in = null;
        try {
            // Read the list of words to lookup
            in = new Scanner(new File(args[1]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Initialize a list of misspelled words
        LinkedList<String> wrongWords = new LinkedList<>();
        SpellChecker spellChecker = new SpellChecker();
        // Read the dictionary
        spellChecker.readDictionary(args[0]);
        String line;

        while (in.hasNextLine()) { // Loop through the dictionary and enter
                                   // every entry into the dictionary
            line = in.nextLine();
            // Check if word is misspelled
            if (spellChecker.checkWord(line) != null) {
                // Get list of suggestions
                String[] temp = spellChecker.checkWord(line);
                // Format the output to the specification
                String tempString = line + ":";
                for (int i = 0; i < temp.length; i++) {
                    if (i == 0) {
                        tempString += " " + temp[0];
                    } else {
                        tempString += ", " + temp[i];
                    }
                }
                // Add the output to the list
                wrongWords.add(tempString);
            }
        }

        spellChecker.writeRunTimeDataToFile(); // Write stats.txt

        // Transfer data from linked list to array
        while (wrongWords.size() > 0) {
            System.out.println(wrongWords.removeFirst());
        }
        in.close();
    }

}
