package com.dave.jobtracker.job_application_tracker.repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JARepository extends JpaRepository<JobApplication, Integer>{

    List<JobApplication> findAllByUser(User user);

    List<JobApplication> findAllByUser(User user, org.springframework.data.domain.Sort sort);

    Optional<JobApplication> findByApplicationIdAndUser(Integer id, User user);

    JobApplication deleteByApplicationIdAndUser(Integer id, User user);

    JobApplication deleteAllByUser(User user);

    List<JobApplication> findByDateAppliedAndUser(LocalDate dateApplied, User user);
    
    List<JobApplication> findByUserAndDateAppliedBetweenOrderByDateAppliedDesc(User user, LocalDate date1, LocalDate date2);

    List<JobApplication> findByUserAndAppStatus(User user, ApplicationStatus appStatus);

    List<JobApplication> findByUserAndIntStatus(User user, InterviewStatus intStatus);

    List<JobApplication> findByAppStatusAndIntStatus(ApplicationStatus appStatus, InterviewStatus intStatus);

}
