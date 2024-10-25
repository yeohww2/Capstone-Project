package com.parking.controller;

import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private AnchorPane resetPasswordPane;

    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private String usernameOrEmail; // To store the user's email or username

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    @FXML
    private void handleResetPassword() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in both password fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        // Update the password in the database
        UserService userService = new UserService();
        userService.updatePassword(usernameOrEmail, newPassword); // Call to update password
        showAlert(Alert.AlertType.INFORMATION, "Success", "Password has been reset successfully!");
        goToLogin();
    }

    @FXML
    private void goToLogin() {
        if (resetPasswordPane == null) {
            System.out.println("resetPasswordPane is null"); // Debugging output
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/Login.fxml"));
            AnchorPane loginPane = loader.load();
            Stage stage = (Stage) resetPasswordPane.getScene().getWindow();
            stage.setScene(new Scene(loginPane));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace(); // Print error if loading fails
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the login page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
