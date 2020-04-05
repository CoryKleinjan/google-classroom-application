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
            ruleList: props.location.data.ruleList
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

    render() {

        console.log(this.state);

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
                        return <Rule returnState={this.state} rule={rule} index={index} courseId={this.state.courseId} deleteClick={() => this.ruleDeleteClick(index)} />
                    })}
                </div>
            </div>
        );
    }
}

export default Grouping;