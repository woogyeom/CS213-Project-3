package fitness.studiomanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a schedule of fitness classes.
 * Allows loading classes from a file, storing them, and providing methods to access and manipulate the schedule.
 * It employs a dynamic array to store the classes, which grows as needed to accommodate new additions.
 *
 * @author Aravind Chendu, Woogyeom Sim
 */
public class Schedule {
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROWTH = 4;
    private FitnessClass[] classes = new FitnessClass[4];
    private int numClasses;

    /**
     * Loads fitness classes from a file and adds them to the schedule.
     *
     * @param file The file containing fitness class information.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void load(File file) throws IOException {
        System.out.println("-Fitness classes loaded-");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (data.trim().isEmpty()) {
                continue;
            }

            String[] tokens = data.split(" +");

            Offer offer;
            offer = Offer.valueOf(tokens[0].toUpperCase());

            Instructor instructor;
            instructor = Instructor.valueOf(tokens[1].toUpperCase());

            Time time;
            time = Time.valueOf(tokens[2].toUpperCase());

            Location homeStudio;
            homeStudio = Location.valueOf(tokens[3].toUpperCase());

            FitnessClass lesson = new FitnessClass(offer, instructor, homeStudio, time);
            add(lesson);
            System.out.println(lesson);
        }

        System.out.println("-end of class list.");

    }

    /**
     * Increases the capacity of the schedule's class array.
     */
    private void grow() {
        FitnessClass[] newClasses = new FitnessClass[classes.length + GROWTH];
        for (int i = 0; i < numClasses; i++) {
            newClasses[i] = classes[i];
        }
        classes = newClasses;
    }

    /**
     * Adds a fitness class to the schedule.
     *
     * @param lesson The fitness class to add.
     */
    private void add(FitnessClass lesson) {
        if (numClasses == classes.length) {
            grow();
        }

        classes[numClasses] = lesson;
        numClasses++;
    }

    /**
     * Gets the number of fitness classes in the schedule.
     *
     * @return The number of fitness classes.
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Gets the array of fitness classes in the schedule.
     *
     * @return The array of fitness classes.
     */
    public FitnessClass[] getClasses() {
        return classes;
    }

    /**
     * Finds a fitness class in the schedule based on class information, instructor, and studio.
     *
     * @param classInfo The class information.
     * @param instructor The instructor.
     * @param studio The studio location.
     * @return The fitness class if found, null otherwise.
     */
    public FitnessClass find(Offer classInfo, Instructor instructor, Location studio) {
        FitnessClass fitnessClass = new FitnessClass(classInfo, instructor, studio, null);
        for (int i = 0; i < numClasses; i++) {
            if (classes[i].equals(fitnessClass)) {
                return classes[i];
            }
        }
        return null;
    }
}
