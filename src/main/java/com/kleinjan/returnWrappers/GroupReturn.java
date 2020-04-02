package com.kleinjan.returnWrappers;

import java.util.List;

public class GroupReturn {

    Integer groupId;
    List<StudentReturn> studentList;

    public GroupReturn(Integer groupId){
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<StudentReturn> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentReturn> studentList) {
        this.studentList = studentList;
    }
}
