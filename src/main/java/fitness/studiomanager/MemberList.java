package fitness.studiomanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a list of members, enabling operations such as add, remove, find, sort
 * and print members in various orders (by profile, county). It employs a dynamic array
 * to store the members, which grows as needed to accommodate new additions.
 * Also, it has functionality to read the text file and load members from it.
 *
 * @author Aravind Chundu, Woogyeom Sim
 */
public class MemberList {
    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROWTH = 4;
    private Member[] members;
    private int size;

    /**
     * Constructs an empty member list with an initial capacity.
     */
    public MemberList() {
        members = new Member[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Finds the index of a member in the list.
     *
     * @param member The member to find.
     * @return The index of the member if found, else returns NOT_FOUND.
     */
    public int find(Member member) {
        for (int i = 0; i < size; i++) {
            if (members[i].getProfile().compareTo(member.getProfile()) == 0) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of the member list.
     */
    private void grow() {
        Member[] newMembers = new Member[members.length + GROWTH];
        for (int i = 0; i < size; i++) {
            newMembers[i] = members[i];
        }
        members = newMembers;
    }

    /**
     * Checks if the member list contains a given member.
     *
     * @param member The member to check.
     * @return True if the member is found, false otherwise.
     */
    public boolean contains(Member member) {
        return find(member) != NOT_FOUND;
    }

    /**
     * Adds a member to the list.
     *
     * @param member The member to add.
     * @return True if the member is added successfully, false if the member already exists.
     */
    public boolean add(Member member) {
        if (contains(member)) {
            return false;
        } //members list already contains this member
        if (size == members.length) {
            grow();
        } //if members is full, increase it's size

        members[size] = member;
        size++;
        return true;
    }

    /**
     * Removes a member from the list.
     *
     * @param member The member to remove.
     * @return True if the member is removed successfully, false if the member is not found.
     */
    public boolean remove(Member member) {
        int index = find(member);
        if (index == NOT_FOUND) {
            return false;
        } // return false if member is not in the member list

        for (int i = index; i < size-1; i++) {
            members[i] = members[i+1];
        }
        size--;
        members[size] = null;
        return true;
    }

    /**
     * Loads members from a text file and adds them to the list.
     *
     * @param file The file to load members from.
     * @throws IOException If an I/O error occurs.
     */
    public void load(File file) throws IOException {

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (data.trim().isEmpty()) {
                continue;
            }
            String[] tokens = data.split(" +");

            if (tokens.length != 6) {
                throw new IllegalArgumentException("Expected 6 tokens, but found " + tokens.length);
            }
            if (!(tokens[0].equalsIgnoreCase("B")) && !(tokens[0].equalsIgnoreCase("F")) && !(tokens[0].equalsIgnoreCase("P"))) {
                throw new IllegalArgumentException("Invalid membership type. Expected B, F or P but got: "+ tokens[0]);
            }

            Date birthDate = stringToDate(tokens[3]);
            Date expirationDate = stringToDate(tokens[4]);
            Location location;

            if (tokens[5].equalsIgnoreCase("Bridgewater")) {
                location = Location.BRIDGEWATER;
            } else if (tokens[5].equalsIgnoreCase("Edison")) {
                location = Location.EDISON;
            } else if (tokens[5].equalsIgnoreCase("Franklin")) {
                location = Location.FRANKLIN;
            } else if (tokens[5].equalsIgnoreCase("Piscataway")) {
                location = Location.PISCATAWAY;
            } else if (tokens[5].equalsIgnoreCase("Somerville")) {
                location = Location.SOMERVILLE;
            } else {
                throw new IllegalArgumentException(tokens[5] + ": Invalid studio location!");
            }

            Profile profile = new Profile(tokens[1], tokens[2], birthDate);
            Member newMember;

            if (tokens[0].equalsIgnoreCase("B")) {
                newMember = new Basic(profile, expirationDate, location);
                add(newMember);
            } else if (tokens[0].equalsIgnoreCase("F")) {
                newMember = new Family(profile, expirationDate, location);
                if (expirationDate.isExpired()) { // Check if member is expired or not
                    ((Family) newMember).setGuest(false);
                }
                add(newMember);
            } else {
                newMember = new Premium(profile, expirationDate, location);
                if (expirationDate.isExpired()) { // Check if member is expired or not
                    ((Premium) newMember).setGuestPass(0);
                }
                add(newMember);
            }

        }
    }

    /**
     * Converts a string representation of a date to a Date object.
     *
     * @param string The string representation of the date.
     * @return The Date object.
     * @throws IllegalArgumentException If the string representation is invalid.
     */
    private Date stringToDate(String string) throws IllegalArgumentException {
        String[] tokens = string.split("/");

        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[0]);
        int day = Integer.parseInt(tokens[1]);

        return new Date(month, day, year);
    }

    /**
     * Prints members sorted by county.
     */
    public void printByCounty() {
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (members[j].getHomeStudio().getCounty().compareTo(members[minIndex].getHomeStudio().getCounty()) < 0) {
                    minIndex = j;
                } else if (members[j].getHomeStudio().getCounty().compareTo(members[minIndex].getHomeStudio().getCounty()) == 0) {
                    if (members[j].getHomeStudio().getZipcode().compareTo(members[minIndex].getHomeStudio().getZipcode()) < 0) {
                        minIndex = j;
                    }
                }
            }

            Member temp = members[minIndex];
            members[minIndex] = members[i];
            members[i] = temp;
        }

        if (size == 0) {
            System.out.println("Members List is empty!");
        } else {
            for (Member member : members) {
                if (member != null) {
                    System.out.println(member);
                }
            }
        }

    }

    /**
     * Prints members sorted by member profile.
     *
     * @param sCommand Indicates if the "S" command is used for indentation.
     */
    public void printByMember(boolean sCommand) { // We need this sCommand boolean otherwise we need one more method
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (members[j].getProfile().compareTo(members[minIndex].getProfile()) < 0) {
                    minIndex = j;
                }
            }

            Member temp = members[minIndex];
            members[minIndex] = members[i];
            members[i] = temp;
        }

        if (size == 0) {
            System.out.println("Members List is empty!");
        } else {
            for (Member member : members) {
                if (member != null) {
                    if (sCommand) { // Please keep this line as we need to indent each line in the result for "S" command.
                        System.out.print("   ");
                    }
                    System.out.println(member);
                }
            }
        }
    }

    /**
     * Prints member fees.
     */
    public void printFees() {
        for (Member member : members) {

            if (member != null) {
                boolean isExpired = expired(member);

                if (member instanceof Basic) {
                    System.out.println(member + " [next due: $" + member.bill() + "]");
                }
                if (member instanceof Family) {
                    if (isExpired) {
                        System.out.println(member + " [next due: $" + member.bill() + "]");
                    } else {
                        if (((Family) member).getGuest()) {
                            System.out.println(member + " [next due: $" + member.bill() + "]");
                        } else {
                            System.out.println(member + " [next due: $" + member.bill() + "]");
                        }
                    }
                }
                if (member instanceof Premium) {
                    if (isExpired) {
                        System.out.println(member + " [next due: $" + member.bill() + "]");
                    } else {
                        System.out.println(member + " [next due: $" + member.bill() + "]");
                    }
                }
            }
        }
    }

    /**
     * Checks if a member's membership has expired.
     *
     * @param member The member to check.
     * @return True if the membership has expired, false otherwise.
     */
    private boolean expired(Member member) {
        return member.getExpire().isExpired();
    }

    /**
     * Gets the member at the specified index.
     *
     * @param i The index of the member.
     * @return The member at the specified index.
     */
    public Member getMember(int i) {
        return members[i];
    }

    /**
     * Gets the size of the member list.
     *
     * @return The size of the member list.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the member list is empty.
     *
     * @return True if the member list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

}
