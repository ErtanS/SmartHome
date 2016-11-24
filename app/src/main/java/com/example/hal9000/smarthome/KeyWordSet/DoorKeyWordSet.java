package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;

public class DoorKeyWordSet extends KeyWordSet {

    public DoorKeyWordSet() {
        stateKeyWords.add(setStateOffKeyWords());
        stateKeyWords.add(setStateOnKeyWords());
    }

    public void determineParams(String statement){
        RequestParamSet stateResult=getState(statement);
        if(stateResult!=null){
            resultSet.add(stateResult);
        }
    }

    private Term setStateOnKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("zu");
        keyWords.add("schliesse");
        keyWords.add("schließe");
        keyWords.add("riegel");
        keyWords.add("sperre");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_EIN),keyWords);
    }

    private Term setStateOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("auf");
        keyWords.add("öffne");
        keyWords.add("oeffne");
        keyWords.add("aufschliessen");
        keyWords.add("aufschließen");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_AUS),keyWords);
    }
}
