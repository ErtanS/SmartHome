package com.example.hal9000.smarthome.Helper;

import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;

/**
 * Speichern von Informationen die in Buttons hinterlegt werden
 */
public class Tag {

    private final String type;
    private int state;
    private DeviceDataSet dataSet;
    private ScenarioDataSet scenarioDataSet;

    public Tag(String type, DeviceDataSet dataSet) {
        this.type = type;
        this.dataSet = dataSet;
    }

    public Tag(String type, ScenarioDataSet scenario) {
        this.type = type;
        this.scenarioDataSet = scenario;
    }

    public Tag(DeviceDataSet scenarioDevice, int selectState) {
        this.type = Config.STRING_TAG_SELECT;
        this.dataSet = scenarioDevice;
        this.state = selectState;
    }

    public DeviceDataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DeviceDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ScenarioDataSet getScenarioDataSet() {
        return scenarioDataSet;
    }

    public void setScenarioDataSet(ScenarioDataSet scenarioDataSet) {
        this.scenarioDataSet = scenarioDataSet;
    }
}
