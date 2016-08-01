package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.PcDataSet;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.squareup.picasso.Picasso;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

public class PcSettings extends DialogListener {
    private ImageView picture;


    private final RequestHandler rh;
    /**
     * Konstruktor
     *
     * @param context     Kontext
     * @param layoutInflater    Inflater
     */
    public PcSettings(Context context, PcDataSet dataSet, LayoutInflater layoutInflater, Inflater inflater ) {
        super(context,inflater);
        rh = new RequestHandler();
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.activity_security_cam, null,false);

        picture = (ImageView) layout.findViewById(R.id.imSecurity);

        setView(layout);
        setPositiveButton("Schließen", setCancelButton());
    }




    public ImageView getPicture() {
        return picture;
    }

    public void refresh(String name){
        updateDatasets();
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + name + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
    }
}

