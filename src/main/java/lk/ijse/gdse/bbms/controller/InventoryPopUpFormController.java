package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InventoryPopUpFormController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private DatePicker datePikcerExpiry;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private Label lblInventoryID;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    @FXML
    void btnAddInventoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteInventoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateInventoryOnAction(ActionEvent event) {

    }

}
