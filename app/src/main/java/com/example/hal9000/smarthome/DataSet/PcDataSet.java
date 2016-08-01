package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class PcDataSet extends DeviceDataSet {
    private int videoid;
    private int pictureid;
    private int volume;


    public PcDataSet(DeviceDataSet values, int videoid, int pictureid, int volume) {
        super(values);
        this.videoid = videoid;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    public int getVideoid() {
        return videoid;
    }

    public int getPictureid() {
        return pictureid;
    }

    public int getVolume() {
        return volume;
    }
}
