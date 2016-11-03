package com.example.hal9000.smarthome.DataSet;


public class TvDataSet extends DeviceDataSet {
    private final int channel;
    private final String pictureid;
    private final int volume;


    /**
     * @param values
     * @param channel
     * @param pictureid
     * @param volume
     */
    TvDataSet(DeviceDataSet values, int channel, String pictureid, int volume) {
        super(values);
        this.channel = channel;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    public int getChannel() {
        return channel;
    }

    public String getPictureid() {
        return pictureid;
    }

    public int getVolume() {
        return volume;
    }
}
