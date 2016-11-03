package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.R;

import static com.example.hal9000.smarthome.Helper.Config.*;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

public class DoorSettings extends DialogListener {

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param password       Passwort
     * @param id             Id
     * @param layoutInflater Inflater
     */
    public DoorSettings(Context context, String password, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.door_settings, null);
        EditText input = (EditText) layout.findViewById(R.id.editPassword);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(password);
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(input, id));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * Erstellen eines OnClickListeners für einen Button
     *
     * @param input Passwortfeld
     * @param id    Geräteid
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final EditText input, final int id) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Mehtode die Ausgeführt wird wenn der Button betätigt wird
             * Auslesen und Aktualisierung des Passworts für eine bestimmte Türe
             * @param dialog Dialog in dem sich der Button befindet
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = input.getText().toString();
                RequestHandler rh = new RequestHandler();

                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_DOOR, TAG_PASSWORD, result, id);
                catchError(getContext(), msgSingle);
            }
        };
    }
}
