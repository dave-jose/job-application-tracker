package com.dave.jobtracker.job_application_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.type.descriptor.java.IntegerPrimitiveArrayJavaType;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.dave.jobtracker.job_application_tracker.service.JATService;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;

@RestController
@RequestMapping("/api/jat")
public class JATController {
    
    // private final JATService service;

    // public JATController(JATService jatService) {
    //     this.service = jatService;
    // }
    
    // @GetMapping("/apps") // WORKS
    // public List<JobApplication> getAllApplications() {
    //     return service.sortbyApplicationsDate();
    // }

    // @PostMapping("/apps") // WORKS
    // public JobApplication addApplication(@RequestBody JobApplication app) {
    //     return service.addApplication(app);
    // }

    // @GetMapping("/apps/date/{dateApplied}") // WORKS
    // public List<JobApplication> getApplicationsByDate(@PathVariable LocalDate dateApplied) {
    //     return service.filterbyApplicationsDate(dateApplied);
    // }

    // @GetMapping("/apps/status/{appStatus}") // WORKS
    // public List<JobApplication> getApplicationsByAppStatus(@PathVariable ApplicationStatus appStatus) {
    //     return service.filterbyApplicationStatus(appStatus);
    // }

    // @GetMapping("/apps/interview/{intStatus}") // WORKS
    // public List<JobApplication> getApplicationsByIntStatus(@PathVariable InterviewStatus intStatus) {
    //     return service.filterbyInterviewStatus(intStatus);
    // }

    // @GetMapping("/apps/{id}") // WORKS
    // public JobApplication findApplication(@PathVariable Integer id) {
    //     return service.findApplication(id);
    // }

    // @DeleteMapping("/apps/{id}") // WORKS
    // public void deleteApplication(@PathVariable Integer id) {
    //     service.deleteApplication(id);
    // }

    // @PutMapping("/apps/ustatus/{id}") // WORKS
    // public JobApplication updateStatus(@RequestBody ApplicationStatus status, @PathVariable Integer id) {
    //     return service.updateStatus(id, status);
    // }

    
    // @PutMapping("/apps/uinterview/{id}") // WORKS
    // public JobApplication updateStatus(@RequestBody InterviewStatus status, @PathVariable Integer id) throws Exception {
    //     return service.updateInterview(id, status);
    // }



}
