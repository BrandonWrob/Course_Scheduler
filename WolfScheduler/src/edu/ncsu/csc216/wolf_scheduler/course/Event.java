package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Event represents a scheduled event with a title, meeting days, start time,
 * end time, and event details and it is a subclass of the Activity class.
 * 
 * @author Brandon Wroblewski
 */
public class Event extends Activity {

	/** optional details about the event */
	private String eventDetails;

	/**
     * Constructs an Event object with the given parameters.
     * 
     * @param title the title of the event
     * @param meetingDays the days on which the event meets
     * @param startTime the start time of the event
     * @param endTime the end time of the event
     * @param eventDetails optional details about the event
     */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
        super(title, meetingDays, startTime, endTime);
        setEventDetails(eventDetails);
    }
	
	/**
     * Checks if another Event is a duplicate of this Event based on their title.
     *
     * @param activity The Course to compare with this Course.
     * @return True if the Courses have the same title, false otherwise.
     */
    @Override
    public boolean isDuplicate(Activity activity) {
        if (activity instanceof Event) {
            Event otherEvent = (Event) activity;
            // Check if the other event has the same title 
            return this.getTitle().equals(otherEvent.getTitle());
        }
        // returns false if other event is different
        return false;
    }
	
	/**
	 * Sets the meeting days and time for an event.
	 * 
	 * @param meetingDays the meeting days to set
	 * @param startTime the start time to set
	 * @param endTime the end time to set
	 * @throws IllegalArgumentException if meetingDays is empty/null, or if user
	 * gives an invalid meeting day or time (if A then start/end time should be 0,
	 * time should be in military, no duplicate meeting days, start time prior to 
	 * end time, and days must be either A, M, T, W, H, F)
	 */ 
	@Override 
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
	        throw new IllegalArgumentException("Invalid meeting days and times.");
	    }
	    int monday = 0;
		int tuesday = 0;
		int wednesday = 0;
		int thursday = 0;
		int friday = 0;
		int saturday = 0;
		int sunday = 0;
		// adds meeting days to their corresponding days
		for (int i = 0; i < meetingDays.length(); i++) {
			char temp = meetingDays.charAt(i);
			switch (temp) {
			case 'M':
				monday++;
				break;
			case 'T':
				tuesday++;
				break;
			case 'W':
				wednesday++;
				break;
			case 'H':
				thursday++;
				break;
			case 'F':
				friday++;
				break;
			case 'S':
				saturday++;
				break;
			case 'U':
				sunday++;
				break;
			// throws exception if invalid meeting day given
			default:
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			// throws exception if duplicate days given
 			if (monday > 1 || tuesday > 1 || wednesday > 1 || thursday > 1 || friday > 1
 					|| sunday > 1 || saturday > 1) {
 				throw new IllegalArgumentException("Invalid meeting days and times.");
 			}
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
     * Sets the optional details about the event.
     * 
     * @param eventDetails optional details about the event
     * @throws IllegalArgumentException if eventDetails is 
     */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}

	/**
     * Retrieves the optional details about the event.
     * 
     * @return the event details
     */
	public String getEventDetails() {
        return eventDetails;
    }
	
	/**
	 * Returns a short display array containing empty strings for name and section,
	 * the title, and the meeting string.
	 * 
	 * @return a string array with the short display information
	 */
	@Override
	public String[] getShortDisplayArray() {
	    String[] shortDisplay = { "", "", getTitle(), getMeetingString() };
	    return shortDisplay;
	}

	/**
	 * Returns a long display array containing empty strings for name and section,
	 * the title, empty strings for credits and instructor, the meeting string, and
	 * the event details.
	 * 
	 * @return a string array with the long display information
	 */
	@Override
	public String[] getLongDisplayArray() {
	    String[] longDisplay = { "", "", getTitle(), "", "", getMeetingString(), getEventDetails() };
	    return longDisplay;
	}

	/**
	 * Returns a string representation of the event in the format: "title,
	 * meetingDays, startTime, endTime, eventDetails".
	 * 
	 * @return a string representation of the event
	 */
	@Override
	public String toString() {
	    return getTitle() + "," + getMeetingDays() + "," + getStartTime() + ","
	            + getEndTime() + "," + getEventDetails();
	}



}
