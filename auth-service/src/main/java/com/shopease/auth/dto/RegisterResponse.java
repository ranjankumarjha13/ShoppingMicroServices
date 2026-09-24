package com.shopease.auth.dto;

public class RegisterResponse {

    private String username;
    private String email;
    private String role;

    public RegisterResponse(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}