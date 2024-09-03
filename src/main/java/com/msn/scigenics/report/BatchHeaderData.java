package com.msn.scigenics.report;


import java.sql.Date;
import java.sql.Timestamp;

public class BatchHeaderData {
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }



    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    private String batchId;

    private Timestamp startDateTime;

    private Timestamp endDateTime;

    private String duration;

    private String processName;

    private String batchStartedBy;

    public String getBatchStartedBy() {
        return batchStartedBy;
    }

    public void setBatchStartedBy(String batchStartedBy) {
        this.batchStartedBy = batchStartedBy;
    }
}
