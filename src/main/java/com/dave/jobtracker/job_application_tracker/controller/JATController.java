package com.dave.jobtracker.job_application_tracker.controller;

import java.util.List;

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
    
    private final JATService service;

    public JATController(JATService jatService) {
        this.service = jatService;
    }
    
    @GetMapping("/apps")
    public List<JobApplication> getAllApplications() {
        return service.listAllApplications();
    }

    @PostMapping("/apps")
    public JobApplication addApplication(@RequestBody JobApplication app) {
        return service.addApplication(app);
    }

    @DeleteMapping("/apps/{id}")
    public void deleteApplication(@PathVariable Long id) {
        service.deleteApplication(id);
    }

    @PutMapping("/apps/{id}")
    public JobApplication updateStatus(@RequestBody ApplicationStatus status1, @RequestBody InterviewStatus status2, @PathVariable Long id) {
        return service.updateStatus(id, status1, status2);
    }



}
