package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * A custom exception class to represent scheduling conflicts between activities.
 * This exception is thrown when a scheduling conflict is detected.
 * @author Brandon Wroblewski
 */
public class ConflictException extends Exception {

    /** ID used for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConflictException with a default message: "Schedule conflict."
     */
    public ConflictException() {
        super("Schedule conflict.");
    }

    /**
     * Constructs a new ConflictException with a specified message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ConflictException(String message) {
        super(message);
    }
}
