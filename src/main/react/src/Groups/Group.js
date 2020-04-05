import React, { Component } from 'react';
import {useHistory} from "react-router-dom";

const Group  = (props) => {

    let history = useHistory();

    return(
        <div>
            <p>Group {props.index + 1}</p>
            {props.group.map(student => {
                return <p>{student.studentName}</p>
            })}
            <button onClick={() => history.push({
                pathname: '/editGroup',
                data: {returnState: props.returnState, group: props.group, courseId: props.courseId, groupId: props.groupId, index: props.index}
            })}> Edit </button>

            <button  onClick={props.deleteClick}> Delete </button>
        </div>
    );
};

export default Group;