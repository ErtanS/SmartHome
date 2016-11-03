package com.example.hal9000.smarthome.DataSet;


public class LightDataSet extends DeviceDataSet {
    private final String color;
    private final int intensity;

    /**
     * Konstruktor
     *
     * @param values    Grunddaten
     * @param color     Farbe "#000000" bis "#FFFFFF"
     * @param intensity Helligkeit 0 bis 100
     */
    LightDataSet(DeviceDataSet values, String color, int intensity) {
        super(values);
        this.color = color;
        this.intensity = intensity;
    }

    public String getColor() {
        return color;
    }

    public int getIntensity() {
        return intensity;
    }
}
