package com.example.crimelocator;

public class LoggedInUserDetails {

    String Username = "Hello";
    String Email = "traequ";
    String Number = "";

    LoggedInUserDetails(String username, String email){
        this.Username = username;
        this.Email = email;
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
