package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.ColorPicker.ColorPickerDialog;
import com.example.hal9000.smarthome.DataSet.WallDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.squareup.picasso.Picasso;

import static com.example.hal9000.smarthome.Helper.Config.BUTTON_ABBRUCH;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_NO;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_OK;
import static com.example.hal9000.smarthome.Helper.Config.BUTTON_YES;
import static com.example.hal9000.smarthome.Helper.Config.DELETE_PICTURE;
import static com.example.hal9000.smarthome.Helper.Config.DELETE_TIMESTAMP;
import static com.example.hal9000.smarthome.Helper.Config.INT_UNSET_ID;
import static com.example.hal9000.smarthome.Helper.Config.STRING_TYPE_EN_TV;
import static com.example.hal9000.smarthome.Helper.Config.STRING_TYPE_EN_WALL;
import static com.example.hal9000.smarthome.Helper.Config.TAG_CHANNEL;
import static com.example.hal9000.smarthome.Helper.Config.TAG_COLOR;
import static com.example.hal9000.smarthome.Helper.Config.TAG_PICTUREID;
import static com.example.hal9000.smarthome.Helper.Config.TAG_SCENARIO;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;


public class WallSettings  extends  DialogListener{
    private ImageView picture;
    private ColorPickerDialog dialog;

    private final RequestHandler rh;

    /**
     * Konstruktor
     *
     * @param context          Kontext
     */
    public WallSettings(Context context, WallDataSet dataSet, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        rh = new RequestHandler();
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.wall_settings, null,false);


        dialog = new ColorPickerDialog(Color.parseColor(dataSet.getColor()),layout);
        picture = (ImageView) layout.findViewById(R.id.imPicture);
        ImageView delete = (ImageView) layout.findViewById(R.id.imDelete);
        delete.setOnClickListener(setDeleteButton(dataSet.getId()));
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(dialog, dataSet.getId()));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());

    }





    /**
     * OnClicklistener hinzufügen
     *
     * @param input      Numberpicker aus dem Dialog das die akutelle Temperatur beinhaltet
     * @param id         Id
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
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_WALL, TAG_COLOR, result, id);
                catchError(getContext(), msgSingle);
                updateDatasets();
            }
        };
    }

    private View.OnClickListener setDeleteButton(final int id) {
        //noinspection JavaDoc
        /**
         * Aktualisierung der Werte beim Klick des Buttons
         * @param dialog Dialog in dem sich die Elemente befinden
         * @param which
         */
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogListener builder = new DialogListener(getContext());
                builder.setMessage(DELETE_PICTURE);
                builder.setPositiveButton(BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = rh.updateSingleValue(STRING_TYPE_EN_WALL,TAG_PICTUREID,Integer.toString(INT_UNSET_ID),id);
                        if (!catchError(getContext(), msg)) {
                            picture.setImageResource(R.drawable.loading);
                            updateDatasets();
                        }
                    }
                });
                builder.setNegativeButton(BUTTON_NO, builder.setCancelButton());
                builder.show();



            }


        };
    }




    public ImageView getPicture() {
        return picture;
    }


    public void refresh(String name){
        updateDatasets();

        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + name + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
        //picture.invalidate();

    }
}

