import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router';
import Home from './Home/Home';

import Courses from './Courses/Courses';

import Groups from './Groups/Groups';
import editGroup from './Groups/editGroup';

import NavBar from './Menu/NavBar';

class App extends React.Component{

    render() {
        return (
            <div className="App">
                <div><NavBar></NavBar></div>
                <header className="App-header">
                    <Switch>
                        <Route path="/classGroups" component={Groups}></Route>
                        <Route path="/editGroup" component={editGroup}></Route>
                        <Route path="/classes" component={Courses}></Route>
                        <Route path="/home" component={Home}></Route>
                        <Route path="/" component={Home}></Route>
                    </Switch>
                </header>
            </div>
        )
    }

}

export default App;
