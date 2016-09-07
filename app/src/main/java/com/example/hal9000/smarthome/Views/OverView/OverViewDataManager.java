package com.example.hal9000.smarthome.Views.OverView;


import android.content.Context;
import android.support.annotation.Nullable;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;


class OverViewDataManager extends DataManager{



    public OverViewDataManager(Context context) {
        fillRoomList(context);
    }

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
                return rooms;
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
        wordsEn.addAll(rooms);
        wordsEn.addAll(getTypesEng());

        ArrayList<String> wordsGer = new ArrayList<>();
        wordsGer.addAll(rooms);
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