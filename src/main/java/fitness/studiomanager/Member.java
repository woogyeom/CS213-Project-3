package fitness.studiomanager;

/**
 * Represents a member of the fitness club.
 *
 * @author Woogyeom Sim, Aravind Chundu
 */
public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

    /**
     * Constructs a member object with specified profile, expiry date, and home studio.
     *
     * @param profile The profile of the member.
     * @param expire The expiry date of the member.
     * @param homeStudio The home studio of the member.
     */
    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;
    }

    /**
     * Returns the next due amount of the member.
     *
     * @return The next due amount.
     */
    public double bill() {
        return 0.0;
    }// Placeholder implementation, actual billing logic can be overridden.

    /**
     * Returns the profile of the member.
     *
     * @return The profile of the member.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Returns the expiry date of the member.
     *
     * @return The expiry date of the member.
     */
    public Date getExpire() {
        return  expire;
    }

    /**
     * Returns the home studio of the member.
     *
     * @return The home studio of the member.
     */
    public Location getHomeStudio() {
        return homeStudio;
    }

    /**
     * Returns a string representation of the member.
     *
     * @return A string representation of the member.
     */
    @Override
    public String toString() {
        String expireStatus = expire.isExpired() ? "Membership expired " + expire.toString() : "Membership expires " + expire.toString();
        String memberShip = null;
        switch (this) {
            case Basic basic -> memberShip = "(Basic) number of classes attended: " + basic.getNumClasses();
            case Family family -> {
                if (family.expired()) {
                    memberShip = "(Family) guest-pass remaining: not eligible";
                } else if (family.getGuest()) {
                    memberShip = "(Family) guest-pass remaining: 1";
                } else {
                    memberShip = "(Family) guest-pass remaining: 0";
                }
            }
            case Premium premium -> {
                if (!premium.expired()) {
                    memberShip = "(Premium) guest-pass remaining: " + premium.getGuestPass();
                } else {
                    memberShip = "(Premium) guest-pass remaining: not eligible";
                }
            }
            default -> {}
        }
        return profile.toString() + ", " + expireStatus + ", Home Studio: " + homeStudio.toString() + ", " + memberShip;
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
        Member comparingMember = (Member) obj;
        return profile.equals(comparingMember.profile);
    } // Writeup said we could uniquely identify a member by profile, so I'm not sure if we need to check other fields

    /**
     * Compares this member with the specified member for order.
     *
     * @param member The member to be compared.
     * @return A negative integer, zero, or a positive integer as this member is less than, equal to, or greater than the specified member.
     */
    public int compareTo(Member member) {
        return this.profile.compareTo(member.profile);
    } //Again writeup said we could uniquely identify a member by profile, so I'm not sure if we need to check other fields to compare

    /**
     * Checks if the membership of the member has expired.
     *
     * @return True if the membership has expired, false otherwise.
     */
    public boolean expired() {
        return expire.isExpired();
    }
}
