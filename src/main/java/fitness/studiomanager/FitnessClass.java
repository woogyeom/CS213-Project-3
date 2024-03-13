package fitness.studiomanager;

/**
 * Represents a fitness class, including its class info, instructor, studio, time.
 * Allows for managing Fitness properties and its member list.
 *
 * @author Aravind Chundu, Woogyeom Sim
 */
public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;

    /**
     * Constructs a fitness class object with specified class info, instructor, studio, and time.
     * Initializes its member list and guest list.
     *
     * @param classInfo The class information of the class.
     * @param instructor The instructor of the class.
     * @param studio The studio location of the class.
     * @param time The time of the class.
     */
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }

    /**
     * Returns the class info of the class.
     *
     * @return The class info of the class.
     */
    public Offer getClassInfo() {
        return classInfo;
    }

    /**
     * Sets the class info of the class.
     *
     * @param classInfo The class info to be set.
     */
    public void setClassInfo(Offer classInfo) {
        this.classInfo = classInfo;
    }

    /**
     * Returns the instructor of the class.
     *
     * @return The instructor of the class.
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor of the class.
     *
     * @param instructor The instructor to be set.
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Returns the studio location of the class.
     *
     * @return The studio location of the class.
     */
    public Location getStudio() {
        return studio;
    }

    /**
     * Sets the studio location of the class.
     *
     * @param studio The studio location to be set.
     */
    public void setStudio(Location studio) {
        this.studio = studio;
    }

    /**
     * Returns the time of the class.
     *
     * @return The time of the class.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the time of the class.
     *
     * @param time The time to be set.
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Returns the list of members enrolled in the class.
     *
     * @return The list of members.
     */
    public MemberList getMembers() {
        return members;
    }

    /**
     * Sets the list of members enrolled in the class.
     *
     * @param members The list of members to be set.
     */
    public void setMembers(MemberList members) {
        this.members = members;
    }

    /**
     * Returns the list of guests attending the class.
     *
     * @return The list of guests.
     */
    public MemberList getGuests() {
        return guests;
    }

    /**
     * Sets the list of guests attending the class.
     *
     * @param guests The list of guests to be set.
     */
    public void setGuests(MemberList guests) {
        this.guests = guests;
    }

    /**
     * Adds a member to the list of members enrolled in the class.
     *
     * @param member The member to be added.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Removes a member from the list of members enrolled in the class.
     *
     * @param member The member to be removed.
     */
    public void removeMember(Member member) {
        members.remove(member);
    }

    /**
     * Adds a guest to the list of guests attending the class.
     *
     * @param guest The guest to be added.
     */
    public void addGuest(Member guest) {
        guests.add(guest);
    }

    /**
     * Removes a guest from the list of guests attending the class.
     *
     * @param guest The guest to be removed.
     */
    public void removeGuest(Member guest) {
        guests.remove(guest);
    }

    /**
     * Compares this fitness class with another object for equality.
     *
     * @param obj The object to be compared.
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
        FitnessClass other = (FitnessClass) obj;
        return this.classInfo == other.classInfo &&
                this.instructor == other.instructor &&
                this.studio == other.studio;
    }

    /**
     * Returns a string representation of the fitness class.
     *
     * @return A string representation of the fitness class.
     */
    @Override
    public String toString() {
        return classInfo.toString().toUpperCase() + " - " + instructor.toString().toUpperCase() + ", " + time.toString() + ", " + studio.getCity().toUpperCase();
    }
}
