package com.example.hal9000.smarthome.Views.TimestampView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Helper.Config;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DeviceTimestampManager extends DataManager {
    private final Context context;

    /**
     * Konstruktor
     *
     * @param name Name des gerätes
     * @param context aktueller Context
     */
    public DeviceTimestampManager(String name, Context context) {
        this.context = context;
        manageTimestampsWithName(name);
    }

    /**
     * Dataset füllen
     *
     * @param name Name des Geräts
     */
    public void manageTimestampsWithName(String name) {
        fillDataSet(Config.STRING_EMPTY, name, Config.STRING_EMPTY, Config.CATEGORY_TIMESTAMP, context);
    }

    /**
     * Überprüft ob die angegebene Zeit für dieses Szenario schon verwendet wird
     *
     * @param hour Stunde
     * @param minute Minute
     * @return true wenn Zeit noch nicht belegt
     * false wenn Zeit schon belegt
     */
    public boolean compareTime(int hour, int minute) {
        ArrayList<DeviceDataSet> timeList = getDataSet();
        for (DeviceDataSet item : timeList) {
            if (item.getHour() == hour && item.getMinute() == minute) {
                return false;
            }
        }
        return true;
    }
}
