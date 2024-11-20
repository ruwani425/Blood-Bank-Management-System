package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.gdse.bbms.dto.tm.SupplierTM;

public class SupplierPageController {

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierID;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierAddress;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierEmail;

    @FXML
    private TableColumn<SupplierTM, String> colDescription;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private JFXButton btnAddSupplier;

    @FXML
    void popUpWindowAddSupplier(ActionEvent event) {

    }

    @FXML
    void searchDataDonors(KeyEvent event) {

    }

}
