package com.kleinjan.ReturnWrappers;

import java.io.Serializable;

public class StudentReturn implements Serializable {
    private String studentName;
    private Integer studentId;

    public StudentReturn(String studentName, Integer studentId){
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
