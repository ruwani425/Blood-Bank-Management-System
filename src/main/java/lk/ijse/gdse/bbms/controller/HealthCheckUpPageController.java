package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class HealthCheckUpPageController {

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
        String nic=checkStatusTxt.getText();
        donorDTO=donorModel.getDonorByNic(nic);


        if (donorDTO == null) {
            clearLabels();
            // Display a message if no donor found with the given NIC
            sugerLevelLbl.setText("No donor found with the given National Id Number.");
            return;
        }

        String donorId=donorDTO.getDonorId();
        HealthCheckupDTO healthCheckupDTO =healthCheckUpModel.getHealthCheckupByDonorId(donorId);

        if (healthCheckupDTO == null) {
            clearLabels();
            // Display a message if no health checkup record found
            sugerLevelLbl.setText("No health checkup record found for this donor.");
            return;
        }

        LocalDate dob = donorDTO.getDob().toLocalDate(); // Convert SQL Date to LocalDate
        LocalDate currentDate = LocalDate.now(); // Get the current date
        int age = Period.between(dob, currentDate).getYears(); // Calculate age in years
        ageLbl.setText(String.valueOf(age)); // Set age in the label

        dateOfCheckUpLbl.setText(String.valueOf(healthCheckupDTO.getCheckupDate()));
        donorIdLbl.setText(donorDTO.getDonorId());
        bloodPresureLbl.setText(healthCheckupDTO.getBloodPressure());
        healthStatusLbl.setText(healthCheckupDTO.getHealthStatus());
        healthCheckupIdLbl.setText(healthCheckupDTO.getCheckupId());
        lastDonationDateLbl.setText(String.valueOf(donorDTO.getLastDonationDate()));
        sugerLevelLbl.setText(String.valueOf(healthCheckupDTO.getSugarLevel()));
        weightLbl.setText(String.valueOf(healthCheckupDTO.getWeight()));
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