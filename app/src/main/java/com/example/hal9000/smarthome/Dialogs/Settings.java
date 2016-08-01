package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;

import static com.example.hal9000.smarthome.Helper.Config.*;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.createToast;

public class Settings extends DialogListener {

    private static final String regEx="^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
    /**
     * Creates a builder for an alert dialog that uses the default alert
     * dialog theme.
     * <p/>
     * The default alert dialog theme is defined by
     * {@link android.R.attr#alertDialogTheme} within the parent
     * {@code context}'s theme.
     *
     * @param context the parent context
     */
    public Settings(Context context,LayoutInflater layoutInflater) {
        super(context);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.door_settings, null);
        //this.category = category;
        EditText input = (EditText) layout.findViewById(R.id.editPassword);

        input.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_CLASS_TEXT);
        input.setText(ip);
        TextView title = (TextView) layout.findViewById(R.id.txtPassword);
        title.setText(R.string.string_IP);
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(input));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * Bestätigung der Eingabe
     *
     * @param input Textfeld zum Eintragen der IP
     * @return Ok-Button
     */
    private DialogInterface.OnClickListener setOkButton(final EditText input) {
        return new DialogInterface.OnClickListener() {
            /**
             * Mehtode die Ausgeführt wird wenn der Button betätigt wird
             * Ändern der IP-Adresse
             * @param dialog Dialog in dem Sich der Button befindet
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = input.getText().toString();
                setIP(result);

            }
        };
    }

    /**
     * Setzt die neue IP, falls diese dem gültigen Format entspricht
     * @param newIp neue IP-Adresse
     */
    private void setIP(String newIp){
        if(Pattern.matches(regEx,newIp)){
            setScripts(newIp);
        }
        else{
            createToast(getContext(), ERROR_INVALID_IP);
        }
    }

    /**
     * Aktualisiert den Pfad der PHP-Dateien
     * @param newIp neue IP-Adresse
     */
    public static void setScripts(String newIp){
        ip=newIp;
        URL_GET_ALL ="http://"+ ip + "/smart/selectAll.php";
        URL_GET_MUSIC_PLAYLIST ="http://"+ ip + "/smart/selectMusicPlaylist.php";
        URL_INSERT_CLOCK = "http://"+ip + "/smart/insertClock.php";
        URL_INSERT_SCENARIO_ROW ="http://"+ ip + "/smart/insertScenarioRow.php";
        URL_INSERT_SCENARIO = "http://"+ip + "/smart/insertScenario.php";
        URL_DELETE_SCENARIO_ROW ="http://"+ ip + "/smart/deleteScenarioRow.php";
        URL_DELETE_SCENARIO = "http://"+ip + "/smart/deleteScenario.php";
        URL_DELETE_CLOCK = "http://"+ip + "/smart/deleteTimestamp.php";
        URL_UPDATE ="http://"+ ip + "/smart/update.php";
        URL_UPDATE_SCENARIO = "http://"+ip + "/smart/updateScenariosInDevices.php";
        URL_UPDATE_SCENARIO_NAME ="http://"+ ip + "/smart/changeScenarioname.php";
        URL_INSERT_FIREBASEID= "http://"+ip + "/smart/insertFirebaseId.php";
        URL_IMAGE_FOLDER= "http://"+ip + "/smart/picture/";
        UPLOAD_URL ="http://"+ ip +"/smart/uploadImage.php";
        URL_IMAGE_SECURITY = "http://"+ip + "/smart/img/";
        URL_EMERGENCY= "http://"+ip + "/smart/emergency.php";
        URL_GET_SCENARIONAMES = "http://"+ip + "/smart/selectScenarioNames.php";


    }
    public static String deviceRegistationFirebase(){
        //Aktuelle Firebaseid in die Datenbank eintragen um später die Berechtigung zu haben Nachrichten zu senden
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        RequestHandler rh = new RequestHandler();
        return rh.insertFirebaseId(refreshedToken);
    }
}
