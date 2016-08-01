package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class TvDataSet extends DeviceDataSet {
    private int channel;
    private int pictureid;
    private int volume;


    public TvDataSet(DeviceDataSet values, int channel, int pictureid, int volume) {
        super(values);
        this.channel = channel;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    public int getChannel() {
        return channel;
    }

    public int getPictureid() {
        return pictureid;
    }

    public int getVolume() {
        return volume;
    }
}
