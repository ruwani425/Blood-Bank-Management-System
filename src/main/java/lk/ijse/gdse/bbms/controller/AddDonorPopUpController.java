package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.gdse.bbms.common.BloodGroup;
import lk.ijse.gdse.bbms.common.Gender;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.model.DonorModel;

import java.sql.Date;
import java.sql.SQLException;


public class AddDonorPopUpController {
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

    @FXML
    void initialize() throws SQLException {
        lblDonorId.setText(model.getNextDonorId());
        populateDonorGender();
        populateDonorBloodGroup();
    }

    private void populateDonorGender() {
        txtDonorGender.getItems().addAll("Male", "Female");
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
    void btnDeleteDonorOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateDonorOnAction(ActionEvent event) {

    }

    public void setDonorData(String selectedDonor) throws SQLException {
        System.out.println(selectedDonor);
    }


//    public void setDonorData(DonorModel donor) {
//        lblDonorId.setText(donor.getDonorId());
//        txtDonorName.setText(donor.getDonorName());
//        txtDonorDob.setValue(donor.getDob().toLocalDate());
//        txtDonorBloodGroup.setValue(String.valueOf(donor.getBloodGroup()));
//        txtDonorEmail.setText(donor.getEmail());
//        txtDonorAddress.setText(donor.getAddress());
//        txtDonorNic.setText(donor.getNic());
//        txtDonorGender.setValue(String.valueOf(donor.getGender()));
//    }
}
