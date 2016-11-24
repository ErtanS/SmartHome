package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.TAG_COLOR;

public class WallKeyWordSet extends KeyWordSet {


    private ArrayList<Term> colorKeyWords = new ArrayList<>();

    public WallKeyWordSet() {

    }

//    public String getState(String statement){
//        for (Term term:stateKeyWords) {
//            if(term.containsKeyword(statement)){
//                return term.getType();
//            }
//        }
//        return null;
//    }

    private Term seColorKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Farbe");
        return new Term(TAG_COLOR,keyWords);
    }

}
