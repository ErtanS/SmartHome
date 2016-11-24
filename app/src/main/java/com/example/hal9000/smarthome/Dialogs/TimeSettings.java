package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.ErrorHandler;

import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.R;
import com.example.hal9000.smarthome.Views.TimestampView.DeviceTimestampManager;
import com.example.hal9000.smarthome.Views.TimestampView.ScenarioTimestampManager;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

/**
 * The type Time settings.
 */
@SuppressWarnings("deprecation")
public class TimeSettings extends DialogListener {

    private final int oldHour;
    private final int oldMinute;
    private TextView time;
    private RequestHandler rh;
    private Context context;
    private TimePicker tp;

    /**
     * Konstruktor für den ScenarioTimestampManager
     *
     * @param context        Kontext
     * @param scenario       Szenariodatensatz
     * @param layoutInflater infalter
     * @param time           Textview
     * @param manager        ScenarioTimestampManager
     * @param inflater       the inflater
     */
    public TimeSettings(Context context, ScenarioDataSet scenario, LayoutInflater layoutInflater, TextView time, ScenarioTimestampManager manager, Inflater inflater) {
        super(context, inflater);
        oldHour = scenario.getHour();
        oldMinute = scenario.getMinute();
        fillVars(context, layoutInflater, time);
        setPositiveButton(BUTTON_OK, setOkButton(scenario.getId(), manager));
    }

    /**
     * Konstruktor für den DeviceTimestampManager
     *
     * @param context        Kontext
     * @param device         Gerätedatensatz
     * @param layoutInflater infalter
     * @param time           Textview
     * @param manager        DeviceTimestampManager
     * @param inflater       the inflater
     */
    public TimeSettings(Context context, DeviceDataSet device, LayoutInflater layoutInflater, TextView time, DeviceTimestampManager manager, Inflater inflater) {
        super(context, inflater);
        oldHour = device.getHour();
        oldMinute = device.getMinute();
        fillVars(context, layoutInflater, time);
        setPositiveButton(BUTTON_OK, setOkButton(device.getType(), device.getId(), manager));
    }

    /**
     * Formatiert die Uhrzeit in ein HH:MM Fornmat
     *
     * @param hour   Stunde
     * @param minute Minute
     * @return Kombinierte Uhrzeit in HH:MM
     */
    public static String formatTimeString(int hour, int minute) {
        String h = Integer.toString(hour);
        String m = Integer.toString(minute);
        if (h.length() == 1) {
            h = "0" + h;
        }
        if (m.length() == 1) {
            m = "0" + m;
        }
        return h + ":" + m;
    }

    /**
     * Setzt die Stunde und Minute in dem Timepicker und initialisiert Klassenvariabeln
     * Zusätzlich unterscheidet es unter verschiedenen Versionen von Android, da diese nicht die selben Methoden benutzen
     *
     * @param context  Kontext
     * @param inflater Inflater
     * @param time     Textview des Timestamps
     */
    private void fillVars(Context context, LayoutInflater inflater, TextView time) {
        this.time = time;
        rh = new RequestHandler();
        this.context = context;
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.time_picker, null);
        tp = (TimePicker) layout.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp.setHour(oldHour);
            tp.setMinute(oldMinute);
        } else {
            tp.setCurrentHour(oldHour);
            tp.setCurrentMinute(oldMinute);
        }
        setView(layout);
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * Erzeugt OnlickListener
     *
     * @param id      Id
     * @param manager Manager
     * @return OnClickManager
     */
    private DialogInterface.OnClickListener setOkButton(final int id, final ScenarioTimestampManager manager) {
        return new DialogInterface.OnClickListener() {
            /**
             * OnClick Methode
             * Aktualisiert die Zeit in der Datenbank auf die des timepickers, wenn diese Zeit nicht der selben Zeit entspricht und diese noch nicht belegt ist
             * @param dialog Dialog
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour;
                int minute;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = tp.getHour();
                    minute = tp.getMinute();
                } else {
                    hour = tp.getCurrentHour();
                    minute = tp.getCurrentMinute();
                }

                if (oldHour != hour || oldMinute != minute) {
                    if (manager.compareTime(hour, minute)) {
                        String msgHour = rh.updateSingleValue(TAG_SCENARIO, TAG_HOUR, Integer.toString(hour), id);
                        String msgMinute = rh.updateSingleValue(TAG_SCENARIO, TAG_MINUTE, Integer.toString(minute), id);
                        if (!catchError(context, msgHour) && !catchError(context, msgMinute)) {
                            time.setText(formatTimeString(hour, minute));
                        }
                    } else {
                        ErrorHandler.createToast(context, ERROR_SAME_TIME);
                    }
                }
            }
        };
    }

    /**
     * Erzeugt OnClickListener
     *
     * @param table   Tabelle
     * @param id      Id
     * @param manager Manager
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final String table, final int id, final DeviceTimestampManager manager) {
        return new DialogInterface.OnClickListener() {
            /**
             * OnClick Methode
             * Aktualisiert die Zeit in der Datenbank auf die des timepickers, wenn diese Zeit nicht der selben Zeit entspricht und diese noch nicht belegt ist
             * @param dialog Dialog
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour;
                int minute;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = tp.getHour();
                    minute = tp.getMinute();
                } else {
                    hour = tp.getCurrentHour();
                    minute = tp.getCurrentMinute();
                }

                if (oldHour != hour || oldMinute != minute) {
                    if (manager.compareTime(hour, minute)) {
                        String msgHour = rh.updateSingleValue(table, TAG_HOUR, Integer.toString(hour), id);
                        String msgMinute = rh.updateSingleValue(table, TAG_MINUTE, Integer.toString(minute), id);
                        if (!catchError(context, msgHour) && !catchError(context, msgMinute)) {
                            time.setText(formatTimeString(hour, minute));
                        }
                    } else {
                        ErrorHandler.createToast(context, ERROR_SAME_TIME);
                    }
                }
            }
        };
    }
}