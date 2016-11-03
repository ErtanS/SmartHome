package com.example.hal9000.smarthome.DataSet;


public class OvenDataSet extends DeviceDataSet {
    private final int temperature;
    private final int duration;

    /**
     * @param values
     * @param temperature
     * @param duration
     */
    OvenDataSet(DeviceDataSet values, int temperature, int duration) {
        super(values);
        this.temperature = temperature;
        this.duration = duration;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDuration() {
        return duration;
    }
}
