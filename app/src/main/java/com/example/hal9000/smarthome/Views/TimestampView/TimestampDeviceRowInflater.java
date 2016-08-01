package com.example.hal9000.smarthome.Views.TimestampView;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.Dialogs.TimeSettings;
import com.example.hal9000.smarthome.Helper.Tag;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Dialogs.TimeSettings.formatTimeString;
import static com.example.hal9000.smarthome.Helper.Config.*;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

@SuppressWarnings("unchecked")
public class TimestampDeviceRowInflater extends Inflater {
    private final DeviceTimestampManager manager;
    private final String deviceName;
    private final RequestHandler rh;

    public TimestampDeviceRowInflater(LayoutInflater inflater, LinearLayout parentView, Context context, String deviceName, android.support.v4.app.FragmentManager fragmentManager) {
        super(R.layout.dynamic_time_row, parentView, inflater, context, CATEGORY_TIMESTAMP, fragmentManager);
        this.deviceName = deviceName;
        manager = new DeviceTimestampManager(deviceName, context);
        rh = new RequestHandler();
    }

    /**
     * Alle Timestamps des Geräts durchlaufen und Inflaten
     */
    public void createRows() {
        ArrayList<DeviceDataSet> timeList = manager.getDataSet();
        for (DeviceDataSet device : timeList) {
            getParentView().addView(inflateTimestampRow(device));
        }

    }

    /**
     * Erstellen der Zeile für ein Gerät das inflatet werden soll
     *
     * @param device Datensatz des zu inflatenden Gerät
     * @return Zeile die Inflatet werden soll
     */
    private View inflateTimestampRow(DeviceDataSet device) {
        View rowView = getInflater().inflate(getRowID(), null);
        int id = device.getId();
        String type = device.getType();
        int state = device.getState();

        TextView txtName = (TextView) rowView.findViewById(R.id.txtRow);
        ImageView imPower = (ImageView) rowView.findViewById(R.id.imRowPower);
        ImageView imDelete = (ImageView) rowView.findViewById(R.id.imRowDelete);
        ImageView imSettings = (ImageView) rowView.findViewById(R.id.imRowSettings);

        imPower.setTag(new Tag(STRING_TAG_POWER, device));
        imPower.setOnClickListener(clickPower());

        imDelete.setOnClickListener(clickDelete(id, type));

        imSettings.setOnClickListener(clickSettings(type));
        imSettings.setTag(new Tag(STRING_TAG_SETTINGS, device));

        txtName.setText(formatTimeString(device.getHour(), device.getMinute()));
        txtName.setTag(new Tag(STRING_TAG_CLOCK, device));
        txtName.setOnClickListener(clickTime());

        switchImage(type, state, imPower);
        deleteSettingsButton(imSettings, type);

        return rowView;
    }

    /**
     * Klick Löschen Button
     * Löschen des Timestamps aus der momentanen View und aus der Datenbank
     *
     * @return OnClickListener
     */
    private View.OnClickListener clickDelete(final int id, final String type) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogListener builder = new DialogListener(getContext());
                builder.setMessage(DELETE_TIMESTAMP);
                builder.setPositiveButton(BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = rh.deleteTimestamp(id, type);
                        if (!catchError(getContext(), msg)) {
                            getParentView().removeView((View) v.getParent());
                        }
                        buttonChanger(INT_UNSET_ID, STRING_EMPTY);
                    }
                });
                builder.setNegativeButton(BUTTON_NO, builder.setCancelButton());
                builder.show();
            }

        };
    }

    /**
     * Klick Power Button
     * Status des Timestamps, somit kann definiert werden ob zu einer Bestimmten Zeit das Szenario aktiviert oder deaktiviert werden soll
     * Wechseln des Status in der Datenbank
     * Abfangen von Datenbankfehlern
     *
     * @return OnClickListener
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
                    if (state > INT_STATUS_EIN) {
                        state = INT_STATUS_AUS;
                    }
                    String msgState = rh.updateSingleValue(type, TAG_STATE, Integer.toString(state), id);
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
     * Klick auf Uhrzeit
     * Öffen des Dialogs zum ändern der Zeit
     *
     * @return OnClickListener
     */
    private View.OnClickListener clickTime() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDismissListener(v);
            }
        };
    }

    /**
     * Öffnen des Dialogs zum ändern der Zeit
     *
     * @param v View
     */
    private void createDismissListener(View v) {
        Tag tag = (Tag) v.getTag();
        DeviceDataSet dataSet = tag.getDataSet();
        TimeSettings editTime = new TimeSettings(getContext(), dataSet, getInflater(), (TextView) v, manager, this);
        editTime.setOnDismissListener(this);
        editTime.show();
    }

    /**
     * Hinzufügen eines neuen Timestamps zum Szenario
     */
    public void addTimestamp(String type) {
        String insertReply = rh.insertTimestamp(deviceName, type);
        if (!catchError(getContext(), insertReply)) {
            int nextId = Integer.parseInt(insertReply);
            manager.manageTimestampsWithName(deviceName);
            DeviceDataSet newRow = manager.updateDevice(nextId, type);
            if(newRow!=null){
                getParentView().addView(inflateTimestampRow(newRow));
            }
        }
        //buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
    }

    /**
     * Durchläuft alle Elemente im Relativlayout und aktualisiert die Werte(z.B. Bild, DataSet)
     * mit den im Tag bzw. in der Datenbank stehenden Informationen
     * Das DataSet wird hierbei mit aktuellen Werten aus der Datenbank gefüllt
     *
     * @param clickedId Geklickte id die nicht verändert werden soll
     */
    public void buttonChanger(int clickedId, String clickedType) {
        manager.manageTimestampsWithName(deviceName);
        for (int i = 0; i < getParentView().getChildCount(); i++) {
            View v = getParentView().getChildAt(i);
            if (v instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) v;
                for (int j = 0; j < rl.getChildCount(); j++) {
                    View view = rl.getChildAt(j);
                    if (view.getTag() != null) {

                        Tag buttonTag = (Tag) view.getTag();
                        DeviceDataSet dataSet = buttonTag.getDataSet();
                        int id = dataSet.getId();
                        String type = dataSet.getType();

                        DeviceDataSet newDataSet = manager.updateDevice(id, type);
                        if (newDataSet != null) {
                            buttonTag.setDataSet(newDataSet);
                            view.setTag(buttonTag);

                            if ((id != clickedId || !type.equals(clickedType)) && buttonTag.getType().equals(STRING_TAG_POWER)) {
                                int state = newDataSet.getState();
                                switchImage(type, state, (ImageView) view);
                            }
                        }
                    }
                }
            }
        }
    }
}
