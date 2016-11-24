package com.example.hal9000.smarthome.KeyWordSet;


import com.example.hal9000.smarthome.SpeechControl.Term;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.*;

public class DeviceTypeKeyWordSet {

    private ArrayList<Term> deviceTypes=new ArrayList<>();


    public DeviceTypeKeyWordSet(){
        deviceTypes.add(setCameraKeyWords());
        deviceTypes.add(setDoorKeyWords());
        deviceTypes.add(setDryerKeyWords());
        deviceTypes.add(setHeaterKeyWords());
        deviceTypes.add(setLightKeyWords());
        deviceTypes.add(setOvenKeyWords());
        deviceTypes.add(setPCKeyWords());
        deviceTypes.add(setShuttersKeyWords());
        deviceTypes.add(setSpeakerKeyWords());
        deviceTypes.add(setStoveKeyWords());
        deviceTypes.add(setTVKeyWords());
        deviceTypes.add(setWallKeyWords());
        deviceTypes.add(setWasherKeyWords());
        deviceTypes.add(setWaterKeyWords());
        deviceTypes.add(setWindowKeyWords());
        //stateKeyWords.add(setStateKeyWords());

    }

    public ArrayList<String> getDeviceTypes(ArrayList<String> spokenWords){
        ArrayList<String> result=new ArrayList<String>();
        for (String word:spokenWords) {
            for (Term term:deviceTypes) {
                if(term.isKeyword(word)){
                    if(!result.contains(term.getType())) {
                        result.add(term.getType());
                    }
                    break;
                }
            }
        }
        return result;
    }

    private Term setCameraKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Kamera");
        keyWords.add("Überwachung");
        keyWords.add("Sicherheitskamera");
        keyWords.add("Überwachungskamera");
        keyWords.add("Kameras");
        keyWords.add("Sicherheitskameras");
        keyWords.add("Überwachungskameras");
        keyWords.add("überwachen");
        keyWords.add("camera");
        return new Term(STRING_TYPE_EN_CAMERA,keyWords);
    }

    private Term setDoorKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Tür");
        keyWords.add("Tuer");
        keyWords.add("Eingang");
        keyWords.add("Pforte");
        keyWords.add("Türe");
        keyWords.add("Tuere");
        keyWords.add("Türen");
        keyWords.add("Tueren");
        keyWords.add("Tor");
        keyWords.add("Door");
        return new Term(STRING_TYPE_EN_DOOR,keyWords);
    }

    private Term setDryerKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Trockner");
        keyWords.add("trocknen");
        keyWords.add("Dryer");
        return new Term(STRING_TYPE_EN_DRYER,keyWords);
    }

    private Term setHeaterKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Heizung");
        keyWords.add("Heizanlage");
        keyWords.add("Heizgerät");
        keyWords.add("Heater");
        keyWords.add("Heizungen");
        keyWords.add("Heizanlagen");
        keyWords.add("Heizgeräte");
        return new Term(STRING_TYPE_EN_HEATER,keyWords);
    }
    private Term setLightKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Licht");
        keyWords.add("Lampe");
        keyWords.add("Lichter");
        keyWords.add("Lampen");
        keyWords.add("Light");
        keyWords.add("Leuchte");
        keyWords.add("Beleuchtung");
        keyWords.add("Leuchten");
        keyWords.add("beleuchten");
        return new Term(STRING_TYPE_EN_LIGHT,keyWords);
    }
    private Term setOvenKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Ofen");
        keyWords.add("Backofen");
        keyWords.add("Oven");
        keyWords.add("Backöfen");
        keyWords.add("Öfen");
        return new Term(STRING_TYPE_EN_OVEN,keyWords);
    }
    private Term setPCKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("PC");
        keyWords.add("Computer");
        keyWords.add("Laptop");
        keyWords.add("Rechner");
        keyWords.add("Laptops");
        keyWords.add("Desktop");
        return new Term(STRING_TYPE_EN_PC,keyWords);
    }
    private Term setShuttersKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Rollladen");
        keyWords.add("Rolladen");
        keyWords.add("Rollläden");
        keyWords.add("Rolläden");
        keyWords.add("Rollo");
        keyWords.add("Rouleau");
        keyWords.add("Jalousie");
        keyWords.add("Jalousien");
        keyWords.add("Rollos");
        keyWords.add("Shutters");
        return new Term(STRING_TYPE_EN_SHUTTERS,keyWords);
    }
    private Term setSpeakerKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Lautsprecher");
        keyWords.add("Musik");
        keyWords.add("Anlage");
        keyWords.add("Speaker");
        keyWords.add("Music");
        return new Term(STRING_TYPE_EN_SPEAKER,keyWords);
    }
    private Term setStoveKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Herd");
        keyWords.add("Herdplatte");
        keyWords.add("Herde");
        keyWords.add("Stove");
        keyWords.add("Platte");
        return new Term(STRING_TYPE_EN_STOVE,keyWords);
    }
    private Term setTVKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("TV");
        keyWords.add("Fernseher");
        keyWords.add("Bildschirm");
        keyWords.add("SmartTV");
        keyWords.add("Fernsehgerät");
        keyWords.add("fernsehen");
        return new Term(STRING_TYPE_EN_TV,keyWords);
    }
    private Term setWallKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Wand");
        keyWords.add("Leinwand");
        keyWords.add("MagicWall");
        keyWords.add("Wall");
        keyWords.add("Zimmerwand");
        keyWords.add("Raumwand");
        keyWords.add("Wände");
        keyWords.add("Leinwände");
        return new Term(STRING_TYPE_EN_WALL,keyWords);
    }
    private Term setWasherKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Washer");
        keyWords.add("Waschmaschine");
        keyWords.add("Maschine");
        keyWords.add("Waschmaschinen");
        keyWords.add("Maschinen");
        return new Term(STRING_TYPE_EN_WASHER,keyWords);
    }
    private Term setWaterKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Water");
        keyWords.add("Wasser");
        keyWords.add("Badewanne");
        keyWords.add("Spüle");
        keyWords.add("Becken");
        keyWords.add("Waschbecken");
        keyWords.add("Wasserhahn");
        keyWords.add("Wasserhähne");
        keyWords.add("Spülbecken");
        keyWords.add("Dusche");
        keyWords.add("duschen");
        keyWords.add("baden");
        keyWords.add("Wanne");
        keyWords.add("Whirlpool");
        return new Term(STRING_TYPE_EN_WATER,keyWords);
    }

    private Term setWindowKeyWords(){
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add("Window");
        keyWords.add("Fenster");
        keyWords.add("Scheibe");
        keyWords.add("Fensterscheibe");
        keyWords.add("Glasfront");
        return new Term(STRING_TYPE_EN_WINDOW,keyWords);
    }
}
