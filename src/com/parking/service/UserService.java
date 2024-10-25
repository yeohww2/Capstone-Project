package com.parking.service;

import java.sql.*;

public class UserService {
    private static final String DATABASE_URL = "jdbc:sqlite:users.db";

    public UserService() {
        createTableIfNotExists();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
        }
        return conn;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL," +
                "fullName TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "icNumber TEXT NOT NULL," +  // Added IC number field
                "securityQuestion TEXT NOT NULL," +  // Added Security Question field
                "securityAnswer TEXT NOT NULL," +  // Added Security Answer field
                "contactNumber TEXT," +
                "carPlateNumber TEXT," +
                "profileImagePath TEXT" +
                ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        return false;
    }

    public String getFullName(String username) {
        return getField(username, "fullName");
    }

    public String getEmail(String username) {
        return getField(username, "email");
    }

    public String getContactNumber(String username) {
        return getField(username, "contactNumber");
    }

    public String getCarPlateNumber(String username) {
        return getField(username, "carPlateNumber");
    }

    public String getProfileImagePath(String username) {
        return getField(username, "profileImagePath");
    }

    private String getField(String username, String fieldName) {
        String sql = "SELECT " + fieldName + " FROM users WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(fieldName);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving field: " + e.getMessage());
        }
        return null;
    }

    public void updateUserDetails(String username, String contactNumber, String carPlateNumber) {
        String sql = "UPDATE users SET contactNumber = ?, carPlateNumber = ? WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contactNumber);
            pstmt.setString(2, carPlateNumber);
            pstmt.setString(3, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user details: " + e.getMessage());
        }
    }

    public void updateProfileImage(String username, String filePath) {
        String sql = "UPDATE users SET profileImagePath = ? WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, filePath);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating profile image: " + e.getMessage());
        }
    }

    public boolean isUserExists(String usernameOrEmail) {
        String sql = "SELECT COUNT(1) FROM users WHERE username = ? OR email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    public void registerUser(String fullName, String username, String email, String password, String icNumber, String securityQuestion, String securityAnswer) {
        String sql = "INSERT INTO users(fullName, username, email, password, icNumber, securityQuestion, securityAnswer) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, icNumber);
            pstmt.setString(6, securityQuestion);
            pstmt.setString(7, securityAnswer);
            pstmt.executeUpdate();
            System.out.println("User registered successfully: " + username);
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    // Only one updatePassword method
    public void updatePassword(String usernameOrEmail, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ? OR email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, usernameOrEmail);
            pstmt.setString(3, usernameOrEmail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

    // Methods to validate user details
    public boolean isCorrectFullName(String usernameOrEmail, String fullName) {
        String sql = "SELECT fullName FROM users WHERE username = ? OR email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("fullName").equals(fullName);
            }
        } catch (SQLException e) {
            System.out.println("Error checking full name: " + e.getMessage());
        }
        return false;
    }

    public boolean isCorrectIcNumber(String usernameOrEmail, String icNumber) {
        String sql = "SELECT icNumber FROM users WHERE username = ? OR email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedIcNumber = rs.getString("icNumber");
                System.out.println("Stored IC Number: " + storedIcNumber); // Debugging output
                return storedIcNumber != null && storedIcNumber.equals(icNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error checking IC number: " + e.getMessage());
        }
        return false;
    }

    public boolean isCorrectSecurityAnswer(String usernameOrEmail, String securityAnswer) {
        String sql = "SELECT securityAnswer FROM users WHERE username = ? OR email = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("securityAnswer").equals(securityAnswer);
            }
        } catch (SQLException e) {
            System.out.println("Error checking security answer: " + e.getMessage());
        }
        return false;
    }

}

