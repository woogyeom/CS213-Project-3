package fitness.studiomanager;

/**
 * Represents a Basic member, including its profile, expiry date, home studio, number of classes, and bill.
 * Provides functionality to calculate the bill.
 *
 * @author Woogyeom Sim
 */
public class Basic extends Member {
    private static final double MONTHLY_FEE = 39.99;
    private static final int BILLING_INTERVAL = 1;
    private static final int MAX_CLASSES = 4;
    private int numClasses;

    /**
     * Constructs a Basic object with specified profile, expiry date, home studio.
     * Initializes number of classes to 0.
     *
     * @param profile The profile.
     * @param expire The expiry date.
     * @param homeStudio The home studio.
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        numClasses = 0;
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
     * Returns the max number of classes.
     *
     * @return The max number of classes.
     */
    public int getMaxClasses() {
        return MAX_CLASSES;
    }

    /**
     * Returns the number of classes.
     *
     * @return The number of classes.
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Sets the number of classes.
     *
     * @param n The new number of classes.
     */
    public void setNumClasses(int n) {
        numClasses = n;
    }

    /**
     * Returns the next due amount.
     *
     * @return The next due amount.
     */
    @Override
    public double bill() {
        double monthlyFee = MONTHLY_FEE;
        if (numClasses > MAX_CLASSES) {
            monthlyFee += 10.0 * (numClasses - MAX_CLASSES);
        }
        return monthlyFee;
    }
}