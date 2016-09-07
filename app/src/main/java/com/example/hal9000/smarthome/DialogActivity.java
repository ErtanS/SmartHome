package com.example.hal9000.smarthome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.CameraDataSet;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.DoorDataSet;
import com.example.hal9000.smarthome.DataSet.DryerDataSet;
import com.example.hal9000.smarthome.DataSet.HeaterDataSet;
import com.example.hal9000.smarthome.DataSet.LightDataSet;
import com.example.hal9000.smarthome.DataSet.OvenDataSet;
import com.example.hal9000.smarthome.DataSet.PcDataSet;
import com.example.hal9000.smarthome.DataSet.SpeakerDataSet;
import com.example.hal9000.smarthome.DataSet.StoveDataSet;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.DataSet.WallDataSet;
import com.example.hal9000.smarthome.DataSet.WasherDataSet;
import com.example.hal9000.smarthome.DataSet.WaterDataSet;
import com.example.hal9000.smarthome.Dialogs.CameraSettings;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.Dialogs.LightSettings;
import com.example.hal9000.smarthome.Dialogs.PcSettings;
import com.example.hal9000.smarthome.Dialogs.TvSettings;
import com.example.hal9000.smarthome.Dialogs.WallSettings;
import com.example.hal9000.smarthome.Helper.Config;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class DialogActivity extends DialogFragment {

    private String name;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private int id;
    private String table ;
    private String picture;
    private String camName;

    private DeviceDataSet dataSet;
    private Inflater inflater;
    private LayoutInflater layoutInflater;
    private DialogListener dialog;

    public DialogActivity(){
    }
    public void setArguments(DeviceDataSet dataSet, Inflater inflater, LayoutInflater layoutInflater) {
        this.dataSet = dataSet;
        this.inflater = inflater;
        this.layoutInflater = layoutInflater;
    }
    public void setPicture(String name){
        this.picture = name;
    }
    public void setCamName(String name){
        this.camName = name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Security Cam - Push Nachricht
        if(dataSet == null){
            this.dialog = new SecurityCam(getContext(),picture,layoutInflater,camName);

        }
        else if (dataSet instanceof CameraDataSet) {

        }
        else if (dataSet instanceof DoorDataSet) {

        }
        else if (dataSet instanceof DryerDataSet) {

        }
        else if (dataSet instanceof HeaterDataSet) {

        }
        else if (dataSet instanceof LightDataSet) {
            this.dialog = new LightSettings(getContext(),(LightDataSet) dataSet,layoutInflater,inflater);

        }
        else if (dataSet instanceof SpeakerDataSet) {

        }
        else if (dataSet instanceof OvenDataSet) {

        }
        else if (dataSet instanceof PcDataSet) {
            this.dialog = new PcSettings(getContext(),(PcDataSet)dataSet,layoutInflater,inflater);
            createPicture();

        }
        else if (dataSet instanceof StoveDataSet) {

        }
        else if (dataSet instanceof TvDataSet) {
            this.dialog = new TvSettings(getContext(), (TvDataSet) dataSet,layoutInflater, inflater);
            createButton();
        }
        else if (dataSet instanceof WallDataSet) {
            this.dialog = new WallSettings(getContext(),(WallDataSet) dataSet,layoutInflater,inflater);
            createPictureWall();
        }
        else if (dataSet instanceof WasherDataSet) {

        }
        else if (dataSet instanceof WaterDataSet) {

        }
        return dialog.create();
    }

    private View.OnClickListener setImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Bundle bundle = new Bundle();
                bundle.putInt(Config.TAG_ID,id);
                bundle.putString(Config.TAG_TABLE,Config.STRING_TYPE_EN_TV);
*/
                if(dataSet instanceof  TvDataSet){
                    table = Config.STRING_TYPE_EN_TV;
                }
                else if(dataSet instanceof PcDataSet){
                    table = Config.STRING_TYPE_EN_PC;
                }
                else if(dataSet instanceof WallDataSet){
                    table = Config.STRING_TYPE_EN_WALL;
                }

                id = dataSet.getId();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Bild auswählen"), 1);
                /*Intent nextScreen = new Intent(getContext(), PictureActivity.class);
                nextScreen.putExtras(bundle);
                getContext().startActivity(nextScreen);
                */

            }
        };
    }

    public void createButton(){
        ImageView picture = ((TvSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((TvDataSet)dataSet).getPictureid() + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
    }
    public void createPictureWall(){
        ImageView picture = ((WallSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((WallDataSet)dataSet).getPictureid() + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
    }

    public void createPicture(){
        ImageView picture = ((PcSettings) dialog).getPicture();
        picture.setOnClickListener(setImage());
        // show The Image in a ImageView
        // TODO: 27.07.2016 wegen out of memory nochmal nachschauen.... wenn man das bild scaled dann gibts weniger probleme
        // TODO: 27.07.2016 nach schließen von dialog -> Fehler(imageview ist nicht mehr erreichbar)
        Picasso.with(getContext()).load(Config.URL_IMAGE_FOLDER + ((PcDataSet)dataSet).getPictureid() + ".png").placeholder(R.drawable.loading).resize(400,400).centerCrop().into(picture);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                String temp = filePath.toString().replace("\\","/");
                String[] imageName = temp.split("/");
                name = imageName[imageName.length-1];
                System.out.println(name);
                uploadImage();
                //getActivity().finish();

                //Setting the Bitmap to ImageView
                // imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                //getActivity().finish();
            }
        }
        else{
            //getActivity().finish();
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    private void uploadImage(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Showing toast message of the response
                        Toast.makeText(getContext(), s , Toast.LENGTH_LONG).show();
                        System.out.println(s);

                        if(dialog instanceof  TvSettings) {
                            ((TvSettings) dialog).refresh(name);
                        }
                        else if(dialog instanceof  PcSettings) {
                            ((PcSettings) dialog).refresh(name);
                        }
                        else if(dialog instanceof  WallSettings) {
                            ((WallSettings) dialog).refresh(name);
                        }
                        System.out.println("--------------------------------------------------kein error--------------------------------------------------");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(getContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        System.out.println(volleyError.getMessage().toString());
                        System.out.println("--------------------------------------------------error--------------------------------------------------");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(Config.TAG_IMAGE, image);
                params.put(Config.TAG_NAME,  name);
                params.put(Config.TAG_ID,Integer.toString(id));
                params.put(Config.TAG_TABLE,table);

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
