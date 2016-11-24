package com.example.hal9000.smarthome.Views.ScenarioView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.hal9000.smarthome.Abstract.ViewActivity;
import com.example.hal9000.smarthome.Views.HomeScreen.HomeScreen;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;


/**
 * The type Scenario view.
 */
@SuppressWarnings("ConstantConditions")
public class ScenarioView extends ViewActivity {

    private ScenarioViewInflater rowInflater;
    private boolean firstCreate = true;

    /**
     * Activity Start
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstCreate = true;
        setTitle(Config.STRING_TITLE_SCENARIO_VIEW);
        LinearLayout mContainerView = (LinearLayout) findViewById(R.id.parentView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowInflater = new ScenarioViewInflater(inflater, mContainerView, this, getSupportFragmentManager());
        rowInflater.createRows();
    }

    /**
     * Hinzufügen der Elemente die in der ActionBar sind
     *
     * @param menu Menü
     * @return Menüauswahl
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scenario_menu, menu);
        return true;
    }

    /**
     * Auswahlt eines Elements aus der ActionBar
     * Dialog öffnen falls ein neues Szenario hinzugefügt werdene soll
     *
     * @param item gewähltes Menüelement
     * @return Auswahl
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                rowInflater.buttonChanger(Config.INT_UNSET_ID);
                return true;
            case R.id.menuAdd:
                rowInflater.showDialog(Config.STRING_EMPTY);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuSpeak:
                startSpeechRecognition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, HomeScreen.class);
    }

    @Override
    public Intent getParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, HomeScreen.class);
    }

    /**
     * Buttonchanger ausführen nachdem die Activity wieder in den Vordergrund kommt (nicht beim Activity Start)
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!firstCreate) {
            rowInflater.buttonChanger(Config.INT_UNSET_ID);
        }
        firstCreate = false;
    }
}
