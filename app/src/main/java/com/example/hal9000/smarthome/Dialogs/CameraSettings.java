package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;

import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.DataSet.CameraDataSet;
import com.example.hal9000.smarthome.DataSet.ScenarioDataSet;
import com.example.hal9000.smarthome.Database.RequestHandler;

import static com.example.hal9000.smarthome.Helper.Config.*;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;

public class CameraSettings extends DialogListener {
    private ArrayList<ScenarioDataSet> scenarioNames;

    /**
     * Konstruktor
     *
     * @param context        Kontext
     * @param layoutInflater Inflater
     */
    public CameraSettings(Context context, CameraDataSet dataSet, LayoutInflater layoutInflater, Inflater inflater) {
        super(context, inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.camera_settings, null);
        addScenarioNameList();
        NumberPicker input = (NumberPicker) layout.findViewById(R.id.np_Frequency);
        NumberPicker inputSzenario = (NumberPicker) layout.findViewById(R.id.np_Emergency);
        CheckBox inputAutoEmergency = (CheckBox) layout.findViewById(R.id.cb_autoEmergency);
        boolean autoEmergencyChecked = false;

        if (dataSet.getAutoEmergency() == 1) {
            autoEmergencyChecked = true;
        }

        inputAutoEmergency.setChecked(autoEmergencyChecked);

        int listSize = scenarioNames.size();
        String[] names;
        if (listSize > 0) {
            names = new String[listSize];
            int i = 0;
            for (ScenarioDataSet name : scenarioNames) {
                names[i++] = name.getName();
            }
        } else {
            names = new String[]{NO_SCENARIO};
        }

        inputSzenario.setDisplayedValues(names);
        inputSzenario.setMinValue(0);
        inputSzenario.setMaxValue(names.length - 1);

        if (dataSet.getEmergency() != -1) {
            int index = getIndex(dataSet.getEmergency(), names);
            inputSzenario.setValue(index);
        } else {
            inputSzenario.setValue(0);
        }

        input.setMinValue(0);
        input.setMaxValue(2);
        input.setDisplayedValues(new String[]{"Selten", "Normal", "Oft"});
        input.setValue(Math.abs(dataSet.getFrequency() - 2));
        input.setWrapSelectorWheel(false);
        setView(layout);
        setPositiveButton(BUTTON_OK, setOkButton(input, inputSzenario, inputAutoEmergency, dataSet.getId(), names));
        setNegativeButton(BUTTON_ABBRUCH, setCancelButton());
    }

    /**
     * OnClicklistener hinzufügen
     *
     * @param input          Numberpicker aus dem Dialog
     * @param id             Id
     * @param inputEmergency Noftfallszenario
     * @param checkbox       Checkbox für automatisches Aktivieren des Notfallszenarios
     * @return OnClickListener
     */
    private DialogInterface.OnClickListener setOkButton(final NumberPicker input, final NumberPicker inputEmergency, final CheckBox checkbox, final int id, final String[] list) {
        //noinspection JavaDoc
        return new DialogInterface.OnClickListener() {
            /**
             * Aktualisierung der Werte beim Klick des Buttons
             * @param dialog Dialog in dem sich die Elemente befinden
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int value = Math.abs(input.getValue() - 2);
                String result = Integer.toString(value);
                int scenario = inputEmergency.getValue();
                int checked = 0;
                if (checkbox.isChecked()) {
                    checked = 1;
                }
                int emergencyId = getId(scenario, list);

                RequestHandler rh = new RequestHandler();
                String msgSingle = rh.updateSingleValue(STRING_TYPE_EN_CAMERA, TAG_FREQUENCY, result, id);
                String msgEmergency = rh.updateSingleValue(STRING_TYPE_EN_CAMERA, TAG_EMERGENCY, Integer.toString(emergencyId), id);
                String msgCheckbox = rh.updateSingleValue(STRING_TYPE_EN_CAMERA, TAG_AUTOEMERGENCY, Integer.toString(checked), id);

                catchError(getContext(), msgSingle);
                catchError(getContext(), msgEmergency);
                catchError(getContext(), msgCheckbox);
            }
        };
    }

    /**
     * Fügt Liste der möglichen Notfallzzenarien hinzu
     */
    private void addScenarioNameList() {
        RequestHandler rh = new RequestHandler();
        String resultScenario = rh.getScenarioNames();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultScenario);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            scenarioNames = new ArrayList<>();

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                int id = jo.getInt(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME).trim();
                scenarioNames.add(new ScenarioDataSet(id, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(getContext());
        }
    }

    /**
     * @param index Index des Szenarios in der Liste
     * @param list  Liste mit Namen
     * @return Id des Szenarios
     */
    private int getId(int index, String[] list) {
        String name = list[index];

        for (ScenarioDataSet tempName : scenarioNames) {
            if (tempName.getName().equals(name)) {
                return tempName.getId();
            }
        }
        return -1;
    }

    /**
     * @param id   id des Szenarios
     * @param list Liste mit Namen
     * @return index des Szenarios
     */
    private int getIndex(int id, String[] list) {
        String resultName = "";
        for (ScenarioDataSet tempName : scenarioNames) {
            if (tempName.getId() == id) {
                resultName = tempName.getName();
            }
        }
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(resultName)) {
                return i;
            }
        }
        return -1;
    }
}
