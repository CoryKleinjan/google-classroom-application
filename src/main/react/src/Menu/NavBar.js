import React from 'react';
import { withRouter } from "react-router-dom";

class NavBar extends React.Component{

    routeHome() {
        this.props.history.push("/home");
    }

    routeClasses() {
        this.props.history.push("/classes");
    }

    render() {
        return (
            <div>
                <ul>
                    <a onClick={() => this.routeHome() }>Home</a>
                    <a onClick={() => this.routeClasses() }>Classes</a>
                </ul>
            </div>
        );
    }
}

export default withRouter(NavBar);