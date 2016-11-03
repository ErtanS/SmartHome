package com.example.hal9000.smarthome.DataSet;


public class WallDataSet extends DeviceDataSet {
    private final String color;
    private final String pictureid;

    /**
     * @param values
     * @param color
     * @param pictureid
     */
    WallDataSet(DeviceDataSet values, String color, String pictureid) {
        super(values);
        this.color = color;
        this.pictureid = pictureid;
    }

    public String getColor() {
        return color;
    }

    public String getPictureid() {
        return pictureid;
    }
}
