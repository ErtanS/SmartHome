package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_INTENSITY;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;
import static com.example.hal9000.smarthome.Helper.Config.TAG_TEMPERATURE;

public class WaterKeyWordSet extends KeyWordSet {

    private ArrayList<Term> intensityKeyWords = new ArrayList<>();
    private ArrayList<Term> temperatureKeyWords = new ArrayList<>();

    public WaterKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        intensityKeyWords.add(setIntensityKeyWords());
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
        RequestParamSet resultIntensity= getIntensity(statement);
        if(resultIntensity!=null){
            if(resultIntensity.getColumnValue().equals("-1")){
                return "Die gewünschte Intensität liegt nicht in dem gültigen Bereich zwischen 0 und 100 Prozent.";
            }
            resultSet.add(resultIntensity);
        }
        return null;
    }

    private RequestParamSet getTemperature(String statement){
        for (Term term:temperatureKeyWords) {
            if(term.containsKeyword(statement)){
                ArrayList<Integer> possibleTemperatures=getNumberinString(statement);
                int temperature=-1;
                for (int number:possibleTemperatures) {
                    if(number>=10 && number<=80 ){
                        temperature=number;
                    }
                }
                //// TODO: 18.11.2016 Entscheidung welche Zahl(näher hinter temperatur?)
                return new RequestParamSet(term.getType(),Integer.toString(temperature));
            }
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
        keyWords.add("auf");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_EIN),keyWords);
    }

    private Term setStateOffKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("aus");
        keyWords.add("zu");
        return new Term(TAG_STATE,Integer.toString(INT_STATUS_AUS),keyWords);
    }

    private Term setIntensityKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Staerke");
        keyWords.add("Intensitaet");
        return new Term(TAG_INTENSITY,keyWords);
    }

    private Term setTemperatureKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Temperatur");
        return new Term(TAG_TEMPERATURE,keyWords);
    }

}
