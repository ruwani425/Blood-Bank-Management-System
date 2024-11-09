package lk.ijse.gdse.bbms.controller;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    @FXML
    private ImageView homeBtn,donorsBtn,hospitalBtn,campaignBtn,requestBtn,inventoryBtn,healthCheckupBtn,donationsBtn,historyBtn,btnEmployee;

    @FXML
    private AnchorPane homeAnchor,rootPane;

    @FXML
    void navigateToCampaignPage(MouseEvent event) {

    }

    @FXML
    void navigateToDonationsPage(MouseEvent event) {

    }

    @FXML
    void navigateToDonorPage(MouseEvent event) {
        navigateTo("/view/donorPage-view.fxml");
    }

    @FXML
    void navigateToHealthChekupPage(MouseEvent event) {
        navigateTo("/view/healthCheckUp-view.fxml");
    }

    @FXML
    void navigateToHistoryPage(MouseEvent event) {

    }

    @FXML
    void navigateToHomePage(MouseEvent event) {
        navigateTo("/view/homeStartPage-view.fxml");
    }

    @FXML
    void navigateToHospitalPage(MouseEvent event) {

    }

    @FXML
    void navigateToInventoryPage(MouseEvent event) {

    }

    @FXML
    void navigateToRequestPage(MouseEvent event) {

    }

    @FXML
    void navigateToEmployeePage(MouseEvent event) {
        navigateTo("/view/employeePage-view.fxml");
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
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        rootPane.setPrefWidth(screenWidth);
        rootPane.setPrefHeight(screenHeight);
        navigateTo("/view/homeStartPage-view.fxml");
    }
    @FXML
    void onMouseEnterdImage(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            icon.setEffect(glow);
        }
    }

    @FXML
    void onMouseExitedImage(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }
}
