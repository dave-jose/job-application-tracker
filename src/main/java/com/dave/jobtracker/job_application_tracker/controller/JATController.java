package com.dave.jobtracker.job_application_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.type.descriptor.java.IntegerPrimitiveArrayJavaType;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dave.jobtracker.job_application_tracker.service.JATService;
import com.dave.jobtracker.job_application_tracker.service.UserService;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.models.User;
import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/jat")
public class JATController {
    
    private final JATService service;
    private final UserService userService;

    public JATController(JATService jatService, UserService userService) {
        this.service = jatService;
        this.userService = userService;
    }

    @PostMapping("/create") // works
    public ResponseEntity<String> createAccount(@RequestBody User user) throws Exception {

        try {
            userService.registerUser(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("Created user");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login") // works
    public ResponseEntity<String> logIn(@RequestBody User user, HttpSession session) throws Exception {

        try {
            User u = userService.login(user.getEmail(), user.getPassword());
            session.setAttribute("user", u);
            return ResponseEntity.status(HttpStatus.OK).body("Logged in");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        
    }

    @PostMapping("/logout") // works
    public ResponseEntity<String> logOut(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Logged Out");
    }

    private User getUserLoggedIn(HttpSession session) throws ResponseStatusException{
        
        User u = (User) session.getAttribute("user");
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User isn't logged in!");
        }
        return u;

    }
    
    @GetMapping("/apps") // works
    public ResponseEntity<List<JobApplication>> getAllApplications(HttpSession session) {
        User u = getUserLoggedIn(session);
        return ResponseEntity.status(HttpStatus.OK).body(service.sortbyApplicationsDate(u));
    }

    @PostMapping("/apps") // works
    public JobApplication addApplication(@RequestBody JobApplication app, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.addApplication(u, app);
    }

    @GetMapping("/apps/date/{dateApplied}") // works
    public List<JobApplication> getApplicationsByDate(@PathVariable LocalDate dateApplied, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.filterbyApplicationsDate(dateApplied, u);
    }

    @GetMapping("/apps/date/{date1}/{date2}") 
    public List<JobApplication> getApplicationsByDateRange(@PathVariable LocalDate date1, @PathVariable LocalDate date2, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.filterbyApplicationsDateRange(date1, date2, u);
    }

    @GetMapping("/apps/status/{appStatus}") // works
    public List<JobApplication> getApplicationsByAppStatus(@PathVariable ApplicationStatus appStatus, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.filterbyApplicationStatus(appStatus, u);
    }

    @GetMapping("/apps/interview/{intStatus}") // works
    public List<JobApplication> getApplicationsByIntStatus(@PathVariable InterviewStatus intStatus, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.filterbyInterviewStatus(intStatus, u);
    }

    @GetMapping("/apps/{id}") // works
    public JobApplication findApplication(@PathVariable Integer id, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.findApplication(u, id);
    }

    @Transactional
    @DeleteMapping("/apps/{id}") // works
    public void deleteApplication(@PathVariable Integer id, HttpSession session) {
        User u = getUserLoggedIn(session);
        service.deleteApplication(id, u);
    }

    @Transactional
    @PutMapping("/apps/ustatus/{id}") // works
    public JobApplication updateStatus(@RequestBody ApplicationStatus status, @PathVariable Integer id, HttpSession session) {
        User u = getUserLoggedIn(session);
        return service.updateStatus(id, u, status);
    }

    @Transactional
    @PutMapping("/apps/uinterview/{id}") // works
    public JobApplication updateStatus(@RequestBody InterviewStatus status, @PathVariable Integer id, HttpSession session) throws Exception {
        User u = getUserLoggedIn(session);
        return service.updateInterview(id, u, status);
    }



}
