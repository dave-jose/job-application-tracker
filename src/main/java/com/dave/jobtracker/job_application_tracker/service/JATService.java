package com.dave.jobtracker.job_application_tracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.models.User;
import com.dave.jobtracker.job_application_tracker.repository.JARepository;
import com.dave.jobtracker.job_application_tracker.repository.UserRepository;

/*Job_ApplicationService
functions: Add application, delete application, view_application_list, sort/filter by date_applied, filter by date range, sort/filter by application status + interview stage
 */

@Service
public class JATService {

    private final JARepository repository;
    private final UserRepository userRepository;

    public JATService(JARepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public boolean isValidUser(User user) {
        return userRepository.existsByEmail(user.getUserEmail());
    }

    public JobApplication findApplication(User user, Integer id) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        return repository.findByApplicationIdAndUser(id, user) .orElseThrow();

    }

    // ADD APPLICATION - must check if status valid etc
    public JobApplication addApplication(User user, JobApplication application) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        if (application.getAppStatus() != ApplicationStatus.Interview && application.getInterviewStatus() != InterviewStatus.NA) {
            return null;
        } else {
            application.setUser(user);
            return repository.save(application);
        }

    }


    // UPDATE STATUSES 
    public JobApplication updateStatus(Integer id, User user, ApplicationStatus applicationStatus) { 

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }
        
        JobApplication application = repository.findByApplicationIdAndUser(id, user) .orElseThrow();

        application.setAppStatus(applicationStatus);
        return repository.save(application);

    }

    public JobApplication updateInterview(Integer id, User user, InterviewStatus interviewStatus) throws Exception { 

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        JobApplication application = repository.findByApplicationIdAndUser(id, user) .orElseThrow();

        application.setInterviewStatus(interviewStatus);
        return repository.save(application);

    }



    // DELETE APPLICATION 
    public void deleteApplication(Integer id, User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        repository.deleteByApplicationIdAndUser(id, user);
    }

    public void deleteAllApplications(User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        repository.deleteAllByUser(user);
    }

    // VIEW APPLICATION LIST
    public List<JobApplication> listAllApplications(User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        return repository.findAllByUser(user);
    }

    // SORT BY DATE APPLIED 
    public List<JobApplication> sortbyApplicationsDate(User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        return repository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "dateApplied"));
    }

    // FILTER BY DATE APPLIED
    public List<JobApplication> filterbyApplicationsDate(LocalDate date, User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        return repository.findByDateAppliedAndUser(date, user);
    }

    // FILTER BY DATE RANGE
    public List<JobApplication> filterbyApplicationsDateRange(LocalDate date1, LocalDate date2, User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        if (date1.isAfter(date2)) {
            return null;
        } else {
            return repository.findByUserAndDateAppliedBetweenOrderByDateAppliedDesc(user, date1, date2);
        }
    }

    // SORT/FILTER BY APPLICATION STATUS + OPTIONALLY INTERVIEW STAGE
    public List<JobApplication> filterbyApplicationStatus(ApplicationStatus status, User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }

        return repository.findByUserAndAppStatus(user, status);
    }

    public List<JobApplication> filterbyInterviewStatus(InterviewStatus status, User user) {

        if (isValidUser(user) == false) {
            throw new RuntimeException("User does not exist!");
        }
        
        return repository.findByUserAndIntStatus(user, status);
    }







    
}
