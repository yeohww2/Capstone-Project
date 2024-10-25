package com.parking.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

    @FXML
    private void goToHomePage(ActionEvent event) {
        loadScene(event, "Home.fxml");
    }

    @FXML
    private void goToParkingOverviewPage(ActionEvent event) {
        loadScene(event, "ParkingOverview.fxml");
    }

    @FXML
    private void goToNotificationPage(ActionEvent event) {
        loadScene(event, "Notification.fxml");
    }

    @FXML
    private void goToPaymentPage(ActionEvent event) {
        loadScene(event, "Payment.fxml");
    }

    @FXML
    private void goToSettingPage(ActionEvent event) {
        loadScene(event, "Setting.fxml");
    }

    protected void loadScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/" + fxmlFile));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set a new scene with the loaded FXML file
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
