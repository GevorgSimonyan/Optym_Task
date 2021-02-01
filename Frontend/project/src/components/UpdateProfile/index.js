import React, { useState } from 'react';
import './index.css';
import { updateUser } from "../../services/user.service";

export default function UpdateProfile({ user }) {
    const [state, setState] = useState({
        firstName: user ?.firstName || "",
        lastName: user ?.lastName || "",
        newPassword: "",
        oldPassword: "",
        saving: false
    });


    function handleChange(e) {
        const value = e.target.value;
        const name = e.target.name;
        setState({
            ...state,
            [name]: value
        })
    }

    async function handleSubmit(e) {
        e.preventDefault();

        let data = {
            firstName: state.firstName,
            lastName: state.lastName
        };

        if (state.newPassword) {
            data = {
                ...data,
                newPassword: state.newPassword,
                oldPassword: state.oldPassword
            }
        }

        setState(prevState => {
            return {
                ...prevState,
                saving: true
            }
        });
        try {
            await updateUser(data);
            setState(prevState => {
                return {
                    ...prevState,
                    newPassword: "",
                    oldPassword: "",
                    saving: false
                }
            });
            alert("Success!");
        } catch (e) {
            const text = e.response?.data?.message || e;
            alert(text);
            setState(prevState => {
                return {
                    ...prevState,
                    saving: false
                }
            });
        }

    }

    const submitIsDisabled = String(state.firstName).trim() === "" && String(state.password).trim() === "";
    const btnText = state.saving ? "Updating..." : "Update";
    return (
        <div className="container">
            <h3 className="text-center">Update Profile</h3>
            <form autoComplete="off" className="form">
                <div className="form-group">
                    <label>First Name</label>
                    <input className="form-control" type="text" name="firstName" onChange={handleChange} value={state.firstName} />
                </div>
                <div className="form-group">
                    <label>Last Name</label>
                    <input className="form-control" type="text" name="lastName" onChange={handleChange} value={state.lastName} />
                </div>
                <div className="form-group">
                    <p>New Password</p>
                    <input className="form-control" type="password" name="newPassword" onChange={handleChange} autoComplete="new-password"
                        value={state.newPassword} />
                </div>
                <div className="form-group">
                    <label>Old Password</label>
                    <input className="form-control" type="password" name="oldPassword" onChange={handleChange} value={state.oldPassword} />
                </div>
                <div className="mt-20">
                    <button className="form-control" type="submit" onClick={handleSubmit} disabled={submitIsDisabled || state.saving}>{btnText}</button>
                </div>
            </form>
        </div>
    )
}
