package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.dto.tm.SupplierTM;
import lk.ijse.gdse.bbms.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {

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

    private final SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblSupplier.setOnMouseClicked(this::handleRowClick);
    }

    private void setCellValueFactory() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void refreshTable() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOs = supplierModel.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMs = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOs) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getSupplierName(),
                    supplierDTO.getAddress(),
                    supplierDTO.getEmail(),
                    supplierDTO.getDescription()
            );
            supplierTMs.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMs);
    }


    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double click
            SupplierTM selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                openEditSupplierWindow(selectedSupplier);
            }
        }
    }

    private void openEditSupplierWindow(SupplierTM selectedSupplier) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplierPopUpForm-view.fxml"));
            Parent root = fxmlLoader.load();

            SupplierPopUpController controller = fxmlLoader.getController();
            controller.setSupplierData(selectedSupplier);
            controller.setSupplierPageController(this); // Pass the instance

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblSupplier.getScene().getWindow());
            stage.showAndWait();

            // Refresh table (redundant as controller will do this on successful update)
            refreshTable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void popUpWindowAddSupplier(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplierPopUpForm-view.fxml"));
            Parent root = fxmlLoader.load();

            SupplierPopUpController controller = fxmlLoader.getController();
            controller.setSupplierPageController(this); // Pass the instance

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();

            // Refresh table (redundant as controller will do this on successful update)
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void searchDataSuppliers(KeyEvent event) {
        FilteredList<SupplierTM> filteredData = new FilteredList<>(tblSupplier.getItems(), b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplierTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();
                return supplierTM.getSupplierId().toLowerCase().contains(searchKeyword) ||
                        supplierTM.getSupplierName().toLowerCase().contains(searchKeyword) ||
                        supplierTM.getAddress().toLowerCase().contains(searchKeyword) ||
                        supplierTM.getEmail().toLowerCase().contains(searchKeyword) ||
                        supplierTM.getDescription().toLowerCase().contains(searchKeyword);
            });
        });

        tblSupplier.setItems(filteredData);
    }
}
