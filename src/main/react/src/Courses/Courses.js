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
        axios.post('/import-courses', ).then((response) => {
            this.loadCourses();
        });
    };

    loadCourses = () => {
        axios.post('/load-all-courses', ).then((response) => {
            this.setState({courses: response.data});
        });
    };

    deleteCourse = (index) => {
        axios({
            method: 'post',
            url: '/delete-course',
            params: {
                courseId: this.state.courses[index].courseId,
            }
        }).then((response) => {
            this.state.courses.splice(index, 1);
            this.forceUpdate();
        });
    };

    render() {
        return(
            <div>
                {this.state.courses.map((course,index) => {
                    return <Course deleteClick={() => this.deleteCourse(index)} courseName={course.courseName} courseId={course.courseId} />
                })}
                <button type="button" onClick={this.importCourses}>
                    Import Courses
                </button>
            </div>
        );
    }
}

export default Courses;