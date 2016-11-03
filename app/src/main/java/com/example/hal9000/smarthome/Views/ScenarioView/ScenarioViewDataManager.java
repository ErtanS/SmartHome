package com.example.hal9000.smarthome.Views.ScenarioView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Helper.Config;


class ScenarioViewDataManager extends DataManager {
    private final Context ctx;

    public ScenarioViewDataManager(Context ctx) {
        this.ctx = ctx;
        manageScenarios();
    }

    /**
     * Aktualisieren der Szenarien mit den Werten aus der Datenbank
     * Fehler abfangen und bearbeiten
     */
    public void manageScenarios() {
        fillDataSet(Config.TAG_SCENARIO, Config.STRING_EMPTY, Config.STRING_EMPTY, Config.CATEGORY_SCENARIO, ctx);
    }


}
