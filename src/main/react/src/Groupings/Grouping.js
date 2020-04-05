import React, {Component} from 'react';
import Group from "../Groups/Group";
import axios from "axios";
import Rule from "../Rules/Rule";

class Grouping extends Component {

    constructor(props) {
        super(props);
        this.state = {
            groupList: props.location.data.groupList,
            courseId: props.location.data.courseId,
            ruleList: props.location.data.ruleList,
            groupId: props.location.data.groupId,
            firstStudent: '',
            secondStudent: '',
            ruleType: ''
        };
    }

    findWithAttr = (array, attr, value) => {
        for(let i = 0; i < array.length; i += 1) {
            if(array[i][attr] === value) {
                return i;
            }
        }
        return -1;
    };

    groupDeleteClick = (index) => {
        axios({
            method: 'post',
            url: '/delete-group',
            params: { groupId: this.state.groupList[index].groupId }
        }).then((response) => {
            this.state.groupList.splice(index, 1);
            this.forceUpdate();
        });
    };

    ruleDeleteClick = (index) => {
        axios({
            method: 'post',
            url: '/delete-rule',
            params: { ruleId: this.state.ruleList[index].ruleId }
        }).then((response) => {
            this.state.ruleList.splice(index, 1);
            this.forceUpdate();
        });
    };

    reCreateGrouping = () => {
        let groupPackage = {ruleReturnList: this.state.ruleList, numberOfGroups: this.state.groupList.length, courseId: this.props.location.data.courseId, groupId: this.state.groupId, recreation: true};

        axios({
            method: 'post',
            url: '/create-grouping',
            data: groupPackage
        }).then((response) => {
            this.setState({
                ruleList: response.data.ruleList,
                groupList: response.data.groupList
            })
        });
    };

    addNewRule = () => {
        this.state.ruleType = 'newRule';
    };

    render() {
        if(this.state.ruleType = 'newRule'){
            this.ruleBuilder = <form>
                <label> Pick Rule Type:  </label>
                <select value={this.state.ruleType} onChange={this.ruleTypeChangeHandler}>
                    <option value=""> Please choose a rule type.</option>
                    <option value="notTogether"> Students cannot share group</option>
                    <option value="together"> Students must share group</option>
                </select>
            </form>
        } else if(this.state.ruleType === "notTogether"){

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
                {this.firstStudent}
                {this.secondStudent}
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>

        } else{
            this.ruleBuilder = <button onClick={this.addNewRule}> Add Rule</button>
        }

        return(
            <div>
                <b>Groups</b>
                <div>
                    {this.state.groupList.map((group, index) => {
                        return <Group returnState={this.state} index={index} groupId={group.groupId} courseId={this.state.courseId}  group={group.studentList} deleteClick={() => this.groupDeleteClick(index)} />
                    })}
                </div>
                <br/>
                <b>Rules</b>
                <div >
                    {this.state.ruleList.map((rule, index) => {
                        return <Rule location="view" returnState={this.state} rule={rule} index={index} courseId={this.state.courseId} deleteClick={() => this.ruleDeleteClick(index)} />
                    })}
                </div>
                <br/>
                <button onClick={this.reCreateGrouping}> Run grouping with new rules</button>
            </div>
        );
    }
}

export default Grouping;