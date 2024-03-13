package fitness.studiomanager;

/**
 * Represents a profile of a member, including first name, last name, and date of birth.
 * Provides methods to access and modify profile information, as well as comparisons between profiles.
 * Implements Comparable for sorting profiles based on last name, first name, and date of birth.
 *
 * @author Woogyeom Sim
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructs a profile object with specified first name, last name, and date of birth.
     *
     * @param fname First name of the member.
     * @param lname Last name of the member.
     * @param dob   Date of birth of the member.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Returns the first name of the member.
     *
     * @return The first name of the member.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the first name of the member.
     *
     * @param fname The first name to set.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Returns the last name of the member.
     *
     * @return The last name of the member.
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the last name of the member.
     *
     * @param lname The last name to set.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Returns the date of birth of the member.
     *
     * @return The date of birth of the member.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the member.
     *
     * @param date The date of birth to set.
     */
    public void setDob(Date date) {
        this.dob = date;
    }

    /**
     * Returns a string representation of the profile.
     *
     * @return A string containing first name, last name, and date of birth.
     */
    @Override
    public String toString() {
        return fname + ":" + lname + ":" + dob;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The object to compare for equality.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Profile profile = (Profile) obj;

        if (this.getFname().equals(profile.getFname()) && this.getLname().equals(profile.getLname())) {
            return this.getDob().compareTo(profile.getDob()) == 0;
        }
        return false;
    }

    /**
     * Compares this profile with the specified profile for order.
     *
     * @param profile The profile to be compared.
     * @return A negative integer, zero, or a positive integer as this profile is less than, equal to, or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.getLname().compareToIgnoreCase(profile.getLname()) == 0) {
            if (this.getFname().compareToIgnoreCase(profile.getFname()) == 0) {
                if (this.getDob().compareTo(profile.getDob()) == 0) {
                    return 0;
                }
                return this.getDob().compareTo(profile.getDob());
            }
            return this.getFname().compareToIgnoreCase(profile.getFname());
        }
        return this.getLname().compareToIgnoreCase(profile.getLname());
    }
}