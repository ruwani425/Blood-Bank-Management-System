package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.CampaignTM;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.CampaignModel;
import lk.ijse.gdse.bbms.model.DonorModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CampaignPopUpWindowController implements Initializable {
    @FXML
    private JFXRadioButton btnRadioActive;

    @FXML
    private JFXRadioButton btnRadioInactive;

    @FXML
    private JFXRadioButton btnRadioPending;

    @FXML
    private JFXRadioButton btnRadioCompleted;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteCampaignBtn;

    @FXML
    private JFXButton addCampaignBtn;

    @FXML
    private Label lblCampaignId;

    @FXML
    private JFXTextField txtCampaignName;

    @FXML
    private JFXTextField txtCampaignAddress;

    @FXML
    private DatePicker datePikerStartDate;

    @FXML
    private DatePicker datePikerEndDate;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private Label headerLabel;

    @FXML
    private Stage stage;

    CampaignModel model = new CampaignModel();
    CampaignPageController campaignPageController;
    CampaignDTO dto=new CampaignDTO();

    public void setCampainPageController(CampaignPageController campaignPageController) {
        this.campaignPageController = campaignPageController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCampaignBtn.setDisable(false);
        deleteCampaignBtn.setDisable(true);
        updateBtn.setDisable(true);
        try {
            lblCampaignId.setText(model.getNextCampaignId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        populateCampaignStatus();
    }

    private void populateCampaignStatus() {
        // Initialize the ToggleGroup
        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);

        // Fetch the campaign status from the database or set a default if needed
        String campaignStatus = dto.getStatus(); // Method to retrieve status from DB

        // Check if campaignStatus is null
        if (campaignStatus == null) {
            campaignStatus = "PENDING"; // Set default status if null
        }

        // Set the selected radio button based on the retrieved status
        switch (campaignStatus) {
            case "ACTIVE":
                btnRadioActive.setSelected(true);
                break;
            case "INACTIVE":
                btnRadioInactive.setSelected(true);
                break;
            case "PENDING":
                btnRadioPending.setSelected(true);
                break;
            case "COMPLETED":
                btnRadioCompleted.setSelected(true);
                break;
            default:
                // Optional: handle cases where the status is unexpected
                btnRadioPending.setSelected(true); // Set a default if necessary
                break;
        }
    }



    @FXML
    void addCampaignOnAction(ActionEvent event) throws SQLException {
        String CampId = lblCampaignId.getText();
        String name = txtCampaignName.getText();
        String address = txtCampaignAddress.getText();

        Date startDate = Date.valueOf(datePikerStartDate.getValue().toString());
        Date endDate =  Date.valueOf(datePikerEndDate.getValue().toString());

        // Initialize the ToggleGroup for the radio buttons
        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);


        // Get status from selected radio button
        String status = null;
        if (btnRadioActive.isSelected()) {
            status = "ACTIVE";
        } else if (btnRadioInactive.isSelected()) {
            status = "INACTIVE";
        } else if (btnRadioPending.isSelected()) {
            status = "PENDING";
        } else if (btnRadioCompleted.isSelected()) {
            status = "COMPLETED";
        }

        int collectedUnits=0;

        CampaignDTO campaignDTO = new CampaignDTO(CampId,name,address,startDate,endDate,status,collectedUnits);
        boolean isSaved = model.addCampaign(campaignDTO);

        if (isSaved) {

            try {
                lblCampaignId.setText(model.getNextCampaignId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            campaignPageController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Campaign saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Campaign...!").show();
        }
        clearFields();
    }

    @FXML
    private void clearFields() throws SQLException {
        txtCampaignAddress.clear();
        txtCampaignName.clear();
        datePikerStartDate.setValue(null);
        datePikerEndDate.setValue(null);

        // Clear the selected radio button in the ToggleGroup
        ToggleGroup statusGroup = btnRadioActive.getToggleGroup();
        statusGroup.selectToggle(null); // Deselects any selected radio button
        lblCampaignId.setText(model.getNextCampaignId());
    }

    @FXML
    void deleteCampaignOnAction(ActionEvent event) throws SQLException {
        String campaignId = lblCampaignId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Campaign?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = model.deleteCampaign(campaignId);

            if (isDeleted) {
                campaignPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Campaign deleted successfully...!").show();
                stage = (Stage) deleteCampaignBtn.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Campaign...!").show();
            }
        }
        clearFields();
    }

    @FXML
    void updateCampaignOnAction(ActionEvent event) throws SQLException {
        String id = lblCampaignId.getText();
        String name = txtCampaignName.getText();
        String address = txtCampaignAddress.getText();

        Date startDate = Date.valueOf(datePikerStartDate.getValue().toString());
        Date endDate =  Date.valueOf(datePikerEndDate.getValue().toString());

        // Initialize the ToggleGroup for the radio buttons
        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);


        // Get status from selected radio button
        String status = null;
        if (btnRadioActive.isSelected()) {
            status = "ACTIVE";
        } else if (btnRadioInactive.isSelected()) {
            status = "INACTIVE";
        } else if (btnRadioPending.isSelected()) {
            status = "PENDING";
        } else if (btnRadioCompleted.isSelected()) {
            status = "COMPLETED";
        }

        CampaignDTO campaignDTO = new CampaignDTO(id,name,address,startDate,endDate,status,0);
        boolean isSaved = model.updateCampaign(campaignDTO);

        if (isSaved) {

            try {
                lblCampaignId.setText(model.getNextCampaignId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            campaignPageController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Campaign updated successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Campaign...!").show();
        }
        clearFields();
    }
    public void setCampaignData(CampaignTM CampaignTM) {
        addCampaignBtn.setDisable(true);
        updateBtn.setDisable(false);
        deleteCampaignBtn.setDisable(false);

        lblCampaignId.setText(CampaignTM.getBlood_campaign_id());
        txtCampaignName.setText(CampaignTM.getCampaign_name());
        txtCampaignAddress.setText(CampaignTM.getAddress());

        // Set startDate and endDate in date pickers
        if (CampaignTM.getStartDate() != null) {
            LocalDate startDate = CampaignTM.getStartDate().toLocalDate();
            datePikerStartDate.setValue(startDate);
        }
        if (CampaignTM.getEndDate() != null) {
            LocalDate endDate = CampaignTM.getEndDate().toLocalDate();
            datePikerEndDate.setValue(endDate);
        }
        // Set status radio button based on campaign status
        String status = CampaignTM.getStatus();
        if (status != null) {
            ToggleGroup statusGroup = btnRadioActive.getToggleGroup();
            switch (status) {
                case "ACTIVE":
                    statusGroup.selectToggle(btnRadioActive);
                    break;
                case "INACTIVE":
                    statusGroup.selectToggle(btnRadioInactive);
                    break;
                case "PENDING":
                    statusGroup.selectToggle(btnRadioPending);
                    break;
                case "COMPLETED":
                    statusGroup.selectToggle(btnRadioCompleted);
                    break;
                default:
                    statusGroup.selectToggle(null); // No selection if status is unrecognized
                    break;
            }
        }
    }
    @FXML
    void btnCloseOnAction(ActionEvent event) {
        stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void setHeaderText(String text) {
        headerLabel.setText(text);
    }
}