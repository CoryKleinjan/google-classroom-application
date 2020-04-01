import React, { Component } from 'react';
import axios from "axios";

class Groupings extends Component {

    constructor(props) {
        super(props);
        this.state = {
            groupings: props.location.data.grouping,
            courseId: props.location.data.courseId,
        };
    }

    componentDidMount() {
        this.loadGroupings();
        console.log(this.state);
    }

    loadGroupings = () => {
        axios.post('/load-groupings-by-course-id', ).then((response) => {
            this.setState({groupings: response.data});
        });
    };

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
            </div>
        );
    }
}

export default Groupings;