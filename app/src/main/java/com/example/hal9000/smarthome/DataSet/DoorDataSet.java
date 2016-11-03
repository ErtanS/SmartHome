package com.example.hal9000.smarthome.DataSet;


public class DoorDataSet extends DeviceDataSet {
    private final String password;

    /**
     * Konstruktor
     *
     * @param values   Grunddaten
     * @param password Passwort
     */
    DoorDataSet(DeviceDataSet values, String password) {
        super(values);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
