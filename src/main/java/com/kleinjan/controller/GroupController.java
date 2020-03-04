package com.kleinjan.controller;

import com.kleinjan.model.Course;
import com.kleinjan.model.Rule;
import com.kleinjan.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @RequestMapping("/create-group")
    public List<List<Student>> createGroup(Course course, Integer numberOfGroups, List<Rule> ruleList){

        List<List<Student>> groupingList = new ArrayList<>();
        List<Student> studentList = course.getStudents();

        Integer groupCounter = 0; //next group to add a student to

        for(int i = 0; i < numberOfGroups; i++){
            groupingList.add(new ArrayList<Student>());
        }

        for(Rule rule : ruleList) {
            switch (rule.getType()) {
                case "":
                    break;
                default:
            }
        }

        for(Student student : studentList){
            groupingList.get(groupCounter).add(student);
            groupCounter++;
        }

        return groupingList;
    }
}
