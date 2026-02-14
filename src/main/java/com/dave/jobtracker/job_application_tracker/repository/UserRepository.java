package com.dave.jobtracker.job_application_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dave.jobtracker.job_application_tracker.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findByEmail(String userEmail);

    public boolean existsByEmail(String userEmail);
    
    
}
