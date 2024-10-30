package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.DonorModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddDonorPopUpController implements Initializable {

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextArea txtDonorAddress;

    @FXML
    private DatePicker txtDonorDob;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TextField txtDonorEmail;

    @FXML
    private TextField txtDonorNic;

    @FXML
    private TextField txtDonorName;

    @FXML
    private ComboBox<String> txtDonorBloodGroup;

    @FXML
    private ComboBox<String> txtDonorGender;

    @FXML
    private Label lblDonorId;

    @FXML
    private Stage stage;

    DonorModel model = new DonorModel();
    DonorPageViewController donorPageViewController;

    public void setDonorPageViewController(DonorPageViewController donorPageViewController) {
        this.donorPageViewController = donorPageViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        try {
            lblDonorId.setText(model.getNextDonorId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        populateDonorGender();
        populateDonorBloodGroup();
    }

    private void populateDonorGender() {
        txtDonorGender.getItems().addAll("MALE","FEMALE","OTHER");
    }
    private void populateDonorBloodGroup() {
        txtDonorBloodGroup.getItems().addAll("A_POSITIVE","A_NEGATIVE","B_POSITIVE","B_NEGATIVE","AB_POSITIVE","AB_NEGATIVE","O_POSITIVE","O_NEGATIVE");
    }

    @FXML
    void btnAddDonorOnAction(ActionEvent event) throws SQLException {
        String name = txtDonorName.getText();
        String email = txtDonorEmail.getText();
        String nic = txtDonorNic.getText();
        String bloodGroup = txtDonorBloodGroup.getSelectionModel().getSelectedItem().toString();
        String gender = txtDonorGender.getSelectionModel().getSelectedItem().toString();
        Date dob = Date.valueOf(txtDonorDob.getValue().toString());
        String address=txtDonorAddress.getText();
        String id = lblDonorId.getText();

        DonorDTO donorDTO = new DonorDTO(id,name,nic,address,email,bloodGroup,gender,dob,null);
        boolean isSaved = model.addDonor(donorDTO);

        if (isSaved) {

            try {
                lblDonorId.setText(model.getNextDonorId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            donorPageViewController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Donor saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Donor...!").show();
        }
        clearFields();
    }

    @FXML
    void btnDeleteDonorOnAction(ActionEvent event) throws SQLException {
        String donorId = lblDonorId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Donor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = model.deleteDonor(donorId);

            if (isDeleted) {
                donorPageViewController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Donor deleted successfully...!").show();
                stage = (Stage) btnDelete.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Donor...!").show();
            }
        }
        clearFields();
    }

    @FXML
    void btnUpdateDonorOnAction(ActionEvent event) throws SQLException {
        String name = txtDonorName.getText();
        String email = txtDonorEmail.getText();
        String nic = txtDonorNic.getText();
        String bloodGroup = txtDonorBloodGroup.getSelectionModel().getSelectedItem().toString();
        String gender = txtDonorGender.getSelectionModel().getSelectedItem().toString();
        Date dob = Date.valueOf(txtDonorDob.getValue().toString());
        String address=txtDonorAddress.getText();
        String id = lblDonorId.getText();

        DonorDTO donorDTO = new DonorDTO(id,name,nic,address,email,bloodGroup,gender,dob,null);
        boolean isUpdate = model.updateDonor(donorDTO);

        if (isUpdate) {
            donorPageViewController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Donor updated successfully...!").show();
            stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Donor...!").show();
        }
        clearFields();
    }

    public void setDonorData(DonorTM donor) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        lblDonorId.setText(donor.getDonorId());
        txtDonorName.setText(donor.getDonorName());
        txtDonorNic.setText(donor.getDonorNic());
        txtDonorAddress.setText(donor.getDonorAddress());
        txtDonorEmail.setText(donor.getDonorEmail());
        txtDonorBloodGroup.setValue(donor.getBloodGroup());
        txtDonorGender.setValue(donor.getGender());

        if (donor.getDob() != null) {
            Date sqlDate = (Date) donor.getDob();
            LocalDate dob = sqlDate.toLocalDate();
            txtDonorDob.setValue(dob);
        }
    }

    @FXML
    private void clearFields() throws SQLException {
        txtDonorAddress.clear();
        txtDonorDob.setValue(null);
        txtDonorEmail.clear();
        txtDonorNic.clear();
        txtDonorName.clear();
        txtDonorBloodGroup.getSelectionModel().clearSelection();
        txtDonorGender.getSelectionModel().clearSelection();
        lblDonorId.setText(model.getNextDonorId());
    }

}
