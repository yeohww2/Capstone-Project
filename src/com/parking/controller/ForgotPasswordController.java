package com.parking.controller;

import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private AnchorPane forgotPasswordPane;

    @FXML
    private TextField usernameEmailField;

    @FXML
    private TextField fullNameField;  // Added for full name input
    @FXML
    private TextField icNumberField;   // Added for IC number input
    @FXML
    private TextField securityAnswerField; // Added for security answer input

    private UserService userService = new UserService();

    @FXML
    private void handlePasswordReset() {
        String usernameOrEmail = usernameEmailField.getText();
        String fullName = fullNameField.getText();
        String icNumber = icNumberField.getText();
        String securityAnswer = securityAnswerField.getText();

        if (usernameOrEmail.isEmpty() || fullName.isEmpty() || icNumber.isEmpty() || securityAnswer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        // Check if the user exists
        if (!userService.isUserExists(usernameOrEmail)) {
            showAlert(Alert.AlertType.ERROR, "Error", "User does not exist.");
            return;
        }

        // Verify user details
        if (!userService.isCorrectFullName(usernameOrEmail, fullName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Full name does not match.");
            return;
        }

        if (!userService.isCorrectIcNumber(usernameOrEmail, icNumber)) {
            showAlert(Alert.AlertType.ERROR, "Error", "IC number does not match.");
            return;
        }

        if (!userService.isCorrectSecurityAnswer(usernameOrEmail, securityAnswer)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Security answer does not match.");
            return;
        }

        // After verifying the user's identity, navigate to the ResetPassword screen
        showResetPasswordScreen(usernameOrEmail);
    }

    private void showResetPasswordScreen(String usernameOrEmail) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/resetpassword.fxml"));
            AnchorPane resetPasswordPane = loader.load();
            ResetPasswordController controller = loader.getController();
            controller.setUsernameOrEmail(usernameOrEmail); // Pass the user's email/username

            Stage stage = (Stage) forgotPasswordPane.getScene().getWindow();
            stage.setScene(new Scene(resetPasswordPane));
            stage.setTitle("Reset Password");
        } catch (IOException e) {
            e.printStackTrace(); // Print error if loading fails
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the reset password page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/Login.fxml"));
            AnchorPane loginPane = loader.load();
            Stage stage = (Stage) forgotPasswordPane.getScene().getWindow();
            stage.setScene(new Scene(loginPane));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace(); // Print error if loading fails
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the login page.");
        }
    }
}
