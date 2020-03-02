package com.kleinjan.groups;

import com.kleinjan.model.Course;
import com.kleinjan.model.Student;

import java.util.ArrayList;
import java.util.List;

public class GroupBuilder {

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

        }

        return groupingList;
    }
}
