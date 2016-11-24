package com.example.hal9000.smarthome.DataSet;


/**
 * The type Camera data set.
 */
public class CameraDataSet extends DeviceDataSet {
    private final int emergency;
    private final int autoEmergency;
    private final int frequency;

    /**
     * Instantiates a new Camera data set.
     *
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

    /**
     * Gets emergency.
     *
     * @return the emergency
     */
    public int getEmergency() {
        return emergency;
    }

    /**
     * Gets auto emergency.
     *
     * @return the auto emergency
     */
    public int getAutoEmergency() {
        return autoEmergency;
    }

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }
}
