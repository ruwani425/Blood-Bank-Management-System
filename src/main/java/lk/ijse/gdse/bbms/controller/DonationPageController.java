package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.gdse.bbms.dto.tm.DonationTM;
import lk.ijse.gdse.bbms.model.CampaignModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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

    CampaignModel campaignModel = new CampaignModel();
    String checkupId;
    String bloodGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    public void setCheckUpId(String s,String bloodGroup) {
        this.checkupId=s;
        System.out.println(s);
        System.out.println(bloodGroup);
        this.bloodGroup=bloodGroup;
    }
}
