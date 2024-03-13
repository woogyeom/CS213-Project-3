package fitness.studiomanager;

/**
 * Represents a Premium member, including its profile, expiry date, home studio, number of available guess pass, and bill.
 * Provides functionality to calculate the bill.
 *
 * @author Woogyeom Sim
 */
public class Premium extends Member {
    private static final double MONTHLY_FEE = 59.99;
    private static final int BILLING_INTERVAL = 12;
    private int guestPass;

    /**
     * Constructs a Premium object with specified profile, expiry date, home studio.
     * Initializes number of guest pass to 3.
     *
     * @param profile The profile.
     * @param expire The expiry date.
     * @param homeStudio The home studio.
     */
    public Premium(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guestPass = 3;
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
     * Returns the number of available guess pass.
     *
     * @return The number of available guess pass.
     */
    public int getGuestPass() {
        return guestPass;
    }

    /**
     * Sets the number of available guess pass.
     *
     * @param n The new number of available guess pass.
     */
    public void setGuestPass(int n) {
        guestPass = n;
    }

    /**
     * Returns the next due amount.
     *
     * @return The next due amount.
     */
    public double bill() {
        return MONTHLY_FEE * BILLING_INTERVAL - MONTHLY_FEE;
    }
}