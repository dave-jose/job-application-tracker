package com.dave.jobtracker.job_application_tracker.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class JobApplication { 

    // REQUIRED JOB APPLICATION FIELDS:
    
    private String jobTitle;
    private String companyName;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long applicationId;
    private LocalDate dateApplied;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus appStatus;

    @Enumerated(EnumType.STRING)
    private InterviewStatus intStatus;

    private String userEmail;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // CONSTRUCTOR JOB APPLICATION:

    public JobApplication(String jobTitle, String companyName, LocalDate dateApplied, ApplicationStatus appStatus, InterviewStatus intStatus,
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
    public ApplicationStatus getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(ApplicationStatus appStatus) {
        this.appStatus = appStatus;
        this.dateUpdated = LocalDateTime.now();
    }

    // INTERVIEW STATUS: GETTER + SETTER
    public InterviewStatus getInterviewStatus() {
        return this.intStatus;
    }

    public void setInterviewStatus(InterviewStatus intStatus) {
        this.intStatus = intStatus;
        this.dateUpdated = LocalDateTime.now();
    }





}