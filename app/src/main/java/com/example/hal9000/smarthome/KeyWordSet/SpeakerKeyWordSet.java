package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_SONGID;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;
import static com.example.hal9000.smarthome.Helper.Config.TAG_VOLUME;

public class SpeakerKeyWordSet extends KeyWordSet {


    private ArrayList<Term> volumeKeyWords = new ArrayList<>();
    private ArrayList<Term> songIDKeyWords = new ArrayList<>();

    public SpeakerKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        volumeKeyWords.add(setVolumeKeyWords());
        songIDKeyWords.add(setSongIDKeyWords());
    }

    public String determineParams(String statement){
        RequestParamSet resultState=getState(statement);
        if(resultState!=null){
            resultSet.add(resultState);
        }
        RequestParamSet resultSongID=getSongID(statement);
        if(resultSongID!=null){
            resultSet.add(resultSongID);
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

    private RequestParamSet getSongID(String statement){
        for (Term term:songIDKeyWords) {
            if(term.containsKeyword(statement)){
                int songID=term.determineValue(statement);
                if(songID>4){
                    songID-=5;
                }
                return new RequestParamSet(term.getType(),Integer.toString(songID));
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

    private Term setSongIDKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        //keyWords.add("Lied");
        //keyWords.add("Song");
        keyWords.add("Blur");
        keyWords.add("Hosen");
        keyWords.add("Nirvana");
        keyWords.add("Killers");
        keyWords.add("U2");
        keyWords.add("Song 2");
        keyWords.add("Tage wie diese");
        keyWords.add("Teen Spirit");
        keyWords.add("Brightside");
        keyWords.add("Walk on");
        return new Term(TAG_SONGID,keyWords);
    }


}
