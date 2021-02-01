package com.gevorg.task.adminmax.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
public class Log extends BaseModel {

    @DBRef(db="user")
    private User user;
    private String logText;

    public Log() {

    }

    public Log(User user, String logText) {
        this.user = user;
        this.logText = logText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }
}
