import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Navbar from "./components/Navbar";
import HomePage from "./components/HomePage";
import "./App.css"

function App() {
    return (
        <Router>
            <Navbar />
            <Switch>
                <Route path="/" exact component={HomePage}/>
            </Switch>
        </Router>
    );
}

export default App;
