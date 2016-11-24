package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.*;

public class CameraKeyWordSet extends KeyWordSet {

    private ArrayList<Term> autoEmergencyKeyWords = new ArrayList<>();

    public CameraKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
    }

    public void determineParams(String statement){
        RequestParamSet stateResult=getState(statement);
        if(stateResult!=null){
            resultSet.add(stateResult);
        }
    }

    private Term setStateOnKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("an");
        keyWords.add("ein");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_EIN),keyWords);
    }

    private Term setStateOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("aus");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_AUS),keyWords);
    }

   /* private Term setAutoEmergencyOnKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("automatisch");
        keyWords.add("ein");
        keyWords.add("an");
        return new Term(TAG_AUTOEMERGENCY,Integer.toString(INT_STATUS_EIN),keyWords);
    }

    private Term setAutoEmergencyOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("nicht");
        keyWords.add("automatisch");
        keyWords.add("aus");
        return new Term(TAG_AUTOEMERGENCY,Integer.toString(INT_STATUS_AUS),keyWords);
    }*/

}
