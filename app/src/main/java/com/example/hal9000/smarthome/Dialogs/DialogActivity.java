package com.example.hal9000.smarthome.Dialogs;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.LightDataSet;
import com.example.hal9000.smarthome.DataSet.PcDataSet;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.DataSet.WallDataSet;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static com.example.hal9000.smarthome.Helper.Config.PNG_FILE_EXTENSION;

/**
 * The type Dialog activity.
 */
public class DialogActivity extends DialogFragment {

    private String name;
    private Bitmap bitmap;
    private int id;
    private String table;
    private String picture;
    private String camName;

    private DeviceDataSet dataSet;
    private Inflater inflater;
    private LayoutInflater layoutInflater;
    private DialogListener dialog;

    /**
     * Standardkonstruktor
     */
    public DialogActivity() {

    }

    /**
     * Argumente füllen
     *
     * @param dataSet        EIgenschaften
     * @param inflater       Inflater
     * @param layoutInflater Inflater
     */
    public void setArguments(DeviceDataSet dataSet, Inflater inflater, LayoutInflater layoutInflater) {
        this.dataSet = dataSet;
        this.inflater = inflater;
        this.layoutInflater = layoutInflater;
    }

    /**
     * Bild setzen
     *
     * @param name BildName
     */
    public void setPicture(String name) {
        this.picture = name;
    }

    /**
     * Kameraname setzen
     *
     * @param name KameraName
     */
    public void setCamName(String name) {
        this.camName = name;
    }

    /**
     * Dialog erstellen, je nachdem welcher Gerätetyp ausgewählt ist
     *
     * @param savedInstanceState Instanz
     * @return den Dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (dataSet == null) {
            this.dialog = new SecurityCam(getContext(), picture, layoutInflater, camName);
        } else if (dataSet instanceof LightDataSet) {
            this.dialog = new LightSettings(getContext(), (LightDataSet) dataSet, layoutInflater, inflater);
        } else if (dataSet instanceof PcDataSet) {
            this.dialog = new PcSettings(getContext(), (PcDataSet) dataSet, layoutInflater, inflater);
            createPicture();
        } else if (dataSet instanceof TvDataSet) {
            this.dialog = new TvSettings(getContext(), (TvDataSet) dataSet, layoutInflater, inflater);
            createButton();
        } else if (dataSet instanceof WallDataSet) {
            this.dialog = new WallSettings(getContext(), (WallDataSet) dataSet, layoutInflater, inflater);
            createPictureWall();
        }

        return dialog.create();
    }

    /**
     * OnCliklistener für klick auf das Bild
     *
     * @return onCklickListener
     */
    private View.OnClickListener setImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataSet instanceof TvDataSet) {
                    table = Config.STRING_TYPE_EN_TV;
                } else if (dataSet instanceof PcDataSet) {
                    table = Config.STRING_TYPE_EN_PC;
                } else if (dataSet instanceof WallDataSet) {
                    table = Config.STRING_TYPE_EN_WALL;
                }

                id = dataSet.getId();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Bild auswählen"), 1);
            }
        };
    }

    /**
     * ImageView mit Bild füllen
     */
    private void createButton() {
        ImageView picture = ((TvSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((TvDataSet) dataSet).getPictureid() + PNG_FILE_EXTENSION).placeholder(R.drawable.loading).resize(400, 400).centerCrop().into(picture);
    }

    /**
     * ImageView mit Bild füllen
     */
    private void createPictureWall() {
        ImageView picture = ((WallSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((WallDataSet) dataSet).getPictureid() + PNG_FILE_EXTENSION).placeholder(R.drawable.loading).resize(400, 400).centerCrop().into(picture);
    }

    /**
     * ImageView mit Bild füllen
     */
    private void createPicture() {
        ImageView picture = ((PcSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((PcDataSet) dataSet).getPictureid() + PNG_FILE_EXTENSION).placeholder(R.drawable.loading).resize(400, 400).centerCrop().into(picture);
    }

    /**
     * Rückgabe der Activity
     *
     * @param requestCode Anfragecode
     * @param resultCode  Ergebniscode
     * @param data        Daten
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int PICK_IMAGE_REQUEST = 1;
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                String temp = filePath.toString().replace("\\", "/");
                String[] imageName = temp.split("/");

                name = imageName[imageName.length - 1];
                if(name.contains("%")){
                    name= name.split("%")[1];
                }
                System.out.println(name);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Wandelt Bitmap in String um
     *
     * @param bmp Bild asl Bitmap
     * @return Blld as String
     */
    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Lädt das Bild hoch
     */
    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (dialog instanceof TvSettings) {
                            ((TvSettings) dialog).refresh(name);
                        } else if (dialog instanceof PcSettings) {
                            ((PcSettings) dialog).refresh(name);
                        } else if (dialog instanceof WallSettings) {
                            ((WallSettings) dialog).refresh(name);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println(volleyError.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Creating parameters
                Map<String, String> params = new Hashtable<>();

                //Adding parameters
                params.put(Config.TAG_IMAGE, image);
                params.put(Config.TAG_NAME, name);
                params.put(Config.TAG_ID, Integer.toString(id));
                params.put(Config.TAG_TABLE, table);

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
