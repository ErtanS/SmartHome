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

    public int getVolume() {
        return volume;
    }

    public int getSongid() {
        return songid;
    }
}
