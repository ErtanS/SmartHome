package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class CameraDataSet extends DeviceDataSet {
    private int emergency;
    private int autoEmergency;
    private int frequency;


    public CameraDataSet(DeviceDataSet values, int emergency, int rotation, int frequency) {
        super(values);
        this.emergency = emergency;
        this.autoEmergency = rotation;
        this.frequency = frequency;
    }

    public int getEmergency() {
        return emergency;
    }

    public int getAutoEmergency() {
        return autoEmergency;
    }

    public int getFrequency() {
        return frequency;
    }
}
