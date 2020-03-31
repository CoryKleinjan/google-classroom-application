import React from 'react';

const Rule = (props) => {

    return <div>
        <p>{props.rule.ruleType}</p>

        <button  onClick={props.deleteClick}> Delete </button>
    </div>
};

export default Rule;