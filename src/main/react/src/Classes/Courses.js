import React, { Component } from 'react';
import axios from "axios";

import Course from './Course';

class Courses extends Component {

    constructor(props) {
        super(props);
        this.state = {
            courses: []
        };
    }

    componentDidMount() {
    this.loadCourses();
    }

    importTest = () => {
        axios.post('/importTest', ).then(function(response){
            console.log(response);
        });
    };

    loadCourses = () => {
        axios.post('/load-all-courses', ).then((response) => {
            this.setState({courses: response.data});
        });
    };

    render() {
        return(
            <div>
                {this.state.courses.map(course => {
                    return <Course courseName={course.courseName} />
                })}
                <button type="button" onClick={this.importTest}>
                    Import Test Class
                </button>
            </div>
        );
    }
}

export default Courses;