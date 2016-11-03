package com.example.hal9000.smarthome.Helper;

public class Config {

    //Datei zum Speichern der IP
    public static final String FILENAME = "PreferencesFilename";
    public static final String VAL_KEY = "ValueKey";

    //Adressen der Skripte die genutzt werden
    public static String ip = "";
    public static String URL_GET_ALL = "http://" + ip + "/smart/selectAll.php";
    public static String URL_GET_MUSIC_PLAYLIST = ip + "/smart/selectMusicPlaylist.php";
    public static String URL_GET_SCENARIONAMES = "http://" + ip + "/smart/selectScenarioNames.php";
    public static String URL_GET_ROOMLIST = "http://" + ip + "/smart/getRooms.php";
    public static String URL_GET_DEVICESLIST = "http://" + ip + "/smart/getDevicesInHouse.php";


    public static String URL_INSERT_FIREBASEID = "http://" + ip + "/smart/insertFirebaseId.php";
    public static String URL_INSERT_CLOCK = "http://" + ip + "/smart/insertClock.php";
    public static String URL_INSERT_SCENARIO_ROW = "http://" + ip + "/smart/insertScenarioRow.php";
    public static String URL_INSERT_SCENARIO = "http://" + ip + "/smart/insertScenario.php";

    public static String URL_DELETE_SCENARIO_ROW = "http://" + ip + "/smart/deleteScenarioRow.php";
    public static String URL_DELETE_SCENARIO = "http://" + ip + "/smart/deleteScenario.php";
    public static String URL_DELETE_CLOCK = "http://" + ip + "/smart/deleteTimestamp.php";

    public static String URL_UPDATE = "http://" + ip + "/smart/update.php";
    public static String URL_UPDATE_SCENARIO = "http://" + ip + "/smart/updateScenariosInDevices.php";
    public static String URL_UPDATE_SCENARIO_NAME = "http://" + ip + "/smart/changeScenarioname.php";
    public static String URL_EMERGENCY = "http://" + ip + "/smart/emergency.php";

    public static String URL_IMAGE_FOLDER = "http://" + ip + "/smart/picture/";
    public static String URL_IMAGE_SECURITY = "http://" + ip + "/smart/img/";
    public static String UPLOAD_URL = "http://" + ip + "/smart/uploadImage.php";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ARRAY_LIGHT = "resultLight";
    public static final String TAG_JSON_ARRAY_WINDOW = "resultWindow";
    public static final String TAG_JSON_ARRAY_SPEAKER = "resultSpeaker";
    public static final String TAG_JSON_ARRAY_SHUTTERS = "resultShutters";
    public static final String TAG_JSON_ARRAY_HEATER = "resultHeater";
    public static final String TAG_JSON_ARRAY_DOOR = "resultDoor";
    public static final String TAG_JSON_ARRAY_CAMERA = "resultCamera";
    public static final String TAG_JSON_ARRAY_DRYER = "resultDryer";
    public static final String TAG_JSON_ARRAY_OVEN = "resultOven";
    public static final String TAG_JSON_ARRAY_PC = "resultPc";
    public static final String TAG_JSON_ARRAY_STOVE = "resultStove";
    public static final String TAG_JSON_ARRAY_TV = "resultTv";
    public static final String TAG_JSON_ARRAY_WALL = "resultWall";
    public static final String TAG_JSON_ARRAY_WASHER = "resultWasher";
    public static final String TAG_JSON_ARRAY_WATER = "resultWater";


    //JSON ID TAGS
    public static final int INT_JSON_ID_LIGHT = 2;
    public static final int INT_JSON_ID_DOOR = 3;
    public static final int INT_JSON_ID_WINDOW = 1;
    public static final int INT_JSON_ID_SHUTTERS = 0;
    public static final int INT_JSON_ID_SPEAKER = 5;
    public static final int INT_JSON_ID_HEATER = 4;
    public static final int INT_JSON_ID_CAMERA = 6;
    public static final int INT_JSON_ID_DRYER = 7;
    public static final int INT_JSON_ID_OVEN = 8;
    public static final int INT_JSON_ID_PC = 9;
    public static final int INT_JSON_ID_STOVE = 10;
    public static final int INT_JSON_ID_TV = 11;
    public static final int INT_JSON_ID_WALL = 12;
    public static final int INT_JSON_ID_WASHER = 13;
    public static final int INT_JSON_ID_WATER = 14;


    //Datenbank Spalten
    public static final String TAG_STATE = "state";
    public static final String TAG_NAME = "name";
    public static final String TAG_COLOR = "color";
    public static final String TAG_INTENSITY = "intensity";
    public static final String TAG_SCENARIOROOM = "scenarioRoom";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_SONGID = "songid";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_VOLUME = "volume";
    public static final String TAG_TEMPERATURE = "temperature";
    public static final String TAG_HOUR = "hour";
    public static final String TAG_MINUTE = "minute";
    public static final String TAG_ID = "id";
    public static final String TAG_STOP = "stop";
    public static final String TAG_PICTUREID = "pictureid";
    public static final String TAG_VIDEOID = "videoid";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_AUTOEMERGENCY = "autoEmergency";
    public static final String TAG_FREQUENCY = "frequency";
    public static final String TAG_EMERGENCY = "emergency";
    public static final String TAG_RPM = "rpm";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_CLOTHES = "clothes";
    public static final String TAG_CHANNEL = "channel";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_MUSIC = "music";


    //Kategorien
    public static final String CATEGORY_TIMESTAMP = "timestamp";
    public static final String CATEGORY_DEVICE = "device";
    public static final String CATEGORY_SCENARIO = "scenario";

    //PHP Tags
    public static final String TAG_SCENARIO = "scenario";
    public static final String TAG_SCENARIO_OLD = "oldscenario";
    public static final String TAG_TABLE = "table";

    public static final String STRING_ACTIVITY_TITLE = "title";
    public static final String STRING_TITLE_ROOM_VIEW = "Raumübersicht";
    public static final String STRING_TITLE_DEVICE_VIEW = "Geräteübersicht";
    public static final String STRING_TITLE_SCENARIO_VIEW = "Szenarienübersicht";

    //
    public static final String STRING_INTENT_ROOM = "room";
    public static final String STRING_INTENT_TYPE = "type";
    public static final String STRING_INTENT_CATEGORY = "category";
    public static final int INT_STATUS_EIN = 1;
    public static final int INT_STATUS_AUS = 0;
    public static final String STRING_EMPTY = "";
    public static final String STRING_SPACE = " ";
    public static final int INT_UNSET_ID = -1;
    public static final String STRING_TAG_SWITCH_IMAGE = "switch";


    public static final String STRING_TABLE = "table";
    public static final String STRING_WHERE_VALUE = "where_value";
    public static final String STRING_WHERE_ROW = "where_row";
    public static final String STRING_SET_COLUMN = "set_column";
    public static final String STRING_SET_VALUE = "set_value";

    //Typ Englisch
    public static final String STRING_TYPE_EN_LIGHT = "light";
    public static final String STRING_TYPE_EN_SPEAKER = "speaker";
    public static final String STRING_TYPE_EN_DOOR = "door";
    public static final String STRING_TYPE_EN_SHUTTERS = "shutters";
    public static final String STRING_TYPE_EN_WINDOW = "window";
    public static final String STRING_TYPE_EN_HEATER = "heater";
    public static final String STRING_TYPE_EN_CAMERA = "camera";
    public static final String STRING_TYPE_EN_DRYER = "dryer";
    public static final String STRING_TYPE_EN_OVEN = "oven";
    public static final String STRING_TYPE_EN_PC = "pc";
    public static final String STRING_TYPE_EN_STOVE = "stove";
    public static final String STRING_TYPE_EN_TV = "tv";
    public static final String STRING_TYPE_EN_WALL = "wall";
    public static final String STRING_TYPE_EN_WASHER = "washer";
    public static final String STRING_TYPE_EN_WATER = "water";


    //Typ Deutsch
    public static final String STRING_TYPE_GER_LIGHT = "Licht";
    public static final String STRING_TYPE_GER_SPEAKER = "Musik";
    public static final String STRING_TYPE_GER_DOOR = "Tür";
    public static final String STRING_TYPE_GER_SHUTTERS = "Rollladen";
    public static final String STRING_TYPE_GER_WINDOW = "Fenster";
    public static final String STRING_TYPE_GER_HEATER = "Heizung";
    public static final String STRING_TYPE_GER_CAMERA = "Überwachung";
    public static final String STRING_TYPE_GER_DRYER = "Trockner";
    public static final String STRING_TYPE_GER_OVEN = "Ofen";
    public static final String STRING_TYPE_GER_PC = "Computer";
    public static final String STRING_TYPE_GER_STOVE = "Herd";
    public static final String STRING_TYPE_GER_TV = "Fernseher";
    public static final String STRING_TYPE_GER_WALL = "Wand";
    public static final String STRING_TYPE_GER_WASHER = "Waschmachine";
    public static final String STRING_TYPE_GER_WATER = "Wasser";


    //Buttons
    public static final String STRING_TAG_POWER = "power";
    public static final String STRING_TAG_CLOCK = "clock";
    public static final String STRING_TAG_SETTINGS = "settings";
    public static final String STRING_TAG_SELECT = "select";

    //Fehler und Meldungen
    public static final String ERROR_SAME_NAME = "Name ist bereits vorhanden";
    public static final String ERROR_NO_NAME = "Bitte geben Sie einen Namen ein";
    public static final String ERROR_SAME_TIME = "Für dieses Gerät ist zu diesem Zeitpunkt bereits ein Event eingerichtet";
    public static final String ERROR = "Fehler";
    public static final String ERROR_NO_CONNECTION = "Fehler: Es konnte keine Verbindung zur Datenbank hergestellt werden. Bitte überprüfen Sie ihre Einstellungen";
    public static final String ERROR_INVALID_IP = "Sie müssen eine gültige IPv4-Adresse angeben (XXX.XXX.XXX.XXX).";
    public static final String ERROR_MUSIC = "Die Musikliste konnte nicht geladen werden.";
    public static final String BUTTON_OK = "OK";
    public static final String BUTTON_ABBRUCH = "Abbrechen";
    public static final String DELETE_SCENARIO = "Sind Sie sicher, dass Sie das Szenario löschen wollen?";
    public static final String DELETE_TIMESTAMP = "Sind Sie sicher, dass Sie diesen Zeitstempel löschen wollen?";
    public static final String DELETE_PICTURE = "Sind Sie sicher, dass Sie dieses Bild löschen wollen?";
    public static final String BUTTON_YES = "Ja";
    public static final String BUTTON_NO = "Nein";
    public static final String BUTTON_NOTFALL = "Notfall!";
    public static final String BUTTON_IGNORIEREN = "Ignorieren";
    public static final String NO_SCENARIO = "Kein Szenario";

    public static final String PNG_FILE_EXTENSION = ".png";

}

