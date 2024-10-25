package com.parking.controller;

import com.parking.session.UserSession;
import com.parking.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HomeController extends NavigationController {

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label weatherConditionLabel;
    @FXML
    private Label weatherDateLabel;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label windLabel;
    @FXML
    private ImageView profileImageView; // ImageView for profile image

    private static final String API_KEY = "c38b56dde11f6eb66196d8f7cdc9790d";
    private static final String CITY = "Selangor,MY";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";

    private UserService userService = new UserService(); // To access user details

    public void initialize() {
        updateWeatherData();
        loadUserInfo();
        loadProfileImage(); // Load the user's profile image
    }

    // Load User Info (Full Name)
    private void loadUserInfo() {
        UserSession session = UserSession.getInstance();
        if (session != null) {
            fullNameLabel.setText(session.getFullName());
        }
    }

    // Load Profile Image
    private void loadProfileImage() {
        UserSession session = UserSession.getInstance();
        if (session != null) {
            String username = session.getUsername();
            String profileImagePath = userService.getProfileImagePath(username);

            if (profileImagePath != null && !profileImagePath.isEmpty()) {
                File imageFile = new File(profileImagePath);
                if (imageFile.exists()) {
                    Image profileImage = new Image(imageFile.toURI().toString());
                    profileImageView.setImage(profileImage); // Set the profile image in the ImageView
                }
            }
        }
    }

    @FXML
    private void refreshWeatherData() {
        updateWeatherData();
    }

    private void updateWeatherData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject json = new JSONObject(inline.toString());

                // Parse JSON data
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("main");
                String date = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
                double temperature = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");

                // Update UI labels
                weatherConditionLabel.setText(weather.toUpperCase());
                weatherDateLabel.setText(date);
                temperatureLabel.setText(String.format("%.1f°C", temperature));
                humidityLabel.setText(humidity + "%");
                windLabel.setText(String.format("%.1f m/s", windSpeed));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Weather Data Error");
            alert.setHeaderText("Unable to retrieve weather data");
            alert.setContentText("Please check your internet connection or try again later.");
            alert.showAndWait();
        }
    }

}
