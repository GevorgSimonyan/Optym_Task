package com.gevorg.task.adminmax.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

public abstract  class BaseModel {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @CreatedDate
    @ApiModelProperty(hidden = true)
    private Date createdOn= new Date();

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private Date updatedOn = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
