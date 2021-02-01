import axios from "axios";

export const getHeaderWithToken = () => {
    const token =  JSON.parse(getToken());
    return {
        headers: {
            Authorization: `Bearer ${token}`
        }
    };
};

export function setToken(token) {
    localStorage.setItem('token', JSON.stringify(token));
}

export function getToken() {
    return localStorage.getItem('token');
}

export function removeToken() {
    return localStorage.removeItem('token');
}

export function login({email, password}) {
    // return new Promise((res, rej) => {
    //     setTimeout(() => {
    //         res({
    //             data: {
    //                 token: "sdfsfsdfdsfsdfsdfsdf"
    //             }
    //         })
    //     }, 1000)
    // });
    return axios.post("http://localhost:8080/api/auth/login", {
        email,
        password
    });
};


export function logout() {
    // return new Promise((res, rej) => {
    //     setTimeout(() => {
    //         res({
    //             data: {
    //                 token: "sdfsfsdfdsfsdfsdfsdf"
    //             }
    //         })
    //     }, 1000)
    // });
    return axios.post("http://localhost:8080/api/auth/logout", null,  getHeaderWithToken());
}
