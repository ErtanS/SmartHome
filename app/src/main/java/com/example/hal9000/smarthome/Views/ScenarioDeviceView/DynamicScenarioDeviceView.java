package com.example.hal9000.smarthome.Views.ScenarioDeviceView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;
import com.example.hal9000.smarthome.Views.ScenarioView.ScenarioView;

@SuppressWarnings("ConstantConditions")
public class DynamicScenarioDeviceView extends AppCompatActivity {

    private ScenarioDeviceRowInflater rowInflater;
    private boolean firstCreate = true;

    /**
     * Activity Start
     * Initalisierung des Inflates und anstoßen des Inflatens
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_scenario_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstCreate = true;
        LinearLayout mContainerView = (LinearLayout) findViewById(R.id.parentView);
        Bundle b = getIntent().getExtras();
        String scenarioName = b.getString(Config.TAG_NAME);
        setTitle(b.getString(Config.STRING_ACTIVITY_TITLE));
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowInflater = new ScenarioDeviceRowInflater(inflater, mContainerView, this, scenarioName, getSupportFragmentManager());
        rowInflater.createRows();
    }


    /**
     * Hinzufügen der Elemente die in der ActionBar sind
     * @param menu Menü
     * @return Menüauswahl
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Auswahlt eines Elements aus der ActionBar
     * @param item Auswahl des Menüs
     * @return Menüauswahl
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Buttonchanger ausführen nachdem die Activity wieder in den Vordergrund kommt (nicht beim Activity Start)
     */
    @Override
    public void onResume() {
        super.onResume();
        if(!firstCreate) {
            rowInflater.buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
        }
        firstCreate = false;

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, ScenarioView.class);
    }

    @Override
    public Intent getParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, ScenarioView.class);
    }

}
