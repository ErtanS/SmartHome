package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.*;


public class HeaterKeyWordSet extends KeyWordSet {

    private ArrayList<Term> temperatureKeyWords = new ArrayList<>();

    public HeaterKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        temperatureKeyWords.add(setTemperatureKeyWords());
    }

    public String determineParams(String statement){
        RequestParamSet resultState=getState(statement);
        if(resultState!=null){
            resultSet.add(resultState);
        }
        RequestParamSet resultTemperature=getTemperature(statement);
        if(resultTemperature!=null){
            if(resultTemperature.getColumnValue().equals("-1")){
                return "Die gewünschte Temperatur liegt nicht in dem gültigen Bereich zwischen 15 und 35 Grad.";
            }
            resultSet.add(resultTemperature);
        }
        return null;
    }

    private RequestParamSet getTemperature(String statement){
        for (Term term:temperatureKeyWords) {
            if(term.containsKeyword(statement)){
                ArrayList<Integer> possibleTemperatures=getNumberinString(statement);
                int temperature=-1;
                for (int number:possibleTemperatures) {
                    if(number>=15 && number<=35 ){
                        temperature=number;
                    }
                }
                //// TODO: 18.11.2016 Entscheidung welche Zahl(näher hinter temperatur?)
                return new RequestParamSet(term.getType(),Integer.toString(temperature));
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

    private Term setTemperatureKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("temperatur");
        return new Term(TAG_TEMPERATURE,keyWords);
    }

    /*private Term setAutoEmergencyOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("temperatur");
        keyWords.add("kälter");
        keyWords.add("kaelter");
        return new Term(TAG_TEMPERATURE,Integer.toString(INT_STATUS_AUS),keyWords);
    }*/

}
