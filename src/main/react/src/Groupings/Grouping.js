import React, {Component} from 'react';
import Group from "../Groups/Group";

class Grouping extends Component {

    constructor(props) {
        super(props);
        this.state = {
            groupList: props.location.data.groupList,
            courseId: props.location.data.courseId,
            ruleList: props.location.data.ruleList
        };
    }

    render() {

        console.log(this.state);

        return(
            <div>
                {this.state.groupList.map((group, index) => {
                    return <Group groupNumber={index} groupId={group.groupId} group={group.studentList} />
                })}
            </div>
        );
    }
}

export default Grouping;