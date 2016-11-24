package com.example.hal9000.smarthome.Views.ScenarioView;

import android.content.Context;

import com.example.hal9000.smarthome.Abstract.DataManager;
import com.example.hal9000.smarthome.Helper.Config;


/**
 * The type Scenario view data manager.
 */
class ScenarioViewDataManager extends DataManager {
    private final Context ctx;

    /**
     * Instantiates a new Scenario view data manager.
     *
     * @param ctx the ctx
     */
    ScenarioViewDataManager(Context ctx) {
        this.ctx = ctx;
        manageScenarios();
    }

    /**
     * Aktualisieren der Szenarien mit den Werten aus der Datenbank
     * Fehler abfangen und bearbeiten
     */
    void manageScenarios() {
        fillDataSet(Config.TAG_SCENARIO, Config.STRING_EMPTY, Config.STRING_EMPTY, Config.CATEGORY_SCENARIO, ctx);
    }


}
