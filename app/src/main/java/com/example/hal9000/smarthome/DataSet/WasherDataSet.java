package com.example.hal9000.smarthome.DataSet;


public class WasherDataSet extends DeviceDataSet {
    private final int amount;
    private final int clothes;
    private final int temperature;
    private final int duration;
    private final int rpm;

    /**
     * @param values
     * @param temperature
     * @param duration
     * @param rpm
     * @param amount
     * @param clothes
     */
    WasherDataSet(DeviceDataSet values, int temperature, int duration, int rpm, int amount, int clothes) {
        super(values);
        this.temperature = temperature;
        this.duration = duration;
        this.rpm = rpm;
        this.amount = amount;
        this.clothes = clothes;
    }

    public int getAmount() {
        return amount;
    }

    public int getClothes() {
        return clothes;
    }
}
