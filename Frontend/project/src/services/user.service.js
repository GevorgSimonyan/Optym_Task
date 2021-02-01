import axios from "axios";
import {getHeaderWithToken} from "./auth.service";

export function getUser(){
    // return new Promise((res, rej)=>{
    //     setTimeout(()=>{
    //         res({
    //             data:{
    //                 user: {
    //                     "firstName": "Admin",
    //                     "lastName": "Adminsky",
    //                     "role": "ADMIN",
    //                     "id": "6012b6d7bbddaa455019d168",
    //                     "email": "admin@admin.com",
    //                 }
    //             }
    //         })
    //     }, 1000)
    // });
    return axios.get(
        "http://localhost:8080/api/user/getUser",
        getHeaderWithToken()
    );
}


export function updateUser(data){
    // return new Promise((res, rej)=>{
    //     setTimeout(()=>{
    //         res({
    //             data:{
    //                 user: {
    //                     "firstName": "Admin",
    //                     "lastName": "Adminsky",
    //                     "role": "ADMIN1",
    //                     "id": "6012b6d7bbddaa455019d168",
    //                     "email": "admin@admin.com",
    //                 }
    //             }
    //         })
    //     }, 1000)
    // });
    return axios.put(
        "http://localhost:8080/api/user/update",
    data,
        getHeaderWithToken()
    );
}

