package com.example.hal9000.smarthome.SpeechControl;


import android.content.Context;

import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.ErrorHandler;
import com.example.hal9000.smarthome.KeyWordSet.CameraKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.DeviceTypeKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.DoorKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.DryerKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.HeaterKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.LightKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.OvenKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.PCKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.ShuttersKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.SpeakerKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.StoveKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.TVKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.WasherKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.WaterKeyWordSet;
import com.example.hal9000.smarthome.KeyWordSet.WindowKeyWordSet;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.hal9000.smarthome.Helper.Config.*;

public class Statement {
    private String statement;
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<Statement> wordCombinations = new ArrayList<>();
    private ArrayList<String> deviceTypes= new ArrayList<>();
    private ArrayList<String> roomList= new ArrayList<>();
    private ArrayList<DeviceDataSet> devices= new ArrayList<>();
    private ArrayList<String> otherWords = new ArrayList<>();
    private int priority;
    private DeviceTypeKeyWordSet deviceTypeKeyWordSet = new DeviceTypeKeyWordSet();

    public Statement(String statement) {
        this.statement = statement;
        removeUmlauts();
        seperateWords();
    }

    public int getPriority() {
        return priority;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public ArrayList<Statement> getWordCombinations() {
        return wordCombinations;
    }

    public void setWordCombinations(ArrayList<Statement> wordCombinations) {
        this.wordCombinations = wordCombinations;
    }

    public void calculatePriority(){
        if(devices!=null && devices.size()>0){
            priority=STATEMENT_PRIORITY_HIGH;
        }
        else if(roomList!=null && roomList.size()>0 && deviceTypes!=null && deviceTypes.size()>0){
            priority=STATEMENT_PRIORITY_NORMAL;
        }
        else{
            priority=STATEMENT_PRIORITY_LOW;
        }
    }

    public void findDeviceTypes(){

        deviceTypes= deviceTypeKeyWordSet.getDeviceTypes(wordList);
    }

    private void seperateWords(){
        if(statement!=null) {
            String[] words = statement.split(Config.STRING_SPACE);
            if (words.length > 0) {
                wordList= new ArrayList<>();
                Collections.addAll(wordList, words);
            }
        }
    }

    public void generateWordCombinations(){
        if(wordList!=null && wordList.size()>2) {
            for (int i = 0; i < wordList.size() - 1; i++) {
                int difference=0;
                if(i==0){
                    difference=1;
                }
                for (int j = wordList.size()-difference; j > i+1; j--) {
                    String statement = Config.STRING_EMPTY;
                    for (int k = i; k < j; k++) {
                        statement = statement + wordList.get(k) + Config.STRING_SPACE;
                    }
                    statement = statement.trim();
                    wordCombinations.add(new Statement(statement));
                }
            }
        }
    }

    private void removeUmlauts() {
        statement = statement.replace("ä", "ae");
        statement = statement.replace("ü", "ue");
        statement = statement.replace("ö", "oe");
        statement=statement.replace("ß","ss");
        statement=statement.toLowerCase();
        statement=statement.trim();
    }

    public void findRoomsInSpokenWords(ArrayList<String> rooms){
        roomList=new ArrayList<>();
        for (String room:rooms) {
            if(equalsStatement(getTrimmedString(room))){
                if(!roomList.contains(room)) {
                    roomList.add(room);
                }
                break;
            }
        }
        for (String word:wordList) {
            for (String room:rooms) {
                if(word.equalsIgnoreCase(getTrimmedString(room))){
                    if(!roomList.contains(room)) {
                        roomList.add(room);
                    }
                    break;
                }
            }
        }
        for (Statement statement:wordCombinations) {
            for (String room:rooms) {
                if(statement.equalsStatement(getTrimmedString(room))){
                    if(!roomList.contains(room)) {
                        roomList.add(room);
                    }
                    break;
                }
            }
        }
    }

    public void findDevicesInSpokenWords(ArrayList<DeviceDataSet> deviceList){
        devices=new ArrayList<>();
        for (DeviceDataSet device:deviceList) {
            if(equalsStatement(getTrimmedString(device.getName()))){
                if(!devices.contains(device)) {
                    devices.add(device);
                }
                break;
            }
        }
        for (String word:wordList) {
            for (DeviceDataSet device:deviceList) {
                if(word.equalsIgnoreCase(getTrimmedString(device.getName()))){
                    if(!devices.contains(device)) {
                        devices.add(device);
                    }
                    break;
                }
            }
        }
        for (Statement statement:wordCombinations) {
            for (DeviceDataSet device:deviceList) {
                if(statement.equalsStatement(getTrimmedString(device.getName()))){
                    if(!devices.contains(device)) {
                        devices.add(device);
                    }
                    break;
                }
            }
        }
    }

    public void printText(){
        System.out.println("Statement:\n"+statement);
        System.out.println("Kombinationen");
        for (Statement state:wordCombinations) {
            System.out.println(state.getStatement());
        }
        System.out.println("Geräte Typen");
        for (String word:deviceTypes) {
            System.out.println(word);
        }
        System.out.println("Räume");
        for (String word:roomList) {
            System.out.println(word);
        }
        System.out.println("Geräte");
        for (DeviceDataSet word:devices) {
            System.out.println(word.getName());
        }
//        System.out.println("Andere");
//        for (String word:otherWords) {
//            System.out.println(word);
//        }
    }

    private Boolean equalsStatement(String statement){
        return statement.equalsIgnoreCase(getTrimmedString(this.statement));
    }

    private String getTrimmedString(String string){
        return string.replaceAll(Config.STRING_SPACE,Config.STRING_EMPTY).trim();
    }

    public Boolean isQualifiedStatement(){
        return (deviceTypes != null && deviceTypes.size() > 0) || (roomList != null && roomList.size() > 0) || (devices != null && devices.size() > 0);
    }

    public boolean isInStatementList(ArrayList<Statement> statements){
        for (Statement state:statements) {
            if(isSameDeviceList(state.devices) && isSameDeviceTypeList(state.deviceTypes) && isSameRoomList(state.roomList)){
                return true;
            }
        }
        return false;
    }

    private Boolean isSameDeviceList(ArrayList<DeviceDataSet> deviceList){
        return deviceList.size() == devices.size() && devices.containsAll(deviceList);
    }

    private Boolean isSameDeviceTypeList(ArrayList<String> deviceTypeList){
        return deviceTypes.size()==deviceTypeList.size() && deviceTypes.containsAll(deviceTypeList);
    }

    private Boolean isSameRoomList(ArrayList<String> roomList){
        return this.roomList.size()==roomList.size() && this.roomList.containsAll(roomList);
    }

    public void createRequestParameters(Context context){
        System.out.println("Auszuführendes Statement:");
        System.out.println(statement);
        RequestHandler rh = new RequestHandler();
        if(devices!=null && devices.size()>0){
            for (DeviceDataSet device:devices) {
                String type = device.getType();
                ArrayList<RequestParamSet> params = getRightParams(context,type);
                if(params!=null && params.size()>0) {
                    for (RequestParamSet param : params) {
                        rh.updateSingleValue(type, param.getColumnName(), param.getColumnValue(), device.getId());
                    }
                }
                else{
                    ErrorHandler.createToast(context,"Es wurde kein korrekter Sprachbefehl für dieses Gerät ausgeführt.");
                }
            }
        }
        else{
            ErrorHandler.createToast(context,"Sie müssen ein bestimmtes Gerät auswählen");
        }
    }

    private ArrayList<RequestParamSet> getRightParams(Context context,String type){
        String error;
        switch (type){
            case STRING_TYPE_EN_CAMERA:
                CameraKeyWordSet camSet = new CameraKeyWordSet();
                camSet.determineParams(statement);
                return camSet.getResultSet();
            case STRING_TYPE_EN_DOOR:
                DoorKeyWordSet doorSet = new DoorKeyWordSet();
                doorSet.determineParams(statement);
                return doorSet.getResultSet();
            case STRING_TYPE_EN_DRYER:
                DryerKeyWordSet dryerSet = new DryerKeyWordSet();
                error= dryerSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return dryerSet.getResultSet();
            case STRING_TYPE_EN_HEATER:
                HeaterKeyWordSet heaterSet = new HeaterKeyWordSet();
                error= heaterSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return heaterSet.getResultSet();
            case STRING_TYPE_EN_LIGHT:
                LightKeyWordSet lightSet = new LightKeyWordSet();
                error= lightSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return lightSet.getResultSet();
            case STRING_TYPE_EN_OVEN:
                OvenKeyWordSet ovenSet = new OvenKeyWordSet();
                error= ovenSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return ovenSet.getResultSet();
            case STRING_TYPE_EN_PC:
                PCKeyWordSet pcSet = new PCKeyWordSet();
                pcSet.determineParams(statement);
                return pcSet.getResultSet();
            case STRING_TYPE_EN_SHUTTERS:
                ShuttersKeyWordSet shuttersSet = new ShuttersKeyWordSet();
                shuttersSet.determineParams(statement);
                return shuttersSet.getResultSet();
            case STRING_TYPE_EN_SPEAKER:
                SpeakerKeyWordSet speakerSet = new SpeakerKeyWordSet();
                error= speakerSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return speakerSet.getResultSet();
            case STRING_TYPE_EN_STOVE:
                StoveKeyWordSet stoveSet = new StoveKeyWordSet();
                error= stoveSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return stoveSet.getResultSet();
            case STRING_TYPE_EN_TV:
                TVKeyWordSet tvSet = new TVKeyWordSet();
                error= tvSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return tvSet.getResultSet();
            /*case STRING_TYPE_EN_WALL:
                WallKeyWordSet wallSet = new WallKeyWordSet();
                break;*/
            case STRING_TYPE_EN_WASHER:
                WasherKeyWordSet washerSet = new WasherKeyWordSet();
                error= washerSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return washerSet.getResultSet();
            case STRING_TYPE_EN_WATER:
                WaterKeyWordSet waterSet = new WaterKeyWordSet();
                error= waterSet.determineParams(statement);
                if(error!=null){
                    ErrorHandler.createToast(context,error);
                }
                return waterSet.getResultSet();
            case STRING_TYPE_EN_WINDOW:
                WindowKeyWordSet windowSet = new WindowKeyWordSet();
                windowSet.determineParams(statement);
                return windowSet.getResultSet();
        }
        return new ArrayList<>();
    }

}
