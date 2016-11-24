package com.example.hal9000.smarthome.Views.TimestampView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Helper.Config;

import java.util.ArrayList;

/**
 * The type Scenario timestamp manager.
 */
@SuppressWarnings("unchecked")
public class ScenarioTimestampManager extends DataManager {
    private final Context context;

    /**
     * Konstruktor
     *
     * @param name    Name des Szenarios
     * @param context aktueller Context
     */
    ScenarioTimestampManager(String name, Context context) {
        this.context = context;
        manageTimestampsOfScenario(name);
    }

    /**
     * Konstruktor
     *
     * @param name Name des Szenarios
     */
    void manageTimestampsOfScenario(String name) {
        fillDataSet(Config.TAG_SCENARIO, name, Config.STRING_EMPTY, Config.CATEGORY_TIMESTAMP, context);
    }

    /**
     * Überprüft ob die angegebene Zeit für dieses Szenario schon verwendet wird
     *
     * @param hour   Stunde
     * @param minute Minute
     * @return true wenn Zeit noch nicht belegt false wenn Zeit schon belegt
     */
    public boolean compareTime(int hour, int minute) {
        ArrayList<ScenarioDataSet> timeList = getDataSet();
        for (ScenarioDataSet item : timeList) {
            if (item.getHour() == hour && item.getMinute() == minute) {
                return false;
            }
        }
        return true;
    }


}
