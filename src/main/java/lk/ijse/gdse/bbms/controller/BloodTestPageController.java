package lk.ijse.gdse.bbms.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodTestTM;
import lk.ijse.gdse.bbms.model.BloodTestModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BloodTestPageController implements Initializable {

    @FXML
    private TableView<BloodTestTM> tblBloodTest;

    @FXML
    private TableColumn<BloodTestTM,String> colTestId;

    @FXML
    private TableColumn<BloodTestTM,String> colDonationID;

    @FXML
    private TableColumn<BloodTestTM, Date> coltestdate;

    @FXML
    private TableColumn<BloodTestTM,String> colResult;

    @FXML
    private TableColumn<BloodTestTM,Double> colHaemoglobin;

    @FXML
    private TableColumn<BloodTestTM,Date> colCollectedDate;

    @FXML
    private TableColumn<BloodTestTM, String> colSerialNum;

    @FXML
    private TableColumn<BloodTestTM,Double> colPlatelets;

    @FXML
    private TableColumn<BloodTestTM,Double> colRedBloodCells;

    @FXML
    private TableColumn<BloodTestTM,Double> colWhiteBloodCells;

    @FXML
    private Label lblTestId;

    @FXML
    private Label lblCollectedDate;

    @FXML
    private TextField txtHaemoglobin;

    @FXML
    private TextField txtSerialNum;

    @FXML
    private TextField txtPlatelets;

    @FXML
    private TextField txtRedBloodCells;

    @FXML
    private TextField txtWhiteBloodCells;

    @FXML
    private Label lblTestDate;

    @FXML
    private DatePicker datePikerExpiryDate;

    @FXML
    private Label lblBloodType;

    @FXML
    private ComboBox<String> cmbResult;

    @FXML
    private Button btnChooseFile;

    @FXML
    private Button pendingBtn;

    @FXML
    private Button finishedBtn;

    @FXML
    private Button BtnUpdate;

    BloodTestModel bloodTestModel = new BloodTestModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCellValueFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            setComboBoxValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add event listener for row selection
        tblBloodTest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    populateFields(newValue);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setComboBoxValues() {
        cmbResult.setItems(FXCollections.observableArrayList("PASS", "FAIL"));
    }

    private void populateFields(BloodTestTM selectedTest) throws SQLException {
        lblTestId.setText(selectedTest.getTestID());
        BloodTestDTO bloodTestDetails = bloodTestModel.getBloodTestDetailById(selectedTest.getTestID());
        lblCollectedDate.setText(String.valueOf(bloodTestDetails.getCollectedDate()));
        txtHaemoglobin.setText(String.valueOf(selectedTest.getHaemoglobin()));
        txtSerialNum.setText(selectedTest.getReportSerialNum());
        txtPlatelets.setText(String.valueOf(selectedTest.getPlatelets()));
        txtRedBloodCells.setText(String.valueOf(selectedTest.getRedBloodCells()));
        txtWhiteBloodCells.setText(String.valueOf(selectedTest.getWhiteBloodCells()));
        lblTestDate.setText(java.time.LocalDate.now().toString());
        lblBloodType.setText(bloodTestDetails.getBloodType());
    }

    private void setCellValueFactory() {
        colTestId.setCellValueFactory(new PropertyValueFactory<>("testID"));
        colDonationID.setCellValueFactory(new PropertyValueFactory<>("donationID"));
        coltestdate.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        colCollectedDate.setCellValueFactory(new PropertyValueFactory<>("collectedDate"));
        colSerialNum.setCellValueFactory(new PropertyValueFactory<>("reportSerialNum"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("testResult"));
        colHaemoglobin.setCellValueFactory(new PropertyValueFactory<>("haemoglobin"));
        colPlatelets.setCellValueFactory(new PropertyValueFactory<>("platelets"));
        colRedBloodCells.setCellValueFactory(new PropertyValueFactory<>("redBloodCells"));
        colWhiteBloodCells.setCellValueFactory(new PropertyValueFactory<>("whiteBloodCells"));
    }

    public void refreshTable() throws SQLException {
        // Fetch all BloodTest data from the model
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestModel.getAllBloodTests("PENDING");
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        // Map BloodTestDTO objects to BloodTestTM objects
        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        // Set the items to the TableView
        tblBloodTest.setItems(bloodTestTMS);
    }
    public void getByStatus(String result) throws SQLException {
        // Fetch all BloodTest data from the model
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestModel.getAllBloodTests(result);
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        // Map BloodTestDTO objects to BloodTestTM objects
        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        // Set the items to the TableView
        tblBloodTest.setItems(bloodTestTMS);
    }

    @FXML
    void btnChooseFileOnFile(ActionEvent event) {
        System.out.println("hh");
    }
    @FXML
    void btnFinishedOnAction(ActionEvent event) throws SQLException {
        getByStatus();
    }

    private void getByStatus() throws SQLException {
        // Fetch all BloodTest data from the model
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestModel.getAllBloodTests();
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        // Map BloodTestDTO objects to BloodTestTM objects
        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        // Set the items to the TableView
        tblBloodTest.setItems(bloodTestTMS);
    }

    @FXML
    void btnPendingOnAction(ActionEvent event) throws SQLException {
        getByStatus("PENDING");
    }
    @FXML
    void BtnUpdateBloodTestOnAction(ActionEvent event) throws SQLException {
        try {
            // Create BloodTestDTO with all attributes
            BloodTestDTO bloodTestDTO = new BloodTestDTO(
                    lblTestId.getText(), // TestID
                    null, // DonationID (not available in UI)
                    Date.valueOf(lblCollectedDate.getText()), // CollectedDate
                    Date.valueOf(datePikerExpiryDate.getValue()), // ExpiryDate
                    cmbResult.getValue(), // TestResult
                    Double.parseDouble(txtHaemoglobin.getText()), // Haemoglobin
                    Date.valueOf(lblTestDate.getText()), // TestDate
                    txtSerialNum.getText(), // ReportSerialNum
                    Float.parseFloat(txtPlatelets.getText()), // Platelets (modified to float)
                    Double.parseDouble(txtRedBloodCells.getText()), // RedBloodCells
                    Double.parseDouble(txtWhiteBloodCells.getText()), // WhiteBloodCells
                    null, // ReportImageUrl (optional)
                    lblBloodType.getText() // BloodGroup
            );

            // Call the update method in the model
            boolean isUpdate = bloodTestModel.updateBloodTest(bloodTestDTO);

            // Handle the result
            if (isUpdate) {
                refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Blood Test updated successfully...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Blood Test...!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating: " + e.getMessage()).show();
        }
    }

}
