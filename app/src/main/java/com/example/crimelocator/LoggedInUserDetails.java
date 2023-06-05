package com.example.crimelocator;

public class LoggedInUserDetails {

    String Username = "Hello";
    String Email = "traequ";

    public LoggedInUserDetails(String username, String email) {
        Username = username;
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
