package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test the Activity Class for handleing conflicts between activities
 * 
 * @author Brandon Wroblewski
 */
class ActivityTest {

	/**
	 * Test that it does not throw something if no conflict occurs
	 */
	@Test
	public void testCheckNoConflict() {
	    Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
	    Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "TH", 1330, 1445);
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Asserts a throw call is throw for overlapping times
	 */
	@Test
	public void testCheckConflictWithConflict() {
	    Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
	    Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	}
	
	/**
	 * Asserts a throw call is thrown for overlapping conflict start time
	 */
	@Test
    public void testCheckConflictSameDayOverlapStartTime() {
        Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
        Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1300, 1330);
        
        Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
        assertEquals("Schedule conflict.", e1.getMessage());
        
        Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
        assertEquals("Schedule conflict.", e2.getMessage());
    }
	
	/**
	 * Asserts a throw call is thrown for overlapping conflict end time
	 */
	@Test
    public void testCheckConflictSameDayOverlapEndTime() {
        Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
        Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1445, 1500);
        
        Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
        assertEquals("Schedule conflict.", e1.getMessage());
        
        Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
        assertEquals("Schedule conflict.", e2.getMessage());
    }
	
	/**
	 * Asserts no throw call thrown for events seperated by 1 minute
	 */
	@Test
	public void testCheckNoConflictOneMinute() {
		Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
        Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1446, 1500);
        Activity a3 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1100, 1329);
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	    assertDoesNotThrow(() -> a1.checkConflict(a3));
	    assertDoesNotThrow(() -> a3.checkConflict(a1));
	}
	
	/**
	 * Asserts a throw call is thrown for start time being equal to end time
	 */
	@Test
    public void testCheckConflictStartTimeEqualsEndTime() {
        Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1520, 1520);
        Exception e = assertThrows(ConflictException.class, () -> a1.checkConflict(a1));
        assertEquals("Schedule conflict.", e.getMessage());
    }
}
