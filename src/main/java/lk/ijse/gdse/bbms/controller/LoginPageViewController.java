package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageViewController implements Initializable {
    @FXML
    private TextField userNameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        rootPane.setPrefWidth(screenWidth);
        rootPane.setPrefHeight(screenHeight);
    }

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        String validUsername = "admin";
        String validPassword = "1234";

        String enteredUsername = userNameTxt.getText();
        String enteredPassword = passwordTxt.getText();

        if (validUsername.equals(enteredUsername) && validPassword.equals(enteredPassword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage-view.fxml"));
            Parent homeView = loader.load();

            Stage stage = (Stage) loginBtn.getScene().getWindow();

            stage.setScene(new Scene(homeView));

            stage.setTitle("BBMS Home Page");

            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }
}
