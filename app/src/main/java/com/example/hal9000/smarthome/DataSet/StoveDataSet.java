package com.example.hal9000.smarthome.DataSet;


public class StoveDataSet extends DeviceDataSet {
    private final int duration;
    private final int temperature;

    /**
     * @param values
     * @param temperature
     * @param duration
     */
    StoveDataSet(DeviceDataSet values, int temperature, int duration) {
        super(values);
        this.duration = duration;
        this.temperature = temperature;
    }

    public int getDuration() {
        return duration;
    }

    public int getTemperature() {
        return temperature;
    }
}
