import React from 'react';
import {useHistory} from "react-router-dom";

const Rule = (props) => {

    let history = useHistory();

    return <div>
        <p>{props.rule.ruleType}</p>

        <button onClick={() => history.push({
            pathname: '/editRule',
            data: {returnState: props.returnState, rule: props.rule, courseId: props.courseId, index: props.index}
        })}> Edit </button>

        <button  onClick={props.deleteClick}> Delete </button>
    </div>
};

export default Rule;