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


    // first tab

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

    @FXML
    private ToggleGroup homeStudioRadio1;

    @FXML
    private Button addNewButton;

    @FXML
    private Button loadMembersButton;

    // second tab

    @FXML
    private ToggleGroup classTypeRadio;

    @FXML
    private ToggleGroup instructorRadio;

    @FXML
    private ToggleGroup homeStudioRadio2;

    @FXML
    private TextField firstNameTextField2;

    @FXML
    private TextField lastNameTextField2;

    @FXML
    private DatePicker dobDatePicker2;

    @FXML
    private TextField guestPassTextField2;

    @FXML
    private Button addMemberButton;

    @FXML
    private Button removeMemberButton;

    @FXML
    private Button addGuestButton;

    @FXML
    private Button removeGuestButton;

    //

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
    private String homeStudioStr1 = "Bridgewater";
    @FXML
    private void handleHomeStudio1() {
        RadioButton selectedRadioButton = (RadioButton) homeStudioRadio1.getSelectedToggle();
        if (selectedRadioButton != null) {
            homeStudioStr1 = selectedRadioButton.getText();
        }
    }

    private String classTypeStr = "Pilates";
    @FXML
    private void handelClassType() {
        RadioButton selectedRadioButton = (RadioButton) classTypeRadio.getSelectedToggle();
        if (selectedRadioButton != null) {
            classTypeStr = selectedRadioButton.getText();
        }
    }

    private String instructorStr = "Jennifer";
    @FXML
    private void handleInstructor() {
        RadioButton selectedRadioButton = (RadioButton) instructorRadio.getSelectedToggle();
        if (selectedRadioButton != null) {
            instructorStr = selectedRadioButton.getText();
        }
    }

    private String homeStudioStr2 = "Bridgewater";
    @FXML
    private void handleHomeStudio2() {
        RadioButton selectedRadioButton = (RadioButton) homeStudioRadio2.getSelectedToggle();
        if (selectedRadioButton != null) {
            homeStudioStr2 = selectedRadioButton.getText();
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
        Location homestudio = stringToLocation(homeStudioStr1);
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

        Stage stage = (Stage) loadMembersButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            memberlist.load(file);
            print("Members loaded.");
        }
    }

    @FXML
    private void onAddMemberButtonClick() {
        FitnessClass fitnessClass = checkClassValidity();
        if (fitnessClass == null) return;
        Member member = checkMemberValdity();
        if (member == null) return;

        if (member instanceof Basic && member.getHomeStudio() != fitnessClass.getStudio()) {
            print(member.getProfile().toString() + " is attending a class at " + fitnessClass.getStudio().getCity() + ". - [BASIC] home studio at " + member.getHomeStudio().getCity());
            return;
        }
        if (fitnessClass.getMembers().contains(member)) {
            print(member.getProfile().toString() + " is already in the class.");
            return;
        }
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
                System.out.println("Time conflict - " + member.getProfile().toString() + " is in another class held at " + time.toString() + " - " + fitnessClass.toString());
                return;
            }
        }
        fitnessClass.addMember(member);
        if (member instanceof  Basic) {
            ((Basic) member).setNumClasses(((Basic) member).getNumClasses() + 1);
        }
        System.out.println(member.getProfile().toString() + " attendance recorded " + fitnessClass.getClassInfo() + " at " + fitnessClass.getStudio());
    }

    @FXML
    private void onRemoveMemberButtonClick() {
        FitnessClass fitnessClass = checkClassValidity();
        if (fitnessClass == null) return;
        Member member = checkMemberValdity();
        if (member == null) return;

        if (!fitnessClass.getMembers().contains(member)) {
            print(member.getProfile().toString() + " is not in " + fitnessClass.toString());
            return;
        }
        fitnessClass.removeMember(member);
        print(member.getProfile().toString() + " is removed from " + fitnessClass.toString());
    }

    @FXML
    private void onAddGuestButtonClick() {
        FitnessClass fitnessClass = checkClassValidity();
        if (fitnessClass == null) return;
        Member member = checkMemberValdity();
        if (member == null) return;
        if (getGuestPass(member) <= 0) {
            print("guest pass not available.");
            return;
        }
        if (member.getHomeStudio() != fitnessClass.getStudio()) {
            print(member.getProfile().toString() + " (guest) is attending a class at " + fitnessClass.getStudio().getCity() + ". - home studio at " + member.getHomeStudio().getCity());
            return;
        }
        fitnessClass.addGuest(member);
        if (member instanceof Family) {
            ((Family) member).setGuest(false);
        } else if (member instanceof Premium) {
            ((Premium) member).setGuestPass(getGuestPass(member) - 1);
        }
        print(member.getProfile().toString() + " (guest) attendance recorded at " + fitnessClass.toString());
    }

    @FXML
    private void onRemoveGuestButtonClick() {
        FitnessClass fitnessClass = checkClassValidity();
        if (fitnessClass == null) return;
        Member member = checkMemberValdity();
        if (member == null) return;

        if (!fitnessClass.getGuests().contains(member)) {
            print(member.getProfile().toString() + " (guest) is not in " + fitnessClass.toString());
            return;
        }
        fitnessClass.removeGuest(member);
        if (member instanceof Family) {
            ((Family) member).setGuest(true);
        } else if (member instanceof Premium) {
            ((Premium) member).setGuestPass(getGuestPass(member) + 1);
        }
        print(member.getProfile().toString() + " (guest) is removed from " + fitnessClass.toString());
    }

    private FitnessClass checkClassValidity() {
        Offer offer = stringToOffer(classTypeStr);
        Instructor instructor = stringToInstructor(instructorStr);
        Location studio = stringToLocation(homeStudioStr2);
        FitnessClass fitnessClass = schedule.find(offer, instructor, studio);
        if (fitnessClass == null) {
            print("no class found");
            return null;
        }
        return fitnessClass;
    }

    private Member checkMemberValdity() {
        String fname = firstNameTextField2.getText();
        String lname = lastNameTextField2.getText();
        if (fname == null || lname == null || dobDatePicker2.getValue() == null)
        {
            print("invalid input");
            return null;
        }
        Date date = stringToDate(dobDatePicker2.getValue().toString());
        Profile profile = new Profile(fname, lname, date);
        Member member = new Member(profile, null, null);
        if (!memberlist.contains(member)) {
            print(member.getProfile().toString() + " is not in the member database.");
            return null;
        }
        member = memberlist.getMember(memberlist.find(member));
        if (member.getExpire().isExpired()) {
            print(member.getProfile().toString() + " membership expired.");
            return null;
        }
        guestPassTextField2.setText(Integer.toString(getGuestPass(member)));
        return member;
    }

    private int getGuestPass(Member member) {
        int guestpass = 0;
        switch (member) {
            case Family f -> {
                guestpass = f.getGuest() ? 1 : 0;
            }
            case Premium p -> {
                guestpass = p.getGuestPass();
            }
            default ->  {
            }
        }
        return guestpass;
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