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

public class DryerSettings extends DialogListener {

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param amount         Menge
     * @param clothes        Art der Kleidung
     * @param id             ID
     * @param layoutInflater Inflater
     */
    public DryerSettings(Context context, int amount, int clothes, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.dryer_settings, null);
        NumberPicker inputAmount = (NumberPicker) layout.findViewById(R.id.np_amount);
        NumberPicker inputClothes = (NumberPicker) layout.findViewById(R.id.np_Clothes);

        inputAmount.setDisplayedValues(new String[]{"Wenig", "Normal", "Viel"});

        inputAmount.setMinValue(0);
        inputAmount.setMaxValue(2);
        inputAmount.setValue(amount);
        inputAmount.setWrapSelectorWheel(false);

        inputClothes.setDisplayedValues(new String[]{"Kochwäsche", "Buntwäsche", "Pflegeleicht", "Feinwäsche", "Baumwolle"});
        inputClothes.setMinValue(0);
        inputClothes.setMaxValue(4);
        inputClothes.setValue(clothes);
        inputClothes.setWrapSelectorWheel(false);

        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(inputAmount, inputClothes, id));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param amount  Numberpicker aus dem Dialog, Menge der Kleidung
     * @param clothes Numberpicker aus dem Dialog, Art der Kleidung
     * @param id      Id
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final NumberPicker amount, final NumberPicker clothes, final int id) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Aktualisierung der Werte beim Klick des Buttons
             * @param dialog Dialog in dem sich die Elemente befinden
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int rpm = -1;
                int temperature = -1;
                int duration = -1;
                int currentAmount = amount.getValue();
                int currentClothes = clothes.getValue();
                String resultAmount = Integer.toString(currentAmount);
                String resultClothes = Integer.toString(currentClothes);
                switch (currentClothes) {
                    case 0:
                    case 1:
                        rpm = 1000;
                        temperature = 100;
                        switch (currentAmount) {
                            case 0:
                                duration = 45;
                                break;
                            case 1:
                                duration = 72;
                                break;
                            case 2:
                                duration = 90;
                                break;
                        }
                        break;
                    case 2:
                        rpm = 900;
                        temperature = 90;
                        switch (currentAmount) {
                            case 0:
                                duration = 35;
                                break;
                            case 1:
                                duration = 42;
                                break;
                            case 2:
                                duration = 45;
                                break;
                        }
                        break;
                    case 3:
                        rpm = 800;
                        temperature = 60;
                        switch (currentAmount) {
                            case 0:
                                duration = 30;
                                break;
                            case 1:
                                duration = 35;
                                break;
                            case 2:
                                duration = 40;
                                break;
                        }
                        break;
                    case 4:
                        rpm = 1000;
                        temperature = 80;
                        switch (currentAmount) {
                            case 0:
                                duration = 35;
                                break;
                            case 1:
                                duration = 40;
                                break;
                            case 2:
                                duration = 45;
                                break;
                        }
                        break;
                }

                RequestHandler rh = new RequestHandler();
                String msgSingleAmount = rh.updateSingleValue(STRING_TYPE_EN_DRYER, TAG_AMOUNT, resultAmount, id);
                String msgSingleClothes = rh.updateSingleValue(STRING_TYPE_EN_DRYER, TAG_CLOTHES, resultClothes, id);

                String msgSingleTemperature = rh.updateSingleValue(STRING_TYPE_EN_DRYER, TAG_TEMPERATURE, Integer.toString(temperature), id);
                String msgSingleDuration = rh.updateSingleValue(STRING_TYPE_EN_DRYER, TAG_DURATION, Integer.toString(duration), id);
                String msgSingleRPM = rh.updateSingleValue(STRING_TYPE_EN_DRYER, TAG_RPM, Integer.toString(rpm), id);

                catchError(getContext(), msgSingleAmount);
                catchError(getContext(), msgSingleClothes);
                catchError(getContext(), msgSingleTemperature);
                catchError(getContext(), msgSingleDuration);
                catchError(getContext(), msgSingleRPM);
            }
        };
    }
}
