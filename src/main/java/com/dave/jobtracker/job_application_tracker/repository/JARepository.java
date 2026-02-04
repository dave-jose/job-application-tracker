package com.dave.jobtracker.job_application_tracker.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JARepository extends JpaRepository<JobApplication, Integer>{

    List<JobApplication> findByDateApplied(LocalDate dateApplied);
    
    List<JobApplication> findByDateAppliedBetweenOrderByDateAppliedDesc(LocalDate date1, LocalDate date2);

    List<JobApplication> findByAppStatus(ApplicationStatus appStatus);

    List<JobApplication> findByIntStatus(InterviewStatus intStatus);

    List<JobApplication> findByAppStatusAndIntStatus(ApplicationStatus appStatus, InterviewStatus intStatus);

}
