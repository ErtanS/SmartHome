package com.example.hal9000.smarthome.Views.OverView;


import android.support.annotation.Nullable;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Helper.Config;
import java.util.ArrayList;


class OverViewDataManager extends DataManager{

    /**
     * Liste aller Räume oder Gerätetypen
     *
     * @param category Raum oder Typ-Liste
     * @return Liste
     */
    @Nullable
    public ArrayList<String> getList(String category) {
        switch (category) {
            case Config.STRING_INTENT_TYPE:
                return getTypesGer();
            case Config.STRING_INTENT_ROOM:
                return getRoomsGer();
            default:
                return null;
        }
    }



    /**
     * Übersetzt Wörter von Deutsch nach Englisch und umgekehrt
     *
     * @param text Zu übersetzendes Wort
     * @return übersetzter Text
     */
    public String uebersetzer(String text) {
        ArrayList<String> wordsEn = new ArrayList<>();
        wordsEn.addAll(getRoomsEng());
        wordsEn.addAll(getTypesEng());

        ArrayList<String> wordsGer = new ArrayList<>();
        wordsGer.addAll(getRoomsGer());
        wordsGer.addAll(getTypesGer());

        Object[] search;
        Object[] find;

        search = wordsGer.toArray();
        find = wordsEn.toArray();


        for (int i = 0; i < search.length; i++) {
            if (text.equals(search[i])) {
                return (String) find[i];
            }
        }
        return text;
    }
}