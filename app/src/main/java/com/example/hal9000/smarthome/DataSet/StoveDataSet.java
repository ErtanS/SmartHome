package com.example.hal9000.smarthome.DataSet;


/**
 * The type Stove data set.
 */
public class StoveDataSet extends DeviceDataSet {
    private final int duration;
    private final int temperature;

    /**
     * Instantiates a new Stove data set.
     *
     * @param values      the values
     * @param temperature the temperature
     * @param duration    the duration
     */
    StoveDataSet(DeviceDataSet values, int temperature, int duration) {
        super(values);
        this.duration = duration;
        this.temperature = temperature;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
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
