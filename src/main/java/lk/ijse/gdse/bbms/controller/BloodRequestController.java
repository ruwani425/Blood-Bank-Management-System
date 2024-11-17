package lk.ijse.gdse.bbms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.model.BloodRequestModel;
import lk.ijse.gdse.bbms.model.HospitalModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BloodRequestController implements Initializable {

    @FXML
    private TableView<BloodRequestTM> tblRequest;

    @FXML
    private TableColumn<BloodRequestTM, String> colRequestID;

    @FXML
    private TableColumn<BloodRequestTM, String> colHospitalID;

    @FXML
    private TableColumn<BloodRequestTM, String> colBloodGroup;

    @FXML
    private TableColumn<BloodRequestTM, Date> colDateOfRequest;

    @FXML
    private TableColumn<BloodRequestTM, Double> colBloodQty;

    @FXML
    private TableColumn<BloodRequestTM, String> colStatus;

    @FXML
    private Label lblRequestID;

    @FXML
    private ComboBox<String> cmbBloodGroup;

    @FXML
    private TextField txtBloodQty;

    @FXML
    private Label lblRequestDate;

    @FXML
    private ComboBox<String> cmbHospital;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private Button btnAddRequest;

    BloodRequestModel bloodRequestModel = new BloodRequestModel();
    private HospitalModel hospitalModel = new HospitalModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblRequestID.setText(bloodRequestModel.getNextRequestId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateBloodGroups();
        populateStatus();
        setCellValueFactory();
        loadBloodRequests();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        lblRequestDate.setText(formattedDate);

        try {
            ArrayList<String> idList = hospitalModel.getAllHospitalIDs();
            cmbHospital.getItems().addAll(idList); // Add items to ComboBox
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colRequestID.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colHospitalID.setCellValueFactory(new PropertyValueFactory<>("hospitalId"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        colDateOfRequest.setCellValueFactory(new PropertyValueFactory<>("dateOfRequest"));
        colBloodQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadBloodRequests() {
        try {
            // Fetch the list of Blood Requests from the database
            ArrayList<BloodRequestDTO> bloodRequestList = bloodRequestModel.getAllRequests();

            // Convert DTOs to TM objects
            ObservableList<BloodRequestTM> observableList = FXCollections.observableArrayList();
            for (BloodRequestDTO dto : bloodRequestList) {
                observableList.add(new BloodRequestTM(
                        dto.getRequestId(),
                        dto.getHospitalId(),
                        dto.getBloodType(),
                        dto.getDateOfRequest(),
                        dto.getQty(),
                        dto.getStatus()
                ));
            }

            // Set data to the table
            tblRequest.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load blood requests!").show();
        }
    }


    private void populateBloodGroups() {
        cmbBloodGroup.getItems().addAll("A_POSITIVE", "A_NEGATIVE", "B_POSITIVE", "B_NEGATIVE", "AB_POSITIVE", "AB_NEGATIVE", "O_POSITIVE", "O_NEGATIVE");
    }

    private void populateStatus() {
        cmbStatus.getItems().addAll("ACTIVE", "INACTIVE", "PENDING", "COMPLETED");
    }

    @FXML
    void btnAddrequestOnAction(ActionEvent event) {
        try {
            // Get input values from the UI components
            String requestId = lblRequestID.getText();
            String hospitalId = cmbHospital.getSelectionModel().getSelectedItem();
            String bloodType = cmbBloodGroup.getSelectionModel().getSelectedItem();
            Date dateOfRequest = Date.valueOf(LocalDate.now()); // Current date
            double qty = Double.parseDouble(txtBloodQty.getText());
            String status = cmbStatus.getSelectionModel().getSelectedItem();

            // Create a BloodRequestDTO with the collected values
            BloodRequestDTO bloodRequestDTO = new BloodRequestDTO(
                    requestId, hospitalId, bloodType, dateOfRequest, qty, status
            );

            // Save the Blood Request
            boolean isSaved = bloodRequestModel.addBloodRequest(bloodRequestDTO);

            if (isSaved) {
                // If saved successfully, refresh the request ID and show a success message
                lblRequestID.setText(bloodRequestModel.getNextRequestId());
                new Alert(Alert.AlertType.INFORMATION, "Blood request saved successfully!").show();
            } else {
                // Show an error message if the operation failed
                new Alert(Alert.AlertType.ERROR, "Failed to save blood request!").show();
            }

            // Optionally clear the fields after saving
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error occurred!").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity value! Please enter a valid number.").show();
        }
    }

    private void clearFields() {
        try {
            // Clear text fields and combo boxes
            txtBloodQty.clear();
            cmbHospital.getSelectionModel().clearSelection();
            cmbBloodGroup.getSelectionModel().clearSelection();
            cmbStatus.getSelectionModel().clearSelection();

            // Set the next request ID
            lblRequestID.setText(bloodRequestModel.getNextRequestId());

            // Set the current date to the date label
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblRequestDate.setText(currentDate.format(formatter));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset fields!").show();
        }
    }

}
