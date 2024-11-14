package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.model.DonorModel;
import lk.ijse.gdse.bbms.model.HealthCheckUpModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

public class HealthCheckUpPageController implements Initializable {

    @FXML
    private JFXButton showStatusBtn;

    @FXML
    private JFXButton checkStatusBtn;

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

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXTextField txtBloodPressure;

    @FXML
    private JFXTextField txtSugarLevel;

    @FXML
    private Label lblHealthCheckUpId;

    @FXML
    private JFXTextField txtNicDonor;

    private HomePageViewController homePageViewController;

    private DonorModel donorModel=new DonorModel();
    private DonorDTO donorDTO;
    private HealthCheckUpModel healthCheckUpModel=new HealthCheckUpModel();
    private HealthCheckupDTO healthCheckupDTO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStatusBtn.setDisable(true);
        try {
            lblHealthCheckUpId.setText(healthCheckUpModel.getNextHealthCheckUpId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCheckDonorHealthCheckUpDetail(ActionEvent event) throws SQLException {
        String healthCheckID = lblHealthCheckUpId.getText();
        Date checkUpDate = Date.valueOf(LocalDate.now());
        double donorWeight = Double.parseDouble(txtWeight.getText());
        double sugarLevel = Double.parseDouble(txtSugarLevel.getText());
        String bloodPressure = txtBloodPressure.getText();
        String donorNic = txtNicDonor.getText();
        donorDTO = donorModel.getDonorByNic(donorNic);


        if (donorDTO == null) {
            clearLabels();
            sugerLevelLbl.setText("No donor found with the given National Id Number.");
            return;
        }

        String donorID = donorDTO.getDonorId();
        int age = Period.between(donorDTO.getDob().toLocalDate(), LocalDate.now()).getYears();
        String gender = donorDTO.getGender();

        // Check if age is between 18 and 65 and weight is above 50
        if (age < 18 || age > 65 || donorWeight < 50) {
            healthStatusLbl.setText("Not Eligible");
            saveHealthCheckup(healthCheckID, donorID, healthStatusLbl.getText(), checkUpDate, donorWeight, sugarLevel, bloodPressure);
            return;
        }

        // Blood pressure separate into systolic and diastolic values
        String[] bpParts = bloodPressure.split("/");
        int systolic = Integer.parseInt(bpParts[0].trim());
        int diastolic = Integer.parseInt(bpParts[1].trim());

        // Additional conditions for eligibility
        boolean isEligible = false;
        if (sugarLevel >= 100 && sugarLevel <= 140) {
            if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("female") && systolic <= 110 && diastolic <= 68) {
                isEligible = true;
            } else if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("male") && systolic <= 119 && diastolic <= 70) {
                isEligible = true;
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("female") && systolic <= 122 && diastolic <= 74) {
                isEligible = true;
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("male") && systolic <= 124 && diastolic <= 77) {
                isEligible = true;
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("female") && systolic <= 139 && diastolic <= 68) {
                isEligible = true;
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("male") && systolic <= 133 && diastolic <= 69) {
                isEligible = true;
            }
        }

        if (isEligible) {
            showStatusBtn.setDisable(false);
            healthStatusLbl.setText("The Donor was eligible for a blood Donation");
            showStatusBtn.setText("Pass");

            healthCheckupDTO=new HealthCheckupDTO();
            healthCheckupDTO.setHealthStatus(healthStatusLbl.getText());
            healthCheckupDTO.setCheckupDate(checkUpDate);
            healthCheckupDTO.setCheckupId(healthCheckID);
            healthCheckupDTO.setBloodPressure(bloodPressure);
            healthCheckupDTO.setSugarLevel(sugarLevel);
            healthCheckupDTO.setDonorId(donorID);
            healthCheckupDTO.setWeight(donorWeight);

            showStatusBtn.setDisable(false);
        } else {
            healthStatusLbl.setText("Not Eligible");
            saveHealthCheckup(healthCheckID, donorID, healthStatusLbl.getText(), checkUpDate, donorWeight, sugarLevel, bloodPressure);
        }
        ageLbl.setText("Donor age: " + age + " years old");

        dateOfCheckUpLbl.setText("Last checkup date: " + healthCheckupDTO.getCheckupDate());

        // Set additional donor information
        donorIdLbl.setText("Donor ID: " + donorDTO.getDonorId());
        bloodPresureLbl.setText("Donor blood Pressure: " + healthCheckupDTO.getBloodPressure());
        healthStatusLbl.setText(healthCheckupDTO.getHealthStatus());
        healthCheckupIdLbl.setText("Donor HealthCheckup ID: " + healthCheckupDTO.getCheckupId());
        lastDonationDateLbl.setText("Last Donation Date: " + donorDTO.getLastDonationDate());
        sugerLevelLbl.setText("Donor sugar Level: " + healthCheckupDTO.getSugarLevel());
        weightLbl.setText("Donor weight: " + healthCheckupDTO.getWeight());
    }

    private void saveHealthCheckup(String healthCheckID, String donorID, String status, Date checkUpDate, double donorWeight, double sugarLevel, String bloodPressure) {
        healthCheckupDTO = new HealthCheckupDTO(healthCheckID, donorID, status, checkUpDate, donorWeight, sugarLevel, bloodPressure);
        boolean isSaved;
        try {
            isSaved = healthCheckUpModel.addHealthCheckup(healthCheckupDTO);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error while saving health checkup.").show();
            return;
        }

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Health Checkup added successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save health checkup details").show();
        }
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

    @FXML
    void btnNavigateToDonationPage(ActionEvent event) {
        homePageViewController.navigateToDonationsPageByButton("CH001","A_POSITIVE",null);
    }
}