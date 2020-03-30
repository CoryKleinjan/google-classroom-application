package com.kleinjan.controller;

import com.kleinjan.returnWrappers.StudentReturn;
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

    @RequestMapping("/create-grouping")
    public List<List<StudentReturn>> createGrouping(@RequestParam Integer courseId, @RequestParam Integer numberOfGroups, @RequestParam List<Rule> ruleList){

        Course course = courseService.findByCourseId(courseId);
        List<List<Student>> groupingList = new ArrayList<>();
        List<Student> studentList = course.getStudents();


        for(int i = 0; i < numberOfGroups; i++){
            groupingList.add(new ArrayList<Student>());
        }

        for(Rule rule : ruleList) {
            switch (rule.getType()) {
                case "notTogether":
                    groupingList.get(0).add(getStudentFromList(studentList, rule.getStudentOne()));
                    studentList.remove(rule.getStudentOne());

                    groupingList.get(groupingList.size() - 1).add(getStudentFromList(studentList, rule.getStudentTwo()));
                    studentList.remove(rule.getStudentTwo());

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

        saveGrouping(groupingList, ruleList, courseId);

        return returnList;
    }

    private void saveGrouping(List<List<Student>> groupingList, List<Rule> ruleList, Integer courseId){

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
    }

    private  Integer getAverageGroupSize(List<List<Student>> groupingList, Integer numberOfGroups){

        Integer totalGroupSize = 0;

        for(List<Student> group: groupingList){
            totalGroupSize += group.size();
        }

        return totalGroupSize/numberOfGroups;
    }

    public Student getStudentFromList(final List<Student> studentList, final Integer studentId){
        return studentList.stream().filter(o -> o.getName().equals(studentId)).findFirst().get();
    }
}
