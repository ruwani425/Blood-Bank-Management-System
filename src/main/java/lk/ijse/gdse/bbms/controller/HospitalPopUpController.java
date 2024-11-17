package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.HospitalTM;
import lk.ijse.gdse.bbms.model.HospitalModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HospitalPopUpController implements Initializable {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblHospitalId;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    HospitalModel hospitalModel=new HospitalModel();
    HospitalPageController hospitalPageController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblHospitalId.setText(hospitalModel.getNextHospitalId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHospitalPageController(HospitalPageController hospitalPageController) {
        this.hospitalPageController = hospitalPageController;
    }

    @FXML
    void btnAddHospitalOnAction(ActionEvent event) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();
        String type = txtType.getText();
        String hospitalId = lblHospitalId.getText();

        HospitalDTO hospitalDTO = new HospitalDTO(hospitalId, name, address, contactNumber, email, type);

        try {
            boolean isAdded = hospitalModel.addHospital(hospitalDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Hospital added successfully!").show();
                clearFields(); // Clear fields after adding
                hospitalPageController.refreshTable(); // Assuming you have a method to refresh the hospital table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Hospital.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while adding Hospital.").show();
        }
    }
    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow(); // Get the current stage (popup window)
        stage.close(); // Close the stage (popup window)
    }

    @FXML
    void btnDeleteHospitalOnAction(ActionEvent event) {
        String hospitalId = lblHospitalId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Hospital?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = hospitalModel.deleteHospital(hospitalId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Hospital deleted successfully!").show();
                    clearFields(); // Clear fields after deletion
                    hospitalPageController.refreshTable(); // Refresh the hospital table
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Hospital.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Hospital.").show();
            }
        }
    }


    @FXML
    void btnUpdateHospitalOnAction(ActionEvent event) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();
        String type = txtType.getText();
        String hospitalId = lblHospitalId.getText();

        HospitalDTO hospitalDTO = new HospitalDTO(hospitalId, name, address, contactNumber, email, type);

        try {
            boolean isUpdated = hospitalModel.updateHospital(hospitalDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Hospital updated successfully!").show();
                clearFields(); // Clear fields after updating
                hospitalPageController.refreshTable(); // Refresh the hospital table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Hospital.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Hospital.").show();
        }
    }
    private void clearFields() throws SQLException {
        txtName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        txtType.clear();
        lblHospitalId.setText(hospitalModel.getNextHospitalId()); // Reset hospital ID for next input
    }
    public void setHospitalData(HospitalTM hospital) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        lblHospitalId.setText(hospital.getHospitalId());
        txtName.setText(hospital.getHospitalName());
        txtAddress.setText(hospital.getHospitalAddress());
        txtContactNumber.setText(hospital.getContactNumber());
        txtEmail.setText(hospital.getEmail());
        txtType.setText(hospital.getType());
    }
}
