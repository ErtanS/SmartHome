package com.example.hal9000.smarthome.DataSet;

/**
 * Oberklasse für die DataSets
 * Beinhaltet die Attribute die in allen DataSets vorhanden sind
 */
@SuppressWarnings("JavaDoc")
public class DeviceDataSet {
    private final int id;
    private final String name;
    private int state;
    private final String scenarioRoom;
    private final int hour;
    private final int minute;
    private final String category;
    private final String type;

    /**
     * Konstruktor
     *
     * @param id
     * @param name
     * @param state
     * @param scenarioRoom
     * @param hour
     * @param minute
     * @param category
     * @param type
     */
    public DeviceDataSet(int id, String name, int state, String scenarioRoom, int hour, int minute, String category, String type) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.scenarioRoom = scenarioRoom;
        this.hour = hour;
        this.minute = minute;
        this.category = category;
        this.type = type;
    }

    /**
     * Konstruktor für Unterklasse
     *
     * @param values
     */
    DeviceDataSet(DeviceDataSet values) {
        this.id = values.getId();
        this.name = values.getName();
        this.state = values.getState();
        this.scenarioRoom = values.getScenarioRoom();
        this.hour = values.getHour();
        this.minute = values.getMinute();
        this.category = values.getCategory();
        this.type = values.getType();
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

    public String getScenarioRoom() {
        return scenarioRoom;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }
}
