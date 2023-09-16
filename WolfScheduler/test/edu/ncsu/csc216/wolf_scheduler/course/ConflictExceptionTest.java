package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the custom exception class ConflictException.
 * @author Brandon Wroblewski
 */
class ConflictExceptionTest {

    /**
     * Test method for the parameterless constructor of ConflictException.
     * Verifies that it correctly sets the default message.
     */
    @Test
    public void testConflictException() {
        ConflictException ce = new ConflictException();
        assertEquals("Schedule conflict.", ce.getMessage());
    }

    /**
     * Test method for the parameterized constructor of ConflictException.
     * Verifies that it correctly sets a custom exception message.
     */
    @Test
    public void testConflictExceptionString() {
        ConflictException ce = new ConflictException("Custom exception message");
        assertEquals("Custom exception message", ce.getMessage());
    }
}
