package com.parking.controller;

import com.parking.MainApp;
import com.parking.session.UserSession;
import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;

public class SettingController extends NavigationController {

    @FXML
    private ImageView profileImageView;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label usernameLabel;  // Update the ID in FXML if needed
    @FXML
    private Label emailLabel;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField carPlateNumberField;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button logoutButton;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        loadUserDetails();
    }

    private void loadUserDetails() {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();

        fullNameLabel.setText(userService.getFullName(username));
        usernameLabel.setText(username);  // Ensure it sets the username directly
        emailLabel.setText(userService.getEmail(username));
        contactNumberField.setText(userService.getContactNumber(username));
        carPlateNumberField.setText(userService.getCarPlateNumber(username));

        String profileImagePath = userService.getProfileImagePath(username);
        if (profileImagePath != null) {
            profileImageView.setImage(new Image(new File(profileImagePath).toURI().toString()));
        }
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(profileImageView.getScene().getWindow());
        if (file != null) {
            String filePath = file.getAbsolutePath();
            UserSession userSession = UserSession.getInstance();
            userService.updateProfileImage(userSession.getUsername(), filePath);
            profileImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void saveSettings() {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        String contactNumber = contactNumberField.getText();
        String carPlateNumber = carPlateNumberField.getText();

        userService.updateUserDetails(username, contactNumber, carPlateNumber);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Changes saved successfully.");
    }

    @FXML
    private void handleLogout() {
        UserSession.clearSession();
        MainApp.logout();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}