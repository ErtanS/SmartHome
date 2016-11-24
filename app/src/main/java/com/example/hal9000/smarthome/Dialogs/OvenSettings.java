package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Database.RequestHandler;

import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.R;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

/**
 * The type Oven settings.
 */
public class OvenSettings extends DialogListener {

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param temperature    Temperatur
     * @param duration       Dauer
     * @param id             ID
     * @param layoutInflater Inflater
     * @param inflater       the inflater
     */
    public OvenSettings(Context context, int temperature, int duration, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.oven_settings, null);
        NumberPicker inputTemperature = (NumberPicker) layout.findViewById(R.id.np_Temperature);
        NumberPicker inputDurationHour = (NumberPicker) layout.findViewById(R.id.numberPickerHour);
        NumberPicker inputDurationMinute = (NumberPicker) layout.findViewById(R.id.numberPickerMinute);

        inputTemperature.setMinValue(0);
        inputTemperature.setMaxValue(275);
        inputTemperature.setValue(temperature);

        inputTemperature.setWrapSelectorWheel(false);

        inputDurationHour.setMinValue(0);
        inputDurationHour.setMaxValue(5);
        inputDurationHour.setValue(duration / 60);
        inputDurationHour.setWrapSelectorWheel(false);

        inputDurationMinute.setMinValue(0);
        inputDurationMinute.setMaxValue(59);
        inputDurationMinute.setValue(duration % 60);
        inputDurationMinute.setWrapSelectorWheel(false);

        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(inputTemperature, inputDurationHour, inputDurationMinute, id));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param temperature Temperatur
     * @param hour Stunde
     * @param minute Minute
     * @param id          Id
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final NumberPicker temperature, final NumberPicker hour, final NumberPicker minute, final int id) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Aktualisierung der Werte beim Klick des Buttons
             * @param dialog Dialog in dem sich die Elemente befinden
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String resultTemperature = Integer.toString(temperature.getValue());
                String resultDuration = Integer.toString(hour.getValue() * 60 + minute.getValue());
                RequestHandler rh = new RequestHandler();
                String msgSingleTemperature = rh.updateSingleValue(STRING_TYPE_EN_OVEN, TAG_TEMPERATURE, resultTemperature, id);
                String msgSingleDuration = rh.updateSingleValue(STRING_TYPE_EN_OVEN, TAG_DURATION, resultDuration, id);
                catchError(getContext(), msgSingleTemperature);
                catchError(getContext(), msgSingleDuration);
            }
        };
    }
}
