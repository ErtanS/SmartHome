package com.example.hal9000.smarthome.DataSet;


/**
 * The type Dryer data set.
 */
public class DryerDataSet extends DeviceDataSet {
    private final int amount;
    private final int clothes;
    private final int temperature;
    private final int duration;
    private final int rpm;

    /**
     * Instantiates a new Dryer data set.
     *
     * @param values      the values
     * @param temperature the temperature
     * @param duration    the duration
     * @param rpm         the rpm
     * @param amount      the amount
     * @param clothes     the clothes
     */
    DryerDataSet(DeviceDataSet values, int temperature, int duration, int rpm, int amount, int clothes) {
        super(values);
        this.temperature = temperature;
        this.duration = duration;
        this.rpm = rpm;
        this.amount = amount;
        this.clothes = clothes;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets clothes.
     *
     * @return the clothes
     */
    public int getClothes() {
        return clothes;
    }
}
