package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.model.DonorModel;
import lk.ijse.gdse.bbms.model.HealthCheckUpModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class HealthCheckUpPageController implements Initializable {

    @FXML
    private JFXButton showStatusBtn;

    @FXML
    private TextField checkStatusTxt;

    @FXML
    private JFXButton checkStatusBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton addHealthCheckBtn;

    @FXML
    private Label healthCheckupIdLbl;

    @FXML
    private Label donorIdLbl;

    @FXML
    private Label lastDonationDateLbl;

    @FXML
    private Label ageLbl;

    @FXML
    private Label weightLbl;

    @FXML
    private Label sugerLevelLbl;

    @FXML
    private Label bloodPresureLbl;

    @FXML
    private Label healthStatusLbl;

    @FXML
    private Label dateOfCheckUpLbl;

    private HomePageViewController homePageViewController;

    private DonorModel donorModel=new DonorModel();
    private DonorDTO donorDTO;
    private HealthCheckUpModel healthCheckUpModel=new HealthCheckUpModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteBtn.setDisable(true);
        updateBtn.setDisable(true);
    }

    @FXML
    void PopUpNewWindowCheckUp(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addNewHealthCheckUpPopUp-view.fxml"));
            Parent root = fxmlLoader.load();
            AddNewHealthCheckUpPopUpController addNewHealthCheckUpPopUpController = fxmlLoader.getController();
            addNewHealthCheckUpPopUpController.setHomePageViewController(homePageViewController);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnCheckDonorHealthCheckUpDetail(ActionEvent event) throws SQLException {
        deleteBtn.setDisable(false);
        updateBtn.setDisable(false);

        String nic = checkStatusTxt.getText();
        donorDTO = donorModel.getDonorByNic(nic);

        if (donorDTO == null) {
            clearLabels();
            deleteBtn.setDisable(true);
            updateBtn.setDisable(true);
            sugerLevelLbl.setText("No donor found with the given National Id Number.");
            return;
        }

        String donorId = donorDTO.getDonorId();
        HealthCheckupDTO healthCheckupDTO = healthCheckUpModel.getHealthCheckupByDonorId(donorId);

        if (healthCheckupDTO == null) {
            clearLabels();
            deleteBtn.setDisable(true);
            updateBtn.setDisable(true);
            sugerLevelLbl.setText("No health checkup record found for this donor.");
            return;
        }

        // Calculate and display the donor's age
        LocalDate dob = donorDTO.getDob().toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        ageLbl.setText("Donor age: " + age + " years old");

        // Get the last checkup date and time
        LocalDateTime lastCheckupDateTime = healthCheckupDTO.getCheckupDateTime();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Calculate the duration
        Duration duration = Duration.between(lastCheckupDateTime, currentDateTime);
        long days = duration.toDays();

        String elapsedTime;

        // Determine the elapsed time based on the conditions
        if (days >= 365) {
            long years = days / 365;
            elapsedTime = years + " years ago";
        } else if (days >= 30) {
            long months = days / 30;
            elapsedTime = months + " months ago";
        } else if (days >= 7) {
            long weeks = days / 7;
            elapsedTime = weeks + " weeks ago";
        } else if (days >= 1) {
            elapsedTime = days + " days ago";
        } else {
            long hours = duration.toHours();
            if (hours >= 1) {
                elapsedTime = hours + " hours ago";
            } else {
                long minutes = duration.toMinutes();
                elapsedTime = minutes + " minutes ago";
            }
        }

        // Format the date and set the label text
        DateTimeFormatter dateFormatter;
        if (lastCheckupDateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            // If time is midnight, only show the date
            dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else {
            // Otherwise, show date and time
            dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        }

        String formattedDateTime = lastCheckupDateTime.format(dateFormatter);
        dateOfCheckUpLbl.setText("Last checkup date: " + formattedDateTime + " (" + elapsedTime + ")");

        // Set additional donor information
        donorIdLbl.setText("Donor ID: " + donorDTO.getDonorId());
        bloodPresureLbl.setText("Donor blood Pressure: " + healthCheckupDTO.getBloodPressure());
        healthStatusLbl.setText(healthCheckupDTO.getHealthStatus());
        healthCheckupIdLbl.setText("Donor HealthCheckup ID: " + healthCheckupDTO.getCheckupId());
        lastDonationDateLbl.setText("Last Donation Date: " + donorDTO.getLastDonationDate());
        sugerLevelLbl.setText("Donor sugar Level: " + healthCheckupDTO.getSugarLevel());
        weightLbl.setText("Donor weight: " + healthCheckupDTO.getWeight());
    }


    @FXML
    public void setHomePageViewController(HomePageViewController homePageViewController) {
        this.homePageViewController = homePageViewController;
    }

    // Method to clear all labels
    private void clearLabels() {
        ageLbl.setText("");
        dateOfCheckUpLbl.setText("");
        donorIdLbl.setText("");
        bloodPresureLbl.setText("");
        healthStatusLbl.setText("");
        healthCheckupIdLbl.setText("");
        lastDonationDateLbl.setText("");
        sugerLevelLbl.setText("");
        weightLbl.setText("");
    }
}