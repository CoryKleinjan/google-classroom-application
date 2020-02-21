package com.kleinjan.controller;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.*;
import com.kleinjan.repository.CourseRepository;
import com.kleinjan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

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

	@RequestMapping("/importTest")
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
			courseObject.setUserId(currentUser.getUsername());

			courseRepository.save(courseObject);

			ListStudentsResponse studentResponse = classroom.courses().students().list(course.getId()).execute();

			List<Student> students = studentResponse.getStudents();

			for(Student student: students){
				try {
					ListStudentSubmissionsResponse submissionsResponse = classroom.courses().courseWork().studentSubmissions().list(course.getId(), "-").execute();

					List<StudentSubmission> submissions = submissionsResponse.getStudentSubmissions();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}