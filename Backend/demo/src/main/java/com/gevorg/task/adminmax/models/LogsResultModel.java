package com.gevorg.task.adminmax.models;

import java.util.List;

public class LogsResultModel {

    private int totalCount;
    private List<Log> logs;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
