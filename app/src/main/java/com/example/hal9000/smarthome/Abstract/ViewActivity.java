package com.example.hal9000.smarthome.Abstract;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;

import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.ErrorHandler;
import com.example.hal9000.smarthome.SpeechControl.SpeechRecognition;
import com.example.hal9000.smarthome.Views.DeviceView.DynamicDeviceView;
import com.example.hal9000.smarthome.Views.OverView.OverViewDataManager;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.hal9000.smarthome.Helper.Config.SPEECHINTENT_REQ_CODE;
import static com.example.hal9000.smarthome.Helper.Config.STRING_INTENT_TYPE;

public abstract class ViewActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SPEECHINTENT_REQ_CODE && resultCode==RESULT_OK){
            SpeechRecognition speechRecognition = new SpeechRecognition(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS),this);
            speechRecognition.fillStatementLists();
            speechRecognition.performBestStatement();
//            String text="Geräte: ";
//            ArrayList<String> deviceTypes = speechRecognition.findDeviceTypes();
//            ArrayList<String> roomList = speechRecognition.getRoomList();
//            ArrayList<DeviceDataSet> devices = speechRecognition.getDevices();
//            if(devices!=null && devices.size()>0){
//                if(devices.size()==1){
//
//                }
//            }
//            if(deviceTypes!=null){
//                for (String device: deviceTypes) {
//                    text += device+", ";
//                }
//                ErrorHandler.createToast(this,text);
//                if(!deviceTypes.isEmpty()){
//                    openDeviceView(deviceTypes.get(0));
//                }
//            }
            /*
            ArrayList<String> speechResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String text= "Vorschlag: ";
            for (String word: speechResults) {
                text=text+word+"Vorschlag: ";
                System.out.println(word);
            }
            ErrorHandler.createToast(this,text);
            */
        }
    }

    protected void startSpeechRecognition(){
        Intent speechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toString());
        startActivityForResult(speechRecognitionIntent,SPEECHINTENT_REQ_CODE);
    }

    private void openDeviceView(String type){
        Intent nextScreen = new Intent(this, DynamicDeviceView.class);
        Bundle b = new Bundle();
        System.out.println(type);
        b.putString(STRING_INTENT_TYPE, type);
        b.putString(Config.STRING_ACTIVITY_TITLE, OverViewDataManager.uebersetzer(type));
        nextScreen.putExtras(b);
        startActivity(nextScreen);
    }
}
