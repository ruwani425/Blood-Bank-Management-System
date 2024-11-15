package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXButton;
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
    private TextField txtQty;

    @FXML
    private JFXButton btnAdd;

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

    @FXML
    private Label lblBloodGroup;

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
        String campaignId = cmbSelectCampaign.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        Date donationDate = Date.valueOf(LocalDate.now());

        System.out.println(campaignId);
        System.out.println(checkupId);

        boolean isSaved = donationModel.addDonation(new DonationDTO(
                lblDonationId.getText(),
                campaignId,
                checkupId,
                bloodGroup,
                qty,
                donationDate
        ));

        if (isSaved) {
            lblDonationId.setText(donationModel.getNextDonationId());
            new Alert(Alert.AlertType.INFORMATION, "Donation saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save donation.").show();
        }
    }


    // Define the setCheckUpId method here
    public void setDateFromHealthCheckUp(String s, String bloodGroup) {
        System.out.printf("HealthCheckupDTO initialized in DonationPageController: %s, %s%n", s, bloodGroup);
        this.checkupId = s;
        this.bloodGroup = bloodGroup;
        System.out.println("HealthCheckupDTO initialized in DonationPageController: ");
    }
}