package com.example.hal9000.smarthome.Views.TimestampView;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.Dialogs.TimeSettings;
import com.example.hal9000.smarthome.Helper.Tag;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Dialogs.TimeSettings.formatTimeString;
import static com.example.hal9000.smarthome.Helper.Config.*;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;

@SuppressWarnings({"unchecked", "JavaDoc"})
public class ScenarioTimestampRowInflater extends Inflater {
    private final ScenarioTimestampManager manager;
    private final RequestHandler rh;

    /**
     * @param inflater
     * @param parentView
     * @param context
     * @param scenarioName
     */
    public ScenarioTimestampRowInflater(LayoutInflater inflater, LinearLayout parentView, Context context, String scenarioName,android.support.v4.app.FragmentManager fragmentManager) {
        super(R.layout.dynamic_time_row, parentView, inflater, scenarioName,context,fragmentManager);
        manager = new ScenarioTimestampManager(scenarioName, getContext());
        rh = new RequestHandler();
    }

    /**
     * Alle Timestamps eines Szenarios durchlaufen und Inflaten
     */
    public void createRows() {
        ArrayList<ScenarioDataSet> times = manager.getDataSet();
        for (ScenarioDataSet item : times) {
            getParentView().addView(inflateScenarioTimestampRow(item));
        }
    }

    /**
     * Erstellen der Zeile für ein Szenario das inflatet werden soll
     *
     * @param scenario Datensatz des zu inflatenden Szenarios
     * @return Zeile die Inflatet werden soll
     */
    private View inflateScenarioTimestampRow(ScenarioDataSet scenario) {
        View rowView = getInflater().inflate(getRowID(), null);

        int hour = scenario.getHour();
        int minute = scenario.getMinute();
        int state = scenario.getState();
        int id = scenario.getId();

        TextView txtName = (TextView) rowView.findViewById(R.id.txtRow);
        ImageView imPower = (ImageView) rowView.findViewById(R.id.imRowPower);
        ImageView imDelete = (ImageView) rowView.findViewById(R.id.imRowDelete);
        ImageView imSettings = (ImageView) rowView.findViewById(R.id.imRowSettings);

        imPower.setTag(new Tag(STRING_TAG_POWER, scenario));
        imPower.setOnClickListener(clickPower());

        imDelete.setOnClickListener(clickDelete(id));

        txtName.setText(formatTimeString(hour, minute));
        txtName.setTag(new Tag(STRING_TAG_CLOCK, scenario));
        txtName.setOnClickListener(clickTime());

        switchImage(STRING_TAG_SWITCH_IMAGE, state, imPower);
        deleteSettingsButton(imSettings, STRING_TYPE_EN_WINDOW);

        return rowView;
    }

    /**
     * Klick Power Button
     * Status des Timestamps, somit kann definiert werden ob zu einer Bestimmten Zeit das Szenario aktiviert oder deaktiviert werden soll
     * Wechseln des Status in der Datenbank
     * Abfangen von Datenbankfehlern
     *
     * @return OnClickListener
     */
    private View.OnClickListener clickPower() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    Tag buttonTag = (Tag) v.getTag();
                    ScenarioDataSet dataSet = buttonTag.getScenarioDataSet();
                    int state = dataSet.getState();
                    int id = dataSet.getId();

                    state++;
                    if (state > INT_STATUS_EIN) {
                        state = INT_STATUS_AUS;
                    }

                    String msgState = rh.updateSingleValue(TAG_SCENARIO, TAG_STATE, Integer.toString(state), id);
                    if (!catchError(getContext(), msgState)) {
                        switchImage(STRING_TAG_SWITCH_IMAGE, state, (ImageView) v);
                        dataSet.setState(state);
                        buttonTag.setScenarioDataSet(dataSet);
                        v.setTag(buttonTag);
                    }
                    buttonChanger(id);
                }

            }
        };
    }

    /**
     * Klick Löschen Button
     * Löschen des Timestamps aus der momentanen View, aus der Datenbank und aus der akutellen DataSet Liste.
     *
     * @return OnClikcListener
     */
    private View.OnClickListener clickDelete(final int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogListener builder = new DialogListener(getContext());
                builder.setMessage(DELETE_TIMESTAMP);
                builder.setPositiveButton(BUTTON_YES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = rh.deleteTimestamp(id, TAG_SCENARIO);
                        if (!catchError(getContext(), msg)) {
                            getParentView().removeView((View) v.getParent());
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
     * Klick auf Uhrzeit
     * Öffen des Dialogs zum ändern der Zeit
     *
     * @return OnClickListener
     */
    private View.OnClickListener clickTime() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(v);
            }
        };
    }

    /**
     * Öffnen des Dialogs zum ändern der Zeit
     *
     * @param v View
     */
    private void onClickListener(View v) {
        Tag tag = (Tag) v.getTag();
        ScenarioDataSet dataSet = tag.getScenarioDataSet();
        TimeSettings editTime = new TimeSettings(getContext(), dataSet, getInflater(), (TextView) v, manager, this);
        editTime.setOnDismissListener(this);
        editTime.show();
    }

    /**
     * Hinzufügen eines neuen Timestamps zum Szenario
     */
    public void addTimestamp() {
        String insertReply = rh.insertTimestamp(getScenarioName(), TAG_SCENARIO);
        if (!catchError(getContext(), insertReply)) {
            int nextId = Integer.parseInt(insertReply);
            manager.manageTimestampsOfScenario(getScenarioName());
            ScenarioDataSet newRow = manager.updateScenario(nextId);
            if(newRow!=null) {
                getParentView().addView(inflateScenarioTimestampRow(newRow));
            }
        }
        //buttonChanger(Config.INT_UNSET_ID);
    }

    /**
     * Durchläuft alle Elemente im Relativlayout und aktualisiert die Werte(z.B. Bild, DataSet)
     * mit den im Tag bzw. in der Datenbank stehenden Informationen
     * Das DataSet wird hierbei mit aktuellen Werten aus der Datenbank gefüllt
     *
     * @param clickedId Geklickte id die nicht verändert werden soll
     */
    public void buttonChanger(int clickedId) {
        manager.manageTimestampsOfScenario(getScenarioName());
        ArrayList<View> buttons = findViewWithTagRecursively(getParentView());
        for (View button:buttons) {
            Tag buttonTag = (Tag) button.getTag();
            ScenarioDataSet dataSet = buttonTag.getScenarioDataSet();
            int id = dataSet.getId();
            ScenarioDataSet newDataSet = manager.updateScenario(id);
            if (newDataSet != null) {
                buttonTag.setScenarioDataSet(newDataSet);
                button.setTag(buttonTag);
                if (id != clickedId && buttonTag.getType().equals(STRING_TAG_POWER)) {
                    int state = newDataSet.getState();
                    switchImage(STRING_TAG_SWITCH_IMAGE, state, (ImageView) button);
                }
            }
        }
        /*for (int i = 0; i < getParentView().getChildCount(); i++) {
            View v = getParentView().getChildAt(i);
            if (v instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) v;
                for (int j = 0; j < rl.getChildCount(); j++) {
                    View view = rl.getChildAt(j);
                    if (view.getTag() != null) {

                        Tag buttonTag = (Tag) view.getTag();
                        ScenarioDataSet dataSet = buttonTag.getScenarioDataSet();
                        int id = dataSet.getId();

                        ScenarioDataSet newDataSet = manager.updateScenario(id);
                        if (newDataSet != null) {
                            buttonTag.setScenarioDataSet(newDataSet);
                            view.setTag(buttonTag);

                            if ((id != clickedId) && buttonTag.getType().equals(STRING_TAG_POWER)) {
                                int state = newDataSet.getState();
                                switchImage(STRING_TAG_SWITCH_IMAGE, state, (ImageView) view);
                            }
                        }
                    }
                }
            }
        }*/
    }
}
