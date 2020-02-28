package com.kleinjan.service;

import com.kleinjan.model.Course;
import com.kleinjan.model.Role;
import com.kleinjan.model.Student;
import com.kleinjan.model.User;
import com.kleinjan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
