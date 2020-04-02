package com.kleinjan.returnWrappers;


import java.util.ArrayList;
import java.util.List;

public class GroupingReturn {

    Integer courseId;
    Integer groupId;
    List<GroupReturn> groupList;
    List<RuleReturn> ruleList = new ArrayList();

    public GroupingReturn(Integer courseId, Integer groupId){
        this.courseId = courseId;
        this.groupId = groupId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public List<RuleReturn> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleReturn> ruleReturnList) {
        this.ruleList = ruleReturnList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<GroupReturn> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupReturn> groupList) {
        this.groupList = groupList;
    }
}