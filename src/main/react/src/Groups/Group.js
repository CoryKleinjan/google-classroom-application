import React, { Component } from 'react';


class Group extends Component {

    render() {
        return(
            <div>
                <p>Group {this.props.groupNumber + 1}</p>

                {this.props.group.map(student => {
                    return <p>{student.studentName}</p>
                })}

            </div>
        );
    }
}

export default Group;