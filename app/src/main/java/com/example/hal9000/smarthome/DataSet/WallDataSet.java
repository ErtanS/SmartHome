package com.example.hal9000.smarthome.DataSet;

/**
 * Created by Ertan on 14.07.2016.
 */
public class WallDataSet extends DeviceDataSet {
    private String color;
    private int pictureid;

    public WallDataSet(DeviceDataSet values, String color, int pictureid) {
        super(values);
        this.color = color;
        this.pictureid = pictureid;
    }

    public String getColor() {
        return color;
    }

    public int getPictureid() {
        return pictureid;
    }
}
