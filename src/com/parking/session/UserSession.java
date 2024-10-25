package com.parking.session;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String fullName;
    private String userId; // New field for user ID

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static void clearSession() {
        instance = null;
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return username != null && fullName != null && userId != null;
    }
}
