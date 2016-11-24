package com.example.hal9000.smarthome.DataSet;


/**
 * The type Light data set.
 */
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

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets intensity.
     *
     * @return the intensity
     */
    public int getIntensity() {
        return intensity;
    }
}
