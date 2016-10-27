package com.example.hal9000.smarthome.Views.DeviceView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Views.TimestampView.DynamicTimeView;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.StringHelper;
import com.example.hal9000.smarthome.Helper.Tag;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

@SuppressWarnings("unchecked")
public class DeviceViewInflater extends Inflater {

    private final DeviceViewDataManager dataManager;
    private final RequestHandler rh;

    /**
     * Konstruktor
     * @param parentView       Parentview
     * @param inflater         Inflater
     * @param context          Kontext
     * @param deviceType       soll gesetzt sein, wenn man von der Geräteübersicht diesen Konstruktor aufruft
*                         ansonsten null
     * @param deviceRoom       soll gesetzt sein, wenn man von der Raumübersicht diesen Konstruktor aufruft
*                         ansonsten null
     * @param positionToDelete Position, an der der Name gekürzt wird
     */
    public DeviceViewInflater(LinearLayout parentView, LayoutInflater inflater, Context context, String deviceType, String deviceRoom, int positionToDelete,android.support.v4.app.FragmentManager fragmentManager) {
        super(parentView, inflater, context, deviceType, deviceRoom, positionToDelete,fragmentManager);
        dataManager = new DeviceViewDataManager(deviceRoom, deviceType, context);
        rh = new RequestHandler();
        createRows();
    }

    /**
     * Inflatet die Zeilen und fügt diese der View hinzu
     */
    private void createRows() {
        ArrayList<DeviceDataSet> devices = dataManager.getDataSet();
        LinearLayout parentView = getParentView();
        for (DeviceDataSet device : devices) {
            parentView.addView(inflateDeviceRow(device), parentView.getChildCount());
        }
    }

    /**
     * Erstellen der Zeile für ein Gerät das inflatet werden soll
     *
     * @param device Datensatz des zu inflatenden Geräts
     * @return Zeile die Inflatet werden soll
     */
    private View inflateDeviceRow(DeviceDataSet device) {

        String name = device.getName();
        String type = device.getType();
        int state = device.getState();

        int positionToDelete = getPositionToDelete();

        if (getDeviceRoom() != null && getDeviceRoom().equals(Config.STRING_EN_FLUR) && type.equals(Config.STRING_TYPE_EN_DOOR)) {
            positionToDelete = -1;
        }

        View rowView = getInflater().inflate(getRowID(), null);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtRow);
        txtName.setText(name);

        ImageView imEdit = (ImageView) rowView.findViewById(R.id.imRowSettings);
        imEdit.setOnClickListener(clickSettings(type));
        imEdit.setTag(new Tag(Config.STRING_TAG_SETTINGS, device));


        ImageView imClock = (ImageView) rowView.findViewById(R.id.imRowClock);
        imClock.setOnClickListener(clickClock(name, type));


        ImageView imPower = (ImageView) rowView.findViewById(R.id.imRowPower);
        imPower.setTag(new Tag(Config.STRING_TAG_POWER, device));
        imPower.setOnClickListener(clickPower());
        switchImage(type, state, imPower);

        deleteSettingsButton(imEdit, type);
        return rowView;
    }

    /**
     * Klick auf Uhrsymbol
     * Wechsel in die DynamictimeView
     *
     * @param name Name des Geräts
     * @param type Typ
     * @return OnClickListener für das Uhrsymbol
     */
    private View.OnClickListener clickClock(final String name, final String type) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getContext(), DynamicTimeView.class);
                Bundle b = new Bundle();
                b.putString(Config.TAG_NAME, name);
                String title = StringHelper.stringCutter(name, -1);
                b.putString(Config.STRING_ACTIVITY_TITLE, title);
                b.putString(Config.STRING_INTENT_TYPE, type);
                b.putString(Config.STRING_INTENT_CATEGORY, getCategory());
                nextScreen.putExtras(b);
                getContext().startActivity(nextScreen);
            }
        };
    }

    /**
     * Klick des Powerbuttons
     * Gerätestatus in der Datenbank aktualisieren und Bild des Buttons ändern
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

                    String msgState = rh.updateSingleValue(type, Config.TAG_STATE, Integer.toString(state), id);
                    if (!catchError(getContext(), msgState)) {
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

    /**
     * Durchläuft alle Elemente im Relativlayout und aktualisiert die Werte
     * (z.B. Bild, DataSet) mit denen im Tag stehenden Informationen
     * Das DataSet wird hierbei mit aktuellen Werten aus der Datenbank gefüllt
     *
     * @param clickedId   Geklickte Id die nicht verändert werden soll
     * @param clickedType Gerätetyp des Geräts das zur Id gehört
     */
    /*public void buttonChanger(int clickedId, String clickedType) {
        dataManager.updateDataSet(getDeviceRoom(), getDeviceType());
        for (int i = 0; i < getParentView().getChildCount(); i++) {
            View v = getParentView().getChildAt(i);
            if (v instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) v;
                for (int k = 0; k < rl.getChildCount(); k++) {
                    View viewK = rl.getChildAt(k);
                    if (viewK instanceof RelativeLayout) {
                        RelativeLayout rl2 = (RelativeLayout) viewK;
                        for (int j = 0; j < rl2.getChildCount(); j++) {
                            View view = rl2.getChildAt(j);
                            if (view instanceof ImageView && view.getTag() != null) {
                                ImageView powerSwitch = (ImageView) view;
                                Tag buttonTag = (Tag) powerSwitch.getTag();
                                DeviceDataSet dataSet = buttonTag.getDataSet();
                                int id = dataSet.getId();
                                String type = dataSet.getType();

                                DeviceDataSet newDataSet = dataManager.updateDevice(id, type);
                                if (newDataSet != null) {
                                    buttonTag.setDataSet(newDataSet);
                                    powerSwitch.setTag(buttonTag);

                                    if ((id != clickedId || !type.equals(clickedType)) && buttonTag.getType().equals(Config.STRING_TAG_POWER)) {
                                        int state = newDataSet.getState();
                                        switchImage(type, state, powerSwitch);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/
    public void buttonChanger(int clickedId, String clickedType) {
        dataManager.updateDataSet(getDeviceRoom(), getDeviceType());
        ArrayList<View> buttons = findViewWithTagRecursively(getParentView());
        for (View button:buttons) {
            Tag buttonTag = (Tag) button.getTag();
            DeviceDataSet dataSet = buttonTag.getDataSet();
            int id = dataSet.getId();
            String type = dataSet.getType();
            DeviceDataSet newDataSet = dataManager.updateDevice(id, type);
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
