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

    /**
     * Instantiates a new Tag.
     *
     * @param type    the type
     * @param dataSet the data set
     */
    public Tag(String type, DeviceDataSet dataSet) {
        this.type = type;
        this.dataSet = dataSet;
    }

    /**
     * Instantiates a new Tag.
     *
     * @param type     the type
     * @param scenario the scenario
     */
    public Tag(String type, ScenarioDataSet scenario) {
        this.type = type;
        this.scenarioDataSet = scenario;
    }

    /**
     * Instantiates a new Tag.
     *
     * @param scenarioDevice the scenario device
     * @param selectState    the select state
     */
    public Tag(DeviceDataSet scenarioDevice, int selectState) {
        this.type = Config.STRING_TAG_SELECT;
        this.dataSet = scenarioDevice;
        this.state = selectState;
    }

    /**
     * Gets data set.
     *
     * @return the data set
     */
    public DeviceDataSet getDataSet() {
        return dataSet;
    }

    /**
     * Sets data set.
     *
     * @param dataSet the data set
     */
    public void setDataSet(DeviceDataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Gets scenario data set.
     *
     * @return the scenario data set
     */
    public ScenarioDataSet getScenarioDataSet() {
        return scenarioDataSet;
    }

    /**
     * Sets scenario data set.
     *
     * @param scenarioDataSet the scenario data set
     */
    public void setScenarioDataSet(ScenarioDataSet scenarioDataSet) {
        this.scenarioDataSet = scenarioDataSet;
    }
}
