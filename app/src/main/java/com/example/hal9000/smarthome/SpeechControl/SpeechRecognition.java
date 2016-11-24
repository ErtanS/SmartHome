package com.example.hal9000.smarthome.SpeechControl;


import android.content.Context;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.ErrorHandler;

import java.util.ArrayList;

public class SpeechRecognition {

    private ArrayList<Statement> statements = new ArrayList<>();
    private SpeechDataManager speechDataManager;
    private Context context;


    public SpeechRecognition(ArrayList<String> speechResults,Context context){
        this.context=context;
        speechDataManager = new SpeechDataManager(context);
        seperateStatements(speechResults);
    }

    private void seperateStatements(ArrayList<String> speechResults){
        statements=new ArrayList<>();
        for (String result: speechResults) {
            statements.add(new Statement(result));
        }
    }

    public void fillStatementLists(){
        ArrayList<Statement> statementsCopy = new ArrayList<>();
        for (Statement statement:statements) {
            statement.generateWordCombinations();
            statement.findDeviceTypes();
            statement.findRoomsInSpokenWords(speechDataManager.getRoomList());
            statement.findDevicesInSpokenWords(speechDataManager.getDataSet());
            if(statement.isQualifiedStatement() && !statement.isInStatementList(statementsCopy)) {
                statement.printText();
                statement.calculatePriority();
                statementsCopy.add(statement);
            }
        }
        statements=statementsCopy;
    }



    public void performBestStatement(){
        if(statements!=null && statements.size()>0) {
            Statement bestStatement = findFirstStatementWithPriority(Config.STATEMENT_PRIORITY_HIGH);
            if (bestStatement == null) {
                bestStatement = findFirstStatementWithPriority(Config.STATEMENT_PRIORITY_NORMAL);
                if (bestStatement == null) {
                    bestStatement = findFirstStatementWithPriority(Config.STATEMENT_PRIORITY_LOW);
                }
            }
            if (bestStatement != null) {
                bestStatement.createRequestParameters(context);
            }
        }
        else{
            ErrorHandler.createToast(context,"Keinen gültigen Befehl erkannt!");
        }
    }

    private Statement findFirstStatementWithPriority(int priority){
        for (Statement statement:statements) {
            if(statement.getPriority()==priority){
                return statement;
            }
        }
        return null;
    }

//    public void sortWordsIntoCategories(){
//        deviceTypes = findDeviceTypes();
//        roomList = getRoomList();
//        devices = getDevices();
//        otherWords=getOtherWords();
//        printText();
//        if(devices!=null && devices.size()>0){
//            if(devices.size()==1){
//
//            }
//        }
//        if(deviceTypes!=null){
//            for (String device: deviceTypes) {
//                text += device+", ";
//            }
//            ErrorHandler.createToast(this,text);
//            if(!deviceTypes.isEmpty()){
//                openDeviceView(deviceTypes.get(0));
//            }
//        }
//    }


    public ArrayList<Statement> getSpokenWords(){
        return statements;
    }

}
