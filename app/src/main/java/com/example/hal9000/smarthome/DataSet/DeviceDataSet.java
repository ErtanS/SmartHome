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
     * @param id           the id
     * @param name         the name
     * @param state        the state
     * @param scenarioRoom the scenario room
     * @param hour         the hour
     * @param minute       the minute
     * @param category     the category
     * @param type         the type
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
     * @param values the values
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

    /**
     * Gets scenario room.
     *
     * @return the scenario room
     */
    public String getScenarioRoom() {
        return scenarioRoom;
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

    private String getCategory() {
        return category;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
