package com.example.hal9000.smarthome.DataSet;


/**
 * The type Scenario data set.
 */
public class ScenarioDataSet {
    private final int id;
    private final String name;
    private int state;
    private int hour;
    private int minute;


    /**
     * Instantiates a new Scenario data set.
     *
     * @param id   the id
     * @param name the name
     */
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
    ScenarioDataSet(int id, String name, int state, int hour, int minute) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets minute.
     *
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(int state) {
        this.state = state;
    }
}
