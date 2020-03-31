package com.kleinjan.returnWrappers;

import java.io.Serializable;

public class RuleReturn implements Serializable {
    String ruleType;
    Integer firstStudentId;
    Integer secondStudentId;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getFirstStudentId() {
        return firstStudentId;
    }

    public void setFirstStudentId(Integer firstStudentId) {
        this.firstStudentId = firstStudentId;
    }

    public Integer getSecondStudentId() {
        return secondStudentId;
    }

    public void setSecondStudentId(Integer secondStudentId) {
        this.secondStudentId = secondStudentId;
    }
}
