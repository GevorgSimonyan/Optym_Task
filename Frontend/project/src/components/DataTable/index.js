import React from "react";
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";


function getNoDataContent() {
    return <p>No Data</p>;
}

function DataTable({
    page,
    sizePerPage,
    totalSize,
    columns,
    data,
    onGetData,
    loading,
}) {
    let options = {};

    const pageButtonRenderer = ({
        page,
        active,
        disabled,
        title,
        onPageChange
    }) => {
        return (
            <li
                key={page}
                className={`page-item ${active ? "active" : ""}`}
                title={page}
                onClick={e => onGetData(e, page)}
            >
                <a href="#" className={`page-link`}>
                    {page}
                </a>
            </li>
        );
    };

    const pagination = paginationFactory({
        page: page,
        totalSize: totalSize,
        sizePerPage: sizePerPage,
        paginationSize: 10,
        hideSizePerPage: true,
        hidePageListOnlyOnePage: true,
        withFirstAndLast: false,
        pageButtonRenderer
    });

    if (sizePerPage) {
        options.pagination = pagination;
    }

    return (
        <div >
            <BootstrapTable
                remote
                keyField="id"
                data={data}
                page={page}
                sizePerPage={sizePerPage}
                totalSize={totalSize}
                columns={columns}
                loading={loading}
                bordered={false}
                {...options}
                striped
                hover
                noDataIndication={getNoDataContent}
            />
        </div>
    );
}

export default DataTable;
