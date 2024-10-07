package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeViewController {
    public JFXButton donorBtn;
    public AnchorPane homeAnchor;

    public void navigateToDonorPage(ActionEvent actionEvent) {
        navigateTo("./addDonor-view.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            homeAnchor.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            homeAnchor.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}

/*import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeViewController {

    @FXML
    private JFXButton donorBtn; // Assuming JFoenix button is used

    @FXML
    private AnchorPane homeAnchor; // The container where you will load the new FXML content

    // Method to navigate to the Donor Page
    @FXML
    public void navigateToDonorPage(ActionEvent actionEvent) {
        navigateTo("addDonor-view.fxml");
    }

    // Method to load the FXML page into homeAnchor
    public void navigateTo(String fxmlPath) {
        try {
            // Clear the current content in the homeAnchor
            homeAnchor.getChildren().clear();

            // Load the new FXML file into an AnchorPane
            AnchorPane load = FXMLLoader.load(getClass().getResource("/lk/ijse/gdse/bbms/view/" + fxmlPath));

            // Add the loaded FXML view to the homeAnchor
            homeAnchor.getChildren().add(load);

        } catch (IOException e) {
            // Show an error alert if the page fails to load
            new Alert(Alert.AlertType.ERROR, "Failed to load the page!").show();
        }
    }
}
*/