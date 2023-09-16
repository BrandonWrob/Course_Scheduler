package edu.ncsu.csc216.wolf_scheduler.course;
/**
 * The Conflict interface provides a method to check for conflicts between activities.
 * @author Brandon Wroblewski
 */
public interface Conflict {

	/**
     * Checks for conflicts with a given activity.
     *
     * @param possibleConflictingActivity the activity to check for conflicts with
     * @throws ConflictException if a scheduling conflict occurs
     */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
	
}
