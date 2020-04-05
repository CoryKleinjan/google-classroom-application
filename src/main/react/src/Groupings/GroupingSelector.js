import React from 'react';
import { useHistory } from "react-router-dom";

const GroupingSelector = (props) => {

    let history = useHistory();

    return <div>
        <p>Group {props.groupId}</p>

        <button onClick={() => history.push({
            pathname: '/courseGrouping',
            data: {groupId: props.groupId, courseId: props.courseId, groupList:props.groupList, ruleList:props.ruleList}
        })}> View Grouping </button>

        <button  onClick={props.deleteClick}> Delete </button>

    </div>
};

export default GroupingSelector;