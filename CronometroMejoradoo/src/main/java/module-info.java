module com.example.cronometromejoradoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cronometromejoradoo to javafx.fxml;
    exports com.example.cronometromejoradoo;
}