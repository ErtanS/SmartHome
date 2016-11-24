package com.example.hal9000.smarthome.Views.ScenarioDeviceView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.DataSet.DataSet;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;

import java.util.ArrayList;

/**
 * The type Scenario device manager.
 */
@SuppressWarnings("unchecked")
class ScenarioDeviceManager extends DataManager {
    private final Context context;

    /**
     * Konstruktor
     * Füllt das DataSet mit allen Szenarien
     *
     * @param name    Name des Szenarios
     * @param context aktueller Context
     */
    ScenarioDeviceManager(String name, Context context) {
        this.context = context;
        fillRoomList(context);
        manageScenariosWithName(name);
    }

    /**
     * Füllt das DataSet mit Szenarien mit einem bestimmten Namen
     *
     * @param name Szenarioname
     */
    void manageScenariosWithName(String name) {
        fillDataSet(Config.STRING_EMPTY, Config.STRING_EMPTY, name, Config.CATEGORY_SCENARIO, context);
    }

    /**
     * Prüft ob das Gerät mit deem übergebenen Namen im DataSet vorhanden ist
     *
     * @param name Name
     * @return Gerätdatensatz oder null falls keins gefunden wurde
     */
    DeviceDataSet getScenarioDevice(String name) {
        ArrayList<DeviceDataSet> devices = getDataSet();
        for (DeviceDataSet item : devices) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Füllen des Datasets mit Geräten eines Szenarios
     *
     * @param room the room
     * @return the device list
     */
    DataSet getDeviceList(String room) {
        RequestHandler rh = new RequestHandler();
        String result = rh.getData(Config.STRING_EMPTY, Config.STRING_EMPTY, room, Config.CATEGORY_DEVICE);
        return new DataSet(result, context);
    }

    /**
     * Gets rooms.
     *
     * @return the rooms
     */
    ArrayList<String> getRooms() {
        return rooms;
    }

}
