package com.kleinjan.controller;

import com.kleinjan.ReturnWrappers.StudentReturn;
import com.kleinjan.model.*;
import com.kleinjan.service.CourseService;
import com.kleinjan.service.GroupService;
import com.kleinjan.service.GroupingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    CourseService courseService;

    @Autowired
    GroupingService groupingService;

    @Autowired
    GroupService groupService;

    @RequestMapping("/create-groups")
    public List<List<StudentReturn>> groupTest(@RequestParam Integer courseId, @RequestParam Integer numberOfGroups){

        Course course = courseService.findCourseById(courseId);
        List<Rule> ruleList = new ArrayList();
        List<List<Student>> groupingList = new ArrayList<>();
        List<Student> studentList = course.getStudents();

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

        Integer averageGroupSize = getAverageGroupSize(groupingList, numberOfGroups);
        for(Student student : studentList){
            for(List<Student> group: groupingList){
                if(group.size() <= averageGroupSize){
                    group.add(student);
                    break;
                }
            }

            averageGroupSize = getAverageGroupSize(groupingList, numberOfGroups);
        }

        List<List<StudentReturn>> returnList = new ArrayList();
        for(List<Student> sList : groupingList){
            List<StudentReturn> rList = new ArrayList();
            for(Student student : sList){
                rList.add(new StudentReturn(student.getName(), student.getStudentId()));
            }

            returnList.add(rList);
        }

        saveGroups(groupingList, ruleList, courseId);

        return returnList;
    }

    private void saveGroups(List<List<Student>> groupingList, List<Rule> ruleList, Integer courseId){

        try {
            Grouping groupingObject = new Grouping();
            groupingObject.setCourseId(courseId);
            groupingObject.setRules(ruleList);

            List<ClassGroup> groupingClassGroups = new ArrayList<>();

            for (List<Student> loopGroup : groupingList) {
                ClassGroup classGroup = new ClassGroup();
                classGroup.setStudents(loopGroup);

                groupService.save(classGroup);

                groupingClassGroups.add(classGroup);
            }

            groupingObject.setClassGroups(groupingClassGroups);
            groupingService.save(groupingObject);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private  Integer getAverageGroupSize(List<List<Student>> groupingList, Integer numberOfGroups){

        Integer totalGroupSize = 0;

        for(List<Student> group: groupingList){
            totalGroupSize += group.size();
        }

        return totalGroupSize/numberOfGroups;
    }
}
