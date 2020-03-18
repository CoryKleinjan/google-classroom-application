import React from 'react';
import { withRouter } from "react-router-dom";

import './NavBar.css';

class NavBar extends React.Component{

    routeHome() {
        this.props.history.push("/home");
    }

    routeClasses() {
        this.props.history.push("/classes");
    }

    routeGroups() {
        this.props.history.push("/groups");
    }

    render() {
        return (
            <div className="NavBar">
                <ul className="NavBar">
                    <a className="NavItem" onClick={() => this.routeHome() }>Home</a>
                    <a className="NavItem" onClick={() => this.routeClasses() }>Classes</a>
                    <a className="NavItem" onClick={() => this.routeGroups() }>Groups</a>
                </ul>
            </div>
        );
    }
}

export default withRouter(NavBar);