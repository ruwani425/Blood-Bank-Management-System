package lk.ijse.gdse.bbms.controller;

import javafx.animation.ScaleTransition;
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
import javafx.animation.TranslateTransition;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    @FXML
    private ImageView homeBtn,donorsBtn,hospitalBtn,campaignBtn,requestBtn,inventoryBtn,healthCheckupBtn,donationsBtn,historyBtn,btnEmployee;

    @FXML
    private AnchorPane homeAnchor,rootPane;

    HomePageViewController homePageViewController;

    @FXML
    void navigateToCampaignPage(MouseEvent event) {
        navigateTo("/view/campaignPage-view.fxml");
    }

    @FXML
    void navigateToDonationsPage(MouseEvent event) {
        navigateTo("/view/donationPage-view.fxml");
    }

    @FXML
    void navigateToDonorPage(MouseEvent event) {
        navigateTo("/view/donorPage-view.fxml");
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

//    public void navigateTo(String fxmlPath) {
//
//        try {
//            homeAnchor.getChildren().clear();
//            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
//            homeAnchor.getChildren().add(load);
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
//        }
//    }

    public void navigateToDonationsPageByButton(String s, String bloodGroup,String donorId){
        try {
            homeAnchor.getChildren().clear();

            // Create FXMLLoader instance and load the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/donationPage-view.fxml"));
            AnchorPane load = loader.load();

            // Get the HealthCheckupController from the loader
            DonationPageController controller = loader.getController();

            // Set initial position for up-to-down transition
            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            // Create the translate transition for up-to-down effect
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            // Now you can use the controller to access any methods or variables in HealthCheckupController
            controller.setDateFromHealthCheckUp(s,bloodGroup,donorId); // Example method call in HealthCheckupController

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            homeAnchor.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Set initial position for up-to-down transition
            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            // Create the translate transition for up-to-down effect
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
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

    @FXML
    public void navigateToHealthChekupPage(MouseEvent event) {
        try {
            homeAnchor.getChildren().clear();

            // Create FXMLLoader instance and load the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/healthCheckUp-view.fxml"));
            AnchorPane load = loader.load();

            // Get the HealthCheckupController from the loader
            HealthCheckUpPageController controller = loader.getController();

            // Set initial position for up-to-down transition
            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            // Create the translate transition for up-to-down effect
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            // Now you can use the controller to access any methods or variables in HealthCheckupController
            controller.setHomePageViewController(this); // Example method call in HealthCheckupController

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }
}