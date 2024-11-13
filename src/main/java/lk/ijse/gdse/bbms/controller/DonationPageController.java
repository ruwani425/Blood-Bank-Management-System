package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.dto.tm.DonationTM;
import lk.ijse.gdse.bbms.model.CampaignModel;
import lk.ijse.gdse.bbms.model.DonationModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DonationPageController implements Initializable {
    @FXML
    private ComboBox<String> cmbSelectCampaign;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<DonationTM> tblDonation;

    @FXML
    private TableColumn<DonationTM,String> colDonationId;

    @FXML
    private TableColumn<DonationTM,String> colBloodCampaignId;

    @FXML
    private TableColumn<DonationTM,String> colHealthCheckUpId;

    @FXML
    private TableColumn<DonationTM,String> colBloodGroup;

    @FXML
    private TableColumn<DonationTM,Integer> colQty;

    @FXML
    private TableColumn<DonationTM, Date> colDateOfDonation;

    @FXML
    private Label lblDonationId;

    private HealthCheckupDTO healthCheckupDTO;
    private DonationModel donationModel=new DonationModel();

    CampaignModel campaignModel = new CampaignModel();
    String checkupId;
    String bloodGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblDonationId.setText(donationModel.getNextDonationId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ArrayList<String> idList = campaignModel.findCampaignIds();
            cmbSelectCampaign.getItems().addAll(idList); // Add items to ComboBox
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddDonationOnAction(ActionEvent event) throws SQLException {
        System.out.println(checkupId);
        System.out.println(bloodGroup);
        String campaignId = cmbSelectCampaign.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        Date donationDate = Date.valueOf(LocalDate.now());

        healthCheckupDTO.setDonation(lblDonationId.getText(),campaignId,checkupId,bloodGroup,qty,donationDate);
        boolean isSaved = donationModel.addDonation(healthCheckupDTO);

        if (isSaved) {

            lblDonationId.setText(donationModel.getNextDonationId());
            new Alert(Alert.AlertType.INFORMATION, "Donation saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Donation...!").show();
        }
    }

    public void setCheckUpId(String s, String bloodGroup, HealthCheckupDTO healthCheckupDTO) {
        this.healthCheckupDTO=healthCheckupDTO;
        this.checkupId=s;
        this.bloodGroup=bloodGroup;
    }
}