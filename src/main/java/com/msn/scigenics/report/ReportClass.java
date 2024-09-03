package com.msn.scigenics.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportClass {

    private BatchHeaderData headerData;

    private List<BatchSetParams> setParamsList;

    private List<BatchStepData> stepDataList;

    private Map<String,List<BatchStepData>> trendsmap = new HashMap<String,List<BatchStepData>>();
    private List<BatchStepData> trendsList;

    private List<AuditTrail> auditTrailList;

    public List<AuditTrail> getAuditTrailList() {
        return auditTrailList;
    }

    private List<AuditTrail> alarmList;

    public void setAuditTrailList(List<AuditTrail> auditTrailList) {
        this.auditTrailList = auditTrailList;
    }

    public BatchHeaderData getHeaderData() {
        return headerData;
    }

    public void setHeaderData(BatchHeaderData headerData) {
        this.headerData = headerData;
    }

    public void addToMap(String key,List<BatchStepData> dataa) {
        trendsmap.put(key,dataa);
    }

    public List<BatchSetParams> getSetParamsList() {
        return setParamsList;
    }

    public void setSetParamsList(List<BatchSetParams> setParamsList) {
        this.setParamsList = setParamsList;
    }

    public List<BatchStepData> getStepDataList() {
        return stepDataList;
    }

    public void setStepDataList(List<BatchStepData> stepDataList) {
        this.stepDataList = stepDataList;
    }

    public List<AuditTrail> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AuditTrail> alarmList) {
        this.alarmList = alarmList;
    }

    public List<BatchStepData> getTrendsList() {
        return trendsList;
    }

    public void setTrendsList(List<BatchStepData> trendsList) {
        this.trendsList = trendsList;


    }

    public Map<String, List<BatchStepData>> getTrendsmap() {
        return trendsmap;
    }

    public void setTrendsmap(Map<String, List<BatchStepData>> trendsmap) {
        this.trendsmap = trendsmap;
    }
}
