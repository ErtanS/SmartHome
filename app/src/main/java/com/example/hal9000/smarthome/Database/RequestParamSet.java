package com.example.hal9000.smarthome.Database;


public class RequestParamSet {

    private String table;
    private String columnName;
    private String columnValue;
    private int id;

    public RequestParamSet(String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public RequestParamSet() {
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
