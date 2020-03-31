package com.kleinjan.controller;

import com.kleinjan.returnWrappers.RuleReturn;
import com.kleinjan.returnWrappers.GroupPackage;
import com.kleinjan.returnWrappers.StudentReturn;
import com.kleinjan.model.*;
import com.kleinjan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    GroupingService groupingService;

    @Autowired
    GroupService groupService;

    @Autowired
    RuleService ruleService;

    @RequestMapping("/create-grouping")
    public List<List<StudentReturn>> createGrouping(@RequestBody GroupPackage groupPackage){

        Integer courseId = groupPackage.getCourseId();
        Integer numberOfGroups = groupPackage.getNumberOfGroups();

        Course course = courseService.findByCourseId(courseId);
        List<List<Student>> groupingList = new ArrayList<>();
        List<Student> studentList = new ArrayList(course.getStudents());
        List<Rule> ruleList = createRuleList(groupPackage.getRuleReturnList());
        Integer currentGroup = 0;

        for(int i = 0; i < numberOfGroups; i++){
            groupingList.add(new ArrayList<Student>());
        }

        for(Rule rule : ruleList) {
            switch (rule.getType()) {
                case "notTogether":
                    groupingList.get(currentGroup).add(getStudentFromList(studentList, rule.getFirstStudent()));
                    currentGroup = iterateCurrentGroup(currentGroup, numberOfGroups);
                    studentList.remove(getStudentFromList(studentList, rule.getFirstStudent()));

                    groupingList.get(groupingList.size() - 1).add(getStudentFromList(studentList, rule.getSecondStudent()));
                    currentGroup = iterateCurrentGroup(currentGroup, numberOfGroups);
                    studentList.remove(getStudentFromList(studentList, rule.getSecondStudent()));

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
        return studentList.stream().filter(o -> o.getStudentId() == studentId).findFirst().get();
    }

    public List<Rule> createRuleList(List<RuleReturn> ruleReturnList){

        List<Rule> ruleList = new ArrayList();

        for(RuleReturn ruleReturn : ruleReturnList){
            Rule rule = new Rule();
            rule.setType(ruleReturn.getRuleType());
            rule.setFirstStudent(studentService.findByStudentId(ruleReturn.getFirstStudentId()).getStudentId());
            rule.setSecondStudent(studentService.findByStudentId(ruleReturn.getSecondStudentId()).getStudentId());

            rule = ruleService.save(rule);
            ruleList.add(rule);
        }

        return ruleList;
    }

    public Integer iterateCurrentGroup(Integer currentGroup, Integer numberOfGroups){
        if(currentGroup < numberOfGroups -1){
            currentGroup++;
        } else {
            currentGroup = 0;
        }

        return currentGroup;
    }
}
