package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;
import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/**
 * The WolfScheduler class represents a scheduler which can be used to
 * manage courses, schedules, and catalogs.
 * 
 * @author Brandon Wroblewski
 */
public class WolfScheduler {

	/**
	 * Creates a ArrayList instance which represents a collection of Course
	 * objects that represent available courses.
	 */
	private ArrayList<Course> catalog;
	
	/**
	 * Creates a ArrayList instance which represents a collection of Course
	 * objects selected by user.
	 */
	private ArrayList<Activity> schedule;
	
	/**
	 * Creates a string instance that represents the title
	 */
	private String title;

	/**
	 * Constructs a WolfScheduler object.
	 *
	 * @param filename The filename for the course records to be read.
	 * @throws IllegalArgumentException if CourseRecordIO.readCourseRecords() throws an exception
	 * for not being able to find the file or file has invalid format
	 */
	public WolfScheduler(String filename) {
		// Creates empty ArrayList to initialize schedule field
		schedule = new ArrayList<>();

		// Set the title field to the default value
		title = "My Schedule";

		// Try to add Course objects from the input file to the catalog field
		try {
			catalog = CourseRecordIO.readCourseRecords(filename);
		} catch (IllegalArgumentException e) {
			// If it fails it will catch it and throw a new IllegalArgumentException
			throw new IllegalArgumentException("Cannot find file.");
		} catch (FileNotFoundException e) {
			// prints the stacktrace
			e.printStackTrace();
		}
	}

	/**
     * returns the course catalog as a 2D String array.
     * String Array stores name, section, title, and meeting string info
     *
     * @return A 2D String array representing the course catalog.
     */
	public String[][] getCourseCatalog() {
		// check if there is no courses in catalog
		if (catalog.isEmpty()) {
			// Return an empty 2D string if it's empty
			return new String[0][0];
		}
		// Create a 2D String array to store course information
		String[][] courseCatalogArray = new String[catalog.size()][4];
		// Fills the array with course information
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			courseCatalogArray[i] = course.getShortDisplayArray();
		}
		// returns catalog array
		return courseCatalogArray;
	}

	 /**
     * Retrieves the scheduled courses as a 2D String array.
     * String Array stores name, section, and title for course
     *
     * @return A 2D String array representing the scheduled courses.
     */
	public String[][] getScheduledActivities() {
		// Check if there are no courses in the schedule
		if (schedule.isEmpty()) {
			// Return an empty 2D String array if it's empty
			return new String[0][0];
		}
		// Create a 2D String array to store course information
		String[][] scheduledCoursesArray = new String[schedule.size()][4];
		// Fills the array with scheduled course information
		for (int i = 0; i < schedule.size(); i++) {
			Activity course = schedule.get(i);
			scheduledCoursesArray[i] = course.getShortDisplayArray();
		}
		// returns scheduled courses array
		return scheduledCoursesArray;
	}

	/**
     * Retrieves the full scheduled courses as a 2D String array. 
     * The schedule includes name, section, title, credits, 
     * instructor ID, and meeting days/times
     *
     * @return A 2D String array representing the full scheduled courses.
     */
	public String[][] getFullScheduledActivities() {
		// Check if there are no courses in the schedule
		if (schedule.isEmpty()) {
			// Return an empty 2D String array if it's empty
			return new String[0][0];
		}
		// Create a 2D String array to store course information
		String[][] fullScheduledCoursesArray = new String[schedule.size()][7];
		// Fills the array with scheduled course information
		for (int i = 0; i < schedule.size(); i++) {
			Activity course = schedule.get(i);
			fullScheduledCoursesArray[i] = course.getLongDisplayArray();
		}
		// returns the array
		return fullScheduledCoursesArray;
	}

	/**
	 * Uses paramaters name and section to iterate through catalog and
	 * return the matching course, returns null of it is not found.
     *
     * @param name The name of the course.
     * @param section The section of the course.
     * @return The Course object matching the name and section, or null if not found.
     */
	public Course getCourseFromCatalog(String name, String section) {
	    // Iterates through catalog.
	    for (Course course : catalog) {
	        // Stores course name and section in temporary string variables.
	        String tempName = course.getName();
	        String tempSection = course.getSection();
	        // If the temporary variables equal the parameters, it returns the course.
	        if (name.equals(tempName) && section.equals(tempSection)) {
	            return course;
	        }
	    }
	    // Returns null if the course is not found.
	    return null;
	}

	/**
     * Adds a course to the schedule based on its name and section assuming course
     * is not already in schedule, if it is then it will throw an exception
     *
     * @param name The name of the course.
     * @param section The section of the course.
     * @return True if the course is added successfully, false if not found in the 
     * catalog or already in the schedule.
     * @throws IllegalArgumentException If a duplicate course is found in the schedule.
     */
	public Boolean addCourseToSchedule(String name, String section) {
		// creates Activity that stores course from catalog that matches parameters
	    Activity activityToAdd = getCourseFromCatalog(name, section);
	    // iterates through schedule to check for duplicates
	    for (Activity activityFromSchedule : schedule) {
	        if (activityFromSchedule.isDuplicate(activityToAdd)) {
	        	// if duplicate is found it throws an exception, does not care about
	        	// different sections
	            throw new IllegalArgumentException("You are already enrolled in " + name);
	        }
	    }
	    // If the course is found in the catalog, add it to the schedule and return true
	    if (activityToAdd != null) {
	        schedule.add(activityToAdd);
	        return true;
	    }
	    // returns false if the course was not found in the catalog
	    return false;
	}
	
	/**
	 * Adds a new event to the schedule.
	 *
	 * @param eventTitle The title of the event to be added.
	 * @param eventMeetingDays The days of the week when the event occurs 
	 * @param eventStartTime The start time of the event  
	 * @param eventEndTime The end time of the event 
	 * @param eventDetails Additional details or description of the event.
	 * @throws IllegalArgumentException If an event with the same title already exists in the schedule.
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
	    // Create an Event object with the provided parameters
	    Event eventToAdd = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);

	    // Check if an event with the same title already exists in the schedule
	    for (Activity activityFromSchedule : schedule) {
	        if (activityFromSchedule.isDuplicate(eventToAdd)) {
	            throw new IllegalArgumentException("You have already created an event called " + eventTitle);
	        }
	    }
	    // Add the event to the schedule
	    schedule.add(eventToAdd);
	}


	/**
     * Removes a course from the schedule based on its name and section and return true
     * if it is in schedule, if it is not than it will return false.
	 * @param idx index of activity being removed
     *
     * @return True if the course is removed successfully, false if not found in the schedule.
     */
	public boolean removeActivityFromSchedule(int idx) {
	    try {
	        // Attempt to remove the activity at the specified index
	        schedule.remove(idx);
	        return true;
	    } catch (IndexOutOfBoundsException e) {
	        // Catch any IndexOutOfBoundsException and return false
	        return false;
	    }
	}


	/**
     * Returns the title of the schedule.
     *
     * @return The title of the schedule.
     */
	public String getScheduleTitle() {
		// returns title
		return title;
	}

	/**
	 * Exports the current schedule to a file with the given filename.
	 *
	 * @param filename The name of the file where the schedule will be exported.
	 * @throws IllegalArgumentException If an IOException occurs while writing to the file.
	 */
	public void exportSchedule(String filename) {
		// attempts to write the schedule in filename
		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		} catch (IOException e) { // catches IOException and throws IllegalArgumentException.
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

	/**
	 * Resets the schedule by creating an empty ArrayList.
	 */
	public void resetSchedule() {
		// Creates empty ArrayList to reset schedule
		schedule = new ArrayList<>();
	}

	/**
	 * Sets the title of the schedule.
	 *
	 * @param title The title for the schedule.
	 * @throws IllegalArgumentException If the provided title is null.
	 */
	public void setScheduleTitle(String title) {
		// throws IllegalArgumentException if title is null
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		// sets current instance of title equal to parameter
		this.title = title;
	}
	
	

}
