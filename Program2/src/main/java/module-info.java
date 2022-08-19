module com.example.program2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.program2 to javafx.fxml;
    exports com.example.program2;
}