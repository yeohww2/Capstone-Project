package com.parking.controller;

import com.parking.MainApp;
import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField icNumberField;  // Added IC Number field
    @FXML
    private TextField securityAnswerField;  // Added Security Answer field

    private UserService userService = new UserService();

    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String icNumber = icNumberField.getText();  // Get IC Number
        String securityAnswer = securityAnswerField.getText();  // Get Security Answer

        // Check for empty fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || icNumber.isEmpty() || securityAnswer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "All fields are required.");
            return;
        }

        // Check minimum character requirement for full name
        if (fullName.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Full name must be at least 3 characters long.");
            return;
        }

        // Check minimum character requirement for username
        if (username.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Username must be at least 8 characters long.");
            return;
        }

        // Check for valid email format
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Invalid email format.");
            return;
        }

        // Check if the password matches the confirmation
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match.");
            return;
        }

        // Check if the username already exists
        if (userService.isUserExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists. Please choose another.");
            return;
        }

        // Register the user
        userService.registerUser(fullName, username, email, password, icNumber, "Who is your favorite lecturer?", securityAnswer);
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered!");
        goToLogin();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    @FXML
    private void goToLogin() {
        MainApp.showLogin();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
