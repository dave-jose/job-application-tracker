package com.dave.jobtracker.job_application_tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.graph.spi.AppliedGraph;
import org.hibernate.type.TrueFalseConverter;
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

	int repoSize = 0;


	/* 
	   Tests if job application service is populated with x amount of applications and if applications that do
	   not meet valid criteria are rejected.
	*/

	@Test
	void populateJobApplications() {

		ApplicationStatus[] statuses = ApplicationStatus.values(); // {ApplicationStatus.Applied, ApplicationStatus.Offer, ApplicationStatus.Rejected, ApplicationStatus.Withdrawn, ApplicationStatus.Interview};
		InterviewStatus[] interviews = InterviewStatus.values(); // {InterviewStatus.Early, InterviewStatus.Mid, InterviewStatus.Final, InterviewStatus.NA};

		int rejectedJobApps = 0;

		for (int i=1; i< 101; i++) {
			
			String jobTitle = "Job" + i;
			String companyName = "Company" + i;
			LocalDate dateApplied = LocalDate.now().minusDays(i);
			ApplicationStatus applicationStatus = statuses[ (int) (Math.random() * statuses.length)];
			InterviewStatus interviewStatus = interviews[ (int) (Math.random() * interviews.length)];
			String email = "user" + i + "@email.com";

			if (applicationStatus != ApplicationStatus.Interview) {
				if (interviewStatus != InterviewStatus.NA) {
					rejectedJobApps+=1;
				}
			}

			JobApplication app = new JobApplication(jobTitle, companyName, dateApplied, applicationStatus, interviewStatus, email);

			service.addApplication(app);
		}

		repoSize = 100 - rejectedJobApps;

		assertTrue(service.listAllApplications().size() == repoSize);

	}

	@Test
	void singleAppDeletion() {
		
		JobApplication a = service.findApplication(1);

		service.deleteApplication(1);
		assertTrue(service.listAllApplications().size() == repoSize - 1);
		RuntimeException r = assertThrows(RuntimeException.class, () -> service.findApplication(1));

		assertEquals("JobApplication not found", r.getMessage());
		repoSize-=1;
		
		
	}

	@Test
	void isAppsRemoved() {

		service.deleteAllApplications();
		assertTrue(service.listAllApplications().size() == 0);

	}

	@Test
	void isAppSorted() {
		
		List<JobApplication> l = service.sortbyApplicationsDate();

		Boolean result = true;
		LocalDate date = LocalDate.now();
		int index = 0;
		
		while (index < l.size()) {
			if (l.get(index).getDateApplied().isAfter(date))	 {
				result = false;
				break;
			} else {
				date = l.get(index).getDateApplied();
			}
			index+=1;
		}

		assertEquals(result, true);


	}

	@Test 
	void isAppFilterDate() {

		JobApplication a = service.findApplication(2);
		String title = a.getJobTitle();
		char[] titleArr = title.toCharArray();
		int validNum = titleArr[titleArr.length - 1];

		LocalDate date = LocalDate.now().minusDays(validNum);

		List<JobApplication> l = service.filterbyApplicationsDate(date);

		Boolean result = true;
		int index = 0;
		
		while (index < l.size()) {
			if (!(l.get(index).getDateApplied().isEqual(date)))	 {
				result = false;
				break;
			} 
			index+=1;
		}

		assertEquals(result, true);

	}

	@Test
	void isAppDateRange() {

		JobApplication a = service.findApplication(2);
		JobApplication b = service.findApplication(5);
		
		LocalDate prevDate;
		LocalDate afterDate;

		if (a.getDateApplied().isAfter(b.getDateApplied())) {
			prevDate = b.getDateApplied();
			afterDate = a.getDateApplied();
		} else {
			prevDate = a.getDateApplied();
			afterDate = b.getDateApplied();
		}

		List<JobApplication> resultList = service.filterbyApplicationsDateRange(prevDate, afterDate);
		Boolean resultBoolean = true;

		for (int i = 0; i < resultList.size(); i++) {
			
			LocalDate currAppDate = resultList.get(i).getDateApplied();
			
			if (! ( (currAppDate.isAfter(prevDate) || currAppDate.equals(prevDate)) && (currAppDate.isBefore(afterDate) || currAppDate.equals(afterDate)) )) {
				resultBoolean = false;
				break;
			}

		}

		assertEquals(resultBoolean, true);
	

	}

	@Test
	void isAppFilterStatus() {

		ApplicationStatus status = ApplicationStatus.Interview;

		List<JobApplication> l = service.filterbyApplicationStatus(status);

		Boolean resultBoolean = true;

		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getAppStatus()!= ApplicationStatus.Interview) {
				resultBoolean = false;
			}
		}

		assertEquals(resultBoolean, true);

	}

	@Test
	void isAppFilterInterview() {

		InterviewStatus status = InterviewStatus.NA;

		List<JobApplication> l = service.filterbyInterviewStatus(status);

		Boolean resultBoolean = true;

		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getInterviewStatus()!= status) {
				resultBoolean = false;
			}
		}

		assertEquals(resultBoolean, true);

	}

	@Test
	void isAppUpdatedStatus() {

		ApplicationStatus a = service.findApplication(2).getAppStatus();

		service.updateStatus(2, ApplicationStatus.Applied);

		assertNotEquals(a, service.findApplication(2).getAppStatus());



	}

	@Test 
	void isAppUpdatedInterview() throws Exception {

		InterviewStatus a = service.findApplication(1).getInterviewStatus();

		service.updateInterview(1, InterviewStatus.Early);

		assertNotEquals(a, service.findApplication(1).getInterviewStatus());

	}

	@Test
	void contextLoads() {
	}



}
