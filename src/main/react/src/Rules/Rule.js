import React from 'react';
import {useHistory} from "react-router-dom";

const Rule = (props) => {

    let history = useHistory();
    let editButton;


    if(props.location === 'create'){
        editButton = '';
    } else if(props.location === 'view'){
        editButton = <button onClick={() => history.push({
            pathname: '/editRule',
            data: {returnState: props.returnState, rule: props.rule, courseId: props.courseId, index: props.index}
        })}> Edit </button>
    }

    return <div>
        <p>{props.rule.ruleType}</p>

        {editButton}

        <button  onClick={props.deleteClick}> Delete </button>
    </div>
};

export default Rule;