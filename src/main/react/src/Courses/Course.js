import React from 'react';
import { useHistory } from "react-router-dom";
import Button from "@material-ui/core/Button";

const Course = (props) => {

    let history = useHistory();

    return <div>
        <p>{props.courseName}</p>

        <Button variant="contained" color="primary" onClick={() => history.push({
            pathname: '/createGrouping',
            data: {courseId: props.courseId}
        })}> Create Grouping </Button>

        <Button variant="contained" color="primary" onClick={() => history.push({
            pathname: '/groupings',
            data: {courseId: props.courseId}
        })}> View Groupings </Button>

        <Button variant="contained" color="primary"  onClick={props.deleteClick}> Delete </Button>

    </div>
};

export default Course;