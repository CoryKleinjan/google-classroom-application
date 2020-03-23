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

    importCourses = () => {
        axios.post('/import-courses', ).then(function(response){
            this.loadCourses();
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
                    return <Course courseName={course.courseName} courseId={course.courseId} />
                })}
                <button type="button" onClick={this.importCourses}>
                    Import Courses
                </button>
            </div>
        );
    }
}

export default Courses;