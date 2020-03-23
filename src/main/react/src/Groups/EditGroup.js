import React, { Component } from 'react';
import axios from "axios";

class editGroup extends Component {

    createGroup = () => {
        axios({
            method: 'get',
            url: '/create-groups',
            params: {
                courseId: this.props.location.data.courseId,
                numberOfGroups: 2,
            }
        }).then(function(response){
            console.log(response);
        });
    };

    render() {
        return(
            <div>
                <button type="button" onClick={this.createGroup}>
                    Create Groups
                </button>
            </div>
        );
    }
}

export default editGroup;