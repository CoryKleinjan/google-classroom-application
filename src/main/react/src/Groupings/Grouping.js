import React, {Component} from 'react';
import Group from "../Groups/Group";
import axios from "axios";
import GroupingSelector from "./GroupingSelector";
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
                <div>
                    {this.state.groupList.map((group, index) => {
                        return <Group groupNumber={index} groupId={group.groupId} group={group.studentList} rules={this.state.ruleList[index]}/>
                    })}
                </div>
                <div >
                    {this.state.ruleList.map((rule, index) => {
                        return <Rule rule={rule} deleteClick={() => this.ruleDeleteClick(index)} />
                    })}
                </div>
            </div>
        );
    }
}

export default Grouping;