import React from 'react';
import { useHistory } from "react-router-dom";

const Course = (props) => {

    let history = useHistory();

    return <div>
            <p>{props.courseName}</p>

            <button onClick={() => history.push({
                pathname: '/editGroup',
                data: {courseId: props.courseId}
            })}> Create Group </button>

            <button > Delete </button>

           </div>
};

export default Course;