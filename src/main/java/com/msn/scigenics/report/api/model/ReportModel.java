package com.msn.scigenics.report.api.model;

public class ReportModel {

    private String jsonkey;

    private String fermenter;

    private String processType;

    public String getJsonkey() {
        return jsonkey;
    }

    public void setJsonkey(String jsonkey) {
        this.jsonkey = jsonkey;
    }

    public String getFermenter() {
        return fermenter;
    }

    public void setFermenter(String fermenter) {
        this.fermenter = fermenter;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }
}
