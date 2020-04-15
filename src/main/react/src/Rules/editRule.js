import React, { Component } from 'react';
import axios from "axios";
import Button from "@material-ui/core/Button";
import Select from "@material-ui/core/Select";

class editRule extends Component {

    constructor(props) {
        super(props);
        this.state = {
            rule: props.location.data.rule,
            courseId: props.location.data.courseId,
            studentList: [],
            returnState: props.location.data.returnState,
            index: props.location.data.index
        }
    }

    componentDidMount() {
        this.loadStudentList();
    }

    loadStudentList = () => {
        axios({
            method: 'get',
            url: '/get-student-list-by-course',
            params: { courseId: this.state.courseId }
        }).then((response) => {
            this.setState({studentList: response.data});
        });
    };

    firstStudentChangeHandler = event => {
        let rule = this.state.rule;

        rule.firstStudentId = event.target.value;
        this.setState({
            rule: rule
        });
    };

    secondStudentChangeHandler = event => {
        let rule = this.state.rule;

        rule.secondStudentId = event.target.value;
        this.setState({
            rule: rule
        });
    };

    submitRule = () => {
        axios({
            method: 'post',
            url: '/update-rule',
            data: this.state.rule
        }).then((response) => {
            let returnState = this.state.returnState;
            returnState.ruleList[this.state.index] = this.state.rule;

            this.setState({
                returnState: returnState
            });

            this.props.history.push({
                pathname: '/courseGrouping',
                data: {courseId: this.state.courseId, groupList:this.state.returnState.groupList, ruleList: this.state.returnState.ruleList, groupId: this.state.returnState.groupId},
            });
        });
    };

    render() {
        if(this.state.rule.ruleType === "notTogether"){

            this.firstStudent = <Select value={this.state.rule.firstStudentId} onChange={this.firstStudentChangeHandler}><option> Select first student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</Select>;
            this.secondStudent = <Select value={this.state.rule.secondStudentId} onChange={this.secondStudentChangeHandler}><option> Select second student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</Select>;

            this.rule = <div>
                {this.state.rule.ruleType}
                {this.firstStudent}
                {this.secondStudent}
                <Button variant="contained" color="primary" onClick={this.submitRule}>Submit Rule</Button>
            </div>

        } else if( this.state.rule.ruleType === "together"){

            this.firstStudent = <Select value={this.state.rule.firstStudentId} onChange={this.firstStudentChangeHandler}><option> Select first student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</Select>;
            this.secondStudent = <Select value={this.state.rule.secondStudentId} onChange={this.secondStudentChangeHandler}><option> Select second student</option>{this.state.studentList.map( (student) => <option value={student.studentId} key={student.studentName}>{student.studentName}</option>)}</Select>;

            this.rule = <div>
                {this.state.rule.ruleType}
                {this.firstStudent}
                {this.secondStudent}
                <Button variant="contained" color="primary" onClick={this.submitRule}>Submit Rule</Button>
            </div>

        } else if( this.state.rule.ruleType === "topInEach"){

            this.rule = <div>
                {this.state.rule.ruleType}
                <Button variant="contained" color="primary" onClick={this.submitRule}>Submit Rule</Button>
            </div>
        } else if( this.state.rule.ruleType === "bottomInEach"){

            this.rule = <div>
                {this.state.rule.ruleType}
                <Button variant="contained" color="primary" onClick={this.submitRule}>Submit Rule</Button>
            </div>
        } else if( this.state.rule.ruleType === "random"){

            this.rule = <div>
                {this.state.rule.ruleType}
                <Button variant="contained" color="primary" onClick={this.submitRule}>Submit Rule</Button>
            </div>
        }

        return(
            <div>
                {this.rule}
            </div>
        );
    }
}

export default editRule;