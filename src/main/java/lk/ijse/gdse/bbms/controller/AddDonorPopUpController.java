package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import lk.ijse.gdse.bbms.model.DonorModel;


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
    private ComboBox txtDonorBloodGroup;

    @FXML
    private ComboBox txtDonorGender;

    @FXML
    private Label lblDonorId;

    @FXML
    void btnAddDonorOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteDonorOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateDonorOnAction(ActionEvent event) {

    }


    public void setDonorData(DonorModel donor) {
        lblDonorId.setText(donor.getDonorId());
        txtDonorName.setText(donor.getDonorName());
        txtDonorDob.setValue(donor.getDob().toLocalDate());
        txtDonorBloodGroup.setValue(donor.getBloodGroup());
        txtDonorEmail.setText(donor.getEmail());
        txtDonorAddress.setText(donor.getAddress());
        txtDonorNic.setText(donor.getNic());
        txtDonorGender.setValue(donor.getGender());
    }
}
