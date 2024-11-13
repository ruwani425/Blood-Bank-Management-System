package lk.ijse.gdse.bbms.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeStartViewController implements Initializable {

    boolean run = true;
    Thread thread;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblYear;

    @FXML
    private Label lblMonthDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentTime();
        setYear();
        setMonthAndDate();
    }

    private void setCurrentTime() {
        thread = new Thread(() -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Time-only format
            while (run) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {}

                final String time = timeFormat.format(new Date());
                Platform.runLater(() -> lblTime.setText(time));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void setYear() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); // Year format
        String currentYear = yearFormat.format(new Date());

        Platform.runLater(() -> lblYear.setText(currentYear));
    }

    private void setMonthAndDate() {
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM dd"); // Month and date format
        String monthDate = monthDateFormat.format(new Date());

        Platform.runLater(() -> lblMonthDate.setText(monthDate));
    }

}