import React, {useEffect, useState} from 'react';
import './index.css';
import DataTable from "../DataTable";
import {getCsv, getLogs} from "../../services/logs.service";
import Loading from "../Loading";
import fileDownload from "js-file-download";

const COLUMNS = [
    {
        dataField: "logText",
        text: "Log",
    },
    {
        dataField: "createdOn",
        text: "Creation Date",
        formatter: function formatDateField(dateStr) {
            if (dateStr) {
                return new Date(dateStr).toISOString();
            }
            return ""
        }
    },
];

export default function Admin(){
    const [state, setState] = useState({
        loading: true,
        logs: [],
        totalSize: 0,
        sizePerPage: 5,
        page: 1,
        sortDirection: "1",
        searchBy: "",
        startDate: "",
        endDate: ""
    });

    useEffect(() => {
        const startDate = state.startDate ? new Date(state.startDate).getTime() : "";
        const endDate = state.endDate ? new Date(state.endDate).getTime() : "";
        getLogs( state.page, state.sizePerPage, state.sortDirection, state.searchBy, startDate, endDate)
            .then(response => {
                const {data: {data:{logs, totalCount}}} = response;
                setState(prevState =>{
                    return {
                        ...prevState,
                        loading: false,
                        logs,
                        totalSize: totalCount,
                    }
                });
            })
            .catch(err => alert(err));
    }, [state.sortDirection, state.searchBy, state.startDate, state.endDate]);

    async function handleNewPageClick(e, page) {
        e.preventDefault();
        let currentPage = page;
        if (page === "<") {
            currentPage = Math.max(state.page - 1, 1);
        } else if (page === ">") {
            currentPage = state.page + 1;
        }

        const startDate = state.startDate ? new Date(state.startDate).getTime() : "";
        const endDate = state.endDate ? new Date(state.endDate).getTime() : "";

        let {
            data : {data: { logs, totalCount }}
        } = await getLogs(
            currentPage,
            state.sizePerPage,
            state.sortDirection,
            startDate,
            endDate,
        );

        setState(prevState => {
            return {
                ...prevState,
                logs,
                page: currentPage,
                totalSize: totalCount,
            };
        });
    }

    async function handleExport() {
        try {
            const {data} = await getCsv();
            // fileDownload(data, `report_${Date.now().toLocaleString()}.xlsx`);
            fileDownload(data, `logs.csv`);


        } catch (e) {
            alert(e)
        }
    }
    function  handleSearchChange(e) {
        const searchBy = e.target.value;
        if(searchBy !== state.searchBy){
            setState({
                ...state,
                searchBy,
                page: 1
            });
        }
    }

    function  handleDateChange(e) {
        const name = e.target.name;
        const value = e.target.value;
            setState(prevState => {
                return {
                    ...prevState,
                    [name]: value,
                    page:1
                }
            });
    }

    function handleChange(e) {
        const sortDirection = e.target.value;
        if(sortDirection !== state.sortDirection){
            setState({
                ...state,
                sortDirection,
                page: 1
            });
        }

    }

    if(state.loading){
        return <Loading />;
    }
    return(
        <div className="wrapper mb-5 mt-5">
            <div className="row">
                <div className="col-md-3">
                    <div className="form-group">
                        <label>Sort by Date:</label>
                        <select className="form-control" onChange={handleChange} value={state.sortDirection}>
                            <option value="1">ASC</option>
                            <option value="-1">DSC</option>
                        </select>
                    </div>
                </div>
                <div className="col-md-3">
                    <div className="form-group">
                        <label>Search by Email:</label>
                        <input type="text" className="form-control" value={state.searchBy} onChange={handleSearchChange}/>
                    </div>
                </div>
                <div className="col-md-4">
                   <div className="row">
                       <div className="col md-6">
                           <div className="form-group">
                               <label>Start Date:</label>
                               <input type="date" name="startDate" className="form-control" value={state.startDate} onChange={handleDateChange}/>
                           </div>
                       </div>
                       <div className="col md-6">
                           <div className="form-group">
                               <label>End Date:</label>
                               <input type="date" name="endDate" className="form-control" value={state.endDate} onChange={handleDateChange}/>
                           </div>
                       </div>
                   </div>
                </div>
                <div className="col-md-2">
                    <label>Export</label>
                    <button className="form-control" onClick={handleExport}>Download</button>
                </div>
            </div>

            <div>
                <DataTable
                    data={state.logs}
                    page={state.page}
                    sizePerPage={state.sizePerPage}
                    totalSize={state.totalSize}
                    columns={COLUMNS}
                    onGetData={handleNewPageClick}
                    loading={true}
                />
            </div>
        </div>
    )
}



