package lk.ijse.gdse.bbms.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label lblResult;

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
            throw new RuntimeException(e);
        }
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
        getByStatus("FINISH");
    }

    @FXML
    void btnPendingOnAction(ActionEvent event) throws SQLException {
        getByStatus("PENDING");
    }
}
