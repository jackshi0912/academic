/*
 * NAME: Huize Shi
 * ID: A92122910
 * Date: Feb. 19th, 2016
 * LOGIN: cs12wjz
 */
package hw6;

/**
 * This Record class encapsulates information about a task in the queue
 * 
 * @author Jack Shi
 * @version Feb. 19, 2016
 */
public class Record implements Comparable<Record> {

    private String process; // The name of the process
    private long deadline; // The deadline of the process
    private long duration; // The duration of the process

    /**
     * constructor to create a new record with process name, deadline and
     * duration
     * 
     * @param process
     *            The name of the task
     * @param deadline
     *            The deadline for it
     * @param duration
     *            The duration of the task
     */
    Record(String process, long deadline, long duration) {
        this.process = process; // Set the instance process name
        this.deadline = deadline; // Set the instance deadline
        this.duration = duration; // Set the instance duration
    }

    /**
     * Copy constructor to create a new record using a record and a new duraton
     * 
     * @param r
     *            The record to copy from
     * @param duration
     *            The new duration to assign
     */
    Record(Record r, long duration) {
        // Initialize the appropriate values
        this.process = r.process;
        this.deadline = r.deadline;
        this.duration = duration;
    }

    /**
     * Getter for the duration
     * 
     * @return The duration of the task
     */
    public long GetDuration() {
        return duration;
    }

    /**
     * To String override method to output desired String
     * 
     * @return A sentence with data displayed according to specifications
     */
    public String toString() {
        return process + " with deadline " + deadline + " and duration "
                + duration;
    }

    /**
     * Overload the toString method to account for late completion
     * 
     * @param current_time
     *            The current time
     * @return The process name and if it's late
     */
    public String toString(long current_time) {
        if (current_time > deadline)
            return process + " (late)";
        return process;
    }

    /**
     * Getter for the process name
     * 
     * @return The process name
     */
    public String getProcess() {
        return process;
    }

    /**
     * Getter for the deadLine
     * 
     * @return The deadline of this task
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * Getter for the duration of the task
     * 
     * @return The duration for the task
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Override the compareTo method of the comparable interface
     * 
     * @param The
     *            record to compare with
     * @return 1 if the deadline for this is greater than that of o, -1 if the
     *         deadline for this is less than that of o, 0 if the deadline of
     *         this is equal to that of o
     */
    @Override
    public int compareTo(Record o) {
        // Check the conditions and return the appropriate values
        if (this.deadline < o.getDeadline()) {
            return -1;
        } else if (this.deadline > o.getDeadline()) {
            return 1;
        } else {
            return 0;
        }
    }
}
