package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.DataSet;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

public class TvSettings extends DialogListener {

    private NumberPicker channel;
    private ImageView picture;
    private SeekBar volume;


    private final RequestHandler rh;
    /**
     * Konstruktor
     *
     * @param context     Kontext
     * @param layoutInflater    Inflater
     */
    public TvSettings(Context context, TvDataSet dataSet, LayoutInflater layoutInflater,Inflater inflater ) {
        super(context,inflater);
        rh = new RequestHandler();
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.tv_settings, null,false);

        String[] channels = addPlaylist(Config.TAG_CHANNEL);
        String[] list = new String[channels.length+1];
        list[0]= "Bild";
        for(int i = 1; i<list.length;i++){
            list[i]= channels[i-1];
        }


        channel = (NumberPicker) layout.findViewById(R.id.np_Channel);
        channel.setDisplayedValues( list );
        channel.setMinValue(0);
        channel.setMaxValue(5);
        channel.setValue(dataSet.getChannel());
        channel.setWrapSelectorWheel(false);

        picture = (ImageView) layout.findViewById(R.id.imPicture);
        createIntensityBar(layout,dataSet.getId(),dataSet.getVolume());
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(channel, dataSet.getId()));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
        //createButton(layout,dataSet.getPictureid(),dataSet.getId());
        
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
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_TV, TAG_CHANNEL, result, id);
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
        volume = (SeekBar) layout.findViewById(R.id.skVolume);
        if (volume != null) {
            volume.setProgress(intensity);
            volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    String msgSingle = rh.updateSingleValue(Config.STRING_TYPE_EN_TV, Config.TAG_VOLUME, Integer.toString(volume.getProgress()), id);
                    catchError(getContext(), msgSingle);
                }
            });
        }
    }

    public NumberPicker getChannel() {
        return channel;
    }

    public ImageView getPicture() {
        return picture;
    }

    public SeekBar getVolume() {
        return volume;
    }

    public void refresh(String name){
        updateDatasets();

        channel.setValue(0);
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + name + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
        //picture.invalidate();

    }
}

