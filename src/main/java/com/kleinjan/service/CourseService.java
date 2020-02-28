package com.kleinjan.service;

import com.kleinjan.model.Course;
import com.kleinjan.model.Student;
import com.kleinjan.model.User;
import com.kleinjan.repository.CourseRepository;
import com.kleinjan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findUserByUsername(String username) {
        return courseRepository.findByUsername(username);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }
}
