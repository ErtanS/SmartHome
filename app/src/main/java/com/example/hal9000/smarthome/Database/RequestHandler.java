package com.example.hal9000.smarthome.Database;


import android.os.AsyncTask;

import com.example.hal9000.smarthome.Helper.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    private final HashMap<String, String> phpVars = new HashMap<>();
    private String url;

    /**
     * Methode zum Senden von httpPostRequest
     *
     * @param requestURL     URL zur Phpdatei
     * @param postDataParams Parameter hashmap
     * @return Rückgabe der Phpdatei
     */
    private String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        StringBuilder sb = new StringBuilder();

        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Methode zum Senden von httpGetRequest
     *
     * @param requestURL URL zur Phpdatei
     * @return Rückgabe der Phpdatei
     */
    private String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (Exception ignored) {
        }
        return sb.toString();
    }

    /**
     * Verarbeitung der Übergabeparameter
     *
     * @param params Übergabeparameter
     * @return rückgabe der Phpdatei
     * @throws UnsupportedEncodingException Fehlerhandling
     */
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * Asynchrone Ausführung der oben aufgeführten sendGetRequest Methode
     *
     * @return Rückgabe der PhpDatei oder eine Fehlermeldung
     */
    private String sendGetRequest() {
        SelectAsyncGet s = new SelectAsyncGet();
        try {
            return s.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Asynchrone Ausführung der oben aufgeführten sendPostRequest Methode
     *
     * @return Rückgabe der PhpDatei oder eine Fehlermeldung
     */
    private String sendPostRequest() {
        SelectAsyncPost s = new SelectAsyncPost();
        try {
            return s.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Hinzufügen zusätzlicher Php Paramter
     *
     * @param key   Php Identifikations String
     * @param value Wert
     */
    private void addPhpVar(String key, String value) {
        phpVars.put(key, value);
    }


    /**
     * Aufruf der PHP-Datei Allgemeine Update Methode
     * Kann dafür genutzt werden einen bestimmten Wert zu bearbeiten
     *
     * @param table       Tabelle
     * @param columnName  Spaltenname
     * @param columnValue Wert
     * @param id          id
     * @return php Rückgabe
     */
    public String updateSingleValue(String table, String columnName, String columnValue, int id) {
        this.url = Config.URL_UPDATE;
        addPhpVar(Config.TAG_TABLE, table);
        addPhpVar(Config.STRING_SET_COLUMN, columnName);
        addPhpVar(Config.STRING_SET_VALUE, columnValue);
        addPhpVar(Config.STRING_WHERE_ROW, Config.TAG_ID);
        addPhpVar(Config.STRING_WHERE_VALUE, Integer.toString(id));
        return sendPostRequest();
    }

    /**
     * Liefert das geforderte DataSet
     *
     * @param table        Tabelle
     * @param name         Name
     * @param scenarioRoom Raum/Scenarioname
     * @param category     Kategorie
     * @return php Rückgabe
     */
    public String getData(String table, String name, String scenarioRoom, String category) {
        this.url = Config.URL_GET_ALL;
        addPhpVar(Config.TAG_TABLE, table);
        addPhpVar(Config.TAG_NAME, name);
        addPhpVar(Config.TAG_SCENARIOROOM, scenarioRoom);
        addPhpVar(Config.TAG_CATEGORY, category);
        return sendPostRequest();
    }

    /**
     * Laden der Gesamten Musikliste aus der Datenbank
     *
     * @return Musikliste
     */
    public String getPlayList(String table) {
        this.url = Config.URL_GET_MUSIC_PLAYLIST;
        addPhpVar(Config.TAG_TABLE, table);

        return sendPostRequest();
    }

    /**
     * Laden aller Szenariennamen aus der Datenbank
     *
     * @return Szenarionamen Liste
     */
    public String getScenarioNames() {
        this.url = Config.URL_GET_SCENARIONAMES;
        return sendGetRequest();
    }

    /**
     * Laden aller Raumnamen aus der Datenbank
     *
     * @return Szenarionamen Liste
     */
    public String getRoomNames() {
        this.url = Config.URL_GET_ROOMLIST;
        return sendGetRequest();
    }

    /**
     * Laden aller Gerätetypen in einem Haus aus der Datenbank
     *
     * @return Szenarionamen Liste
     */
    public String getDevicesInHouse() {
        this.url = Config.URL_GET_DEVICESLIST;
        return sendGetRequest();
    }


    /**
     * Gerät aus einem Szenario entfernen
     *
     * @param name     Gerätename
     * @param scenario Scenarioname
     * @param table    zugehörige Tabelle
     * @return Erfolgsmeldung oder Fehler
     */
    public String deleteDeviceFromScenario(String name, String scenario, String table) {
        this.url = Config.URL_DELETE_SCENARIO_ROW;
        return editDevicesInScneario(name, scenario, table);
    }

    /**
     * Neues Gerät zu einem Szenario hinzufügen
     *
     * @param name     Gerätename
     * @param scenario Scenarioname
     * @param table    zugehörige Tabelle
     * @return Erfolgsmeldung oder Fehler
     */
    public String insertDeviceInScenario(String name, String scenario, String table) {
        this.url = Config.URL_INSERT_SCENARIO_ROW;
        return editDevicesInScneario(name, scenario, table);
    }

    /**
     * Verwaltung der Übergabeparameter für delete-/insertDeviceInScenario
     *
     * @param name     Gerätename
     * @param scenario Scenarioname
     * @param table    Tabelle
     */
    private String editDevicesInScneario(String name, String scenario, String table) {
        addPhpVar(Config.TAG_NAME, name);
        addPhpVar(Config.TAG_SCENARIO, scenario);
        addPhpVar(Config.TAG_TABLE, table);
        return sendPostRequest();
    }

    /**
     * Änderung des Sznearionames
     *
     * @param scenario    Neuer Szenarioname
     * @param oldScenario Alter Szenarioname
     * @return Erfolgsmeldung oder Fehler
     */
    public String changeScenarioName(String scenario, String oldScenario) {
        this.url = Config.URL_UPDATE_SCENARIO_NAME;
        addPhpVar(Config.TAG_SCENARIO, scenario);
        addPhpVar(Config.TAG_SCENARIO_OLD, oldScenario);
        return sendPostRequest();
    }

    /**
     * Szenariostatus aktualisieren
     *
     * @param scenario Scenarioname
     * @param state    neuer Status
     * @return Erfolgsmeldung oder Fehler
     */
    public String updateScenarioState(String scenario, int state) {
        this.url = Config.URL_UPDATE_SCENARIO;
        addPhpVar(Config.TAG_SCENARIO, scenario);
        addPhpVar(Config.TAG_STATE, Integer.toString(state));
        return sendPostRequest();
    }

    /**
     * Szenariostatus aktualisieren
     *
     * @return Erfolgsmeldung oder Fehler
     */
    public String triggerEmergencyScenario(String camName) {
        this.url = Config.URL_EMERGENCY;
        addPhpVar(Config.TAG_NAME, camName);
        return sendPostRequest();
    }


    /**
     * Anlegen eines Neuen Szenarios
     *
     * @param scenario Szenarioname
     * @return Erfolgsmeldung oder Fehler
     */
    public String insertScenario(String scenario) {
        this.url = Config.URL_INSERT_SCENARIO;
        addPhpVar(Config.TAG_NAME, scenario);
        return sendPostRequest();
    }

    /**
     * Löschen eines Szenarios
     *
     * @param scenario Szenarioname
     * @return erfolgsmeldung oder Fehler
     */
    public String deleteScenario(String scenario) {
        this.url = Config.URL_DELETE_SCENARIO;
        addPhpVar(Config.TAG_NAME, scenario);
        return sendPostRequest();
    }

    /**
     * Löschen eines Timestamps
     *
     * @param id    Id des zu löschenden Timestamps
     * @param table Gerätetyp bzw. Tabelle
     * @return Erfolgsmeldung oder Fehler
     */
    public String deleteTimestamp(int id, String table) {
        this.url = Config.URL_DELETE_CLOCK;
        addPhpVar(Config.TAG_ID, Integer.toString(id));
        addPhpVar(Config.STRING_TABLE, table);
        return sendPostRequest();
    }

    /**
     * Hinzufügen eines neuen Timestamps
     *
     * @param name  Gerätename
     * @param table Gerätetyp bzw. Tabelle
     * @return Id des eingefügten Timestamps oder Fehlermeldung
     */
    public String insertTimestamp(String name, String table) {
        this.url = Config.URL_INSERT_CLOCK;
        addPhpVar(Config.TAG_NAME, name);
        addPhpVar(Config.TAG_TABLE, table);
        return sendPostRequest();
    }

    /**
     * Hinzufügen der Firebase id in die Datenbank um später die Berechtigung zu haben, Pushnachrichten auf das Gerät zu senden
     *
     * @return Id des eingefügten Timestamps oder Fehlermeldung
     */
    public String insertFirebaseId(String id) {
        this.url = Config.URL_INSERT_FIREBASEID;
        addPhpVar(Config.TAG_ID, id);
        return sendPostRequest();
    }

    /**
     * Asynchroner PostRequest Task
     */
    private class SelectAsyncPost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return sendPostRequest(url, phpVars);
        }
    }

    /**
     * Asynchroner GetRequest Task
     */
    private class SelectAsyncGet extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return sendGetRequest(url);
        }
    }
}
