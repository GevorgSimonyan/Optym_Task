package com.gevorg.task.adminmax.utilitis.types;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ServiceResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data;
    private int status;
    private String message;

    public ServiceResponse(T data, String message, int status) {
        this.setData(data);
        this.setStatus(status);
        this.setMessage(message);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @ApiModelProperty(name = "status", notes = "Request status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ApiModelProperty(name = "message", notes = "Request message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

