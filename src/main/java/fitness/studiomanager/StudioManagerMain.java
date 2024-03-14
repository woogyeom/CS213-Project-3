package fitness.studiomanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudioManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudioManagerMain.class.getResource("studiomanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 550);
        stage.setTitle("RU Fitness Club - Studio Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}