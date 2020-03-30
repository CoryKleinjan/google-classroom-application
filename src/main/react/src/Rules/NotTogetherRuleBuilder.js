import React from 'react';

const NotTogetherRuleBuilder = (props) => {

    let firstStudent = <select value={this.state.firstStudent} onChange={this.firstStudentChangeHandler}>{props.studentList.map((x,y) => <option value={y} key={y}>{x}</option>)}</select>;
    let secondStudent = <select value={this.state.secondStudent} onChange={this.secondStudentChangeHandler}>{props.studentList.map((x,y) => <option value={y} key={y}>{x}</option>)}</select>;

    return <div>
        test
        {firstStudent}
        {secondStudent}
    </div>
};

export default NotTogetherRuleBuilder;