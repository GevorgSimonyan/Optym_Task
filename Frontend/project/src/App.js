import React from 'react';
import {BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';
import Login from "./components/Login";
import PrivateRoute from "./components/PrivateRoute";
import Home from "./components/Home";
import {Redirect} from "react-router";

function App() {
    return (
        <div className="wrapper">
            <BrowserRouter>
                <Switch>
                  <Route
                      exact
                      path="/"
                      render={() => {
                        return (
                                <Redirect to="/home" />
                        )
                      }}
                  />
                    <Route path="/login" component={Login} />
                    <PrivateRoute path="/home" component={Home} />
                    <Redirect from="*" to='/home' />
                </Switch>
            </BrowserRouter>
        </div>
    );
}

export default App;
