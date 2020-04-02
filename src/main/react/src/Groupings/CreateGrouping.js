import React, { Component } from 'react';
import axios from "axios";
import Rule from '../Rules/Rule';

class createGrouping extends Component {

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

        let groupPackage = {ruleReturnList: this.state.ruleList, numberOfGroups: 2, courseId: this.props.location.data.courseId};
        axios({
            method: 'post',
            url: '/create-grouping',
            data: groupPackage
        }).then((response) => {
            console.log(response);
            this.props.history.push({
                pathname: '/courseGrouping',
                data: {courseId: this.props.location.data.courseId, groupList:response.data.groupList, ruleList: response.data.ruleList}
            });
        });
    };

    submitRule = () => {
        let rule = {};
        let ruleList = this.state.ruleList;

        rule.ruleType = this.state.ruleType;
        rule.firstStudentId = this.state.firstStudent;
        rule.secondStudentId = this.state.secondStudent;

        ruleList.push(rule);

        this.setState({
            ruleList: ruleList,
            ruleType: '',
            firstStudent: '',
            secondStudent: ''
        });

        this.forceUpdate();
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

    deleteRule = (index) => {
        this.state.ruleList.splice(index, 1);
        this.forceUpdate();
    };

    render() {
        if(this.state.ruleType === "notTogether"){

            this.firstStudent = <select value={this.state.firstStudent} onChange={this.firstStudentChangeHandler}><option> Select first student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</select>;
            this.secondStudent = <select value={this.state.secondStudent} onChange={this.secondStudentChangeHandler}><option> Select second student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</select>;

            this.ruleBuilder = <div>
                {this.firstStudent}
                {this.secondStudent}
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>

        } else if( this.state.ruleType === "together"){

            this.firstStudent = <select value={this.state.firstStudent} onChange={this.firstStudentChangeHandler}><option> Select first student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</select>;
            this.secondStudent = <select value={this.state.secondStudent} onChange={this.secondStudentChangeHandler}><option> Select second student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</select>;

            this.ruleBuilder = <div>
                test
                {this.firstStudent}
                {this.secondStudent}
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>

        } else{
            this.ruleBuilder = <div></div>
        }

        return(
            <div>
                <p> Rules </p>
                {this.state.ruleList.map((rule, index) => {
                    return <Rule deleteClick={() => this.deleteRule(index)} rule={rule} />
                })}
                <form>
                    <label> Pick Rule Type:  </label>
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

export default createGrouping;