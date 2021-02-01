import axios from "axios";
import {getHeaderWithToken} from "./auth.service";

export function getLogs(page, pageSize, sortDirection, searchBy, startDate, endDate) {
    // console.log("get: ", `http://localhost:8080/api/user/getLogs?pageNo=${page}&pageSize=${pageSize}&sortDirection=${sortDirection}&email=${searchBy}&startDate=${startDate}&endDate=${endDate}`);
    // return new Promise((res, rej) => {
    //     setTimeout(() => {
    //         res({
    //             data: {
    //                 logs: [
    //                     {
    //                         id: "1",
    //                         "logText": "lorm  asdasdasdasd",
    //                         "createdOn": new Date()
    //                     },
    //                     {
    //                         id: "2",
    //                         "logText": "lorm  asdasdasdasd",
    //                         "createdOn": new Date()
    //                     },
    //                     {
    //                         id: "3",
    //                         "logText": "lorm  asdasdasdasd",
    //                         "createdOn": new Date()
    //                     },
    //                     {
    //                         id: "4",
    //                         "logText": "lorm  asdasdasdasd",
    //                         "createdOn": new Date()
    //                     },
    //                     {
    //                         id: "5",
    //                         "logText": "lorm  asdasdasdasd",
    //                         "createdOn": new Date()
    //                     },
    //                 ],
    //                 count: 7
    //             }
    //         })
    //     }, 1000)
    // });
    return axios.get(
        `http://localhost:8080/api/log/logs?pageNo=${page}&pageSize=${pageSize}&sortDirection=${sortDirection}&email=${searchBy}&startDate=${startDate}&endDate=${endDate}`,
        getHeaderWithToken()
    );
}

export function getCsv() {
    return axios.get(
        `http://localhost:8080/api/log/exportLogs`,
        getHeaderWithToken()
    );
}



