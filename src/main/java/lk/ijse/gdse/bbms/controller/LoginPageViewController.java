package lk.ijse.gdse.bbms.controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageViewController {
    @FXML
    private TextField userNameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        // Sample credentials for validation (you can replace this with actual logic)
        String validUsername = "admin";
        String validPassword = "1234";

        // Get the entered username and password from the text fields
        String enteredUsername = userNameTxt.getText();
        String enteredPassword = passwordTxt.getText();

        // Check if the entered username and password match the valid credentials
        if (validUsername.equals(enteredUsername) && validPassword.equals(enteredPassword)) {
            // Load the home view FXML if the credentials are correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homePage-view.fxml"));
            Parent homeView = loader.load();

            // Get the current stage (window) from the login button
            Stage stage = (Stage) loginBtn.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(homeView));

            // Optionally, set the title of the stage
            stage.setTitle("BBMS Home Page");

            // Show the stage
            stage.show();
        } else {
            // Show an error alert if the credentials are incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }
}
