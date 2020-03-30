import React, { Component } from 'react';
import axios from "axios";
import Course from "../Courses/Course";

class editGrouping extends Component {

    constructor(props) {
        super(props);
        this.state = {
            ruleType: '',
            ruleList: [],
            studentList: [],
            firstStudent: '',
            secondStudent: ''
        }
    }

    componentDidMount() {
        this.getStudentList();
    }

    getStudentList = () => {
        axios({
            method: 'get',
            url: '/get-student-list-by-course',
            params: {
                courseId: this.props.location.data.courseId,
            }
        }).then((response) => {
            this.setState({studentList: response.data});
        });
    };

    createGrouping = () => {
        axios({
            method: 'get',
            url: '/create-grouping',
            params: {
                courseId: this.props.location.data.courseId,
                numberOfGroups: 2,
                ruleList: this.state.ruleList
            }
        }).then((response) => {
            console.log(response);
        });
    };

    firstStudentChangeHandler = event => {
        this.setState({
            firstStudent: event.target.value
        });
    };

    secondStudentChangeHandler = event => {
        this.setState({
            secondStudent: event.target.value
        });
    };

    ruleTypeChangeHandler = event => {
        this.setState({
            ruleType: event.target.value
        });
    };

    render() {
        if(this.state.ruleType === "notTogether"){

            this.firstStudent = <select value={this.state.firstStudent} onChange={this.firstStudentChangeHandler}>{this.state.studentList.map( (student) => <option value={student.studentName} key={student.studentName}>{student.studentName}</option>)}</select>;
            this.secondStudent = <select value={this.state.secondStudent} onChange={this.secondStudentChangeHandler}>{this.state.studentList.map( (student) => <option value={student.studentName} key={student.studentName}>{student.studentName}</option>)}</select>;

            this.ruleBuilder = <div>
                test
                {this.firstStudent}
                {this.secondStudent}
            </div>

        } else if( this.state.ruleType === "together"){
            this.getStudentList();
        }

        return(
            <div>
                <form>
                    <label> Pick Rule Type</label>
                    <select value={this.state.ruleType} onChange={this.ruleTypeChangeHandler}>
                        <option value=""> Please choose a rule type.</option>
                        <option value="notTogether"> Students cannot share group</option>
                        <option value="together"> Students must share group</option>
                    </select>
                </form>
                {this.ruleBuilder}
                <button type="button" onClick={this.createGrouping}>
                    Create Grouping
                </button>
            </div>
        );
    }
}

export default editGrouping;