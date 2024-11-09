package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HealthCheckUpPageController {

    @FXML
    private JFXButton showStatusBtn;

    @FXML
    private TextField checkStatusTxt;

    @FXML
    private JFXButton checkStatusBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton addHealthCheckBtn;

    @FXML
    private Label healthCheckupIdLbl;

    @FXML
    private Label donorIdLbl;

    @FXML
    private Label lastDonationDateLbl;

    @FXML
    private Label ageLbl;

    @FXML
    private Label weightLbl;

    @FXML
    private Label sugerLevelLbl;

    @FXML
    private Label bloodPresureLbl;

    @FXML
    private Label healthStatusLbl;

    @FXML
    private Label dateOfCheckUpLbl;

    @FXML
    void PopUpNewWindowCheckUp(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addNewHealthCheckUpPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}