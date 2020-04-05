package com.kleinjan.returnWrappers;


import java.util.ArrayList;
import java.util.List;

public class GroupPackage {

    List<RuleReturn> ruleReturnList = new ArrayList();
    Integer courseId;
    Integer numberOfGroups;
    Integer groupId;
    Boolean recreation;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(Integer numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public List<RuleReturn> getRuleReturnList() {
        return ruleReturnList;
    }

    public void setRuleReturnList(List<RuleReturn> ruleReturnList) {
        this.ruleReturnList = ruleReturnList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getRecreation() {
        return recreation;
    }

    public void setRecreation(Boolean recreation) {
        this.recreation = recreation;
    }
}
