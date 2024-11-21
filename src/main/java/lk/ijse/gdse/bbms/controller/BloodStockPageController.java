package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.dto.tm.BloodStockTM;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.BloodStockModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;


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
    private TableColumn<BloodIssueTM, JFXButton> colAction;

    @FXML
    private Label lblRequestID;

    @FXML
    private JFXButton btnIssue;

    private BloodRequestTM bloodRequestTM;

    @FXML
    private JFXButton btnBloodIssueReport;

    BloodStockModel bloodStockModel=new BloodStockModel();
    ObservableList<BloodIssueTM> bloodIssueTMS = FXCollections.observableArrayList();
    ArrayList<BloodIssueTM>issuedBlood=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getExpiredBlood();
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblBloodStock.setOnMouseClicked(this::handleRowClick);
    }

    public void setRequestID(BloodRequestTM bloodRequestTM) {
        this.bloodRequestTM = bloodRequestTM;
        lblRequestID.setText(bloodRequestTM.getRequestId());
        System.out.println(bloodRequestTM);
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

        colAction.setCellValueFactory(new PropertyValueFactory<>("button"));
        colIssueBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colIssueBloodId.setCellValueFactory(new PropertyValueFactory<>("bloodIssueID"));
        colIssueExpireDate.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        colIssueQty.setCellValueFactory(new PropertyValueFactory<>("bloodQty"));
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            BloodStockTM selectedItem = tblBloodStock.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                BloodIssueTM bloodIssueTM=new BloodIssueTM(selectedItem.getBloodID(),selectedItem.getBloodGroup(),selectedItem.getQty(),selectedItem.getExpiryDate(),null);
                bloodIssueTM.setBloodID(selectedItem.getBloodID());
                addIssueItem(bloodIssueTM);
            }
        }
    }

    private void addIssueItem(BloodIssueTM bloodIssueTM) {
        // Initialize button with action
        var button = new JFXButton("Delete");
        button.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;"); // Optional styling
        button.setOnAction(event -> {
            // Remove the item from both the table and the ObservableList
            bloodIssueTMS.remove(bloodIssueTM);
            tblBloodIssue.getItems().remove(bloodIssueTM);
            System.out.println("Deleted row with Blood Issue ID: " + bloodIssueTM.getBloodIssueID());
        });
        issuedBlood.add(bloodIssueTM);
        System.out.println(issuedBlood.toString());
        bloodIssueTM.setButton(button);

        // Add the item to the data source and the table
        bloodIssueTMS.add(bloodIssueTM);
        tblBloodIssue.getItems().add(bloodIssueTM);
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
    void btnIssueOnAction(ActionEvent event) throws SQLException {
            boolean isAdd=bloodStockModel.addBloodIssue(bloodRequestTM,issuedBlood);
            if (isAdd){
                refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "successfully saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to saved blood issue").show();
            }
    }

    @FXML
    public void btnViewIssueBloodReportOnAction(ActionEvent actionEvent) {
        try {
            // Establish a database connection
            Connection connection = DBConnection.getInstance().getConnection();

            // Parameters for the report (add if needed)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Blood Issue Report");

            // Compile the JRXML template to a JasperReport object
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/RecivedBloodDetails.jrxml"));

            // Fill the report with data and parameters
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Display the report in a viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            // Show an error message if the report fails to generate
            new Alert(Alert.AlertType.ERROR, "Failed to generate the report.").show();
        } catch (SQLException e) {
            // Show an error message if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database connection error.").show();
        }
    }

}
