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
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    @FXML
    private ImageView homeBtn;

    @FXML
    private ImageView donorsBtn;

    @FXML
    private ImageView hospitalBtn;

    @FXML
    private ImageView campaignBtn;

    @FXML
    private ImageView requestBtn;

    @FXML
    private ImageView inventoryBtn;

    @FXML
    private ImageView healthCheckupBtn;

    @FXML
    private ImageView donationsBtn;

    @FXML
    private ImageView historyBtn;

    @FXML
    private AnchorPane homeAnchor;

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

    }

    @FXML
    void navigateToHistoryPage(MouseEvent event) {

    }

    @FXML
    void navigateToHomePage(MouseEvent event) {

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

    public void onMouseEnterdImage(MouseEvent event) {
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

    public void onMouseExitedImage(MouseEvent event) {
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
