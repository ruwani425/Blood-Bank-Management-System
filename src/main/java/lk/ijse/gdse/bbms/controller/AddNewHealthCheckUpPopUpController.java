package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button btnSearchDonor;

    private DonorModel donorModel = new DonorModel();
    private HealthCheckUpModel healthCheckUpModel = new HealthCheckUpModel();
    private DonorDTO donorDTO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        String healthCheckID=lblCheckUpId.getText();
        String status=lblStatus.getText();
        Date checkUpDate = Date.valueOf(LocalDate.now());
        double donorWeight= Double.parseDouble(txtDonorWeight.getText());
        double sugarLevel= Double.parseDouble(txtSugarLevel.getText());
        String bloodPressure=txtBloodPressure.getText();

        String donorNic=txtDonorNic.getText();
        donorDTO =donorModel.getDonorByNic(donorNic);
        String donorID=donorDTO.getDonorId();

        HealthCheckupDTO healthCheckupDTO = new HealthCheckupDTO(healthCheckID,donorID,status,checkUpDate,donorWeight,sugarLevel,bloodPressure);
        boolean isSaved = healthCheckUpModel.addHealthCheckup(healthCheckupDTO);

        if (isSaved) {

            lblCheckUpId.setText(healthCheckUpModel.getNextHealthCheckUpId());

            new Alert(Alert.AlertType.INFORMATION, "health checkup saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save health Checkup...!").show();
        }
    }

    @FXML
    void navigateToDonationPage(ActionEvent event) {

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

//        if (donorDTO.getLastDonationDate()==null){
//            lblLastDonationDate.setText("Donation is pending");
//        }
//        lblLastDonationDate.setText(donorDTO.getLastDonationDate().toString());
    }
}