import React from 'react';
import { useHistory } from "react-router-dom";
import Button from "@material-ui/core/Button";

const GroupingSelector = (props) => {

    let history = useHistory();

    return <div>
        <h1>Grouping {props.groupId}</h1>

        <Button variant="contained" color="primary" onClick={() => history.push({
            pathname: '/courseGrouping',
            data: {groupId: props.groupId, courseId: props.courseId, groupList:props.groupList, ruleList:props.ruleList}
        })}> View Grouping </Button>

        <Button variant="contained" color="primary" onClick={props.deleteClick}> Delete </Button>

    </div>
};

export default GroupingSelector;