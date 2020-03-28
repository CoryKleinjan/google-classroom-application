package com.kleinjan.controller;

import com.kleinjan.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/delete-course")
    public void deleteCourse(@RequestParam Integer courseId){
        courseService.deleteById(courseId);
    }
}
