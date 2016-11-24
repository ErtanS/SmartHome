package com.example.hal9000.smarthome.DataSet;


/**
 * The type Heater data set.
 */
public class HeaterDataSet extends DeviceDataSet {
    private final int temperature;

    /**
     * Konstruktor
     *
     * @param values      Grunddaten
     * @param temperature Temperatur
     */
    HeaterDataSet(DeviceDataSet values, int temperature) {
        super(values);
        this.temperature = temperature;
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
