package com.kleinjan.controller;

import com.kleinjan.model.ClassGroup;
import com.kleinjan.model.Course;
import com.kleinjan.model.Grouping;
import com.kleinjan.model.Student;
import com.kleinjan.returnWrappers.StudentReturn;
import com.kleinjan.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/delete-course")
    public void deleteCourse(@RequestParam Integer courseId){
        courseService.deleteById(courseId);
    }

    @RequestMapping("/get-student-list-by-course")
    public List<StudentReturn> getStudentListByCourse(@RequestParam Integer courseId){
        Course course = courseService.findByCourseId(courseId);
        List<StudentReturn> returnList = new ArrayList();

        for(Student student : course.getStudents()){
            StudentReturn returnStudent = new StudentReturn(student.getName(),student.getStudentId());

            returnList.add(returnStudent);
        }

        return returnList;
    }
}
