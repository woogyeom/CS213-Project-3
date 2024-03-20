package fitness.studiomanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents the main entry point for the Studio Manager application of RU Fitness Club.
 * It sets up the initial stage for the application.
 *
 * @author Woogyeom Sim
 */
public class StudioManagerMain extends Application {
    /**
     * Starts the Studio Manager application by loading the FXML layout and displaying it in a stage.
     *
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML layout
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudioManagerMain.class.getResource("studiomanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 550);
        stage.setTitle("RU Fitness Club - Studio Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point for the Studio Manager application.
     * It launches the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch();
    }
}