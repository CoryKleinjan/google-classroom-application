package com.kleinjan.returnWrappers;


import java.util.ArrayList;
import java.util.List;

public class GroupPackage {

    List<RuleReturn> ruleReturnList = new ArrayList();
    Integer courseId;
    Integer numberOfGroups;

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
}
