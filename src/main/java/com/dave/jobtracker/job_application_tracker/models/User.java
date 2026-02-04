package com.dave.jobtracker.job_application_tracker.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.security.*;

@Entity
public class User {

    @Id
    private String userEmail;
    
    private String password;
    private LocalDateTime dateCreated;

    public User() {

    }

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
        this.dateCreated = LocalDateTime.now();
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void setUserPass(String password) {
        this.password = password;
    }





    
}
