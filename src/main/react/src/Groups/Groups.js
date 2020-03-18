import React, { Component } from 'react';
import axios from "axios";

class Groups extends Component {

    groupTest = () => {
        axios({
            method: 'get',
            url: '/groupTest',
            params: {
                courseId: 198,
                numberOfGroups: 2,
            }
        }).then(function(response){
            console.log(response);
        });
    };

    render() {
        return(
            <div>
                <button type="button" onClick={this.groupTest}>
                    Group Test
                </button>
            </div>
        );
    }
}

export default Groups;