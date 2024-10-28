package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.gdse.bbms.common.BloodGroup;
import lk.ijse.gdse.bbms.common.Gender;
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

    DonorModel model = new DonorModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            new Alert(Alert.AlertType.INFORMATION, "Donor saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Donor...!").show();
        }
    }

    @FXML
    void btnDeleteDonorOnAction(ActionEvent event) throws SQLException {
        String donorId = lblDonorId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Donor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = model.deleteDonor(donorId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Donor deleted successfully...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Donor...!").show();
            }
        }

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
            new Alert(Alert.AlertType.INFORMATION, "Donor updated successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Donor...!").show();
        }
    }

    public void setDonorData(DonorTM donor) {
        lblDonorId.setText(donor.getDonorId());
        txtDonorName.setText(donor.getDonorName());
        txtDonorNic.setText(donor.getDonorNic());
        txtDonorAddress.setText(donor.getDonorAddress());
        txtDonorEmail.setText(donor.getDonorEmail());
        txtDonorBloodGroup.setValue(donor.getBloodGroup());
        txtDonorGender.setValue(donor.getGender());

        // Set the date for DatePicker
        if (donor.getDob() != null) { // Ensure the DOB is not null
            Date sqlDate = (Date) donor.getDob(); // Get the SQL Date
            LocalDate dob = sqlDate.toLocalDate(); // Convert to LocalDate
            txtDonorDob.setValue(dob); // Set the date in the DatePicker
        }
    }
}
