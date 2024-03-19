package fitness.studiomanager;

import java.util.Calendar;

/**
 * Manages calendar dates with functions for setting, comparing, and validating.
 * Supports leap year handling.
 * @author Woogyeom Sim, Aravind Chundu
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Constructs a Date object with specified year, month, and day.
     *
     * @param month The month of the date.
     * @param day   The day of the date.
     * @param year  The year of the date.
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Returns the year of this date.
     *
     * @return The year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of this date.
     *
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the month of this date.
     *
     * @return The month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month of this date.
     *
     * @param month The month to set.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the day of this date.
     *
     * @return The day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day of this date.
     *
     * @param day The day to set.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Converts this Date object to a String in mm/dd/yyyy format.
     *
     * @return A string representation of this date.
     */
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Checks if this date is valid.
     * Also checks for valid month,
     * day considering leap years.
     *
     * @return true if this date is valid, false otherwise.
     */
    public boolean isValid() {
        if (month < 1 || month > 12) {
            return false;
        }

        Calendar input = Calendar.getInstance();
        input.setLenient(false); // only valid calendar date
        input.set(year, month - 1, day);

        try {
            input.getTime();
            return true;
        } catch (Exception e) {
            return false; // not a valid calendar date
        }
    }

    /**
     * Checks if this dob is underage.
     * Only considers year and month.
     *
     * @return true if this dob is underage, false otherwise.
     */
    public boolean isUnderage() {
        Calendar today = Calendar.getInstance();
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentYear = today.get(Calendar.YEAR);

        if (currentYear - year < 18) {
            return true;
        } else if (currentYear - year == 18 && month > currentMonth) {
            return true;
        }

        return false;
    }

    /**
     * Checks if this expiry date is expired.
     *
     * @return true if this date is expired, false otherwise.
     */
    public boolean isExpired() {
        Calendar today = Calendar.getInstance();

        Calendar input = Calendar.getInstance();
        input.set(year, month - 1, day);
        return input.before(today);
    }

    /**
     * Calculates the expiry date depending on membership using today's date.
     *
     * @param string The membership.
     * @return The expiry date.
     */
    public static Date getExpirationDate(String string) {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DAY_OF_MONTH);

        switch (string) {
            case "B":
                month += 1;
                if (month > 12) {
                    month = 1;
                    year += 1;
                }
                int maxDay = getMaxDayOfMonth(year, month);
                day = Math.min(day, maxDay);
                break;
            case "F":
                month += 3;
                if (month > 12) {
                    month = 1;
                    year += 1;
                }
                maxDay = getMaxDayOfMonth(year, month);
                day = Math.min(day, maxDay);
                break;
            case "P":
                year += 1;
                maxDay = getMaxDayOfMonth(year, month);
                day = Math.min(day, maxDay);
                break;
        }

        return new Date(month, day, year);
    }

    /**
     * Returns the last day of the month.
     *
     * @param year The year.
     * @param month The month.
     * @return The last day of the month.
     */
    private static int getMaxDayOfMonth(int year, int month) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> 0;
        };
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    /**
     * Compares this Date object with another Date object for order.
     * Returns a negative integer, zero, or a positive integer as this Date is less than,
     * equal to, or greater than the specified Date.
     *
     * @param date The Date object to be compared.
     * @return the value 0 if the argument Date is equal to this Date; a value less than 0 if this Date
     * is before the Date argument; and a value greater than 0 if this Date is after the Date argument.
     */
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return this.year - date.year;
        }
        if (this.month != date.month) {
            return this.month - date.month;
        }
        return this.day - date.day;
    } // returns -1 if this is earlier
      // returns 0 if it's the same day
      // returns 1 if this is later

    /**
     * Main method for testing the Date class functionality.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args){
        Date dateTest1 = new Date(13, 15, 2023);
        Date dateTest2 = new Date(2, 29, 2023);
        Date dateTest3 = new Date(4, 31, 2023);
        Date dateTest4 = new Date(4, 29, 2025);
        Date dateTest5 = new Date(12, 31, 1899);
        Date dateTest6 = new Date(2, 29, 2000);
        Date dateTest7 = new Date(12, 31, 2023);

//        System.out.println(dateTest1.isValid()); //False - Invalid date - month out of range
//        System.out.println(dateTest2.isValid()); //False - Invalid date - Day beyond range for Feb in non-leap year
//        System.out.println(dateTest3.isValid()); //False - Invalid date - Day beyond range for a month
//        System.out.println(dateTest4.isValid()); //False - Invalid date - Day is in the future
//        System.out.println(dateTest5.isValid()); //False - Invalid date - Year is before 1900
//        System.out.println(dateTest6.isValid()); //True - Valid Leap year date
//        System.out.println(dateTest7.isValid()); //True - Valid day since it is in a 31-day month


    }
}
