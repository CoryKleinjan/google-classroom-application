import React, { Component } from 'react';
import axios from "axios";
import GroupingSelector from "./GroupingSelector";

class Groupings extends Component {

    constructor(props) {
        super(props);
        this.state = {
            groupings: [],
            courseId: props.location.data.courseId,
        };
    }

    componentDidMount() {
        this.loadGroupings();
    }

    loadGroupings = () => {
        axios({
            method: 'post',
            url: '/load-groupings-by-course-id',
            params: {
                courseId: this.state.courseId,
            }
        }).then((response) => {
            this.setState({
                groupings: response.data
            });
        });
    };

    deleteGrouping = (index, groupingId) => {
        axios({
            method: 'post',
            url: '/delete-grouping',
            params: {
                groupingId: groupingId,
            }
        }).then((response) => {
            this.state.groupings.splice(index, 1);
            this.forceUpdate();
        });
    };

    render() {
        return(
            <div>
                {this.state.groupings.map((grouping,index) => {
                    return <GroupingSelector deleteClick={() => this.deleteGrouping(index, grouping.groupId)} courseId={this.state.courseId} groupId={grouping.groupId} groupList={grouping.groupList} ruleList={grouping.ruleList}/>
                })}
            </div>
        );
    }
}

export default Groupings;