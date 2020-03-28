import React from 'react';
import { useHistory } from "react-router-dom";

const Course = (props) => {

    let history = useHistory();

    return <div>
            <p>{props.courseName}</p>

            <button onClick={() => history.push({
                pathname: '/editGrouping',
                data: {courseId: props.courseId}
            })}> Create Grouping </button>

            <button  onClick={props.deleteClick}> Delete </button>

           </div>
};

export default Course;