import React from 'react';

const Student = (props) => {

    return <div>
        {props.student.studentName}
        <button onClick={props.deleteClick}> Delete</button>
    </div>
};

export default Student;