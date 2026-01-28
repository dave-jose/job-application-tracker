package com.dave.jobtracker.job_application_tracker.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobApplication { 

    // REQUIRED JOB APPLICATION FIELDS:
    
    private String jobTitle;
    private String companyName;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long applicationId;
    private LocalDate dateApplied;
    
    enum applicationStatus {
        Applied, 
        Interview, 
        Rejected, 
        Offer, 
        Withdrawn
    }

    private applicationStatus appStatus;

    enum interviewStatus {
        NA, 
        Early, 
        Mid, 
        Final
    }

    private interviewStatus intStatus;

    private String userEmail;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // CONSTRUCTOR JOB APPLICATION:

    public JobApplication(String jobTitle, String companyName, LocalDate dateApplied, applicationStatus appStatus, interviewStatus intStatus,
        String userEmail
    ) {

        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.dateApplied = dateApplied;
        this.appStatus = appStatus;
        this.intStatus = intStatus;
        this.userEmail = userEmail;
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }
    
    // JOB TITLE: GETTER + SETTER
    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobtitle) {
        this.jobTitle = jobtitle;
        this.dateUpdated = LocalDateTime.now();
    }

    // COMPANY NAME: GETTER + SETTER
    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        this.dateUpdated = LocalDateTime.now();
    }

    // DATE APPLIED: GETTER + SETTER
    public LocalDate getDateApplied() {
        return this.dateApplied;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
        this.dateUpdated = LocalDateTime.now();
    }

    // APP STATUS: GETTER + SETTER
    public applicationStatus getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(applicationStatus appStatus) {
        this.appStatus = appStatus;
        this.dateUpdated = LocalDateTime.now();
    }

    // INTERVIEW STATUS: GETTER + SETTER
    public interviewStatus getInterviewStatus() {
        return this.intStatus;
    }

    public void setInterviewStatus(interviewStatus intStatus) {
        this.intStatus = intStatus;
        this.dateUpdated = LocalDateTime.now();
    }










}