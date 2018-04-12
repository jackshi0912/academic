/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 19th, 2016
 * LOGIN: cs12wjz
 */
package hw6;

import java.io.*;
import java.util.Scanner;

/**
 * This class runs the program and sort the schedule
 * 
 * @author Jack Shi
 * @version Feb. 19, 2016
 */
public class EDF {

    // Initiating appropriate variables and constants
    private static Scanner in;
    public static final int START_OF_PROCESS = 9;
    public static final int START_OF_TIME = 4;
    public static final int INITAL_SIZE = 10;

    /**
     * The main method of this program
     * 
     * @param args
     *            Command line argument, String array
     */
    public static void main(String[] args) {
        // Check if the command line argument is correct
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments: " + args.length);
            System.err.println("java hw6.EDF <input-file>");
            System.exit(1);
        }
        // Set the file to the path of the file
        File file = new File(args[0]);
        MyPriorityQueue<Record> queue = new MyPriorityQueue<Record>(
                INITAL_SIZE); // Initialize the priority queue
        long currentTime = 0;
        try {
            in = new Scanner(file); // Try to read the file
        } catch (FileNotFoundException e) {
            // Handle the error if the opening of the file failed
            System.err.println("Failed to open " + file);
            System.exit(1);
        }

        String line = in.nextLine(); // Save the next line of the file as a
                                     // string
        // Initialize local variables to use for parsing the array
        int space = 0;
        int prevSpace = 0;
        String process = "";
        int hour = 0;
        int duration = 0;
        int runTime = 0;

        // Keep reading the file while there are still lines left
        while (in.hasNextLine()) {
            // Check if the lines reached "run"
            while (line.charAt(0) != 'r') {
                // Set the line to the part after "schedule"
                line = line.substring(START_OF_PROCESS);
                // Loop through ever character of the line
                for (int i = 0; i < line.length(); i++) {
                    // Look for spaces
                    if (line.charAt(i) == ' ') {
                        // Store the data in different variables based on which
                        // space is hit
                        switch (space) {
                        case 0:
                            // On first space, store the name of the process
                            process = line.substring(0, i);
                            prevSpace = i;
                            break;
                        case 1:
                            // On second space, store the deadline and the
                            // duration of the task
                            hour = Integer
                                    .parseInt(line.substring(prevSpace + 1, i));
                            duration = Integer.parseInt(
                                    line.substring(i + 1, line.length()));
                            break;
                        }
                        space++;
                    }
                }
                // Initialize the record to appropriate values
                Record newRec = new Record(process, hour, duration);
                queue.add(newRec); // Add the new record to the queue
                System.out
                        .println(currentTime + ": adding " + newRec.toString());
                space = 0; // Reset space count
                line = in.nextLine(); // Advance the line
            }

            // Set runtime value
            runTime = Integer
                    .parseInt(line.substring(START_OF_TIME, line.length()));

            // Executing the tasks
            while (queue.size() > 0 && currentTime != runTime) {
                Record currentRecord = queue.poll(); // Pull off the highest
                                                     // priority
                // Check if the runtime is enough to execute the tasks
                if (currentTime + currentRecord.GetDuration() > runTime) {
                    Record reducedRec = new Record(currentRecord.getProcess(),
                            currentRecord.getDeadline(),
                            currentRecord.getDuration()
                                    - (runTime - currentTime)); // Creating the
                                                                // record with
                                                                // leftover time
                    queue.add(reducedRec); // Add the record with leftover time
                                           // to the priority queue
                    System.out.println(currentTime + ": busy with "
                            + currentRecord.toString());
                    currentTime = runTime; // Set current time to runtime
                    System.out.println(
                            currentTime + ": adding " + reducedRec.toString());
                } else {
                    System.out.println(currentTime + ": busy with "
                            + currentRecord.toString());
                    // Advance the current time by the duration of the task
                    currentTime += currentRecord.getDuration();
                    System.out.println(currentTime + ": done with "
                            + currentRecord.toString(currentTime));
                }
            }

            // Check if the end of the file has been reached
            if (in.hasNextLine()) {
                line = in.nextLine();
                continue;
            } else {
                break;
            }
        }

        System.exit(0);

    }

}
