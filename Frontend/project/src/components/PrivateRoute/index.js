import React from 'react';
import {
    Route,
    Redirect
} from 'react-router-dom';
import {getToken} from "../../services/auth.service";

const PrivateRoute = ({ component: Component, ...rest }) => {
    return (
        <Route {...rest} render={(props) => {
            if(getToken()){
                return  <Component {...props} {...rest}/>

            }
            return <Redirect to='/login' />
        }} />
    )
};

export default PrivateRoute;
