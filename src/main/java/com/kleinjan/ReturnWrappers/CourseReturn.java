package com.kleinjan.ReturnWrappers;

import java.io.Serializable;

public class CourseReturn implements Serializable {
    private String courseName;
    private Integer courseId;

    public CourseReturn(String courseName, Integer courseId){
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
