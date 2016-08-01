package com.example.hal9000.smarthome.Views.TimestampView;

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

import com.example.hal9000.smarthome.Views.DeviceView.DynamicDeviceView;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.R;
import com.example.hal9000.smarthome.Views.ScenarioView.ScenarioView;


@SuppressWarnings("ConstantConditions")
public class DynamicTimeView extends AppCompatActivity {

    private String type;
    private String departure;
    private Inflater rowInflater;
    private boolean firstCreate = true;

    /**
     * Activity Start
     * Initialisierung der Elemente in der View und Unterscheidung von wo es gestartet wurde
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_time_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstCreate = true;
        LinearLayout mContainerView = (LinearLayout) findViewById(R.id.containerView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Bundle b = getIntent().getExtras();
        departure = b.getString(Config.STRING_INTENT_CATEGORY);
        String name = b.getString(Config.TAG_NAME);

        type = b.getString(Config.STRING_INTENT_TYPE);
        setTitle(b.getString(Config.STRING_ACTIVITY_TITLE));
        if (departure.equals(Config.CATEGORY_DEVICE)) {
            rowInflater = new TimestampDeviceRowInflater(inflater, mContainerView, this, name, getSupportFragmentManager());
            ((TimestampDeviceRowInflater) rowInflater).createRows();
        } else {
            rowInflater = new ScenarioTimestampRowInflater(inflater, mContainerView, this, name, getSupportFragmentManager());
            ((ScenarioTimestampRowInflater) rowInflater).createRows();
        }
    }

    /**
     * Hinzufügen der Actionbar Elemente
     *
     * @param menu Menü
     * @return Menü
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_time_menu, menu);
        return true;
    }

    /**
     * Ausführung beim Klick eines Elements in der Actionbar
     *
     * @param item geklicktes Element
     * @return Menüauswahl
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                if (departure.equals(Config.CATEGORY_SCENARIO)) {
                    ((ScenarioTimestampRowInflater) rowInflater).addTimestamp();
                }
                else {
                    ((TimestampDeviceRowInflater) rowInflater).addTimestamp(type);
                }
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent() {

        if (departure.equals(Config.CATEGORY_SCENARIO)) {
            return rowInflater.getParentActivityIntentImpl(this, ScenarioView.class);
        }
        else {
            return rowInflater.getParentActivityIntentImpl(this, DynamicDeviceView.class);
        }
    }

    @Override
    public Intent getParentActivityIntent() {
        if (departure.equals(Config.CATEGORY_SCENARIO)) {
            return rowInflater.getParentActivityIntentImpl(this, ScenarioView.class);
        }
        else {
            return rowInflater.getParentActivityIntentImpl(this, DynamicDeviceView.class);
        }
    }

    /**
     * Überprüfung von wo es gestartet wurde und Ausführung des entsprechenden Buttonchangers
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!firstCreate) {
            if (departure.equals(Config.CATEGORY_SCENARIO)) {
                ((ScenarioTimestampRowInflater) rowInflater).buttonChanger(Config.INT_UNSET_ID);
            }
            else {
                ((TimestampDeviceRowInflater) rowInflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
            }
        }
        firstCreate = false;
    }
}
