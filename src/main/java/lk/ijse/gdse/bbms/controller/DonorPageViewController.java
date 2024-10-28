package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.common.BloodGroup;
import lk.ijse.gdse.bbms.common.Gender;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.DonorModel;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonorPageViewController {

    public Button addDonorBtn;

    @FXML
    private TableView<DonorTM> tblDonors;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colGender;

    @FXML
    private TableColumn colBloodGroup;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colNic;

    private ArrayList<DonorTM> donorList;

    @FXML
    void initialize() {
        donorList = new ArrayList<>();
        setCellValueFactory();

        ObservableList<DonorTM> donorObList = FXCollections.observableArrayList();
        donorObList.addAll(donorList);
        tblDonors.setItems(donorObList);
        tblRowOnAction();
    }

    private void tblRowOnAction() {
        tblDonors.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected donor from the table
                DonorTM selectedDonor = tblDonors.getSelectionModel().getSelectedItem();
                if (selectedDonor != null) {
                 //   String firstColumnValue = selectedDonor.getFirstColumn(); // Replace with the actual getter method name for the first column value
                    System.out.println(selectedDonor);
                }
                if (selectedDonor != null) {
                    try {
                        // Load the FXML for the pop-up window
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addDonorPopUp-view.fxml"));
                        Parent root = fxmlLoader.load();

                        // Pass the selected donor to the pop-up controller
                        AddDonorPopUpController controller = fxmlLoader.getController();
                        controller.setDonorData(selectedDonor.getDonorId());  // Method to set data in the pop-up

                        // Create a new stage for the pop-up window
                        Stage stage = new Stage();
                        stage.setTitle("Add New Donor");
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));

                        // Optional: Specify window modality (e.g., make it a modal window)
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(((Node) event.getSource()).getScene().getWindow());  // Set owner window

                        // Show the new pop-up window
                        stage.showAndWait();  // Blocks interaction with other windows until this one is closed

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }

    @FXML
    void popUpNewWindowAddDonor(ActionEvent event) {
        try {
            // Load the FXML file for the pop-up window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addDonorPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the pop-up window
            Stage stage = new Stage();
            stage.setTitle("Add New Donor");  // Set title of the window
            stage.setResizable(false);
            stage.setScene(new Scene(root));  // Set the scene with the loaded FXML

            // Optional: Specify window modality (e.g., make it a modal window)
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());  // Set owner window

            // Show the new pop-up window
            stage.showAndWait();  // Blocks interaction with other windows until this one is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNewOnAction(ActionEvent actionEvent) {
    }
}
