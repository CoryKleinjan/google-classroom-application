import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router';
import Home from './Home/Home';
import Import from './Import/Import';

class App extends React.Component{

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <Switch>
                        <Route path="/import" component={Import}></Route>
                        <Route path="/home" component={Home}></Route>
                        <Route path="/" component={Home}></Route>
                    </Switch>
                </header>
            </div>
        )
    }

}

export default App;
