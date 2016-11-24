package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.ColorPicker.ColorPickerDialog;
import com.example.hal9000.smarthome.DataSet.LightDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;

import static com.example.hal9000.smarthome.Helper.Config.BUTTON_ABBRUCH;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_OK;
import static com.example.hal9000.smarthome.Helper.Config.STRING_TYPE_EN_LIGHT;
import static com.example.hal9000.smarthome.Helper.Config.TAG_COLOR;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;


/**
 * The type Light settings.
 */
class LightSettings extends DialogListener {

    private SeekBar skIntensity;
    private final RequestHandler rh;

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param dataSet        the data set
     * @param layoutInflater Inflater
     * @param inflater       the inflater
     */
    LightSettings(Context context, LightDataSet dataSet, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        rh = new RequestHandler();
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.light_settings, null, false);
        ColorPickerDialog dialog = new ColorPickerDialog(Color.parseColor(dataSet.getColor()), layout);

        createIntensityBar(layout, dataSet.getId(), dataSet.getIntensity());
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(dialog, dataSet.getId()));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param input ColorPicker aus dem Dialog das die akutelle Farbe beinhaltet
     * @param id    Id
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final ColorPickerDialog input, final int id) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Aktualisierung der Werte beim Klick des Buttons
             * @param dialog Dialog in dem sich die Elemente befinden
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = String.format("#%06X", (0xFFFFFF & input.getColor()));
                RequestHandler rh = new RequestHandler();
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_LIGHT, TAG_COLOR, result, id);
                catchError(getContext(), msgSingle);
                updateDatasets();
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
        skIntensity = (SeekBar) layout.findViewById(R.id.helligkeitBar);
        if (skIntensity != null) {
            skIntensity.setProgress(intensity);
            skIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    String msgSingle = rh.updateSingleValue(Config.STRING_TYPE_EN_LIGHT, Config.TAG_INTENSITY, Integer.toString(skIntensity.getProgress()), id);
                    catchError(getContext(), msgSingle);
                }
            });
        }
    }
}
