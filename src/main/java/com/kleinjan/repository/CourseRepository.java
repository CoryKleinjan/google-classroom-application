package com.kleinjan.repository;

import com.kleinjan.model.Course;
import com.kleinjan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findByUsername(String username);
}
