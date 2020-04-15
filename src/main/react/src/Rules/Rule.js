import React from 'react';
import {useHistory} from "react-router-dom";
import Button from "@material-ui/core/Button";

const Rule = (props) => {

    let history = useHistory();
    let editButton;


    if(props.location === 'create'){
        editButton = '';
    } else if(props.location === 'view'){
        editButton = <Button variant="contained" color="primary" onClick={() => history.push({
            pathname: '/editRule',
            data: {returnState: props.returnState, rule: props.rule, courseId: props.courseId, index: props.index}
        })}> Edit </Button>
    }

    return <div>
        <p>{props.rule.ruleType}</p>

        {editButton}

        <Button variant="contained" color="primary" onClick={props.deleteClick}> Delete </Button>
    </div>
};

export default Rule;