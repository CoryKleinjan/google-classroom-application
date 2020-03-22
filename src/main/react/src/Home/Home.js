import React, { Component } from 'react';
import axios from "axios";

class Home extends Component {

    getUrlVars = () => {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
        });
        return vars;
    };

    componentDidMount() {
        let token = this.getUrlVars();

        if(token["code"]) {
            axios.post('/oauth', null, { params: {
                    token: token["code"]
                }}).then(function(){
                window.history.replaceState({}, document.title, "/");
            });
        }
    }

    render() {
        return(
            <div>
            </div>
        );
    }

}

export default Home;