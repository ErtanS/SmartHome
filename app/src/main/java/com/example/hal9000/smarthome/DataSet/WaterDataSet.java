package com.example.hal9000.smarthome.DataSet;


/**
 * The type Water data set.
 */
public class WaterDataSet extends DeviceDataSet {
    private final int intensity;
    private final int temperature;

    /**
     * Instantiates a new Water data set.
     *
     * @param values      the values
     * @param intensity   the intensity
     * @param temperature the temperature
     */
    WaterDataSet(DeviceDataSet values, int intensity, int temperature) {
        super(values);
        this.intensity = intensity;
        this.temperature = temperature;
    }

    /**
     * Gets intensity.
     *
     * @return the intensity
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public int getTemperature() {
        return temperature;
    }
}
