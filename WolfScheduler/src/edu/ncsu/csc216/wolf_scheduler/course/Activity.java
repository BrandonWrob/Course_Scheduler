package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * The abstract class Activity represents a general activity with fields 
 * such as title, meeting days, start time, and end time.
 * It serves as a base class for more specific types of activities, such as Course and Event.
 * 
 * @author Brandon Wroblewski
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days. */
	private String meetingDays;
	/** Course's starting time. */
	private int startTime;
	/** Course's ending time. */
	private int endTime;
	/** represents maximum number of hours */
	private static final int UPPER_HOUR = 23;
	/** represents maximum number of minutes */
	private static final int UPPER_MINUTE = 59;

	/**
	 * Constructs an Activity object with the provided title, meeting days, start time, and end time.
	 *
	 * @param title The title of the activity.
	 * @param meetingDays The days of the week when the activity occurs 
	 * @param startTime The start time of the activity 
	 * @param endTime The end time of the activity 
	 */	
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
	
	/**
	 * checks if activity is a duplicate
	 * @param activity a abstract class that represents activities in fields
	 * @return true if duplicate/ false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns the course title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the course title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null or empty
	 */
	public void setTitle(String title) {
		// throws exception if title string is null or empty
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Return the meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Set the meeting days
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime the startTime to set
	 * @param endTime the endTime to set
	 * @throws IllegalArgumentException if meeting days is empty/null, or if user
	 * gives an invalid meeting day or time (if A then start/end time should be 0, 
	 * time should be in military,no duplicate meeting days, start time prior to 
	 * end time, and days must be either A, M, T, W, H, F)
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// throws exception if meetingDays is empty or null
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// breaks military time into hours/minutes
		int startH = startTime / 100;
		int startM = startTime % 100;
		int endH = endTime / 100;
		int endM = endTime % 100;
		// throws exception if hours/minutes format is wrong
		if (startH > UPPER_HOUR || endH > UPPER_HOUR || startM > UPPER_MINUTE || endM > UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// throws exception if start time is greater than end time
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// throws exception if start or end time is negative
		if (startTime < 0 || endTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// sets fields
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Getter method that returns a string for meeting days in standard format
	 * 
	 * @return A string of the meeting days/times
	 */
	public String getMeetingString() {
		// returns Arranged if meetingDays is A
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		// Returns meeting days and time formatted, calls helper method
		// getTimeString to turn the military time into standard time
		return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}

	/**
	 * Private helper method to convert military time to standard time format.
	 * 
	 * @param time the military time to convert to standard
	 * @return a string in standard time format "hh:mm AM/PM
	 */
	private String getTimeString(int time) {
		// assigns hours/ minutes to variables
		int hours = time / 100;
		int minutes = time % 100;
		// sets time format to AM for standard, if hours is greater than
		// 12 then it makes it PM
		String timeFormat = "AM";
		if (hours >= 12) {
			if (hours > 12) {
				hours = hours - 12;
			}
			timeFormat = "PM";
		}
		// if hours is 0 then it sets it to 12
		if (hours == 0) {
			hours = 12;
		}
		// creates strings to store standard time, and adds a 0 prior to
		// minutes if it is under 10 so it will always be 2 digits
		String stringM;
		if (minutes < 10) {
			stringM = "0" + minutes;
		} else {
			stringM = Integer.toString(minutes);
		}
		// returns the standard time in proper format
		return Integer.toString(hours) + ":" + stringM + timeFormat;
	}

	/**
	 * Checks for a schedule conflict with a given activity.
	 * 
	 * @param possibleConflictingActivity The activity to check for a schedule conflict with.
	 * @throws ConflictException If a schedule conflict is detected, a ConflictException is thrown.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
	    // Check if the two activities share a similar meet day
		for (int i = 0; i < meetingDays.length(); i++) {
			char tempDay = meetingDays.charAt(i);
			if (possibleConflictingActivity.meetingDays.indexOf(tempDay) != -1) {
				// if they share a meeting day it checks if the times overlap
				if (this.startTime <= possibleConflictingActivity.startTime &&
		                this.endTime >= possibleConflictingActivity.startTime) {
		                // There is a time overlap, so throw a ConflictException
		                throw new ConflictException("The event cannot be added due to a conflict.");
		        }
				if (this.startTime >= possibleConflictingActivity.startTime &&
		                this.startTime <= possibleConflictingActivity.endTime) {
		                // There is a time overlap, so throw a ConflictException
		                throw new ConflictException("The event cannot be added due to a conflict.");
		        }
			}
		}
	}

	/**
	 * Return the start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Returns a hash code value for the Activity object based on its attributes.
	 *
	 * @return the hash code value for this Activity object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this Activity object.
	 *
	 * @param obj the reference object with which to compare.
	 * @return true if this Activity is equal to the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	/**
	 * The short display array is used to populate the rows of the course catalog and student schedule 
	 * @return returns an array of length 4 containing the Course name, section, title, and meeting string.
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * The full display array is used to display the final schedule 
	 * @return returns an array of length 7 containing the Course name, section, title, credits, instructorId, 
	 * meeting string, empty string
	 */
	public abstract String[] getLongDisplayArray();
}