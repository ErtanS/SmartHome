package com.example.hal9000.smarthome.Abstract;

import android.content.Context;

import com.example.hal9000.smarthome.DataSet.DataSet;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;

/**
 * Stellt allgemeine Methoden zur Verfügung, die andere Datenmanager erben
 */
@SuppressWarnings("unchecked")
public abstract class DataManager {
    private DataSet dataSet;
    protected ArrayList<String> rooms;
    protected ArrayList<String> devices;


    public DataSet getDataSet() {
        return dataSet;
    }


    /**
     * Aktuellen Datensatz für ein bestimmtes Gerät finden
     *
     * @param id   Gerät id
     * @param type Gerätetyp Bezeichnung z.B. 'window', 'speaker', etc...
     * @return aktueller Datensatz
     */
    public DeviceDataSet updateDevice(int id, String type) {
        ArrayList<DeviceDataSet> devices = dataSet;
        for (DeviceDataSet item : devices) {
            if (item.getId() == id && item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }


    /**
     * Gibt den Datensatz zurück mit dem übergebenen Szenarionamen
     *
     * @param id id des Szenarios
     * @return Szenario Datesatz
     */
    public ScenarioDataSet updateScenario(int id) {
        ArrayList<ScenarioDataSet> scenarios = dataSet;
        for (ScenarioDataSet scenario : scenarios) {
            if (scenario.getId() == id) {
                return scenario;
            }
        }
        return null;
    }

    /**
     * DataSet mit aktuellen Werten aus der Datenbank füllen
     *
     * @param table        Tabellenname
     * @param name         Gerätename
     * @param scenarioRoom Raumbezeichnung oder Scenarioname
     * @param category     Kategoriebezeichnung z.B. 'device', 'scenario', 'timestamp'
     */
    protected void fillDataSet(String table, String name, String scenarioRoom, String category, Context context) {
        RequestHandler rh = new RequestHandler();
        String result = rh.getData(table, name, scenarioRoom, category);
        if (table.equals(Config.CATEGORY_SCENARIO)) {
            dataSet = new DataSet(context, result);
        } else {
            dataSet = new DataSet(result, context);
        }
    }


    /**
     * Hinzufügen aller Raumtypen
     */
    protected  void fillRoomList(Context context) {
        rooms=new ArrayList<>();
        RequestHandler rh = new RequestHandler();
        String resultMusic = rh.getRoomNames();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultMusic);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String room = jo.getString(Config.STRING_INTENT_ROOM).trim();
                rooms.add(room);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            fatalError( context);

        }
    }

    /**
     * Hinzufügen aller Gerätetypen
     */
    protected  void fillDeviceList(Context context) {
        devices=new ArrayList<>();
        RequestHandler rh = new RequestHandler();
        String resultDevices = rh.getDevicesInHouse();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultDevices);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String room = jo.getString(Config.STRING_TABLE).trim();
                devices.add(room);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            fatalError( context);

        }
    }
}


