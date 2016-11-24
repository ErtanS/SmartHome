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
 * The type Heater settings.
 */
public class HeaterSettings extends DialogListener {

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param temperature    Temepratur
     * @param id             ID
     * @param layoutInflater Inflater
     * @param inflater       Inflater
     */
    public HeaterSettings(Context context, int temperature, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.heater_settings, null);
        NumberPicker input = (NumberPicker) layout.findViewById(R.id.np_Temperature);
        input.setMinValue(15);
        input.setMaxValue(35);
        input.setValue(temperature);
        input.setWrapSelectorWheel(false);
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(input, id));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param input Numberpicker aus dem Dialog das die akutelle Temperatur beinhaltet
     * @param id    Id
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final NumberPicker input, final int id) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Aktualisierung der Werte beim Klick des Buttons
             * @param dialog Dialog in dem sich die Elemente befinden
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = Integer.toString(input.getValue());
                RequestHandler rh = new RequestHandler();
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_HEATER, TAG_TEMPERATURE, result, id);
                catchError(getContext(), msgSingle);
            }
        };
    }
}
