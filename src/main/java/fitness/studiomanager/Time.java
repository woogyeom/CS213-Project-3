package fitness.studiomanager;

/**
 * Defines the times available for fitness classes.
 * Allows for categorization and sorting of classes by their specific time schedule.
 * Also, prevents members taking multiple classes at the same time.
 *
 * @author Woogyeom Sim
 */
public enum Time {
    MORNING (9, 30),
    AFTERNOON (14, 0),
    EVENING (18, 30);

    private final int hour;
    private final int minute;

    /**
     * Constructs a Time object with the specified hour and minute.
     *
     * @param hour The hour of the class.
     * @param minute The minute of the class.
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of the class.
     *
     * @return The hour of the class.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the class.
     *
     * @return The minute of the class.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Returns a string representation of the time in "HH:mm" format.
     *
     * @return A string representation of the time.
     */
    public String toString() {
        String strHour = String.valueOf(hour);
        String strMinute = String.format("%02d", minute);

        return strHour + ":" + strMinute;
    }
}
