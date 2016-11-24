package com.example.hal9000.smarthome.DataSet;

import android.content.Context;

import com.example.hal9000.smarthome.Helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;
import static com.example.hal9000.smarthome.Helper.Config.*;

/**
 * Interne Verwaltung der Daten aus der Datenbank
 */
@SuppressWarnings("unchecked")
public class DataSet extends ArrayList {

    private final Context context;

    /**
     * Konstruktor
     *
     * @param result  Mehrdimensionales JSON Array
     * @param context Kontext
     */
    public DataSet(String result, Context context) {
        this.context = context;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            JSONArray resultArray = jsonObject.getJSONArray(TAG_JSON_ARRAY);
            ArrayList<DoorDataSet> doors = addDoor(resultArray.getJSONObject(INT_JSON_ID_DOOR));
            ArrayList<LightDataSet> lights = addLight(resultArray.getJSONObject(INT_JSON_ID_LIGHT));
            ArrayList<HeaterDataSet> heaters = addHeater(resultArray.getJSONObject(INT_JSON_ID_HEATER));
            ArrayList<SpeakerDataSet> speakers = addSpeaker(resultArray.getJSONObject(INT_JSON_ID_SPEAKER));
            ArrayList<WindowDataSet> windows = addWindow(resultArray.getJSONObject(INT_JSON_ID_WINDOW));
            ArrayList<ShuttersDataSet> shutters = addShutters(resultArray.getJSONObject(INT_JSON_ID_SHUTTERS));
            ArrayList<CameraDataSet> cameras = addCamera(resultArray.getJSONObject(INT_JSON_ID_CAMERA));
            ArrayList<DryerDataSet> dryers = addDryer(resultArray.getJSONObject(INT_JSON_ID_DRYER));
            ArrayList<OvenDataSet> ovens = addOven(resultArray.getJSONObject(INT_JSON_ID_OVEN));
            ArrayList<PcDataSet> pcs = addPc(resultArray.getJSONObject(INT_JSON_ID_PC));
            ArrayList<StoveDataSet> stoves = addStove(resultArray.getJSONObject(INT_JSON_ID_STOVE));
            ArrayList<TvDataSet> tvs = addTv(resultArray.getJSONObject(INT_JSON_ID_TV));
            ArrayList<WallDataSet> walls = addWall(resultArray.getJSONObject(INT_JSON_ID_WALL));
            ArrayList<WasherDataSet> washers = addWasher(resultArray.getJSONObject(INT_JSON_ID_WASHER));
            ArrayList<WaterDataSet> water = addWater(resultArray.getJSONObject(INT_JSON_ID_WATER));

            if (doors != null && lights != null && heaters != null && speakers != null && windows != null && shutters != null && cameras != null && dryers != null && ovens != null && pcs != null && stoves != null && tvs != null && walls != null && washers != null && water != null) {
                ArrayList<DeviceDataSet> list = new ArrayList<>();
                list.addAll(doors);
                list.addAll(lights);
                list.addAll(heaters);
                list.addAll(speakers);
                list.addAll(cameras);
                list.addAll(walls);
                list.addAll(water);
                list.addAll(tvs);
                list.addAll(pcs);
                list.addAll(ovens);
                list.addAll(stoves);
                list.addAll(washers);
                list.addAll(dryers);
                list.addAll(windows);
                list.addAll(shutters);
                sortData(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
        }
    }

    /**
     * Konstruktor
     *
     * @param context Kontext
     * @param result  Mehrdimensionales JSON Array
     */
    public DataSet(Context context, String result) {
        this.context = context;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            addScenario(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
        }
    }

    /**
     * Fügt Szenarien zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     */
    private void addScenario(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                int id = jo.getInt(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME).trim();
                int state = jo.getInt(Config.TAG_STATE);
                int hour = jo.getInt(Config.TAG_HOUR);
                int minute = jo.getInt(Config.TAG_MINUTE);
                ScenarioDataSet data = new ScenarioDataSet(id, name, state, hour, minute);
                add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
        }
    }

    /**
     * Fügt Türen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Türen oder beim Fehler null
     */
    private ArrayList<DoorDataSet> addDoor(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_DOOR);
            ArrayList<DoorDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String password = jo.getString(TAG_PASSWORD).trim();
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_DOOR);
                DoorDataSet data = new DoorDataSet(jsonData, password);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }

    }

    /**
     * Fügt Lampen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Lampen oder beim Fehler null
     */
    private ArrayList<LightDataSet> addLight(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_LIGHT);
            ArrayList<LightDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String color = jo.getString(TAG_COLOR).trim();
                int intensity = jo.getInt(TAG_INTENSITY);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_LIGHT);
                LightDataSet data = new LightDataSet(jsonData, color, intensity);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Rollladen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Rollladen oder beim Fehler null
     */
    private ArrayList<ShuttersDataSet> addShutters(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_SHUTTERS);
            ShuttersDataSet data;
            ArrayList<ShuttersDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_SHUTTERS);
                data = new ShuttersDataSet(jsonData);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Lautsprecher zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Lautsprecher oder beim Fehler null
     */
    private ArrayList<SpeakerDataSet> addSpeaker(JSONObject jsonObject) {
        SpeakerDataSet data;
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_SPEAKER);
            ArrayList<SpeakerDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                int volume = jo.getInt(TAG_VOLUME);
                int songid = jo.getInt(TAG_SONGID);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_SPEAKER);
                data = new SpeakerDataSet(jsonData, volume, songid);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Fenster zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Fenster oder beim Fehler null
     */
    private ArrayList<WindowDataSet> addWindow(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_WINDOW);
            WindowDataSet data;
            ArrayList<WindowDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_WINDOW);
                data = new WindowDataSet(jsonData);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Heizungen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Heizungen oder beim Fehler null
     */
    private ArrayList<HeaterDataSet> addHeater(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_HEATER);
            HeaterDataSet data;
            ArrayList<HeaterDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_HEATER);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                data = new HeaterDataSet(jsonData, temperature);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Kameras zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Kameras oder beim Fehler null
     */
    private ArrayList<CameraDataSet> addCamera(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_CAMERA);
            CameraDataSet data;
            ArrayList<CameraDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_CAMERA);
                int emergency = jo.getInt(TAG_EMERGENCY);
                int autoEmergency = jo.getInt(TAG_AUTOEMERGENCY);
                int frequency = jo.getInt(TAG_FREQUENCY);
                data = new CameraDataSet(jsonData, emergency, autoEmergency, frequency);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Trockner zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Trockner oder beim Fehler null
     */
    private ArrayList<DryerDataSet> addDryer(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_DRYER);
            DryerDataSet data;
            ArrayList<DryerDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_DRYER);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                int duration = jo.getInt(TAG_DURATION);
                int rpm = jo.getInt(TAG_RPM);
                int amount = jo.getInt(TAG_AMOUNT);
                int clothes = jo.getInt(TAG_CLOTHES);
                data = new DryerDataSet(jsonData, temperature, duration, rpm, amount, clothes);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Ofen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Ofen oder beim Fehler null
     */
    private ArrayList<OvenDataSet> addOven(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_OVEN);
            OvenDataSet data;
            ArrayList<OvenDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_OVEN);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                int duration = jo.getInt(TAG_DURATION);
                data = new OvenDataSet(jsonData, temperature, duration);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt PCs zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller PCs oder beim Fehler null
     */
    private ArrayList<PcDataSet> addPc(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_PC);
            PcDataSet data;
            ArrayList<PcDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_PC);
                int videoid = jo.getInt(TAG_VIDEOID);
                String pictureid = jo.getString(TAG_PICTUREID);
                int volume = jo.getInt(TAG_VOLUME);
                data = new PcDataSet(jsonData, videoid, pictureid, volume);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Herde zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Herde oder beim Fehler null
     */
    private ArrayList<StoveDataSet> addStove(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_STOVE);
            StoveDataSet data;
            ArrayList<StoveDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_STOVE);
                int duration = jo.getInt(TAG_DURATION);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                data = new StoveDataSet(jsonData, temperature, duration);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt TVs zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller TVs oder beim Fehler null
     */
    private ArrayList<TvDataSet> addTv(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_TV);
            TvDataSet data;
            ArrayList<TvDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_TV);
                int channel = jo.getInt(TAG_CHANNEL);
                String pictureid = jo.getString(TAG_PICTUREID);
                int volume = jo.getInt(TAG_VOLUME);
                data = new TvDataSet(jsonData, channel, pictureid, volume);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Leinwände zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Leinwände oder beim Fehler null
     */
    private ArrayList<WallDataSet> addWall(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_WALL);
            WallDataSet data;
            ArrayList<WallDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_WALL);
                String color = jo.getString(TAG_COLOR);
                String pictureid = jo.getString(TAG_PICTUREID);
                data = new WallDataSet(jsonData, color, pictureid);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Waschmaschinen zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Waschmachinen oder beim Fehler null
     */
    private ArrayList<WasherDataSet> addWasher(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_WASHER);
            WasherDataSet data;
            ArrayList<WasherDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_WASHER);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                int duration = jo.getInt(TAG_DURATION);
                int rpm = jo.getInt(TAG_RPM);
                int amount = jo.getInt(TAG_AMOUNT);
                int clothes = jo.getInt(TAG_CLOTHES);
                data = new WasherDataSet(jsonData, temperature, duration, rpm, amount, clothes);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Fügt Wasser zum DataSet hinzu
     *
     * @param jsonObject aktuelles JSON Array
     * @return Arrayliste aller Wasser oder beim Fehler null
     */
    private ArrayList<WaterDataSet> addWater(JSONObject jsonObject) {
        try {
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_WATER);
            WaterDataSet data;
            ArrayList<WaterDataSet> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DeviceDataSet jsonData = jsonExtension(jo, STRING_TYPE_EN_WATER);
                int intensity = jo.getInt(TAG_INTENSITY);
                int temperature = jo.getInt(TAG_TEMPERATURE);
                data = new WaterDataSet(jsonData, intensity, temperature);
                list.add(data);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(context);
            return null;
        }
    }

    /**
     * Verarbeitet Daten die bei jedem Gerät vorhanden sind
     *
     * @param jo   JSON Objekt
     * @param type Typ
     * @return neuer DeviceDataSet
     * @throws JSONException
     */
    private DeviceDataSet jsonExtension(JSONObject jo, String type) throws JSONException {
        int id = jo.getInt(TAG_ID);
        String name = jo.getString(TAG_NAME).trim();
        int state = 0;
        if (!type.equals(STRING_TYPE_EN_WALL)) {
            state = jo.getInt(TAG_STATE);
        }
        String scenarioRoom = jo.getString(TAG_SCENARIOROOM).trim();
        String category = jo.getString(TAG_CATEGORY).trim();
        int hour = jo.getInt(TAG_HOUR);
        int minute = jo.getInt(TAG_MINUTE);
        return new DeviceDataSet(id, name, state, scenarioRoom, hour, minute, category, type);
    }

    /**
     * Sortiert eine Arrayliste
     *
     * @param list zu sortierende Liste
     */
    private void sortData(ArrayList<DeviceDataSet> list) {
        if (list.size() > 0) {
            for (DeviceDataSet item : list) {
                add(item);
            }
        }
    }
}
