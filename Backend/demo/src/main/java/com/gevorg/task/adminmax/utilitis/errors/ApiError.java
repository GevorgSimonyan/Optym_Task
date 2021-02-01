package com.gevorg.task.adminmax.utilitis.errors;

import org.springframework.http.HttpStatus;

public class ApiError {
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private HttpStatus httpStatus;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;


    public String getMessage() {
        return message;
    }

    public ApiError(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.status =httpStatus.value();
    }
}
