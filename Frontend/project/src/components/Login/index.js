import React, {useState} from 'react';
import './index.css';
import { login, setToken} from "../../services/auth.service";
import {useHistory} from "react-router";

export default function Login() {
    const [state, setState] = useState({
        email: "",
        password: ""
    });
    const history = useHistory();

    function handleChange(e){
        const value = e.target.value;
        const name = e.target.name;
        setState({
            ...state,
            [name]:value
        })
    }

    async function handleSubmit(e) {
        e.preventDefault();

        try {
            const {data:{data: {token}}} = await login({email: state.email, password: state.password});
            if (token) {
                setToken(token);
                return history.push("/home");
            }
        } catch (e) {
            const text = e.response?.data?.message || e;
            alert(text);
        }

    }

    const submitIsDisabled = String(state.email).trim()==="" || String(state.password).trim()==="";

    return(
        <div className="container">
            <h3 className="m-5 text-center">Please Log In (Sign In)</h3>
            <div className="row flex justify-content-center align-items-center">
                <div className="col-md-6">
                    <form className="form">
                        <div className="form-group">
                            <label>Email</label>
                            <input className="form-control" type="email" name="email" onChange={handleChange} />
                        </div>
                        <div className="form-group">
                            <label>Password</label>
                            <input  className="form-control" type="password" name="password" onChange={handleChange}/>
                        </div>
                        <div>
                            <button className="form-control" type="submit" onClick={handleSubmit} disabled={submitIsDisabled}>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}
