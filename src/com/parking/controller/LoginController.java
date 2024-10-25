package com.parking.controller;

import com.parking.MainApp;
import com.parking.session.UserSession;
import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.authenticate(username, password)) {
            String fullName = userService.getFullName(username); // Retrieve the user's full name
            UserSession userSession = UserSession.getInstance();
            userSession.setUsername(username);  // Store username in session
            userSession.setFullName(fullName);  // Store full name in session
            MainApp.showHome();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleForgotPassword() {
        MainApp.showForgotPassword();
    }

    @FXML
    private void handleRegister() {
        MainApp.showRegister();
    }
}