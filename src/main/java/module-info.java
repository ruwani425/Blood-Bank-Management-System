module lk.ijse.gdse.bbms {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires static lombok;
    requires java.sql;

    opens lk.ijse.gdse.bbms.controller to javafx.fxml;
    exports lk.ijse.gdse.bbms;
}