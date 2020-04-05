package com.kleinjan.controller;

import com.kleinjan.returnWrappers.*;
import com.kleinjan.model.*;
import com.kleinjan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
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
    public GroupingReturn createGrouping(@RequestBody GroupPackage groupPackage){

        Integer courseId = groupPackage.getCourseId();
        Integer numberOfGroups = groupPackage.getNumberOfGroups();

        Course course = courseService.findByCourseId(courseId);
        List<List<Student>> groupingList = new ArrayList<>();
        List<Student> studentList = new ArrayList(course.getStudents());
        List<Rule> ruleList = createRuleList(groupPackage.getRuleReturnList());
        Integer currentGroup = 0;

        for (int i = 0; i < numberOfGroups; i++) {
            groupingList.add(new ArrayList<Student>());
        }

        for (Rule rule : ruleList) {
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
        for (Student student : studentList) {
            for (List<Student> group : groupingList) {
                if (group.size() <= averageGroupSize) {
                    group.add(student);
                    break;
                }
            }

            averageGroupSize = getAverageGroupSize(groupingList, numberOfGroups);
        }

        Grouping savedGrouping;
        if(groupPackage.getRecreation()){
            savedGrouping = saveGrouping(groupingList, ruleList, courseId, true, groupPackage.getGroupId());
        } else{
            savedGrouping = saveGrouping(groupingList, ruleList, courseId, false, null);
        }

        List<GroupReturn> gList = new ArrayList();
        for (ClassGroup group : savedGrouping.getClassGroups()) {
            GroupReturn groupReturn = new GroupReturn(group.getGroupId());

            List<StudentReturn> sList = new ArrayList();
            for (Student student : group.getStudents()) {
                StudentReturn studentReturn = new StudentReturn(student.getName(), student.getStudentId());

                sList.add(studentReturn);
            }
            groupReturn.setStudentList(sList);

            gList.add(groupReturn);
        }

        List<RuleReturn> rList = new ArrayList();
        for (Rule rule : savedGrouping.getRules()) {
            RuleReturn ruleReturn = new RuleReturn();

            ruleReturn.setRuleType(rule.getType());
            if(rule.getId() != null) {
                ruleReturn.setRuleId(rule.getId());
            }
            try {
                ruleReturn.setFirstStudentId(rule.getFirstStudent());
                ruleReturn.setSecondStudentId(rule.getSecondStudent());
            } catch (NullPointerException e) {
            }
             rList.add(ruleReturn);
        }

        GroupingReturn groupingReturn = new GroupingReturn(savedGrouping.getCourseId(), savedGrouping.getGroupingId());
        groupingReturn.setGroupList(gList);
        groupingReturn.setRuleList(rList);

        return groupingReturn;
    }

    @RequestMapping("load-groupings-by-course-id")
    public List<GroupingReturn> loadGroupingsByCourseId(@RequestParam Integer courseId){
        List<Grouping> groupingList = groupingService.findByCourseId(courseId);

        List<GroupingReturn> returnList = new ArrayList();

        for(Grouping grouping : groupingList){
            GroupingReturn groupingReturn = new GroupingReturn(grouping.getCourseId(), grouping.getGroupingId());

            List<GroupReturn> groupList = new ArrayList();
            for(ClassGroup group : grouping.getClassGroups()){
                GroupReturn groupReturn = new GroupReturn(group.getGroupId());


                List<StudentReturn> studentList = new ArrayList();
                for(Student student : group.getStudents()){
                    StudentReturn studentReturn = new StudentReturn(student.getName(), student.getStudentId());

                    studentList.add(studentReturn);
                }
                groupReturn.setStudentList(studentList);

                groupList.add(groupReturn);
            }

            groupingReturn.setGroupList(groupList);

            List<RuleReturn> ruleList = new ArrayList();
            for(Rule rule : grouping.getRules()){
                RuleReturn ruleReturn = new RuleReturn();

                ruleReturn.setRuleType(rule.getType());
                if(rule.getId() != null) {
                    ruleReturn.setRuleId(rule.getId());
                }
                try{
                    ruleReturn.setFirstStudentId(rule.getFirstStudent());
                    ruleReturn.setSecondStudentId(rule.getSecondStudent());
                }catch(NullPointerException e){}

                ruleList.add(ruleReturn);
            }

            groupingReturn.setRuleList(ruleList);

            returnList.add(groupingReturn);
        }

        return returnList;
    }

    @RequestMapping("/delete-grouping")
    public void deleteGrouping(@RequestParam Integer groupingId){
        groupingService.deleteById(groupingId);
    }

    @RequestMapping("/delete-group")
    public void deleteGroup(@RequestParam Integer groupId){
        groupService.deleteById(groupId);
    }

    @RequestMapping("/delete-student-from-group")
    public void deleteStudentFromGroup(@RequestParam Integer studentId, @RequestParam Integer groupId){
        ClassGroup group = groupService.getById(groupId);
        List<Student> studentList = group.getStudents();

        Iterator<Student> iter = studentList.iterator();

        while (iter.hasNext()) {
            Student student = iter.next();

            if (student.getStudentId() == studentId) {
                iter.remove();
            }
        }

        group.setStudents(studentList);
        groupService.save(group);
    }

    @RequestMapping("/add-student-to-group")
    public StudentReturn addNewStudentToGroup(@RequestParam Integer studentId, @RequestParam Integer groupId){
        ClassGroup group = groupService.getById(groupId);
        List<Student> studentList = group.getStudents();

        Student student = studentService.findByStudentId(studentId);
        studentList.add(student);

        groupService.save(group);

        StudentReturn studentReturn = new StudentReturn(student.getName(), student.getStudentId());
        return studentReturn;
    }

    @RequestMapping("/get-students-not-in-group")
    public List<StudentReturn> loadStudentsNotInGroup(@RequestParam Integer courseId){
        Course course = courseService.findByCourseId(courseId);
        List<Grouping> groupingList = course.getGroupings();

        List<Student> studentList = course.getStudents();

        for(Grouping grouping : groupingList){
            for(ClassGroup group : grouping.getClassGroups()){
                for(Student student : group.getStudents()){
                    if(studentList.contains(student)){
                        studentList.remove(student);
                    }
                }
            }
        }

        List<StudentReturn> returnList = new ArrayList();

        for(Student student : studentList){
            StudentReturn studentReturn = new StudentReturn(student.getName(), student.getStudentId());
            returnList.add(studentReturn);
        }

        return returnList;
    }

    private Grouping saveGrouping(List<List<Student>> groupingList, List<Rule> ruleList, Integer courseId, Boolean recreation, Integer groupingId){

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

        if(recreation){
            groupingObject.setGroupingId(groupingId);
        }
        groupingObject.setClassGroups(groupingClassGroups);
        return groupingService.save(groupingObject);
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
            if(ruleReturn.getRuleId() != null) {
                rule.setId(ruleReturn.getRuleId());
            }
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
