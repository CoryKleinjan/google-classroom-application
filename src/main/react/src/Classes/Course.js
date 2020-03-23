import React from 'react';

const Course = (props) => {
    return <div>
            <p>{props.courseName}</p>
            <button> Create Group</button>
            <button> Delete </button>
           </div>
};

export default Course;