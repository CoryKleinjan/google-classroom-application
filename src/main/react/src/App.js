import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router';
import Home from './Home/Home';

import Courses from './Courses/Courses';

import Groupings from './Groupings/Groupings';
import editGrouping from './Groupings/editGrouping';

import NavBar from './Menu/NavBar';

class App extends React.Component{

    render() {
        return (
            <div className="App">
                <div><NavBar></NavBar></div>
                <header className="App-header">
                    <Switch>
                        <Route path="/courseGroups" component={Groups}></Route>
                        <Route path="/editGroup" component={editGroup}></Route>
                        <Route path="/courseGroupings" component={Groupings}></Route>
                        <Route path="/editGrouping" component={editGrouping}></Route>
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
