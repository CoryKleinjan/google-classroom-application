package com.kleinjan.controller;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.*;
import com.kleinjan.ReturnWrappers.CourseReturn;
import com.kleinjan.service.AssignmentService;
import com.kleinjan.service.CourseService;
import com.kleinjan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

@RestController
public class GoogleController {

	@Autowired
	CourseService courseService;

	@Autowired
	StudentService studentService;

	@Autowired
	AssignmentService assignmentService;

	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final Set<String> SCOPES = ClassroomScopes.all();

	@Value("${google.oauth.callback.uri}")
	private String CALLBACK_URI;

	@Value("${google.secret.key.path}")
	private Resource gdSecretKeys;

	@Value("${google.credentials.folder.path}")
	private Resource credentialsFolder;

	private GoogleAuthorizationCodeFlow flow;

	@RequestMapping("/google-login")
	public void googleLogin(HttpServletResponse response) throws Exception {
		GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(gdSecretKeys.getInputStream()));
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile())).build();

		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		String redirectURL = url.setRedirectUri(CALLBACK_URI).setAccessType("offline").build();
		response.sendRedirect(redirectURL);
	}

	@RequestMapping("/oauth")
	public void saveAuthorizationCode(@RequestParam String token) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		GoogleTokenResponse response = flow.newTokenRequest(token).setRedirectUri(CALLBACK_URI).execute();
		flow.createAndStoreCredential(response, username);
	}

	@RequestMapping("/load-all-courses")
	public List<CourseReturn> loadTest(@AuthenticationPrincipal UserDetails currentUser){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		List<com.kleinjan.model.Course> courseList = courseService.findCoursesByUsername(username);

		List<CourseReturn> returnList = new ArrayList();
		for( com.kleinjan.model.Course course : courseList){
			returnList.add(new CourseReturn(course.getName(),course.getCourseId()));
		}

		return returnList;
	}

	@RequestMapping("/import-courses")
	public void importTest(@AuthenticationPrincipal UserDetails currentUser) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		Credential cred = flow.loadCredential(username);

		Classroom classroom = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
				.setApplicationName("Classroom App").build();

		ListCoursesResponse courseResponse = classroom.courses().list()
				.execute();
		List<Course> courses = courseResponse.getCourses();

		for(Course course: courses){
			com.kleinjan.model.Course courseObject = new com.kleinjan.model.Course();
			courseObject.setGoogleId(course.getId());
			courseObject.setName(course.getName());
			courseObject.setUsername(currentUser.getUsername());

			ListStudentsResponse studentResponse = classroom.courses().students().list(course.getId()).execute();

			List<Student> students = studentResponse.getStudents();

			List<com.kleinjan.model.Student> courseStudents = new ArrayList<>();
			for(Student student: students){
				com.kleinjan.model.Student studentObject = new com.kleinjan.model.Student();
				studentObject.setGoogleId(student.getUserId());
				studentObject.setName(student.getProfile().getName().getFullName());

				List<com.kleinjan.model.Course> studentCourses = new ArrayList<>();
				studentCourses.add(courseObject);
				studentObject.setCourses(studentCourses);

				studentObject = studentService.save(studentObject);

				courseStudents.add(studentObject);

				ListStudentSubmissionsResponse submissionsResponse = classroom.courses().courseWork().studentSubmissions().list(course.getId(), "-").execute();
				List<StudentSubmission> submissions = submissionsResponse.getStudentSubmissions();
				for(StudentSubmission submission: submissions){
					if(submission.getUserId().equals(student.getUserId())) {
						com.kleinjan.model.Assignment assignmentObject = new com.kleinjan.model.Assignment();
						assignmentObject.setStudentId(studentObject.getStudentId());
						assignmentObject.setTotalPoints(submission.getSubmissionHistory().get(1).getGradeHistory().getMaxPoints());
						assignmentObject.setCourseId(courseObject.getCourseId());
						assignmentObject.setGrade(submission.getAssignedGrade());

						assignmentObject = assignmentService.save(assignmentObject);
					}
				}
			}

			courseObject.setStudents(courseStudents);
			courseService.save(courseObject);
		}
	}
}