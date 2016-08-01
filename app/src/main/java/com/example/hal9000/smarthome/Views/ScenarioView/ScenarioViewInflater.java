package com.example.hal9000.smarthome.Views.ScenarioView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Views.ScenarioDeviceView.DynamicScenarioDeviceView;
import com.example.hal9000.smarthome.Views.TimestampView.DynamicTimeView;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.Config.*;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

@SuppressWarnings("unchecked")
public class ScenarioViewInflater extends Inflater {

    private final ScenarioViewDataManager manager;
    private final RequestHandler rh;

    /**
     * Konstruktor
     * @param inflater   Inflater
     * @param parentView ParenView
     * @param context    Kontext
     */
    public ScenarioViewInflater(LayoutInflater inflater, LinearLayout parentView, Context context, android.support.v4.app.FragmentManager fragmentManager) {
        super(R.layout.dynamic_scenario_row, parentView, inflater, context, CATEGORY_SCENARIO, fragmentManager);
        rh = new RequestHandler();
        manager = new ScenarioViewDataManager(context);
    }

    /**
     * Alle Szenarien durchlaufen und Inflaten
     */
    public void createRows() {
        LinearLayout parentView = getParentView();
        ArrayList<ScenarioDataSet> scenarios = manager.getDataSet();
        for (ScenarioDataSet scenario : scenarios) {
            parentView.addView(inflateScenarioRow(scenario));
        }
    }

    /**
     * Erstellen der Zeile für ein Gerät das inflatet werden soll
     *
     * @param scenario Datensatz des zu inflatenden Szenarios
     * @return Zeile die Inflatet werden soll
     */
    private View inflateScenarioRow(ScenarioDataSet scenario) {
        String name = scenario.getName();
        int state = scenario.getState();
        View rowView = getInflater().inflate(getRowID(), null);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtRow);
        txtName.setText(name);
        txtName.setOnClickListener(clickName(txtName.getText().toString()));

        ImageView imEdit = (ImageView) rowView.findViewById(R.id.imRowSettings);
        imEdit.setOnClickListener(clickEdit(txtName.getText().toString()));

        ImageView imClock = (ImageView) rowView.findViewById(R.id.imRowClock);
        imClock.setOnClickListener(clickClock(txtName.getText().toString()));

        ImageView imPower = (ImageView) rowView.findViewById(R.id.imRowPower);
        imPower.setTag(scenario);
        imPower.setOnClickListener(clickPowerScenario());

        ImageView imDelete = (ImageView) rowView.findViewById(R.id.imRowDelete);

        imDelete.setOnClickListener(clickDelete(txtName.getText().toString()));

        switchImage(STRING_TAG_SWITCH_IMAGE, state, imPower);
        return rowView;
    }

    /**
     * Klick des DeleteButtons
     * Löschen des gesamten Szenarios aus der Datenbank
     * Szenario aus der View entfernen
     *
     * @param name Name des geklickten Szenarios
     * @return OnClickListener
     */
    private View.OnClickListener clickDelete(final String name) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogListener builder = new DialogListener(getContext());
                builder.setMessage(DELETE_SCENARIO);
                builder.setPositiveButton(BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = rh.deleteScenario(name);
                        if (!catchError(getContext(), msg)) {
                            getParentView().removeView((View) v.getParent().getParent());
                        }
                        buttonChanger(INT_UNSET_ID);
                    }
                });
                builder.setNegativeButton(BUTTON_NO, builder.setCancelButton());
                builder.show();
            }
        };
    }

    /**
     * Klick des Einstellungs Buttons
     * Öffnen der Geräteübersicht
     *
     * @param scenarioName Name des geklickten Szenarios
     * @return OnClickListener
     */
    private View.OnClickListener clickEdit(final String scenarioName) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen;
                nextScreen = new Intent(getContext(), DynamicScenarioDeviceView.class);
                Bundle b = new Bundle();
                b.putString(TAG_NAME, scenarioName);
                b.putString(STRING_ACTIVITY_TITLE, scenarioName);
                nextScreen.putExtras(b);
                getContext().startActivity(nextScreen);
            }
        };
    }

    /**
     * Klick des Uhr Buttons
     * auf Timestamp Activity wechseln
     *
     * @param name Name des geklickten Szenarios
     * @return OnClickListener
     */
    private View.OnClickListener clickClock(final String name) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getContext(), DynamicTimeView.class);
                Bundle b = new Bundle();
                b.putString(TAG_NAME, name);
                b.putString(STRING_ACTIVITY_TITLE, name);
                b.putString(STRING_INTENT_TYPE, STRING_EMPTY);
                b.putString(STRING_INTENT_CATEGORY, getCategory());
                nextScreen.putExtras(b);
                getContext().startActivity(nextScreen);
            }
        };
    }

    /**
     * Klick auf den Szenarionamen
     * Öffnet Dialog zum ändern des Namens
     *
     * @param scenarioName Name des Szenarios
     * @return Dialog zum Ändern des Namens
     */
    private View.OnClickListener clickName(final String scenarioName) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(scenarioName);

            }
        };
    }

    /**
     * Erstellen eines Dialogs für ein Szenario
     *
     * @param scenarioName Szenarioname
     */
    public void showDialog(final String scenarioName) {
        DialogListener builder = new DialogListener(getContext());

        // Set up the input
        @SuppressLint("InflateParams") View layout = getInflater().inflate(R.layout.door_settings, null);
        final EditText input = (EditText) layout.findViewById(R.id.editPassword);
        TextView title = (TextView) layout.findViewById(R.id.txtPassword);
        title.setText(R.string.string_Name);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(scenarioName);
        builder.setView(layout);


        // Set up the buttons
        builder.setPositiveButton(BUTTON_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newScenarioName = input.getText().toString();
                boolean error;
                if (!newScenarioName.equals(scenarioName)) {
                    ArrayList<ScenarioDataSet> scenarioNames = manager.getDataSet();
                    boolean containsName = false;
                    for (ScenarioDataSet tempName : scenarioNames) {
                        if (tempName.getName().equalsIgnoreCase(newScenarioName)) {
                            containsName = true;
                            break;
                        }
                    }
                    if (newScenarioName.trim().length() == 0  || containsName) {
                        Toast toast;
                        if (containsName) {
                            toast = Toast.makeText(getParentView().getContext(), ERROR_SAME_NAME, Toast.LENGTH_LONG);
                        }
                        else {
                            toast = Toast.makeText(getParentView().getContext(), ERROR_NO_NAME, Toast.LENGTH_LONG);
                        }
                        toast.show();
                    }
                    else {
                        if (scenarioName.equals(STRING_EMPTY)) {
                            String msg = rh.insertScenario(newScenarioName);
                            error=catchError(getContext(), msg);
                            if (!error) {
                                manager.manageScenarios();
                                int id = Integer.parseInt(msg);
                                getParentView().addView(inflateScenarioRow(manager.updateScenario(id)));
                            }
                        }
                        else {
                            String msg = rh.changeScenarioName(newScenarioName, scenarioName);
                            error=catchError(getContext(), msg);

                        }
                        if(!error) {
                            input.setText(newScenarioName);
                        }
                    }
                }
            }
        });
        builder.setNegativeButton(BUTTON_ABBRUCH, builder.setCancelButton());
        builder.show();
    }

    /**
     * Klick Power Button eines Szenarios
     * Aktivieren des Szenarios in der Datenbank
     * Wechsel des angezeigten Bildes im Powerbutton
     *
     * @return OnClickListener
     */
    private View.OnClickListener clickPowerScenario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    ScenarioDataSet scenarioDataSet = (ScenarioDataSet) v.getTag();
                    int state = scenarioDataSet.getState();
                    String scenarioName = scenarioDataSet.getName();
                    int id=scenarioDataSet.getId();

                    state++;
                    //scenario off
                    if (state > INT_STATUS_EIN) {
                        state = INT_STATUS_AUS;
                    }
                    String msg = rh.updateScenarioState(scenarioName, state);
                    if (!catchError(getContext(), msg)) {
                        scenarioDataSet.setState(state);
                        v.setTag(scenarioDataSet);
                        switchImage(STRING_TAG_SWITCH_IMAGE, state, (ImageView) v);
                    }
                    buttonChanger(id);
                }
            }
        };

    }

    /**
     * Durchläuft alle Elemente im Relativlayout und aktualisiert die Werte(z.B. Bild, DataSet)
     * mit den im Tag bzw. in der Datenbank stehenden Informationen
     * Das DataSet wird hierbei mit aktuellen Werten aus der Datenbank gefüllt
     *
     * @param clickedID gecklicktes Szenario das nicht verändert werden soll
     */
    public void buttonChanger(int clickedID) {
        manager.manageScenarios();
        for (int i = 0; i < getParentView().getChildCount(); i++) {
            View v = getParentView().getChildAt(i);
            if (v instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) v;
                for (int k = 0; k < rl.getChildCount(); k++) {
                    View viewK = rl.getChildAt(k);
                    if (viewK instanceof RelativeLayout) {
                        RelativeLayout rl2 = (RelativeLayout) viewK;
                        for (int j = 0; j < rl2.getChildCount(); j++) {
                            View view = rl2.getChildAt(j);
                            if (view instanceof ImageView && view.getTag() != null) {
                                ImageView powerSwitch = (ImageView) view;
                                ScenarioDataSet dataSet = (ScenarioDataSet) powerSwitch.getTag();
                                int id = dataSet.getId();

                                ScenarioDataSet newDataSet = manager.updateScenario(id);
                                if (newDataSet != null) {
                                    powerSwitch.setTag(newDataSet);

                                    if (id!=clickedID) {
                                        int state = newDataSet.getState();
                                        switchImage(STRING_TAG_SWITCH_IMAGE, state, powerSwitch);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
