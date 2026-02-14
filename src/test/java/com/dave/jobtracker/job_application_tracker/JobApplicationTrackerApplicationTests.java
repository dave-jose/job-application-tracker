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
import org.springframework.cglib.core.Local;

import com.dave.jobtracker.job_application_tracker.enums.ApplicationStatus;
import com.dave.jobtracker.job_application_tracker.enums.InterviewStatus;
import com.dave.jobtracker.job_application_tracker.models.JobApplication;
import com.dave.jobtracker.job_application_tracker.models.User;
import com.dave.jobtracker.job_application_tracker.repository.JARepository;
import com.dave.jobtracker.job_application_tracker.repository.UserRepository;
import com.dave.jobtracker.job_application_tracker.service.JATService;
import com.dave.jobtracker.job_application_tracker.service.UserService;

@SpringBootTest
class JobApplicationTrackerApplicationTests {

	@Autowired
	private JATService service; 

	@Autowired
	private JARepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	int repoSize = 0;

	// TESTING USER SERVICE

	// Testing register user, checking if user exists, and finding user
	// @Test
	// void test1() throws Exception {

	// 	for (int i = 1; i < 31; i++) {
	// 		String u = "user" + i + "@email.com";
	// 		String p = "password" + i;
	// 		userService.registerUser(u, p);
	// 	}

	// 	assertEquals(userRepository.findAll().size(), 30);

	// 	for (int i = 1; i < 31; i++) {

	// 		String u = "user" + i + "@email.com";
	// 		assertTrue(userService.userExists(u));
	// 		User user = userService.findUser(u);
	// 		assertEquals(user.getUserEmail(), u);
	// 	}

	// }

	// @Test
	// void test2() {

	// 	userRepository.deleteAll();

	// 	assertEquals(userRepository.findAll().size(), 0);
		
	// }

	void AddfiveAppsPerUser() {

		ApplicationStatus[] appStatuss = {ApplicationStatus.Applied, ApplicationStatus.Interview, ApplicationStatus.Offer, ApplicationStatus.Rejected, ApplicationStatus.Withdrawn};
		InterviewStatus[] intstatuss = {InterviewStatus.Early, InterviewStatus.Mid, InterviewStatus.Final};

		for (int i = 1; i < 31; i++) {
			
			String email = "user" + i + "@email.com";
			User user = userService.findUser(email);

			for (int j=1; j<6; j++) {

				String jobTitle = "job" + j;
				String companyName = "company" + j;
				LocalDate date = LocalDate.now().minusDays(j);
				ApplicationStatus applicationStatus = appStatuss[(int) (Math.random() * 4)];
				InterviewStatus interviewStatus = InterviewStatus.NA;

				if (applicationStatus == ApplicationStatus.Interview) {
					interviewStatus = intstatuss[ (int) (Math.random() * 2)];
				} 

				JobApplication app = new JobApplication(jobTitle, companyName, date, applicationStatus, interviewStatus, user);
				service.addApplication(user, app);
			}

		}

	}

	// TESTING JOB APPLICATION SERVICE 

	@Test
	void test3() {

		// adding 5 apps per user
		AddfiveAppsPerUser();

		int index = 1;
		
		// checking find user 
		for (int i = 1; i < 31; i++) {
			String email = "user" + i + "@email.com";
			User user = userService.findUser(email);

			List<JobApplication> jobAppList = service.listAllApplications(user);
			assertEquals(jobAppList.size(), 5);

			for (int j=0; j<jobAppList.size();j++) {
				JobApplication a = service.findApplication(user, index);
				assertEquals(jobAppList.get(j), a);
			}
		}

	}

	// updating status and interview

	@Test
	void test4() throws Exception {

		User user = userService.findUser("user2@email.com");

		JobApplication a = service.findApplication(user, 6);

		assertEquals(a.getAppStatus(), ApplicationStatus.Applied);
		assertEquals(a.getInterviewStatus(), InterviewStatus.NA);

		service.updateStatus(6, user, ApplicationStatus.Interview);
		service.updateInterview(6, user, InterviewStatus.Early);

		a = service.findApplication(user, 6);

		assertEquals(a.getAppStatus(), ApplicationStatus.Interview);
		assertEquals(a.getInterviewStatus(), InterviewStatus.Early);

	}

			/*
		CHECKLIST OF WHAT TO TEST:

		USER SERVICE: 
		- LOGIN
		- REGISTER USER -> Done
		- FINDING THE USER -> Done
		- CHECKING IF A USER EXISTS -> Done

		JOB APPLICATION SERVICE:
		- APPLICATION DELETED
		- DELETING ALL APPLICATIONS 
		- LISTING ALL APPLICATIONS SORTED BY DATE -> Done
		- FILTER BY APPLICATIONS STATUS -> Done
		- FILTER BY APPLICATIONS INTERVIEW -> Done
		- UPDATE STATUS -> Done
		- UPDATE INTERVIEW -> Done
		- LISTING ALL APPLICATIONS -> Done
		- APPLICATION ADDED -> Done
		- CAN AN APPLICATION BE FOUND -> Done
		- FILTER BY APPLICATIONS DATE -> Done
		- FILTER BY APPLICATIONS DATE RANGE -> Done

	*/

	// filtering 
	@Test 
	void test5() {
		
		User user = userService.findUser("user1@email.com");

		// filter by applications date
		List<JobApplication> jobAppList = service.filterbyApplicationsDate(LocalDate.now().minusDays(3), user);
		
		for (JobApplication j: jobAppList) {
			assertEquals(j.getDateApplied(), LocalDate.now().minusDays(3));
		}

		// filter by applications date range
		jobAppList = service.filterbyApplicationsDateRange(LocalDate.now().minusDays(4), LocalDate.now().minusDays(2), user);

		for (JobApplication j: jobAppList) {

			LocalDate dateApplied = j.getDateApplied();

			boolean statement = dateApplied.isBefore(LocalDate.now().minusDays(2)) || dateApplied.isEqual(LocalDate.now().minusDays(2));
			assertTrue(statement);

			statement = dateApplied.isAfter(LocalDate.now().minusDays(4)) || dateApplied.isEqual(LocalDate.now().minusDays(4));
			assertTrue(statement);

		}

		// filter by status
		jobAppList = service.filterbyApplicationStatus(ApplicationStatus.Applied, user);

		for (JobApplication j: jobAppList) {
			assertEquals(j.getAppStatus(), ApplicationStatus.Applied);
		}

		// filter by interview
		user = userService.findUser("user10@email.com");

		jobAppList = service.filterbyInterviewStatus(InterviewStatus.Mid, user);

		for (JobApplication j: jobAppList) {
			assertEquals(j.getInterviewStatus(), InterviewStatus.Mid);
		}		

		// Listing all job applications by date
		jobAppList = service.sortbyApplicationsDate(user);

		LocalDate prevDate = jobAppList.get(0).getDateApplied(); // Dates sorted in descending order

		for (JobApplication j: jobAppList) {

			LocalDate currJobAppDate = j.getDateApplied();
			
			boolean statement = currJobAppDate.isBefore(prevDate) || currJobAppDate.isEqual(prevDate);

			assertTrue(statement);

			prevDate = currJobAppDate; // changing prevDate to date just evaluated

		}


	}




	@Test
	void contextLoads() {
	}




}
