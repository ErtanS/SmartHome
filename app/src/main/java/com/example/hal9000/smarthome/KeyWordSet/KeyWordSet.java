package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

public abstract class KeyWordSet {

    protected ArrayList<Term> stateKeyWords = new ArrayList<>();
    protected ArrayList<RequestParamSet> resultSet= new ArrayList<>();

    public ArrayList<RequestParamSet> getResultSet(){
        return resultSet;
    }

    protected RequestParamSet getState(String statement){
        for (Term term:stateKeyWords) {
            if(term.containsKeyword(statement)){
                return new RequestParamSet(term.getType(),term.getValue());
            }
        }
        return null;
    }

    protected ArrayList<Integer> getNumberinString(String string){
        String[] words=string.split(Config.STRING_SPACE);
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String word:words) {
            if(isParsable(word)){
                numbers.add(Integer.parseInt(word));
            }
        }
        return numbers;
    }

    private static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }
}
