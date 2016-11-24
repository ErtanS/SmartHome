package com.example.hal9000.smarthome.Views.DeviceView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Helper.Config;


/**
 * The type Device view data manager.
 */
class DeviceViewDataManager extends DataManager {
    private final Context context;

    /**
     * Konstruktor
     *
     * @param room    Raum
     * @param type    Typ
     * @param context Kontext
     */
    DeviceViewDataManager(String room, String type, Context context) {
        this.context = context;
        updateDataSet(room, type);
    }

    /**
     * Aktualisieren des DataSets
     * Jenachdem welche Kategorie nicht null ist, wird dieser DatenSatz gefüllt
     *
     * @param room Raum
     * @param type Typ
     */
    void updateDataSet(String room, String type) {
        if (type != null) {
            fillDataSet(type, Config.STRING_EMPTY, Config.STRING_EMPTY, Config.CATEGORY_DEVICE, context);
        } else {
            fillDataSet(Config.STRING_EMPTY, Config.STRING_EMPTY, room, Config.CATEGORY_DEVICE, context);
        }
    }
}
