package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.gdse.bbms.dto.tm.InventoryTM;

import java.sql.Date;

public class InventoryPageController {

    @FXML
    private TableView<InventoryTM> tblInventory;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryID;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryName;

    @FXML
    private TableColumn<InventoryTM, String> colStatus;

    @FXML
    private TableColumn<InventoryTM, Date> colExpiryDate;

    @FXML
    private TableColumn<InventoryTM, Integer> colQty;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private JFXButton btnAddInventory;

    @FXML
    private JFXButton btnAddSupplier;

    @FXML
    void navigateToSupplierPage(ActionEvent event) {

    }

    @FXML
    void popUpNewWindowAddInventory(ActionEvent event) {

    }

    @FXML
    void searchDataDonors(KeyEvent event) {

    }

}
