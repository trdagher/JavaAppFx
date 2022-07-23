module com.example.loginform {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.loginform to javafx.fxml;
    exports com.example.loginform;
}