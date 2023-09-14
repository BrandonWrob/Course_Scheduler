/** imports wolf scheduler course package*/
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * The Course class represents a course offered and track of multiple attributes including 
 * the course name, title, section, credit hours, instructor's Unity ID, meeting days, 
 * start time, and end time of the class.
 * 
 * @author Brandon Wroblewski
 */
public class Course extends Activity {
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours. */
	private int credits;
	/** Course's instructor. */
	private String instructorId;
	/** represents minimum characters in course name */
	private static final int MIN_NAME_LENGTH = 5;
	/** represents maximum characters in course name */
	private static final int MAX_NAME_LENGTH = 8;
	/** represents minimum letter count in course name */
	private static final int MIN_LETTER_COUNT = 1;
	/** represents maximum letter count in course name */
	private static final int MAX_LETTER_COUNT = 4;
	/** represents number of digits that should be in course name */
	private static final int DIGIT_COUNT = 3;
	/** represents section length */
	private static final int SECTION_LENGTH = 3;
	/** represents minimum number of credit hours */
	private static final int MIN_CREDITS = 1;
	/** represents maximum number of credit hours */
	private static final int MAX_CREDITS = 5;
	
	/**
	 * default constructor for name, title, section, credits,instructor id, meeting
	 * days, start and end times.
	 * 
	 * @param name         name of Course.
	 * @param title        title of Course.
	 * @param section      section of Course.
	 * @param credits      credit hours for Course.
	 * @param instructorId instructor's unity id.
	 * @param meetingDays  meeting days for Course as a series of chars.
	 * @param startTime    start time for the course.
	 * @param endTime      end time for the course.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Constructor for name, title, section, credits, instructor id, and meeting
	 * time
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}
	
	/**
     * Checks if another Course is a duplicate of this Course based on their names.
     *
     * @param activity The Course to compare with this Course.
     * @return True if the Courses have the same name , false otherwise.
     */
    @Override
    public boolean isDuplicate(Activity activity) {
        if (activity instanceof Course) {
            Course otherCourse = (Course) activity;
            // Check if the other course has the same name (doesn't care about different sections)
            return this.getName().equals(otherCourse.getName());
        }
        // returns false if other course name is different
        return false;
    }

	/**
	 * Sets the meeting days and time for a course.
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
	    // throws exception if user selects Arranged and the start/end times are not 0
 		if ("A".equals(meetingDays)) {
 			if (startTime != 0 || endTime != 0) {
 				throw new IllegalArgumentException("Invalid meeting days and times.");
 			}
 		} else {
 			int monday = 0;
 			int tuesday = 0;
 			int wednesday = 0;
 			int thursday = 0;
 			int friday = 0;
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
 				// throws exception if invalid meeting day given
 				default:
 					throw new IllegalArgumentException("Invalid meeting days and times.");
 				}
 			}
 			// throws exception if duplicate days given
 			if (monday > 1 || tuesday > 1 || wednesday > 1 || thursday > 1 || friday > 1) {
 				throw new IllegalArgumentException("Invalid meeting days and times.");
 			}
 	
 		}
 		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Returns the course name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		if (name == null) {
			// throws an exception if name is null
			throw new IllegalArgumentException("Invalid course name.");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			// Throws an exception if name is an invalid length
			throw new IllegalArgumentException("Invalid course name.");
		}
		// creates counters for letters and digits, and a boolean for if space is found
		int numLetters = 0;
		int numDigits = 0;
		boolean spaceFound = false;
		// iterates through String name to check if it is a valid name
		// counts letters and digits as it iterates
		for (int i = 0; i < name.length(); i++) {
			Character value = name.charAt(i);
			if (!spaceFound) {
				if (Character.isLetter(value)) {
					numLetters++;
				} else if (value == '\t') {
					throw new IllegalArgumentException("Invalid course name.");
				} else if (Character.isWhitespace(value)) {
					spaceFound = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else {
				if (Character.isDigit(value)) {
					numDigits++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		// throws an exception if number of digits/letters in name is invalid
		if (numLetters < MIN_LETTER_COUNT || numLetters > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		// throws an exception if there is an invalid number of digits
		if (numDigits != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}

	/**
	 * Returns the section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null, not 3 chars, or if it
	 *                                  contains a nondigit character
	 */
	public void setSection(String section) {
		// throws an exception if section string is empty or not 3 chars
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		for (int i = 0; i < section.length(); i++) {
			char temp = section.charAt(i);
			// throws an exception of a character is not a digit
			if (!Character.isDigit(temp)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the number of credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the number of credits
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits are less than 1 or greater than 5
	 */
	public void setCredits(int credits) {
		// throws an excepton if credits are less than 1 or greater than 5
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Return the instructors ID
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Set the instructors ID
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorID String is null or empty
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns a hash code value for the Course object.
	 *
	 * @return the hash code value for this Course object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Checks to see if Course object is equal to argument object.
	 *
	 * @param obj the object being compared
	 * @return true if this Course is equal to the obj argument, false if they are not equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + ","
				+ getStartTime() + "," + getEndTime();
	}

	/**
	 * Returns an array of length 4 containing the Course name, section, title, and meeting string.
	 *
	 * @return An array of length 4 containing course information.
	 */
	@Override
	public String[] getShortDisplayArray() {
	    String[] shortDisplayArray = new String[4];
	    shortDisplayArray[0] = name;
	    shortDisplayArray[1] = section;
	    shortDisplayArray[2] = getTitle();
	    shortDisplayArray[3] = getMeetingString();
	    return shortDisplayArray;
	}

	/**
	 * Returns an array of length 7 containing the Course name, section, title, credits, instructorId, meeting string,
	 * and an empty string (for a field that Event will have that Course does not).
	 *
	 * @return An array of length 7 containing detailed course information.
	 */
	@Override
	public String[] getLongDisplayArray() {
	    String[] longDisplayArray = new String[7];
	    longDisplayArray[0] = name;
	    longDisplayArray[1] = section;
	    longDisplayArray[2] = getTitle();
	    longDisplayArray[3] = Integer.toString(credits);
	    longDisplayArray[4] = instructorId;
	    longDisplayArray[5] = getMeetingString();
	    longDisplayArray[6] = ""; // Empty string as the last field
	    return longDisplayArray;
	}

}