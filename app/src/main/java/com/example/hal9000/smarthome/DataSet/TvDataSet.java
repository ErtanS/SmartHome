package com.example.hal9000.smarthome.DataSet;


/**
 * The type Tv data set.
 */
public class TvDataSet extends DeviceDataSet {
    private final int channel;
    private final String pictureid;
    private final int volume;


    /**
     * Instantiates a new Tv data set.
     *
     * @param values    the values
     * @param channel   the channel
     * @param pictureid the pictureid
     * @param volume    the volume
     */
    TvDataSet(DeviceDataSet values, int channel, String pictureid, int volume) {
        super(values);
        this.channel = channel;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    /**
     * Gets channel.
     *
     * @return the channel
     */
    public int getChannel() {
        return channel;
    }

    /**
     * Gets pictureid.
     *
     * @return the pictureid
     */
    public String getPictureid() {
        return pictureid;
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public int getVolume() {
        return volume;
    }
}
