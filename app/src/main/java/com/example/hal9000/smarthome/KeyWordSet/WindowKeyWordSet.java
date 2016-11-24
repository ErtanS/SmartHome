package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;

public class WindowKeyWordSet extends KeyWordSet {

    public WindowKeyWordSet() {
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
        keyWords.add("auf");
        keyWords.add("oeffne");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_EIN),keyWords);
    }

    private Term setStateOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("zu");
        keyWords.add("schließe");
        keyWords.add("schliesse");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_AUS),keyWords);
    }
}
