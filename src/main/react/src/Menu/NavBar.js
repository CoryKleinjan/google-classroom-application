import React from 'react';
import { withRouter } from "react-router-dom";

import './NavBar.css';

class NavBar extends React.Component{

    routeHome() {
        this.props.history.push("/home");
    }

    routeClasses() {
        this.props.history.push("/courses");
    }

    routeGroups() {
        this.props.history.push("/groupings");
    }

    render() {
        return (
            <div className="NavBar">
                <ul className="NavBar">
                    <a className="NavItem" onClick={() => this.routeHome() }><b>HOME</b></a>
                    <a className="NavItem" onClick={() => this.routeClasses() }><b>COURSES</b></a>
                </ul>
            </div>
        );
    }
}

export default withRouter(NavBar);