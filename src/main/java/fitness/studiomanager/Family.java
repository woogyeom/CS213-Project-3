package fitness.studiomanager;

/**
 * Represents a Family member, including its profile, expiry date, home studio, guest pass availability, and bill.
 * Provides functionality to calculate the bill.
 *
 * @author Woogyeom Sim
 */
public class Family extends Member {
    private static final double MONTHLY_FEE = 49.99;
    private static final int BILLING_INTERVAL = 3;
    private boolean guest;

    /**
     * Constructs a Family object with specified profile, expiry date, home studio.
     * Initializes guest pass to true.
     *
     * @param profile The profile.
     * @param expire The expiry date.
     * @param homeStudio The home studio.
     */
    public Family(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guest = true;
    }

    /**
     * Returns the monthly fee.
     *
     * @return The monthly fee.
     */
    public double getMonthlyFee() {
        return MONTHLY_FEE;
    }

    /**
     * Returns the billing interval.
     *
     * @return The billing interval.
     */
    public int getBillingInterval() {
        return BILLING_INTERVAL;
    }

    /**
     * Returns the guest pass availability.
     *
     * @return The guest pass availability.
     */
    public boolean getGuest() {
        return guest;
    }

    /**
     * Sets the guest pass availability.
     *
     * @param bool The new guest pass availability.
     */
    public void setGuest(boolean bool) {
        guest = bool;
    }

    public double bill() {
        return MONTHLY_FEE * BILLING_INTERVAL;
    }
}