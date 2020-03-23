import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router';
import Home from './Home/Home';
import Courses from './Classes/Courses';
import Groups from './Groups/Groups';
import NavBar from './Menu/NavBar';

class App extends React.Component{

    render() {
        return (
            <div className="App">
                <div><NavBar></NavBar></div>
                <header className="App-header">
                    <Switch>
                        <Route path="/groups" component={Groups}></Route>
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
