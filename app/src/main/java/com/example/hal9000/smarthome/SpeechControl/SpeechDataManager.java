package com.example.hal9000.smarthome.SpeechControl;


import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Helper.Config;

import java.util.ArrayList;

public class SpeechDataManager extends DataManager {

    public SpeechDataManager(Context context){
        fillRoomList(context);
        fillDataSet(Config.STRING_EMPTY,Config.STRING_EMPTY,Config.STRING_EMPTY,Config.CATEGORY_DEVICE,context);
    }

    public ArrayList<String> getRoomList(){
        return rooms;
    }


}
