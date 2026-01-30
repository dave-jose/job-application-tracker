package com.dave.jobtracker.job_application_tracker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.repository.JARepository;
import com.dave.jobtracker.job_application_tracker.service.JATService;

@SpringBootTest
class JobApplicationTrackerApplicationTests {

	@Autowired
	private JATService service; 

	@Autowired
	private JARepository repository;
	
	@Test
	void isAppAdded() {
		
		JobApplication app = new JobApplication("Choral Conductor", "University of Maryland", LocalDate.now(), ApplicationStatus.Applied, InterviewStatus.NA, "merlin@gmail.com");
		JobApplication storedApp = service.addApplication(app);

		Optional<JobApplication> foundApp = repository.findById(storedApp.getAppId());
		assertTrue(foundApp.isPresent());

	}

	@Test
	void contextLoads() {
	}



}
