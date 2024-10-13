package lk.ijse.gdse.bbms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton donorsBtn;

    @FXML
    private JFXButton hospitalsBtn;

    @FXML
    private JFXButton bloodCampaignBtn;

    @FXML
    private JFXButton bloodStocksBtn;

    @FXML
    private JFXButton inventoryBtn;

    @FXML
    private JFXButton bloodRequestBtn;

    @FXML
    private JFXButton donationHistoryBtn;

    @FXML
    private AnchorPane homeAnchor;

    @FXML
    void navigateToBloodCampaignPage(ActionEvent event) {

    }

    @FXML
    void navigateToBloodRequestsPage(ActionEvent event) {

    }

    @FXML
    void navigateToBloodStockPage(ActionEvent event) {

    }

    @FXML
    void navigateToDonationHistoryPage(ActionEvent event) {

    }

    @FXML
    void navigateToDonorPage(ActionEvent event) {
        navigateTo("/view/donorPage-view.fxml");
    }

    @FXML
    void navigateToHomePage(ActionEvent event) {

    }

    @FXML
    void navigateToHospitalPage(ActionEvent event) {

    }

    @FXML
    void navigateToInventoryPage(ActionEvent event) {

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/homeStartPage-view.fxml");
    }
}
