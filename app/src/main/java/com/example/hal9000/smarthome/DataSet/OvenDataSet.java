package com.example.hal9000.smarthome.DataSet;


/**
 * The type Oven data set.
 */
public class OvenDataSet extends DeviceDataSet {
    private final int temperature;
    private final int duration;

    /**
     * Instantiates a new Oven data set.
     *
     * @param values      the values
     * @param temperature the temperature
     * @param duration    the duration
     */
    OvenDataSet(DeviceDataSet values, int temperature, int duration) {
        super(values);
        this.temperature = temperature;
        this.duration = duration;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }
}
