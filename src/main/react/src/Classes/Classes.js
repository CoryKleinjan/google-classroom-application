import React, { Component } from 'react';
import axios from "axios";

class Classes extends Component {

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
                <button type="button" onClick={this.importTest}>
                    Import Test Class
                </button>
                <button type="button" onClick={this.loadTest}>
                    Load Test Class
                </button>
            </div>
        );
    }
}

export default Classes;