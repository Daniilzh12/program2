module com.program.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;


    opens com.program.passwordmanager to javafx.fxml;
    exports com.program.passwordmanager;
}