package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class WaterDataSet extends DeviceDataSet {
    private int intensity;
    private int temperature;

    public WaterDataSet(DeviceDataSet values, int intensity, int temperature) {
        super(values);
        this.intensity = intensity;
        this.temperature = temperature;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getTemperature() {
        return temperature;
    }
}
