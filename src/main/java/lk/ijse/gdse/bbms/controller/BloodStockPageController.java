package lk.ijse.gdse.bbms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
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
    private Button btnVerified;

    @FXML
    private Button btnExpired;

    BloodStockModel bloodStockModel=new BloodStockModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        // Fetch all BloodStock data from the model
        ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockModel.getAllBloodStocks("VERIFIED");
        ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

        // Map BloodStockDTO objects to BloodStockTM objects
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

        // Set the items to the TableView
        tblBloodStock.setItems(bloodStockTMS);
    }

    @FXML
    void btnIsExpiredOnAction(ActionEvent event) {
        try {
            // Fetch expired data directly from the database
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
    void btnIsVerifiedOnAction(ActionEvent event) {
        try {
            // Fetch verified data
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

                // Add only verified data
                if (bloodStockDTO.getStatus().equalsIgnoreCase("VERIFIED")) {
                    bloodStockTMS.add(bloodStockTM);
                }
            }

            tblBloodStock.setItems(bloodStockTMS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
