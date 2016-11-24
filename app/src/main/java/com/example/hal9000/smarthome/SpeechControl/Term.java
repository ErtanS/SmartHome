package com.example.hal9000.smarthome.SpeechControl;


import java.util.ArrayList;

public class Term {

    private String type;
    private String value;
    private ArrayList<String> keyWords;

    public Term(String type,ArrayList<String> wordList) {
        this.keyWords = wordList;
        this.type = type;
    }

    public Term(String type,String value,ArrayList<String> wordList) {
        this(type,wordList);
        this.value=value;
    }

    public Boolean isKeyword(String word){
        for (String keyWord:keyWords) {
            if(word.equalsIgnoreCase(keyWord)){
                return true;
            }
        }
        return false;
    }

    public Boolean containsKeyword(String statement){
        for (String keyWord:keyWords) {
            if(statement.toLowerCase().contains(keyWord.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public int determineValue(String statement){
        int index=-1;
        for (String keyWord:keyWords) {
            if(statement.toLowerCase().contains(keyWord.toLowerCase())){
                index=keyWords.indexOf(keyWord);
                value=Integer.toString(index);
                break;
            }
        }
        return index;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }
}
