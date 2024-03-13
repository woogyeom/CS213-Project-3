package fitness.studiomanager;

/**
 * Defines the studio locations available for fitness classes.
 * Allows for categorization and sorting of classes by their studio location.
 *
 * @author Woogyeom Sim
 */
public enum Location {
    BRIDGEWATER("BRIDGEWATER", "08807", "Somerset"),
    EDISON("EDISON", "08837", "Middlesex"),
    FRANKLIN("FRANKLIN", "08873", "Somerset"),
    PISCATAWAY("PISCATAWAY", "08854", "Middlesex"),
    SOMERVILLE("SOMERVILLE", "08876", "Somerset");

    private final String city;
    private final String zipcode;
    private final String county;

    Location(String city, String zipcode, String county) {
        this.city = city;
        this.zipcode = zipcode;
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public  String getCounty() {
        return county;
    }

    public String toString() {
        return city + ", " + zipcode + ", " + county.toUpperCase();
    }
}
