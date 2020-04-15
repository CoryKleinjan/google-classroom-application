import React, { Component } from 'react';
import {useHistory} from "react-router-dom";
import Button from "@material-ui/core/Button";

const Group  = (props) => {

    let history = useHistory();

    return(
        <div>
            <p>Group {props.index + 1}</p>
            {props.group.map(student => {
                return <p>{student.studentName}</p>
            })}
            <Button variant="contained" color="primary" onClick={() => history.push({
                pathname: '/editGroup',
                data: {returnState: props.returnState, group: props.group, courseId: props.courseId, groupId: props.groupId, index: props.index}
            })}> Edit </Button>

            <Button variant="contained" color="primary" onClick={props.deleteClick}> Delete </Button>
        </div>
    );
};

export default Group;