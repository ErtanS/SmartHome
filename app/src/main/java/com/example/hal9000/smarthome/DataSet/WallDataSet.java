package com.example.hal9000.smarthome.DataSet;


/**
 * The type Wall data set.
 */
public class WallDataSet extends DeviceDataSet {
    private final String color;
    private final String pictureid;

    /**
     * Instantiates a new Wall data set.
     *
     * @param values    the values
     * @param color     the color
     * @param pictureid the pictureid
     */
    WallDataSet(DeviceDataSet values, String color, String pictureid) {
        super(values);
        this.color = color;
        this.pictureid = pictureid;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
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
