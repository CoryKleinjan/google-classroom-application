package com.kleinjan.service;

import com.kleinjan.model.Course;
import com.kleinjan.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findCoursesByUsername(String username) {
        return courseRepository.findCoursesByUsername(username);
    }

    public Course findCourseById(Integer courseId) { return courseRepository.findByCourseId(courseId); }

    public Course save(Course course) {
        return courseRepository.save(course);
    }
}
