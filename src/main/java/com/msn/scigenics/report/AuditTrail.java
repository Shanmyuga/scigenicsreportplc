package com.msn.scigenics.report;

import java.sql.Timestamp;

public class AuditTrail {

    private Timestamp logTimeStamp;

    private String actor;
    private String categoryName;

    private String condition;

    private String source;

    private String block;
    private String value;
    private String description;
    private String areaName;

    private String alarmUnit;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getLogTimeStamp() {
        return logTimeStamp;
    }

    public void setLogTimeStamp(Timestamp logTimeStamp) {
        this.logTimeStamp = logTimeStamp;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAlarmUnit() {
        return alarmUnit;
    }

    public void setAlarmUnit(String alarmUnit) {
        this.alarmUnit = alarmUnit;
    }
}
