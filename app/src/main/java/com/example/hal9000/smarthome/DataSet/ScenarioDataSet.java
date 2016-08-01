package com.example.hal9000.smarthome.DataSet;


public class ScenarioDataSet {
    private final int id;
    private final String name;
    private int state;
    private int hour;
    private int minute;


    public ScenarioDataSet(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Konstruktor
     *
     * @param id     Id
     * @param name   Name des Scenarios
     * @param state  Status
     * @param hour   Zeitschaltung Stunde
     * @param minute Zeitschaltung Minute
     */
    public ScenarioDataSet(int id, String name, int state, int hour, int minute) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
