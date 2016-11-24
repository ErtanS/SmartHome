package com.example.hal9000.smarthome.DataSet;


/**
 * The type Door data set.
 */
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

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
