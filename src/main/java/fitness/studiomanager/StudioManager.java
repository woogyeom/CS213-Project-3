package fitness.studiomanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Manages member and class schedules for a fitness studio.
 * Handles operations like adding/removing members with different memberships,
 * displaying class schedules with attendee information,
 * taking attendance, and managing guest attendance.
 *
 * @author Woogyeom Sim
 */
public class StudioManager {
    private MemberList memberlist;
    private Schedule schedule;

    /**
     * Initializes the StudioManager with an empty member list and schedule.
     */
    public StudioManager() {
        this.memberlist = new MemberList();
        this.schedule = new Schedule();
    }

    /**
     * Processes commands from a file to manage the classes and members. Supports adding,
     * removing, and taking attendance, as well as printing the members/schedule in various orders.
     */
    public void run() {
        try {
            File file = new File("memberList.txt");
            memberlist.load(file);
            System.out.println();
            System.out.println();
            file = new File("classSchedule.txt");
            schedule.load(file);
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error loading" + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Studio Manager is up running...");
        System.out.println();

        while (true) {
            String input = scanner.nextLine();
            String[] tokens = input.split(" +");

            switch (tokens[0]) {
                case "AB": // add a member with Basic membership
                    addBasic(tokens);
                    break;
                case "AF": // add a member with Family membership to the member database
                    addFamily(tokens);
                    break;
                case "AP": // add a member with Premium membership to the member database
                    addPremium(tokens);
                    break;
                case "C": // cancel the membership and remove the member from the member database
                    cancelMembership(tokens);
                    break;
                case "S": // display the class schedule with the current attendees
                    displaySchedule();
                    break;
                case "PM": // display the members sorted by member profiles
                    System.out.println();
                    System.out.println("-list of members sorted by member profiles-");
                    memberlist.printByMember();
                    System.out.println("-end of list-");
                    System.out.println();
                    break;
                case "PC": // display the members sorted by county names
                    System.out.println();
                    System.out.println("-list of members sorted by county names-");
                    memberlist.printByCounty();
                    System.out.println("-end of list-");
                    System.out.println();
                    break;
                case "PF": // print the members with the membership fees
                    System.out.println();
                    System.out.println("-list of members with next dues-");
                    memberlist.printFees();
                    System.out.println("-end of list-");
                    System.out.println();
                    break;
                case "R": // take attendance of a member attending a class and add the member to the class
                    takeAttendance(tokens);
                    break;
                case "U": // remove a member from a class
                    removeMember(tokens);
                    break;
                case "RG": // take attendance of a guest attending a class and add the guest to the class
                    takeAttendanceGuest(tokens);
                    break;
                case "UG": // remove the guest from a class
                    removeGuest(tokens);
                    break;
                case "Q":  // quit
                    System.out.println("Studio Manager terminated.");
                    return;
                case "": // empty line
                    break;
                default:
                    System.out.println(tokens[0] + " is an invalid command!");
                    break;
            }
        }
    }

    private Date stringToDate(String string) throws IllegalArgumentException {
        String[] tokens = string.split("/");

        for (String token : tokens) {
            if (token.matches("[a-zA-Z]+")) {
                System.out.println("The date contains characters.");
                return null;
            }
        }

        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[0]);
        int day = Integer.parseInt(tokens[1]);

        return new Date(month, day, year);
    }

    /**
     * Converts a string representation of an offer into an Offer object.
     *
     * @param string The string representation of the offer.
     * @return An Offer object representing the specified offer if exists, null and print otherwise.
     */
    private Offer stringToOffer(String string) {
        Offer offer = null;
        if (string.equalsIgnoreCase("Pilates")) {
            offer = Offer.PILATES;
        } else if (string.equalsIgnoreCase("Spinning")) {
            offer = Offer.SPINNING;
        } else if (string.equalsIgnoreCase("Cardio")) {
            offer = Offer.CARDIO;
        } else {
            System.out.println(string + " - class name does not exist.");
        }
        return offer;
    }

    /**
     * Converts a string representation of an instructor into an Instructor object.
     *
     * @param string The string representation of the instructor.
     * @return An Instructor object representing the specified instructor if exists, null and print otherwise.
     */
    private Instructor stringToInstructor(String string) {
        Instructor instructor = null;
        if (string.equalsIgnoreCase("Jennifer")) {
            instructor = Instructor.JENNIFER;
        } else if (string.equalsIgnoreCase("Davis")) {
            instructor = Instructor.DAVIS;
        } else if (string.equalsIgnoreCase("Kim")) {
            instructor = Instructor.KIM;
        } else if (string.equalsIgnoreCase("Emma")) {
            instructor = Instructor.EMMA;
        } else if (string.equalsIgnoreCase("Denise")) {
            instructor = Instructor.DENISE;
        } else {
            System.out.println(string + " - instructor does not exist.");
        }
        return instructor;
    }

    /**
     * Converts a string representation of a studio location into a Location object.
     *
     * @param string The string representation of the location.
     * @return A Location object representing the specified studio location if exists, null and print otherwise.
     */
    private Location stringToLocation(String string) {
        Location location = null;
        if (string.equalsIgnoreCase("Bridgewater")) {
            location = Location.BRIDGEWATER;
        } else if (string.equalsIgnoreCase("Edison")) {
            location = Location.EDISON;
        } else if (string.equalsIgnoreCase("Franklin")) {
            location = Location.FRANKLIN;
        } else if (string.equalsIgnoreCase("Piscataway")) {
            location = Location.PISCATAWAY;
        } else if (string.equalsIgnoreCase("Somerville")) {
            location = Location.SOMERVILLE;
        } else {
            System.out.println(string + " - invalid studio location.");
        }
        return location;
    }

    /**
     * Checks if the provided dob is valid and not underage.
     * Prints error messages for invalid or underage dobs.
     *
     * @param date The date to be checked.
     * @return True if the date is valid and not underage, false otherwise.
     */
    private boolean dateCheck(Date date) { // return true if the date is valid
        if (!date.isValid()) {
            System.out.println("DOB " + date.toString() + ": invalid calendar date!");
            return false;
        } else if (!date.isExpired()) {
            System.out.println("DOB " + date.toString() + ": cannot be today or a future date!");
            return false;
        } else if (date.isUnderage()) {
            System.out.println("DOB " + date.toString() + ": must be 18 or older to join!");
            return false;
        }

        return true;
    }

    /**
     * Checks if the provided string corresponds to a valid location.
     *
     * @param string The string representing the location.
     * @return The location if valid, null otherwise.
     */
    private Location locationCheck(String string) {
        Location homeStudio = null;
        if (string.equalsIgnoreCase("Bridgewater")) {
            homeStudio = Location.BRIDGEWATER;
        } else if (string.equalsIgnoreCase("Edison")) {
            homeStudio = Location.EDISON;
        } else if (string.equalsIgnoreCase("Franklin")) {
            homeStudio = Location.FRANKLIN;
        } else if (string.equalsIgnoreCase("Piscataway")) {
            homeStudio = Location.PISCATAWAY;
        } else if (string.equalsIgnoreCase("Somerville")) {
            homeStudio = Location.SOMERVILLE;
        } else {
            System.out.println(string + ": invalid studio location!");
        }
        return homeStudio;
    }

    /**
     * Adds a basic member to the member list.
     *
     * @param tokens Array of tokens containing member information.
     */
    private void addBasic(String[] tokens) {
        if (tokens.length < 5) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date date = stringToDate(tokens[3]);
        if (date == null) {
            return;
        }
        if (!dateCheck(date)) {
            return;
        }
        Profile profile = new Profile(tokens[1], tokens[2], stringToDate(tokens[3]));
        Date expire = Date.getExpirationDate("B");
        Location homeStudio = locationCheck(tokens[4]);
        if (homeStudio == null) {
            return;
        }
        Member member = new Basic(profile, expire, homeStudio);

        if (memberlist.contains(member)) {
            System.out.println(tokens[1] + " " + tokens[2] + " is already in the member database.");
            return;
        }

        memberlist.add(member);
        System.out.println(tokens[1] + " " + tokens[2] + " added.");
    }

    /**
     * Adds a family member to the member list.
     *
     * @param tokens Array of tokens containing member information.
     */
    private void addFamily(String[] tokens) {
        if (tokens.length < 5) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date date = stringToDate(tokens[3]);
        if (date == null) {
            return;
        }
        if (!dateCheck(date)) {
            return;
        }
        Profile profile = new Profile(tokens[1], tokens[2], date);
        Date expire = Date.getExpirationDate("F");
        Location homeStudio = locationCheck(tokens[4]);
        if (homeStudio == null) {
            return;
        }
        Member member = new Family(profile, expire, homeStudio);

        if (memberlist.contains(member)) {
            System.out.println(tokens[1] + " " + tokens[2] + " is already in the member database.");
            return;
        }

        memberlist.add(member);
        System.out.println(tokens[1] + " " + tokens[2] + " added.");
    }

    /**
     * Adds a premium member to the member list.
     *
     * @param tokens Array of tokens containing member information.
     */
    private void addPremium(String[] tokens) {
        if (tokens.length < 5) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date date = stringToDate(tokens[3]);
        if (date == null) {
            return;
        }
        if (!dateCheck(date)) {
            return;
        }
        Profile profile = new Profile(tokens[1], tokens[2], date);
        Date expire = Date.getExpirationDate("P");
        Location homeStudio = locationCheck(tokens[4]);
        if (homeStudio == null) {
            return;
        }
        Member member = new Premium(profile, expire, homeStudio);

        if (memberlist.contains(member)) {
            System.out.println(tokens[1] + " " + tokens[2] + " is already in the member database.");
            return;
        }

        memberlist.add(member);
        System.out.println(tokens[1] + " " + tokens[2] + " added.");
    }

    /**
     * Cancels the membership of a member.
     *
     * @param tokens Array of tokens containing member information.
     */
    private void cancelMembership(String[] tokens) {
        if (tokens.length < 4) {
            System.out.println("Missing data tokens.");
            return;
        }
        Date date = stringToDate(tokens[3]);
        if (date == null) {
            return;
        }
        Profile profile = new Profile(tokens[1], tokens[2], date);
        Member member = new Member(profile, null, null);

        if (memberlist.contains(member)) {
            memberlist.remove(member);
            System.out.println(tokens[1] + " " + tokens[2] + " removed.");
        } else {
            System.out.println(tokens[1] + " " + tokens[2] + " is not in the member database.");
        }
    }

    /**
     * Displays the schedule of fitness classes.
     */
    private void displaySchedule() {
        System.out.println("-Fitness classes-");
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            FitnessClass fitnessClass = schedule.getClasses()[i];
            System.out.println(fitnessClass);
            if (!fitnessClass.getMembers().isEmpty()) {
                System.out.println("[Attendees]");
                fitnessClass.getMembers().printByMember();
            }
            if (!fitnessClass.getGuests().isEmpty()) {
                System.out.println("[Guests]");
                fitnessClass.getGuests().printByMember();
            }
        }
        System.out.println("-end of class list.");
        System.out.println();
    }

    /**
     * Records the attendance of a member in a fitness class.
     *
     * @param tokens Array of tokens containing attendance information.
     */
    private void takeAttendance(String[] tokens) {
        if (tokens.length < 7) { // data token check
            System.out.println("Missing data tokens.");
            return;
        }
        Offer offer = stringToOffer(tokens[1]);
        Instructor instructor = stringToInstructor(tokens[2]);
        Location studio = stringToLocation(tokens[3]);
        if (offer == null || instructor == null || studio == null) { // invalid class info check
            return;
        }
        FitnessClass fitnessClass = schedule.find(offer, instructor, studio);
        if (fitnessClass == null) { // invalid class check
            System.out.println(tokens[1] + " by " + tokens[2] + " does not exist at " + tokens[3]);
            return;
        }
        Profile profile = new Profile(tokens[4], tokens[5], stringToDate(tokens[6]));
        Member member = new Member(profile, null, null);
        if (!memberlist.contains(member)) { // invalid member check
            System.out.println(tokens[4] + " " + tokens[5] + " " + tokens[6] + " is not in the member database.");
            return;
        }
        member = memberlist.getMember(memberlist.find(member));
        if (member.getExpire().isExpired()) { // expired member check
            System.out.println(tokens[4] + " " + tokens[5] + " " + tokens[6] + " membership expired.");
            return;
        }
        if (member instanceof Basic &&  member.getHomeStudio() != studio) { // basic member home studio check
            System.out.println(tokens[4] + " " + tokens[5] + " is attending a class at " + fitnessClass.getStudio().getCity() + " - [BASIC] home studio at " + member.getHomeStudio().getCity());
            return;
        }

        if (fitnessClass.getMembers().contains(member)) { // already in class check
            System.out.println(tokens[4] + " " + tokens[5] + " is already in the class.");
            return;
        }

        // time conflict check
        FitnessClass[] classes = new FitnessClass[schedule.getNumClasses()];
        int count = 0;
        Time time = fitnessClass.getTime();
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getTime().equals(time)) {
                classes[count] = schedule.getClasses()[i];
                count++;
            }
        }
        for (int i = 0; i < count; i++) {
            if (classes[i].getMembers().contains(member)) {
                System.out.println("Time conflict - " + tokens[4] + " " + tokens[5] + " is in another class held at " + time.toString() + " - " + tokens[2].toUpperCase() + ", " + time.toString() + ", " + tokens[3].toUpperCase());
                return;
            }
        }

        fitnessClass.addMember(member);
        if (member instanceof  Basic) {
            ((Basic) member).setNumClasses(((Basic) member).getNumClasses() + 1);
        }
        System.out.println(tokens[4] + " " + tokens[5] + " attendance recorded " + offer + " at " + studio);
    }

    /**
     * Removes a member from a fitness class.
     *
     * @param tokens Array of tokens containing information about the member to be removed.
     */
    private void removeMember(String[] tokens) {
        if (tokens.length < 7) { // data token check
            System.out.println("Missing data tokens.");
            return;
        }
        Offer offer = stringToOffer(tokens[1]);
        Instructor instructor = stringToInstructor(tokens[2]);
        Location studio = stringToLocation(tokens[3]);
        FitnessClass fitnessClass = schedule.find(offer, instructor, studio);
        Profile profile = new Profile(tokens[4], tokens[5], stringToDate(tokens[6]));
        Member member = new Member(profile, null, null);
        Time time = fitnessClass.getTime();
        if (!fitnessClass.getMembers().contains(member)) { // invalid member check
            System.out.println(tokens[4] + " " + tokens[5] + " is not in " + fitnessClass.getInstructor() + ", " + time.toString() + ", " + fitnessClass.getStudio());
            return;
        }
        fitnessClass.removeMember(member);
        System.out.println(tokens[4] + " " + tokens[5] + " is removed from " + fitnessClass.getInstructor() + ", " + time.toString() + ", " + fitnessClass.getStudio());
    }

    /**
     * Records the attendance of a guest in a fitness class.
     *
     * @param tokens Array of tokens containing guest attendance information.
     */
    private void takeAttendanceGuest(String[] tokens) {
        if (tokens.length < 7) { // data token check
            System.out.println("Missing data tokens.");
            return;
        }
        Offer offer = stringToOffer(tokens[1]);
        Instructor instructor = stringToInstructor(tokens[2]);
        Location studio = stringToLocation(tokens[3]);
        if (offer == null || instructor == null || studio == null) { // invalid class info check
            return;
        }
        FitnessClass fitnessClass = schedule.find(offer, instructor, studio);
        if (fitnessClass == null) { // invalid class check
            System.out.println(tokens[1] + " by " + tokens[2] + " does not exist at " + tokens[3]);
            return;
        }
        Profile profile = new Profile(tokens[4], tokens[5], stringToDate(tokens[6]));
        Member member = new Member(profile, null, null);
        if (!memberlist.contains(member)) { // invalid member check
            System.out.println(tokens[4] + " " + tokens[5] + " " + tokens[6] + " is not in the member database.");
            return;
        }
        member = memberlist.getMember(memberlist.find(member));
        if (member instanceof Basic) { // basic check
            System.out.println(tokens[4] + " " + tokens[5] + " [BASIC] - no guest pass.");
            return;
        }
        if (member.getExpire().isExpired()) { // expired member check
            System.out.println(tokens[4] + " " + tokens[5] + " " + tokens[6] + " membership expired.");
            return;
        }
        if ((member instanceof Family && !((Family) member).getGuest()) || (member instanceof Premium && ((Premium) member).getGuestPass() <= 0)) {
            System.out.println(tokens[4] + " " + tokens[5] + " guest pass not available.");
            return;
        }

        if (member.getHomeStudio() != studio) { // home studio check
            System.out.println(tokens[4] + " " + tokens[5] + " (guest) is attending a class at " + fitnessClass.getStudio().getCity() + " - home studio at " + member.getHomeStudio().getCity());
            return;
        }

        fitnessClass.addGuest(member);
        if (member instanceof  Family) {
            ((Family) member).setGuest(false);
        } else if (member instanceof  Premium) {
            ((Premium) member).setGuestPass(((Premium) member).getGuestPass() - 1);
        }
        System.out.println(tokens[4] + " " + tokens[5] + " (guest) attendance recorded " + offer + " at " + studio);

    }

    /**
     * Removes a guest from a fitness class.
     *
     * @param tokens Array of tokens containing information about the guest to be removed.
     */
    private void removeGuest(String[] tokens) {
        if (tokens.length < 7) { // data token check
            System.out.println("Missing data tokens.");
            return;
        }
        Offer offer = stringToOffer(tokens[1]);
        Instructor instructor = stringToInstructor(tokens[2]);
        Location studio = stringToLocation(tokens[3]);
        FitnessClass fitnessClass = schedule.find(offer, instructor, studio);
        Profile profile = new Profile(tokens[4], tokens[5], stringToDate(tokens[6]));
        Member member = new Member(profile, null, null);
        member = memberlist.getMember(memberlist.find(member));
        Time time = fitnessClass.getTime();
        if (!fitnessClass.getGuests().contains(member)) { // invalid member check
            System.out.println(tokens[4] + " " + tokens[5] + " (guest) is not in " + fitnessClass.getInstructor() + ", " + time.toString() + ", " + fitnessClass.getStudio());
            return;
        }
        fitnessClass.removeGuest(member);
        if (member instanceof Family) {
            ((Family) member).setGuest(true);
        } else if (member instanceof  Premium) {
            ((Premium) member).setGuestPass(((Premium) member).getGuestPass() + 1);
        }
        System.out.println(tokens[4] + " " + tokens[5] + " (guest) is removed from " + fitnessClass.getInstructor() + ", " + time.toString() + ", " + fitnessClass.getStudio());
    }
}
