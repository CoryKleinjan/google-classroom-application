package com.kleinjan.repository;

import com.kleinjan.model.Course;
import com.kleinjan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
    List<Course> findCoursesByUsername(String username);

    Course findByCourseId(Integer courseId);
}
