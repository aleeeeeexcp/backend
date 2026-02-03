package com.app.management.config;

public class JwtInfo {

    private String userId;
    private String userName;
    private String role;

    public JwtInfo(String userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
