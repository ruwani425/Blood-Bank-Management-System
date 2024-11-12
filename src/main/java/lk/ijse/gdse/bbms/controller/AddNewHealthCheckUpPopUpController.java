package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.model.DonorModel;
import lk.ijse.gdse.bbms.model.HealthCheckUpModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class AddNewHealthCheckUpPopUpController implements Initializable {
    @FXML
    private Label lblCheckUpDate;

    @FXML
    private Label lblCheckUpId;

    @FXML
    private Label lblDonorAge;

    @FXML
    private JFXTextField txtDonorWeight;

    @FXML
    private Label lblLastDonationDate;

    @FXML
    private JFXTextField txtBloodPressure;

    @FXML
    private JFXTextField txtSugarLevel;

    @FXML
    private JFXButton donateBtn;

    @FXML
    private Label lblStatus;

    @FXML
    private JFXButton checkStatusBtn;

    @FXML
    private JFXTextField txtDonorNic;

    @FXML
    private JFXButton btnSearchDonor;

    private Stage stage;
    String bloodGroup;

    private DonorModel donorModel = new DonorModel();
    private HealthCheckUpModel healthCheckUpModel = new HealthCheckUpModel();
    private DonorDTO donorDTO;
    private HomePageViewController homePageViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        donateBtn.setDisable(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lblCheckUpDate.setText(LocalDate.now().format(formatter));
        try {
            lblCheckUpId.setText(healthCheckUpModel.getNextHealthCheckUpId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveHealthCheckUpDataOnAction(ActionEvent event) throws SQLException {
        String healthCheckID = lblCheckUpId.getText();
        Date checkUpDate = Date.valueOf(LocalDate.now());
        double donorWeight = Double.parseDouble(txtDonorWeight.getText());
        double sugarLevel = Double.parseDouble(txtSugarLevel.getText());
        String bloodPressure = txtBloodPressure.getText();
        String donorNic = txtDonorNic.getText();
        donorDTO = donorModel.getDonorByNic(donorNic);
        String donorID = donorDTO.getDonorId();
        int age = Period.between(donorDTO.getDob().toLocalDate(), LocalDate.now()).getYears();
        String gender = donorDTO.getGender();

        // Check if age is between 18 and 65 and weight is above 50
        if (age < 18 || age > 65 || donorWeight < 50) {
            lblStatus.setText("Not Eligible");
            return;
        }

        // Blood pressure separate into systolic and diastolic values
        String[] bpParts = bloodPressure.split("/");
        int systolic = Integer.parseInt(bpParts[0].trim());
        int diastolic = Integer.parseInt(bpParts[1].trim());

        // Conditions check
        if (sugarLevel >= 100 && sugarLevel <= 140) {
            if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("female") && systolic <= 110 && diastolic <= 68) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("male") && systolic <= 119 && diastolic <= 70) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("female") && systolic <= 122 && diastolic <= 74) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("male") && systolic <= 124 && diastolic <= 77) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("female") && systolic <= 139 && diastolic <= 68) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("male") && systolic <= 133 && diastolic <= 69) {
                lblStatus.setText("The Donor was eligible for a blood Donation");
                donateBtn.setDisable(false);
            } else {
                lblStatus.setText("Not Eligible");
            }
        } else {
            lblStatus.setText("Not Eligible");
        }

        // Create and save the health check-up
        HealthCheckupDTO healthCheckupDTO = new HealthCheckupDTO(healthCheckID, donorID, lblStatus.getText(), checkUpDate, donorWeight, sugarLevel, bloodPressure);
        boolean isSaved = healthCheckUpModel.addHealthCheckup(healthCheckupDTO);

        if (isSaved) {
            lblCheckUpId.setText(healthCheckUpModel.getNextHealthCheckUpId());
            new Alert(Alert.AlertType.INFORMATION, "Health check-up saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save health check-up!").show();
        }
    }



//    @FXML
//    void btnSaveHealthCheckUpDataOnAction(ActionEvent event) throws SQLException {
//        String healthCheckID=lblCheckUpId.getText();
//        String status=lblStatus.getText();
//        Date checkUpDate = Date.valueOf(LocalDate.now());
//        double donorWeight= Double.parseDouble(txtDonorWeight.getText());
//        double sugarLevel= Double.parseDouble(txtSugarLevel.getText());
//        String bloodPressure=txtBloodPressure.getText();
//
//        String donorNic=txtDonorNic.getText();
//        donorDTO =donorModel.getDonorByNic(donorNic);
//        String donorID=donorDTO.getDonorId();
//        int age = Period.between(donorDTO.getDob().toLocalDate(), LocalDate.now()).getYears();
//
//
//
//        HealthCheckupDTO healthCheckupDTO = new HealthCheckupDTO(healthCheckID,donorID,status,checkUpDate,donorWeight,sugarLevel,bloodPressure);
//        boolean isSaved = healthCheckUpModel.addHealthCheckup(healthCheckupDTO);
//
//        if (isSaved) {
//
//            lblCheckUpId.setText(healthCheckUpModel.getNextHealthCheckUpId());
//
//            new Alert(Alert.AlertType.INFORMATION, "health checkup saved successfully...!").show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Fail to save health Checkup...!").show();
//        }
//    }
    void setHomePageViewController(HomePageViewController homePageViewController) {
        this.homePageViewController=homePageViewController;
    }

    //donate button navigation
    @FXML
    void navigateToDonationPage(ActionEvent event) {
        homePageViewController.navigateToDonationsPageByButton(lblCheckUpId.getText(),bloodGroup);
        stage = (Stage) donateBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void btnSearchDonorOnAction(ActionEvent event) throws SQLException {
        String donorNic=txtDonorNic.getText();
        donorDTO =donorModel.getDonorByNic(donorNic);
        int age = Period.between(donorDTO.getDob().toLocalDate(), LocalDate.now()).getYears();
        lblDonorAge.setText(String.valueOf(age));

        lblLastDonationDate.setText(
                donorDTO.getLastDonationDate() == null ? "Donation is pending" : donorDTO.getLastDonationDate().toString()
        );
        bloodGroup=donorDTO.getBloodGroup();

//        if (donorDTO.getLastDonationDate()==null){
//            lblLastDonationDate.setText("Donation is pending");
//        }
//        lblLastDonationDate.setText(donorDTO.getLastDonationDate().toString());
    }
}