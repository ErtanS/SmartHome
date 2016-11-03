package com.example.hal9000.smarthome.Views.ScenarioDeviceView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.StringHelper;
import com.example.hal9000.smarthome.Helper.Tag;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;


@SuppressWarnings("unchecked")
public class ScenarioDeviceRowInflater extends Inflater {

    private final ScenarioDeviceManager manager;
    private final RequestHandler rh;

    /**
     * Konstruktor
     *
     * @param inflater     Inflater
     * @param parentView   Parentview
     * @param context      Kontext
     * @param scenarioName Szenarioname
     */
    public ScenarioDeviceRowInflater(LayoutInflater inflater, LinearLayout parentView, Context context, String scenarioName, android.support.v4.app.FragmentManager fragmentManager) {
        super(R.layout.dynamic_device_scenario_row, parentView, inflater, scenarioName, context, fragmentManager);
        manager = new ScenarioDeviceManager(scenarioName, context);
        rh = new RequestHandler();
    }

    /**
     * Durchlaufen aller Geräte
     * Geräte zu der View hinzufügen
     */
    public void createRows() {
        ArrayList<String> rooms = manager.getRooms();

        for (String room : rooms) {
            parentView.addView(inflateRoomRow(room));
        }
    }

    private View inflateRoomRow(String room) {


        View rowView = inflater.inflate(R.layout.dynamic_scenario_list_row, null);

        LinearLayout parentView = (LinearLayout) rowView.findViewById((R.id.parentRoom));
        TextView roomName = (TextView) rowView.findViewById(R.id.roomName);


        roomName.setOnClickListener(clickRoom(parentView));
        roomName.setText(room);
        ArrayList<DeviceDataSet> devices = manager.getDeviceList(room);

        for (DeviceDataSet device : devices) {
            if (device.getScenarioRoom().equals(room)) {
                parentView.addView(inflateDeviceScenarioRow(device));
            }
        }

        return rowView;
    }


    /**
     * Erstellen einer View die einer Zeile/Gerät die Inflatet werden soll
     * Setzen der OnClickListener für die Imageview
     * Anzeigebilder auf den Status, Typ anpassen
     *
     * @param device Device Datensatz
     * @return Zeile die zu Inflaten ist
     */
    private View inflateDeviceScenarioRow(DeviceDataSet device) {

        String name = device.getName();
        String type = device.getType();

        String label = StringHelper.stringCutter(name, -1);
        View rowView = inflater.inflate(rowID, null);

        DeviceDataSet scenarioDevice = manager.getScenarioDevice(name);
        int selectState;

        ImageView imEdit = (ImageView) rowView.findViewById(R.id.imRowSettings);
        ImageView imSelect = (ImageView) rowView.findViewById(R.id.imRowSelect);
        ImageView imPower = (ImageView) rowView.findViewById(R.id.imRowPower);

        if (scenarioDevice == null) {
            selectState = 0;
            deleteButtons(imEdit, imPower, true, Config.INT_UNSET_ID, Config.STRING_EMPTY);
        } else {
            selectState = 1;
            deleteButtons(imEdit, imPower, false, scenarioDevice.getId(), scenarioDevice.getType());
        }

        imSelect.setTag(new Tag(device, selectState));
        imEdit.setOnClickListener(clickSettings(type));
        imPower.setOnClickListener(clickPower());
        imSelect.setOnClickListener(clickSelect(imEdit, imPower));

        TextView txtName = (TextView) rowView.findViewById(R.id.txtRow);
        txtName.setText(label);

        switchImage(Config.CATEGORY_SCENARIO, selectState, imSelect);

        return rowView;
    }

    /**
     * Klick des Powerbuttons
     * Aktualisiert die Datenbank mit dem momentan gewählten Gerätestatus
     *
     * @return OnClickListener für den Powerbutton
     */
    private View.OnClickListener clickPower() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    Tag buttonTag = (Tag) v.getTag();
                    DeviceDataSet dataSet = buttonTag.getDataSet();

                    int id = dataSet.getId();
                    String type = dataSet.getType();
                    int state = dataSet.getState();

                    state++;
                    if (state > Config.INT_STATUS_EIN) {
                        state = Config.INT_STATUS_AUS;
                    }

                    String msgSingle = rh.updateSingleValue(type, Config.TAG_STATE, Integer.toString(state), id);
                    //String msgTimestamp = rh.updateTimestampOfScenario(type, Config.TAG_STATE, Integer.toString(state), id);
                    //String msgDevice = rh.updateDeviceOfScenario(type, Config.TAG_STATE, Integer.toString(state), id);
                    if (!catchError(context, msgSingle)) { // && !catchError(getContext(), msgTimestamp) && !catchError(getContext(), msgDevice)) {
                        switchImage(type, state, (ImageView) v);
                        dataSet.setState(state);
                        buttonTag.setDataSet(dataSet);
                        v.setTag(buttonTag);
                    }
                    buttonChanger(id, type);
                }
            }
        };
    }


    private View.OnClickListener clickRoom(final LinearLayout layout) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getVisibility() == LinearLayout.GONE) {
                    layout.setVisibility(LinearLayout.VISIBLE);
                } else {
                    layout.setVisibility(LinearLayout.GONE);
                }
            }
        };
    }

    /**
     * Klick des Select Button
     * Wird nur angezeigt wenn das Gerät noch nicht zu dem Szenario hinzugefüht wurde
     * Gerät in der Datenbank zu dem Szenario hinzufügen
     *
     * @param imSettings Settings-Button
     * @param imPower    Power-Button
     * @return OnClickListener
     */
    private View.OnClickListener clickSelect(final ImageView imSettings, final ImageView imPower) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    boolean error;
                    Tag buttonTag = (Tag) v.getTag();
                    DeviceDataSet dataSet = buttonTag.getDataSet();
                    String name = dataSet.getName();
                    int state = buttonTag.getState();
                    String type = dataSet.getType();
                    String scenario = scenarioName;
                    state++;
                    if (state > Config.INT_STATUS_EIN) {
                        state = Config.INT_STATUS_AUS;
                    }
                    if (state == Config.INT_STATUS_AUS) {
                        String msg = rh.deleteDeviceFromScenario(name, scenario, type);
                        error = catchError(context, msg);
                        if (!error) {
                            manager.manageScenariosWithName(scenario);
                            deleteButtons(imSettings, imPower, true, Config.INT_UNSET_ID, Config.STRING_EMPTY);
                        }
                    } else {
                        String msg = rh.insertDeviceInScenario(name, scenario, type);
                        error = catchError(context, msg);
                        if (!error) {
                            int id = Integer.parseInt(msg);
                            manager.manageScenariosWithName(scenario);
                            deleteButtons(imSettings, imPower, false, id, type);
                        }
                    }
                    if (!error) {
                        buttonTag.setState(state);
                        v.setTag(buttonTag);
                        switchImage(Config.CATEGORY_SCENARIO, state, (ImageView) v);
                    }
                    buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
                }
            }
        };
    }

    /**
     * Löschen/Ausblenden der Buttons aus der momentanen View
     *
     * @param imSettings  Settings-Button
     * @param imPower     Power-Button
     * @param deleteImage ob Buttons entfenrt werden sollen
     * @param id          id des Gerätes
     * @param type        Typ des Gerätes
     */
    private void deleteButtons(ImageView imSettings, ImageView imPower, boolean deleteImage, int id, String type) {
        if (deleteImage) {
            imPower.setVisibility(View.INVISIBLE);
            imSettings.setVisibility(View.INVISIBLE);
            imPower.setTag(null);
            imSettings.setTag(null);

        } else {
            DeviceDataSet newDataSet = manager.updateDevice(id, type);
            if (newDataSet != null) {
                if (!type.equals(Config.STRING_TYPE_EN_WINDOW) && !type.equals(Config.STRING_TYPE_EN_SHUTTERS)) {
                    imSettings.setTag(new Tag(Config.STRING_TAG_SETTINGS, newDataSet));
                    imSettings.setVisibility(View.VISIBLE);
                } else {
                    imSettings.setTag(null);
                    imSettings.setVisibility(View.INVISIBLE);
                }
                imPower.setTag(new Tag(Config.STRING_TAG_POWER, newDataSet));
                switchImage(type, newDataSet.getState(), imPower);
                imPower.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Durchläuft alle Elemente im Relativlayout und aktualisiert die Werte(z.B. Bild, DataSet)
     * mit den im Tag bzw. in der Datenbank stehenden Informationen
     * Das DataSet wird hierbei mit aktuellen Werten aus der Datenbank gefüllt
     *
     * @param clickedId   Geklickte Id die nicht verändert werden soll
     * @param clickedType Gerätetyp des Geräts das zur Id gehört
     */

    public void buttonChanger(int clickedId, String clickedType) {
        manager.manageScenariosWithName(scenarioName);
        ArrayList<View> buttons = findViewWithTagRecursively(parentView);
        for (View button : buttons) {
            Tag buttonTag = (Tag) button.getTag();
            DeviceDataSet dataSet = buttonTag.getDataSet();
            int id = dataSet.getId();
            String type = dataSet.getType();
            DeviceDataSet newDataSet = manager.updateDevice(id, type);
            if (newDataSet != null) {
                buttonTag.setDataSet(newDataSet);
                button.setTag(buttonTag);
                if ((id != clickedId || !type.equals(clickedType)) && buttonTag.getType().equals(Config.STRING_TAG_POWER)) {
                    int state = newDataSet.getState();
                    switchImage(type, state, (ImageView) button);
                }
            }
        }
    }


}
