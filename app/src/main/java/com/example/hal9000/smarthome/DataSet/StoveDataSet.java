package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class StoveDataSet extends DeviceDataSet {
    private int duration;
    // TODO: 19.07.2016 überall ändern!!!!!!!!!!!!!!!!!!!!!!!
    private int temperature;

    public StoveDataSet(DeviceDataSet values,int temperature, int duration) {
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
