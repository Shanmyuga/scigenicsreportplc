package com.msn.scigenics.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchStepData {

    private String batchStep;

    private Timestamp logTime;


private Map<String,Object> actualValueMap = new HashMap<String,Object>();

    public Map<String, Object> getActualValueMap() {
        return actualValueMap;
    }

    public void setActualValueMap(Map<String, Object> actualValueMap) {
        this.actualValueMap = actualValueMap;
    }





    public String getBatchStep() {
        return batchStep;
    }

    public void setBatchStep(String batchStep) {
        this.batchStep = batchStep;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }






    public void addtoMap(String key,Object value) {

        this.actualValueMap.put(key,value);
    }
}
