package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Database.RequestHandler;
import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

public class WaterSettings extends DialogListener {

    private final RequestHandler rh;


    /**
     * Konstruktor
     *
     * @param context     Kontext
     * @param temperature Temepratur
     * @param id          ID
     * @param layoutInflater    Inflater
     */
    public WaterSettings(Context context, int temperature,int intensity, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context,inflater);
        rh = new RequestHandler();
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.water_settings, null);
        NumberPicker input = (NumberPicker) layout.findViewById(R.id.np_Water);
        input.setMinValue(10);
        input.setMaxValue(80);
        input.setValue(temperature);
        input.setWrapSelectorWheel(false);
        createIntensityBar(layout,id,intensity);
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(input, id));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param input      Numberpicker aus dem Dialog das die akutelle Temperatur beinhaltet
     * @param id         Id
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
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_WATER, TAG_TEMPERATURE, result, id);
                catchError(getContext(), msgSingle);
            }
        };
    }

    /**
     * Zuweisung des Lautstärkereglers an ein layout Element
     * Übertragung der aktuellen Lautstärke an die Datenbank nachdem der Regler benutzt wurde
     *
     * @param layout    aktuelle View
     * @param id        Id
     * @param intensity Lautstärke zum Start
     */
    private void createIntensityBar(View layout, final int id, int intensity) {
        final SeekBar intensityBar = (SeekBar) layout.findViewById(R.id.skIntensity);
        if (intensityBar != null) {
            intensityBar.setProgress(intensity);
            intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    String msgSingle = rh.updateSingleValue(Config.STRING_TYPE_EN_WATER, Config.TAG_INTENSITY, Integer.toString(intensityBar.getProgress()), id);
                    catchError(getContext(), msgSingle);
                }
            });
        }
    }
}
