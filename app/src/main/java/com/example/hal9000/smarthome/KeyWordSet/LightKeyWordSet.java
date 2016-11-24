package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_INTENSITY;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;

public class LightKeyWordSet extends KeyWordSet {

    private ArrayList<Term> intensityKeyWords = new ArrayList<>();

    public LightKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        intensityKeyWords.add(setIntensityKeyWords());
    }

    public String determineParams(String statement){
        RequestParamSet resultState=getState(statement);
        if(resultState!=null){
            resultSet.add(resultState);
        }
        RequestParamSet resultIntensity= getIntensity(statement);
        if(resultIntensity!=null){
            if(resultIntensity.getColumnValue().equals("-1")){
                return "Die gewünschte Helligkeit liegt nicht in dem gültigen Bereich zwischen 0 und 100 Prozent.";
            }
            resultSet.add(resultIntensity);
        }
        return null;
    }

    private RequestParamSet getIntensity(String statement){
        for (Term term:intensityKeyWords) {
            if(term.containsKeyword(statement)){
                ArrayList<Integer> possibleIntensities=getNumberinString(statement);
                int intensity=-1;
                for (int number:possibleIntensities) {
                    if(number>=0 && number<=100 ){
                        intensity=number;
                    }
                }
                //// TODO: 18.11.2016 Entscheidung welche Zahl(näher hinter temperatur?)
                return new RequestParamSet(term.getType(),Integer.toString(intensity));
            }
        }
        return null;
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

    private Term setIntensityKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Helligkeit");
        keyWords.add("Intensitaet");
        return new Term(TAG_INTENSITY,keyWords);
    }

   /* private Term setAutoEmergencyOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Helligkeit");
        keyWords.add("Intensitaet");
        keyWords.add("dunkler");
        return new Term(TAG_TEMPERATURE,Integer.toString(INT_STATUS_AUS),keyWords);
    }*/

    /*private Term seColorKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Farbe");
        return new Term(TAG_COLOR,keyWords);
    }*/

}
