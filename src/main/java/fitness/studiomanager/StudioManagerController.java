package fitness.studiomanager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StudioManagerController {

    private MemberList memberlist;
    private Schedule schedule;

    /**
     * Initializes the StudioManager with an empty member list and schedule.
     */
    public StudioManagerController() {
        this.memberlist = new MemberList();
        this.schedule = new Schedule();
    }

    @FXML
    private TextArea textArea;

    @FXML
    private TextField firstNameTextField1;

    @FXML
    private TextField lastNameTextField1;

    @FXML
    private DatePicker dobDatePicker1;

    @FXML
    private ToggleGroup memberTypeRadio;

    @FXML
    private TextField guestPassTextField1;
    private TextField guestPassTextField2;

    @FXML
    private ToggleGroup homeStudioRadio1;

    @FXML
    private ToggleGroup classTypeRadio;

    @FXML
    private ToggleGroup instructorRadio;

    @FXML
    private ToggleGroup HomeStudioRadio2;

    @FXML
    private Button addNewButton;

    @FXML
    private Button loadMembersButton;

    private String memberTypeStr = "Basic";
    @FXML
    private void handleMemberType() {
        RadioButton selectedRadioButton = (RadioButton) memberTypeRadio.getSelectedToggle();
        if (selectedRadioButton != null) {
            memberTypeStr = selectedRadioButton.getText();
            switch (memberTypeStr) {
                case "Basic":
                    guestPassTextField1.setText("0");
                    break;
                case "Family":
                    guestPassTextField1.setText("1");
                    break;
                case "Premium":
                    guestPassTextField1.setText("3");
                    break;
            }
        }
    }
    private String homeStudioStr = "Bridgewater";
    @FXML
    private void handleHomeStudio1() {
        RadioButton selectedRadioButton = (RadioButton) homeStudioRadio1.getSelectedToggle();
        if (selectedRadioButton != null) {
            homeStudioStr = selectedRadioButton.getText();
        }
    }

    @FXML
    private void onAddNewButtonClick() {

        String fname = firstNameTextField1.getText();
        String lname = lastNameTextField1.getText();
        if (fname == null || lname == null || dobDatePicker1.getValue() == null)
        {
            print("invalid input");
            return;
        }
        Date date = stringToDate(dobDatePicker1.getValue().toString());
        if (!dateCheck(date)) {
            return;
        }
        Profile profile = new Profile(fname, lname, date);
        Location homestudio = stringToLocation(homeStudioStr);
        Member member = switch (memberTypeStr) {
            case "Basic" -> new Basic(profile, Date.getExpirationDate("B"), homestudio);
            case "Family" -> new Family(profile, Date.getExpirationDate("F"), homestudio);
            case "Premium" -> new Premium(profile, Date.getExpirationDate("P"), homestudio);
            default -> null;
        };

        if (memberlist.contains(member)) {
            print(fname + " " + lname + " is already in the member database.");
            return;
        }

        memberlist.add(member);
        print(fname + " " + lname + " added");
    }

    @FXML
    private void onLoadMembersButtonClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) addNewButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            memberlist.load(file);
            print("Members loaded.");
        }
    }

    @FXML
    private void print(String str) {
        textArea.appendText(str + '\n');
    }

    /**
     * Converts a string representation of a date into a Date object.
     *
     * @param string The string representation of the date.
     * @return A Date object representing the specified date if exists, null and print otherwise.
     */
    private Date stringToDate(String string) throws IllegalArgumentException {
        String[] tokens = string.split("-");

        for (String token : tokens) {
            if (token.matches("[a-zA-Z]+")) {
                System.out.println("The date contains characters.");
                return null;
            }
        }

        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);

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
            print("DOB " + date.toString() + ": invalid calendar date!");
            return false;
        } else if (!date.isExpired()) {
            print("DOB " + date.toString() + ": cannot be today or a future date!");
            return false;
        } else if (date.isUnderage()) {
            print("DOB " + date.toString() + ": must be 18 or older to join!");
            return false;
        }

        return true;
    }

}