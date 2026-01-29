package com.dave.jobtracker.job_application_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dave.jobtracker.job_application_tracker.models.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JARepository extends JpaRepository<JobApplication, Long>{

}
