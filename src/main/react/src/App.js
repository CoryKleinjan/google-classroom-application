import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router';
import Home from './Home/Home';

import Courses from './Courses/Courses';

import Groupings from './Groupings/Groupings';
import Grouping from './Groupings/Grouping';
import createGrouping from './Groupings/createGrouping';

import editGroup from './Groups/editGroup';

import editRule from './Rules/editRule';

import NavBar from './Menu/NavBar';

class App extends React.Component{

    render() {
        return (
            <div className="App">
                <div><NavBar></NavBar></div>
                <header className="App-header">
                    <Switch>
                        <Route path="/editRule" component={editRule}></Route>
                        <Route path="/editGroup" component={editGroup}></Route>
                        <Route path="/courseGrouping" component={Grouping}></Route>
                        <Route path="/createGrouping" component={createGrouping}></Route>
                        <Route path="/groupings" component={Groupings}></Route>
                        <Route path="/courses" component={Courses}></Route>
                        <Route path="/home" component={Home}></Route>
                        <Route path="/" component={Home}></Route>
                    </Switch>
                </header>
            </div>
        )
    }

}

export default App;
