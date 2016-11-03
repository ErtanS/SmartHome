package com.example.hal9000.smarthome.DataSet;


public class WaterDataSet extends DeviceDataSet {
    private final int intensity;
    private final int temperature;

    /**
     * @param values
     * @param intensity
     * @param temperature
     */
    WaterDataSet(DeviceDataSet values, int intensity, int temperature) {
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
