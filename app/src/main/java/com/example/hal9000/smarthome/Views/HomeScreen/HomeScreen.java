package com.example.hal9000.smarthome.Views.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hal9000.smarthome.Abstract.ViewActivity;
import com.example.hal9000.smarthome.Dialogs.DialogActivity;
import com.example.hal9000.smarthome.Dialogs.Settings;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Views.OverView.DynamicOverView;
import com.example.hal9000.smarthome.R;
import com.example.hal9000.smarthome.Views.ScenarioView.ScenarioView;

import static com.example.hal9000.smarthome.Helper.Config.*;

/**
 * The type Home screen.
 */
public class HomeScreen extends ViewActivity {
    private LayoutInflater inflater;
    /**
     * Wird ausgeführt beim start der Activity
     * Initialisierung der Imageviews
     * Setzen der Tags
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = getSharedPreferences(FILENAME, 0);
        String ip=sharedPrefs.getString(VAL_KEY, STRING_EMPTY);
        Settings.setScripts(ip);
        Settings.deviceRegistationFirebase();

        setContentView(R.layout.activity_homescreen);

        TextView textViewRoom = (TextView) findViewById(R.id.txt_menu_room);
        TextView textViewSzenario = (TextView) findViewById(R.id.txt_menu_scenario);
        TextView textViewDevice = (TextView) findViewById(R.id.txt_menu_device);
        TextView textViewSettings = (TextView) findViewById(R.id.txt_menu_settings);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (textViewRoom != null) {
            textViewRoom.setOnClickListener(onClickListener(STRING_INTENT_ROOM, STRING_TITLE_ROOM_VIEW));
        }
        if (textViewSzenario != null) {
            textViewSzenario.setOnClickListener(onClickListener(STRING_INTENT_TYPE, TAG_SCENARIO));
        }
        if (textViewDevice != null) {
            textViewDevice.setOnClickListener(onClickListener(STRING_INTENT_TYPE, STRING_TITLE_DEVICE_VIEW));
        }
        if (textViewSettings != null){
            textViewSettings.setOnClickListener(onClickListener(STRING_INTENT_TYPE, STRING_TAG_SETTINGS));
        }
        Bundle b = getIntent().getExtras();
        if(b != null && b.getString(Config.TAG_NAME) != null ){
            DialogActivity dialog = new DialogActivity();
            dialog.setArguments(null,null,inflater);
            dialog.setPicture(b.getString(Config.TAG_NAME));
            dialog.setCamName(b.getString(Config.STRING_TYPE_EN_CAMERA));
            dialog.show(getSupportFragmentManager(),"missiles");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    /**
     * Ausführung  beim klick der Elemente in der Actionbar
     * Wechel der View einleiten oder Aktualisierung der zurzeit angezeigten Werte
     *
     * @param item Auswahl im Menü
     * @return gewählte Menü
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuSpeak:
                startSpeechRecognition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Entscheidung welcher Button geklickt wurde
     *
     * @param category Unterscheidung der Buttons
     * @param label Titel der nächsten View
     * @return OnclickListener
     */
    private View.OnClickListener onClickListener(final String category, final String label){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen=null;
                switch (label) {
                    case TAG_SCENARIO:
                        nextScreen = new Intent(HomeScreen.this, ScenarioView.class);
                        break;
                    case STRING_TAG_SETTINGS:
                        Settings settings = new Settings(HomeScreen.this, inflater);
                        settings.show();
                        break;
                    default:
                        nextScreen = new Intent(HomeScreen.this, DynamicOverView.class);
                        break;
                }
                if (nextScreen!=null) {
                    Bundle b = new Bundle();
                    b.putString(STRING_INTENT_CATEGORY, category);
                    b.putString(STRING_ACTIVITY_TITLE, label);
                    nextScreen.putExtras(b);
                    startActivity(nextScreen);
                }
            }
        };
    }

    /**
     * Speichern der IP in Datei
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPrefs = getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(VAL_KEY, ip);
        editor.apply();

    }
}
