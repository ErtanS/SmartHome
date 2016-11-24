package com.example.hal9000.smarthome.DataSet;

/**
 * The type Pc data set.
 */
public class PcDataSet extends DeviceDataSet {
    private final String pictureid;
    private final int videoid;
    private final int volume;

    /**
     * Instantiates a new Pc data set.
     *
     * @param values    the values
     * @param videoid   the videoid
     * @param pictureid the pictureid
     * @param volume    the volume
     */
    PcDataSet(DeviceDataSet values, int videoid, String pictureid, int volume) {
        super(values);
        this.videoid = videoid;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    /**
     * Gets pictureid.
     *
     * @return the pictureid
     */
    public String getPictureid() {
        return pictureid;
    }

}
