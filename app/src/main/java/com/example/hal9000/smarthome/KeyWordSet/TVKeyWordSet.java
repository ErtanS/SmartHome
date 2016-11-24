package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_CHANNEL;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;
import static com.example.hal9000.smarthome.Helper.Config.TAG_VOLUME;

public class TVKeyWordSet extends KeyWordSet {

    private ArrayList<Term> volumeKeyWords = new ArrayList<>();
    private ArrayList<Term> channelKeyWords = new ArrayList<>();

    public TVKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        volumeKeyWords.add(setVolumeKeyWords());
        channelKeyWords.add(setChannelKeyWords());
    }

    public String determineParams(String statement){
        RequestParamSet resultState=getState(statement);
        if(resultState!=null){
            resultSet.add(resultState);
        }
        RequestParamSet resultChannel= getChannel(statement);
        if(resultChannel!=null){
            resultSet.add(resultChannel);
        }
        RequestParamSet resultVolume= getVolume(statement);
        if(resultVolume!=null){
            if(resultVolume.getColumnValue().equals("-1")){
                return "Die gewünschte Lautstärke liegt nicht in dem gültigen Bereich zwischen 0 und 100 Prozent.";
            }
            resultSet.add(resultVolume);
        }
        return null;
    }

    private RequestParamSet getVolume(String statement){
        for (Term term:volumeKeyWords) {
            if(term.containsKeyword(statement)){
                ArrayList<Integer> possibleVolumes=getNumberinString(statement);
                int volume=-1;
                for (int number:possibleVolumes) {
                    if(number>=0 && number<=100 ){
                        volume=number;
                    }
                }
                //// TODO: 18.11.2016 Entscheidung welche Zahl(näher hinter temperatur?)
                return new RequestParamSet(term.getType(),Integer.toString(volume));
            }
        }
        return null;
    }

    private RequestParamSet getChannel(String statement){
        for (Term term:channelKeyWords) {
            if(term.containsKeyword(statement)){
                term.determineValue(statement);
                return new RequestParamSet(term.getType(),term.getValue());
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

    private Term setVolumeKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Lautstärke");
        keyWords.add("Volume");
        return new Term(TAG_VOLUME,keyWords);
    }

    private Term setChannelKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Bild");
        keyWords.add("Fail");
        keyWords.add("Fussball");
        keyWords.add("Norway");
        keyWords.add("Simpsons");
        keyWords.add("Tagesschau");
        return new Term(TAG_CHANNEL,keyWords);
    }


}
