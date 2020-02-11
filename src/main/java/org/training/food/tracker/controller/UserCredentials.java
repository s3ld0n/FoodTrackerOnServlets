package org.training.food.tracker.controller;

public class UserCredentials {
    private String username;
    private String role;

    public UserCredentials(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override public String toString() {
        return "UserCredentials{" + "username='" + username + '\'' + ", role='" + role + '\'' + '}';
    }
}
