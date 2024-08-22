package org.example;

import java.sql.Timestamp;

public class BatchActualValues {

    private String actualProcessVariableName;

    private String getActualProcessVariableValue;

    private String getActualProcessVariableUnits;

    private Timestamp actualValueChangeTimeStamp;

    public String getActualProcessVariableName() {
        return actualProcessVariableName;
    }

    public void setActualProcessVariableName(String actualProcessVariableName) {
        this.actualProcessVariableName = actualProcessVariableName;
    }

    public String getGetActualProcessVariableValue() {
        return getActualProcessVariableValue;
    }

    public void setGetActualProcessVariableValue(String getActualProcessVariableValue) {
        this.getActualProcessVariableValue = getActualProcessVariableValue;
    }

    public String getGetActualProcessVariableUnits() {
        return getActualProcessVariableUnits;
    }

    public void setGetActualProcessVariableUnits(String getActualProcessVariableUnits) {
        this.getActualProcessVariableUnits = getActualProcessVariableUnits;
    }

    public Timestamp getActualValueChangeTimeStamp() {
        return actualValueChangeTimeStamp;
    }

    public void setActualValueChangeTimeStamp(Timestamp actualValueChangeTimeStamp) {
        this.actualValueChangeTimeStamp = actualValueChangeTimeStamp;
    }
}
