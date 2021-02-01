import React, {useEffect, useState} from "react";

import './index.css';
import {getUser} from "../../services/user.service";
import {logout, removeToken} from "../../services/auth.service";
import Admin from "../Admin";
import UpdateProfile from "../UpdateProfile";
import {useHistory} from "react-router";
import Loading from "../Loading";

export default function Main(){
    const [user, setUser] = useState(null);
    const history = useHistory();

    useEffect(()=>{
        async function getUserInfo() {
            try {
                const {data: {data: user}} = await getUser();
                setUser(user);
            } catch (e) {
                const text = e.response?.data?.message || e;
                alert(text);
            }
        }
        getUserInfo();
    }, []);


    async function logoutUser(){
        
        await logout();
        removeToken();
        return history.push("/login");
    }

    if(!user){
        return <Loading />;
    }
    let Component = null;
    let props = {};
    if(user){
        if(String(user.role).toUpperCase() === "ADMIN"){
            Component = Admin;
        }else{
            Component = UpdateProfile;
            props.user = user;
        }

    }
    return(
        <div className="Home">
            <div className="header">
                <button onClick={logoutUser}>Logout</button></div>
            <div>
                <Component {...props}/>
            </div>
        </div>
    )
}
