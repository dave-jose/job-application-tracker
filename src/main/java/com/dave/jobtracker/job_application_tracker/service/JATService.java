package com.dave.jobtracker.job_application_tracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.repository.JARepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.None;

/*Job_ApplicationService
functions: Add application, delete application, view_application_list, sort/filter by date_applied, filter by date range, sort/filter by application status + optionally interview stage
 */

@Service
public class JATService {

    private final JARepository repository;

    public JATService(JARepository repository) {
        this.repository = repository;
    }

    // ADD APPLICATION - must check if status valid etc
    public JobApplication addApplication(JobApplication application) {
        if (application.getAppStatus() != ApplicationStatus.Interview && application.getInterviewStatus() != InterviewStatus.NA) {
            return null;
        } else {
            return repository.save(application);
        }
    }

    public JobApplication updateStatus(Long id, ApplicationStatus applicationStatus, InterviewStatus interviewStatus) { 
        JobApplication application = repository.findById(id) .orElseThrow(() -> new RuntimeException("JobApplication not found"));;

        application.setAppStatus(applicationStatus);
        application.setInterviewStatus(interviewStatus);
        return repository.save(application);

    }

    // DELETE APPLICATION 
    public void deleteApplication(Long id) {
        repository.deleteById(id);
    }

    // VIEW APPLICATION LIST
    public List<JobApplication> listAllApplications() {
        return repository.findAll();
    }

    // SORT BY DATE APPLIED 
    public List<JobApplication> sortbyApplicationsDate() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dateApplied"));
    }

    // FILTER BY DATE APPLIED
    public List<JobApplication> filterbyApplicationsDate(LocalDate date) {
        return repository.findByDateApplied(date);
    }

    // FILTER BY DATE RANGE
    public List<JobApplication> filterbyApplicationsDateRange(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            return null;
        } else {
            return repository.findByDateAppliedBetweenOrderByDateAppliedDesc(date1, date2);
        }
    }

    // SORT/FILTER BY APPLICATION STATUS + OPTIONALLY INTERVIEW STAGE
    public List<JobApplication> filterbyApplicationStatus(ApplicationStatus status) {
        return repository.findByAppStatus(status);
    }

    public List<JobApplication> filterbyApplicationStatusInterview(InterviewStatus status) {
        return repository.findByIntStatus(status);
    }

    public List<JobApplication> filterByStatuses(ApplicationStatus status1, InterviewStatus status2) {
        return repository.findByAppStatusAndIntStatus(status1, status2);
    }







    
}
