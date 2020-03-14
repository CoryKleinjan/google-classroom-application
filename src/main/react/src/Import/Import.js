import React, { Component } from 'react';
import axios from "axios";

class Import extends Component {


    routeHome = () => {
        this.props.history.push("/home");
    };

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

    importTest = () => {
        axios.post('/importTest', ).then(function(response){
            console.log(response);
        });
    };

    loadTest = () => {
        axios.post('/loadTest', ).then(function(response){
            console.log(response);
        });
    };

    render() {
        return(
            <div>
                <button type="button" onClick={this.routeHome}>
                    Go to Home
                </button>
                <button type="button" onClick={this.importTest}>
                    Import Test Class
                </button>
                <button type="button" onClick={this.loadTest}>
                    Load Test Class
                </button>
                <button type="button" onClick={this.groupTest}>
                    Group Test
                </button>
            </div>
        );
    }

}

export default Import;