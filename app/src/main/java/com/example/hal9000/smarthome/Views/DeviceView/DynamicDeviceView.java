package com.example.hal9000.smarthome.Views.DeviceView;

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

import com.example.hal9000.smarthome.Views.OverView.DynamicOverView;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;


@SuppressWarnings("ConstantConditions")
public class DynamicDeviceView extends AppCompatActivity {

    private DeviceViewInflater rowInflater;
    private boolean firstCreate = true;

    /**
     * Initalisierung der Elemente in der View
     * Inflater initialisieren und zum Inflaten anstoßen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_device_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstCreate = true;
        LinearLayout parentView = (LinearLayout) findViewById(R.id.parentView);
        Bundle b = getIntent().getExtras();
        String room = b.getString(Config.STRING_INTENT_ROOM);
        String type = b.getString(Config.STRING_INTENT_TYPE);
        setTitle(b.getString(Config.STRING_ACTIVITY_TITLE));
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int positionToDelete=1;
        if (room!=null){
            positionToDelete=0;
        }
        rowInflater = new DeviceViewInflater(parentView,inflater,this,type,room,positionToDelete,getSupportFragmentManager());
    }

    /**
     * Wird ausgeführt wenn die View wieder ausgeführt wird, nachdem es im Hintergrund war
     * Ruft den Buttonchanger auf
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Ausführung  beim klick der Elemente in der Actionbar
     * Wechel der View einleiten oder Aktualisierung der zurzeit angezeigten Werte
     * @param item Auswahl im Menü
     * @return gewählte Menü
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                rowInflater.buttonChanger(Config.INT_UNSET_ID,Config.STRING_EMPTY);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, DynamicOverView.class);
    }

    @Override
    public Intent getParentActivityIntent() {
        return rowInflater.getParentActivityIntentImpl(this, DynamicOverView.class);
    }


}
