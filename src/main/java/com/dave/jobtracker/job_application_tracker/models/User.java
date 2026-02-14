package com.dave.jobtracker.job_application_tracker.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.security.*;

@Entity
@Table(name="dave_users")
public class User {

    @Id
    @Column(nullable=false, unique=true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> applications;

    public User() {

    }

    public User(String userEmail, String password) {
        this.email = userEmail;
        this.password = password;
        this.dateCreated = LocalDateTime.now();
    }

    public String getUserEmail() {
        return this.email;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUserPass(String password) {
        this.password = password;
    }





    
}
