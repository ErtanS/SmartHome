package com.example.hal9000.smarthome.DataSet;


public class CameraDataSet extends DeviceDataSet {
    private final int emergency;
    private final int autoEmergency;
    private final int frequency;

    /**
     * @param values        Kameraeigenschaften
     * @param emergency     Notfallszenario
     * @param autoEmergency Szenario wird automatisch geschaltet
     * @param frequency     Frequenz
     */
    CameraDataSet(DeviceDataSet values, int emergency, int autoEmergency, int frequency) {
        super(values);
        this.emergency = emergency;
        this.autoEmergency = autoEmergency;
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
