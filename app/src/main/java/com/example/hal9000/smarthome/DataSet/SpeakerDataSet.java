package com.example.hal9000.smarthome.DataSet;

/**
 * Dataset für die Lautsprecher
 */
public class SpeakerDataSet extends DeviceDataSet {
    private final int volume;
    private final int songid;

    /**
     * Konstruktor
     *
     * @param values Grunddaten
     * @param volume Lautstärke
     * @param songid Songid des aktuellen Songs
     */
    SpeakerDataSet(DeviceDataSet values, int volume, int songid) {
        super(values);
        this.volume = volume;
        this.songid = songid;
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Gets songid.
     *
     * @return the songid
     */
    public int getSongid() {
        return songid;
    }
}
