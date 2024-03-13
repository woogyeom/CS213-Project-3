module fitness.studiomanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens fitness.studiomanager to javafx.fxml;
    exports fitness.studiomanager;
}