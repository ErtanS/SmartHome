package com.example.hal9000.smarthome.DataSet;

public class PcDataSet extends DeviceDataSet {
    private final String pictureid;
    private final int videoid;
    private final int volume;

    /**
     * @param values
     * @param videoid
     * @param pictureid
     * @param volume
     */
    PcDataSet(DeviceDataSet values, int videoid, String pictureid, int volume) {
        super(values);
        this.videoid = videoid;
        this.pictureid = pictureid;
        this.volume = volume;
    }

    public String getPictureid() {
        return pictureid;
    }

}
