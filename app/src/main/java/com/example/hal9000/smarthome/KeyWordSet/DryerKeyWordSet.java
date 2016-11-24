package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.Database.RequestParamSet;
import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_AUS;
import static com.example.hal9000.smarthome.Helper.Config.INT_STATUS_EIN;
import static com.example.hal9000.smarthome.Helper.Config.TAG_AMOUNT;
import static com.example.hal9000.smarthome.Helper.Config.TAG_CLOTHES;
import static com.example.hal9000.smarthome.Helper.Config.TAG_DURATION;
import static com.example.hal9000.smarthome.Helper.Config.TAG_RPM;
import static com.example.hal9000.smarthome.Helper.Config.TAG_STATE;
import static com.example.hal9000.smarthome.Helper.Config.TAG_TEMPERATURE;

public class DryerKeyWordSet extends KeyWordSet {
    
    private ArrayList<Term> amountKeyWords = new ArrayList<>();
    private ArrayList<Term> clothesKeyWords = new ArrayList<>();
    private int amount=-1;
    private int clothes=-1;

    public DryerKeyWordSet() {
        stateKeyWords.add(setStateOnKeyWords());
        stateKeyWords.add(setStateOffKeyWords());
        amountKeyWords.add(setAmountKeyWords());
        clothesKeyWords.add(setClothesKeyWords());
    }

    public String determineParams(String statement){
        RequestParamSet resultState=getState(statement);
        if(resultState!=null){
            resultSet.add(resultState);
        }
        RequestParamSet resultAmount=getAmount(statement);
        RequestParamSet resultClothes=getClothes(statement);
        if(amount!=-1 && clothes!=-1){
            resultSet.add(resultAmount);
            resultSet.add(resultClothes);
            resultSet.addAll(determineWashCycle(amount,clothes));
        }
        else if (amount == -1 && clothes != -1 || amount != -1){
            return "Sie müssen sowohl die Art der Wäsche als auch die Menge einstellen.";
        }
        return null;
    }

    private RequestParamSet getAmount(String statement){
        for (Term term:amountKeyWords) {
            if(term.containsKeyword(statement)){
                amount=term.determineValue(statement);
                return new RequestParamSet(term.getType(),term.getValue());
            }
        }
        return null;
    }

    private RequestParamSet getClothes(String statement){
        for (Term term:clothesKeyWords) {
            if(term.containsKeyword(statement)){
                clothes=term.determineValue(statement);
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

    private Term setAmountKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("wenig");
        keyWords.add("normal");
        keyWords.add("viel");
        return new Term(TAG_AMOUNT,keyWords);
    }

    private Term setClothesKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("kochwaesche");
        keyWords.add("buntwaesche");
        keyWords.add("pflegeleicht");
        keyWords.add("feinwaesche");
        keyWords.add("baumwolle");
        return new Term(TAG_CLOTHES,keyWords);
    }

    private ArrayList<RequestParamSet> determineWashCycle(int amount,int clothes){
        int rpm=0;
        int temperature=0;
        int duration=0;
        switch (clothes) {
            case 0:
            case 1:
                rpm = 1000;
                temperature = 100;
                switch (amount) {
                    case 0:
                        duration = 45;
                        break;
                    case 1:
                        duration = 72;
                        break;
                    case 2:
                        duration = 90;
                        break;
                }
                break;
            case 2:
                rpm = 900;
                temperature = 90;
                switch (amount) {
                    case 0:
                        duration = 35;
                        break;
                    case 1:
                        duration = 42;
                        break;
                    case 2:
                        duration = 45;
                        break;
                }
                break;
            case 3:
                rpm = 800;
                temperature = 60;
                switch (amount) {
                    case 0:
                        duration = 30;
                        break;
                    case 1:
                        duration = 35;
                        break;
                    case 2:
                        duration = 40;
                        break;
                }
                break;
            case 4:
                rpm = 1000;
                temperature = 80;
                switch (amount) {
                    case 0:
                        duration = 35;
                        break;
                    case 1:
                        duration = 40;
                        break;
                    case 2:
                        duration = 45;
                        break;
                }
                break;
        }
        ArrayList<RequestParamSet> result= new ArrayList<>();
        result.add(new RequestParamSet(TAG_TEMPERATURE,Integer.toString(temperature)));
        result.add(new RequestParamSet(TAG_DURATION,Integer.toString(duration)));
        result.add(new RequestParamSet(TAG_RPM,Integer.toString(rpm)));
        return result;

    }
}
