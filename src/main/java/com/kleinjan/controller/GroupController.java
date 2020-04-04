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

        Grouping savedGrouping = saveGrouping(groupingList, ruleList, courseId);

        List<GroupReturn> gList = new ArrayList();
        for(ClassGroup group : savedGrouping.getClassGroups()){
            GroupReturn groupReturn = new GroupReturn(group.getGroupId());

            List<StudentReturn> sList = new ArrayList();
            for(Student student : group.getStudents()) {
                StudentReturn studentReturn = new StudentReturn(student.getName(), student.getStudentId());

                sList.add(studentReturn);
            }
            groupReturn.setStudentList(sList);

            gList.add(groupReturn);
        }

        List<RuleReturn> rList = new ArrayList();
        for(Rule rule : savedGrouping.getRules()){
            RuleReturn ruleReturn = new RuleReturn();

            ruleReturn.setRuleType(rule.getType());
            ruleReturn.setRuleId(rule.getId());
            try{
                ruleReturn.setFirstStudentId(rule.getFirstStudent());
                ruleReturn.setSecondStudentId(rule.getSecondStudent());
            }catch(NullPointerException e){}

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
                ruleReturn.setRuleId(rule.getId());
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

    private Grouping saveGrouping(List<List<Student>> groupingList, List<Rule> ruleList, Integer courseId){

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
            rule.setId(ruleReturn.getRuleId());
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
