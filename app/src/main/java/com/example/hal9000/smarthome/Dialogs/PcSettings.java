package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.PcDataSet;

import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.squareup.picasso.Picasso;

/**
 * The type Pc settings.
 */
class PcSettings extends DialogListener {
    private final ImageView picture;


    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param dataSet        the data set
     * @param layoutInflater Inflater
     * @param inflater       the inflater
     */
    PcSettings(Context context, PcDataSet dataSet, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.pc_settings, null, false);

        picture = (ImageView) layout.findViewById(R.id.imSecurity);

        setView(layout);
        setPositiveButton("Schließen", setCancelButton());
    }


    /**
     * Gets picture.
     *
     * @return the picture
     */
    ImageView getPicture() {
        return picture;
    }

    /**
     * Refresh.
     *
     * @param name the name
     */
    void refresh(String name) {
        updateDatasets();
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + name + PNG_FILE_EXTENSION).placeholder(R.drawable.loading).resize(400, 400).centerCrop().into(picture);
    }
}

