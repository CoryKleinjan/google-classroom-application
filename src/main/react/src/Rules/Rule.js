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

    let ruleDisplay

    if(props.rule.ruleType === "notTogether"){
        ruleDisplay = "Not together";
    } else if(props.rule.ruleType === "together"){
        ruleDisplay = "Together";
    } else if(props.rule.ruleType === "topInEach"){
        ruleDisplay = "Top student in each group";
    } else if(props.rule.ruleType === "bottomInEach"){
        ruleDisplay = "Struggling student in each group";
    } else if(props.rule.ruleType === "random"){
        ruleDisplay = "Random";
    }

    return <div>
        <p>{ruleDisplay}</p>

        {editButton}

        <Button variant="contained" color="primary" onClick={props.deleteClick}> Delete </Button>
    </div>
};

export default Rule;