import React, {Component} from 'react';
import axios from "axios";
import Group from "../Groups/Group";

class Grouping extends Component {

    constructor(props) {
        super(props);
        this.state = {
            groupings: props.location.data.grouping,
            courseId: props.location.data.courseId,
            groupData: props.location.data.groupPackage
        };
    }

    deleteGrouping = (index) => {
        axios({
            method: 'post',
            url: '/delete-grouping',
            params: {
                groupingId: this.state.groupings[index].groupingId,
            }
        }).then((response) => {
            this.state.groupings.splice(index, 1);
            this.forceUpdate();
        });
    };

    render() {
        return(
            <div>
                {this.state.groupings.map((group, index) => {
                    return <Group groupNumber={index} group={group} />
                })}
            </div>
        );
    }
}

export default Grouping;