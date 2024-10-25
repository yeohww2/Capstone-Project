package com.parking;

import com.parking.session.UserSession;
import com.parking.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;


public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLogin();
    }

    public static void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resource/Login.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(MainApp.class.getResource("/resource/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resource/Home.fxml"));
            AnchorPane pane = loader.load();
            HomeController controller = loader.getController();
            controller.initialize();

            Scene scene = new Scene(pane);
            scene.getStylesheets().add(MainApp.class.getResource("/resource/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showForgotPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resource/ForgotPassword.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(MainApp.class.getResource("/resource/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Forgot Password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/resource/Register.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene   (pane);
            scene.getStylesheets().add(MainApp.class.getResource("/resource/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        UserSession.clearSession();  // Clear the session when logging out
        showLogin();  // Return to the login page
    }

    public static void main(String[] args) {
        launch(args);
    }
}