package com.example.hal9000.smarthome.Views.OverView;


import android.content.Context;
import android.support.annotation.Nullable;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Helper.Config;

import java.util.ArrayList;


/**
 * The type Over view data manager.
 */
public class OverViewDataManager extends DataManager {


    /**
     * Instantiates a new Over view data manager.
     *
     * @param context the context
     */
    OverViewDataManager(Context context) {
        fillRoomList(context);
        fillDeviceList(context);
    }

    /**
     * Liste aller Räume oder Gerätetypen
     *
     * @param category Raum oder Typ-Liste
     * @return Liste list
     */
    @Nullable
    ArrayList<String> getList(String category) {
        switch (category) {
            case Config.STRING_INTENT_TYPE:
                return devices;
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
    public static String uebersetzer(String text) {
        switch (text) {
            case Config.STRING_TYPE_EN_HEATER:
                return Config.STRING_TYPE_GER_HEATER;
            case Config.STRING_TYPE_EN_CAMERA:
                return Config.STRING_TYPE_GER_CAMERA;
            case Config.STRING_TYPE_EN_DOOR:
                return Config.STRING_TYPE_GER_DOOR;
            case Config.STRING_TYPE_EN_DRYER:
                return Config.STRING_TYPE_GER_DRYER;
            case Config.STRING_TYPE_EN_LIGHT:
                return Config.STRING_TYPE_GER_LIGHT;
            case Config.STRING_TYPE_EN_OVEN:
                return Config.STRING_TYPE_GER_OVEN;
            case Config.STRING_TYPE_EN_PC:
                return Config.STRING_TYPE_GER_PC;
            case Config.STRING_TYPE_EN_SHUTTERS:
                return Config.STRING_TYPE_GER_SHUTTERS;
            case Config.STRING_TYPE_EN_SPEAKER:
                return Config.STRING_TYPE_GER_SPEAKER;
            case Config.STRING_TYPE_EN_STOVE:
                return Config.STRING_TYPE_GER_STOVE;
            case Config.STRING_TYPE_EN_TV:
                return Config.STRING_TYPE_GER_TV;
            case Config.STRING_TYPE_EN_WALL:
                return Config.STRING_TYPE_GER_WALL;
            case Config.STRING_TYPE_EN_WASHER:
                return Config.STRING_TYPE_GER_WASHER;
            case Config.STRING_TYPE_EN_WATER:
                return Config.STRING_TYPE_GER_WATER;
            case Config.STRING_TYPE_EN_WINDOW:
                return Config.STRING_TYPE_GER_WINDOW;
        }
        return text;
    }
}