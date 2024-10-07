module lk.ijse.gdse.bbms {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens lk.ijse.gdse.bbms.controller to javafx.fxml;
    exports lk.ijse.gdse.bbms;
}