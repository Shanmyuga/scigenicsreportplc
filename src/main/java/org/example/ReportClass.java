package org.example;

import java.util.List;

public class ReportClass {

    private BatchHeaderData headerData;

    private List<BatchSetParams> setParamsList;

    private List<BatchStepData> stepDataList;

    public BatchHeaderData getHeaderData() {
        return headerData;
    }

    public void setHeaderData(BatchHeaderData headerData) {
        this.headerData = headerData;
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
}
