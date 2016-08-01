package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class WasherDataSet extends DeviceDataSet {
    private int temperature;
    private int duration;
    private int rpm;
    private int amount;
    private int clothes;


    public WasherDataSet(DeviceDataSet values, int temperature, int duration, int rpm, int amount,int clothes) {
        super(values);
        this.temperature = temperature;
        this.duration = duration;
        this.rpm = rpm;
        this.amount = amount;
        this.clothes = clothes;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDuration() {
        return duration;
    }

    public int getRpm() {
        return rpm;
    }

    public int getAmount() {
        return amount;
    }

    public int getClothes() {
        return clothes;
    }
}
