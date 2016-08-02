package com.example.hal9000.smarthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.Helper.Config;
import com.squareup.picasso.Picasso;

import static com.example.hal9000.smarthome.Helper.Config.BUTTON_ABBRUCH;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_IGNORIEREN;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_NOTFALL;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_OK;
import static com.example.hal9000.smarthome.Helper.Config.STRING_TYPE_EN_DOOR;
import static com.example.hal9000.smarthome.Helper.Config.TAG_PASSWORD;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

public class SecurityCam extends DialogListener {

    public SecurityCam(Context context, String pictureName, LayoutInflater layoutInflater, String camName) {
        super(context);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.activity_security_cam, null);
        ImageView input = (ImageView) layout.findViewById(R.id.imSecurity);
        Picasso.with(getContext()).load(Config.URL_IMAGE_SECURITY + pictureName).placeholder(R.drawable.loading).into(input);
        System.out.println(camName);
        setView(layout);
        setPositiveButton(BUTTON_NOTFALL, setOkButton(camName));
        setNegativeButton(BUTTON_IGNORIEREN, setCancelButton());
    }

    /**
     * Erstellen eines OnClickListeners für einen Button
     *
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final String camName) {
        return new DialogInterface.OnClickListener() {
            /**
             * Mehtode die Ausgeführt wird wenn der Button betätigt wird
             * Auslesen und Aktualisierung des Passworts für eine bestimmte Türe
             * @param dialog Dialog in dem sich der Button befindet
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestHandler rh= new RequestHandler();
                String result = rh.triggerEmergencyScenario(camName);
                catchError(getContext(), result);
            }
        };
    }

    // hallo test
}
