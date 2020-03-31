package com.kleinjan.repository;

import com.kleinjan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public Student findByStudentId(Integer studentId);

}
