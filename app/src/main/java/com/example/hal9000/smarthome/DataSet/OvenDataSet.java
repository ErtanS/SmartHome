package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class OvenDataSet extends DeviceDataSet {
    private int temperature;
    private int duration;

    public OvenDataSet(DeviceDataSet values, int temperature, int duration) {
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
