package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodStockTM;
import lk.ijse.gdse.bbms.model.BloodStockModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BloodStockPageController implements Initializable {

    @FXML
    private TableView<BloodStockTM> tblBloodStock;

    @FXML
    private TableColumn<BloodStockTM,String> colBloodID;

    @FXML
    private TableColumn<BloodStockTM,String> colTestID;

    @FXML
    private TableColumn<BloodStockTM,String> colBloodGroup;

    @FXML
    private TableColumn<BloodStockTM,Integer> colQty;

    @FXML
    private TableColumn<BloodStockTM,Double> colHaemoglobin;

    @FXML
    private TableColumn<BloodStockTM,Float> colPlatelets;

    @FXML
    private TableColumn<BloodStockTM,Double> colRedBloodCells;

    @FXML
    private TableColumn<BloodStockTM,Double> colWhiteBloodCells;

    @FXML
    private TableColumn<BloodStockTM, Date> colExpiryDate;

    @FXML
    private TableColumn<BloodStockTM,String> colStatus;

    @FXML
    private JFXButton btnVerified;

    @FXML
    private JFXButton btnExpired;

    @FXML
    private TableView<BloodIssueTM> tblBloodIssue;

    @FXML
    private TableColumn<BloodIssueTM, String> colIssueBloodId;

    @FXML
    private TableColumn<BloodIssueTM, Date> colIssueExpireDate;

    @FXML
    private TableColumn<BloodIssueTM, String> colIssueBloodGroup;

    @FXML
    private TableColumn<BloodIssueTM, Double> colIssueQty;

    @FXML
    private Label lblRequestID;

    @FXML
    private JFXButton btnIssue;

    private String requestID;

    BloodStockModel bloodStockModel=new BloodStockModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getExpiredBlood();
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
        lblRequestID.setText(requestID);
        System.out.println(requestID);
    }

    private void setCellValueFactory() {
        colBloodID.setCellValueFactory(new PropertyValueFactory<>("bloodID"));
        colTestID.setCellValueFactory(new PropertyValueFactory<>("testID"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colHaemoglobin.setCellValueFactory(new PropertyValueFactory<>("haemoglobin"));
        colPlatelets.setCellValueFactory(new PropertyValueFactory<>("platelets"));
        colRedBloodCells.setCellValueFactory(new PropertyValueFactory<>("redBloodCells"));
        colWhiteBloodCells.setCellValueFactory(new PropertyValueFactory<>("whiteBloodCells"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void refreshTable() throws SQLException {
        ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockModel.getAllBloodStocks("VERIFIED");
        ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

        for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
            BloodStockTM bloodStockTM = new BloodStockTM(
                    bloodStockDTO.getBloodID(),
                    bloodStockDTO.getTestID(),
                    bloodStockDTO.getBloodGroup(),
                    bloodStockDTO.getQty(),
                    bloodStockDTO.getHaemoglobin(),
                    bloodStockDTO.getPlatelets(),
                    bloodStockDTO.getRedBloodCells(),
                    bloodStockDTO.getWhiteBloodCells(),
                    bloodStockDTO.getExpiryDate(),
                    bloodStockDTO.getStatus()
            );
            bloodStockTMS.add(bloodStockTM);
        }

        tblBloodStock.setItems(bloodStockTMS);
    }

    public void getExpiredBlood(){
        try {
            ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockModel.getExpiredBloodStocks();
            ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

            for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
                BloodStockTM bloodStockTM = new BloodStockTM(
                        bloodStockDTO.getBloodID(),
                        bloodStockDTO.getTestID(),
                        bloodStockDTO.getBloodGroup(),
                        bloodStockDTO.getQty(),
                        bloodStockDTO.getHaemoglobin(),
                        bloodStockDTO.getPlatelets(),
                        bloodStockDTO.getRedBloodCells(),
                        bloodStockDTO.getWhiteBloodCells(),
                        bloodStockDTO.getExpiryDate(),
                        bloodStockDTO.getStatus()
                );

                bloodStockTMS.add(bloodStockTM);
            }

            tblBloodStock.setItems(bloodStockTMS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnIsExpiredOnAction(ActionEvent event) {
      getExpiredBlood();
    }


    @FXML
    void btnIsVerifiedOnAction(ActionEvent event) {
        try {
            ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockModel.getAllBloodStocks("VERIFIED");
            ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

            for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
                BloodStockTM bloodStockTM = new BloodStockTM(
                        bloodStockDTO.getBloodID(),
                        bloodStockDTO.getTestID(),
                        bloodStockDTO.getBloodGroup(),
                        bloodStockDTO.getQty(),
                        bloodStockDTO.getHaemoglobin(),
                        bloodStockDTO.getPlatelets(),
                        bloodStockDTO.getRedBloodCells(),
                        bloodStockDTO.getWhiteBloodCells(),
                        bloodStockDTO.getExpiryDate(),
                        bloodStockDTO.getStatus()
                );

                if (bloodStockDTO.getStatus().equalsIgnoreCase("VERIFIED")) {
                    bloodStockTMS.add(bloodStockTM);
                }
            }

            tblBloodStock.setItems(bloodStockTMS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnIssueOnAction(ActionEvent event) {

    }
}
