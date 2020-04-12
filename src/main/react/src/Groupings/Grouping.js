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
            ruleType: '',
            studentList: [],
            showError: false
        };
    }

    componentDidMount() {
        this.getStudentList();
    }

    findWithAttr = (array, attr, value) => {
        for(let i = 0; i < array.length; i += 1) {
            if(array[i][attr] === value) {
                return i;
            }
        }
        return -1;
    };

    getStudentList = () => {
        axios({
            method: 'get',
            url: '/get-student-list-by-course',
            params: {
                courseId: this.state.courseId,
            }
        }).then((response) => {
            this.setState({studentList: response.data});
        });
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
            this.setState({showError: false});
            this.forceUpdate();
        }).catch((error) => {
            this.setState({showError: true});
            this.forceUpdate();
        });
    };

    addNewRule = () => {
        this.state.ruleType = 'newRule';
        this.forceUpdate()
    };

    submitRule = () => {
        let rule = {};
        let ruleList = this.state.ruleList;

        rule.ruleType = this.state.ruleType;
        rule.firstStudentId = this.state.firstStudent;
        rule.secondStudentId = this.state.secondStudent;

        axios({
            method: 'post',
            url: '/create-rule',
            data: rule
        }).then((response) => {
            rule.ruleId = response.data;

            ruleList.push(rule);

            this.setState({
                ruleList: ruleList,
                ruleType: '',
                firstStudent: '',
                secondStudent: ''
            });
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

    render() {
        if(this.state.ruleType === 'newRule'){
            this.ruleBuilder = <form>
                <label> Pick Rule Type:  </label>
                <select value={this.state.ruleType} onChange={this.ruleTypeChangeHandler}>
                    <option value=""> Please choose a rule type.</option>
                    <option value="notTogether"> Students cannot share group</option>
                    <option value="together"> Students must share group</option>
                    <option value="topInEach"> Top student in every group</option>
                    <option value="bottomInEach"> Bottom student in every group</option>
                    <option value="random"> Random </option>
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

        } else if( this.state.ruleType === "topInEach"){

            this.ruleBuilder = <div>
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>
        } else if( this.state.ruleType === "bottomInEach"){

            this.ruleBuilder = <div>
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>
        } else if( this.state.ruleType === "random"){

            this.ruleBuilder = <div>
                <button onClick={this.submitRule}>Submit Rule</button>
            </div>
        }else{
            this.ruleBuilder = <button onClick={this.addNewRule}> Add Rule</button>
        }

        if(this.state.showError === true) {
            this.error = <span>YOUR GROUP CANT BE CREATED WITH CURRENT RULES.</span>;
        } else{
            this.error = '';
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
                    {this.ruleBuilder}
                </div>
                <br/>
                {this.error}
                <button onClick={this.reCreateGrouping}> Run grouping with new rules</button>
            </div>
        );
    }
}

export default Grouping;