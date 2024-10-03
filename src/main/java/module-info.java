module lk.ijse.gdse.jfoenix {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens lk.ijse.gdse.jfoenix.controller to javafx.fxml;
    exports lk.ijse.gdse.jfoenix;
}