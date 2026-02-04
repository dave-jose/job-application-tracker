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

/*Job_ApplicationService
functions: Add application, delete application, view_application_list, sort/filter by date_applied, filter by date range, sort/filter by application status + interview stage
 */

@Service
public class JATService {

    private final JARepository repository;

    public JATService(JARepository repository) {
        this.repository = repository;
    }

    public JobApplication findApplication(Integer id) {
        return repository.findById(id) .orElseThrow(() -> new RuntimeException("JobApplication not found"));

    }

    // ADD APPLICATION - must check if status valid etc
    public JobApplication addApplication(JobApplication application) {
        if (application.getAppStatus() != ApplicationStatus.Interview && application.getInterviewStatus() != InterviewStatus.NA) {
            return null;
        } else {
            return repository.save(application);
        }
    }


    // UPDATE STATUSES 
    public JobApplication updateStatus(Integer id, ApplicationStatus applicationStatus) { 
        JobApplication application = repository.findById(id) .orElseThrow(() -> new RuntimeException("JobApplication not found"));

        application.setAppStatus(applicationStatus);
        return repository.save(application);

    }

    public JobApplication updateInterview(Integer id, InterviewStatus interviewStatus) throws Exception { 
        JobApplication application = repository.findById(id) .orElseThrow(() -> new RuntimeException("JobApplication not found"));

        application.setInterviewStatus(interviewStatus);
        return repository.save(application);

    }



    // DELETE APPLICATION 
    public void deleteApplication(Integer id) {
        repository.deleteById(id);
    }

    public void deleteAllApplications() {
        repository.deleteAll();
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

    public List<JobApplication> filterbyInterviewStatus(InterviewStatus status) {
        return repository.findByIntStatus(status);
    }







    
}
