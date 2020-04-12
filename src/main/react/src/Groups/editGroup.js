import React, { Component } from 'react';
import Student from "../Students/Student";
import axios from "axios";

class editGroup extends Component {

    constructor(props) {
        super(props);
        this.state = {
            returnState: props.location.data.returnState,
            index: props.location.data.index,
            groupId: props.location.data.groupId,
            courseId: props.location.data.courseId,
            group: props.location.data.group,
            studentList: [],
            newStudent: false
        }
    }

    studentDeleteClick = (index) => {
        axios({
            method: 'post',
            url: '/delete-student-from-group',
            params: { studentId: this.state.group[index].studentId, groupId: this.state.groupId}
        }).then((response) => {
            let student = this.state.group[index];
            this.state.studentList.push(student);

            this.state.group.splice(index, 1);
            this.forceUpdate();
        });
    };

    addStudentClick = () => {
        this.state.newStudent = true;
        this.forceUpdate();
    };

    returnClick = () => {
        let returnState = this.state.returnState;
        let groupObject = {
            groupId: this.state.groupId,
            studentList: this.state.group
        };
        returnState.groupList[this.state.index] = groupObject;

        this.setState({
            returnState: returnState
        });

        this.props.history.push({
            pathname: '/courseGrouping',
            data: {courseId: this.state.courseId, groupList:this.state.returnState.groupList, ruleList: this.state.returnState.ruleList, groupId: this.state.returnState.groupId}
        });
    };

    studentChangeHandler = event => {
        this.setState({
            student: event.target.value
        });
    };

    addStudentHandler = () => {
        axios({
            method: 'post',
            url: '/add-student-to-group',
            params: { studentId: this.state.student, groupId: this.state.groupId}
        }).then((response) => {
            this.state.group.push(response.data);
            this.forceUpdate()
        });
    };

    cancelAddStudent = () => {
        this.state.newStudent = false;
        this.forceUpdate();
    };

    render() {

        if(this.state.newStudent){
            this.student = <select value={this.state.student} onChange={this.studentChangeHandler}><option> Select student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</select>;

            this.studentBuilder = <div>
                {this.student}
                <button onClick={this.addStudentHandler}> Add Student</button>
                <button onClick={this.cancelAddStudent}> Cancel </button>
            </div>
        } else{
            this.studentBuilder = <button onClick={this.addStudentClick}> Add Student </button>;
        }

        return(
            <div>
                <b>Students</b>
                {this.state.group.map((student, index) => {
                    return <Student student={student} deleteClick={() => this.studentDeleteClick(index)} />
                })}

                {this.studentBuilder}
                <button onClick={this.returnClick}> Return to Grouping </button>
            </div>
        );
    }
}

export default editGroup;