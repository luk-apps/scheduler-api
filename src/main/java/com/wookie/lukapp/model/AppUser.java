package com.wookie.lukapp.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AppUser {

    private String id;

    private String username;
    private String password;

    public AppUser(String username) {
        this.username = username;
    }
    public AppUser() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
