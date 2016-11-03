package com.example.hal9000.smarthome.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Views.DeviceView.DeviceViewInflater;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Views.ScenarioDeviceView.ScenarioDeviceRowInflater;
import com.example.hal9000.smarthome.Views.ScenarioView.ScenarioViewInflater;
import com.example.hal9000.smarthome.Views.TimestampView.ScenarioTimestampRowInflater;
import com.example.hal9000.smarthome.Views.TimestampView.TimestampDeviceRowInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;

public class DialogListener extends AlertDialog.Builder {
    private Inflater inflater;
    private boolean error = false;

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
    DialogListener(Context context, Inflater inflater) {
        super(context);
        this.inflater = inflater;
    }

    public DialogListener(Context context) {
        super(context);
    }


    /**
     * onDismissListener hinzufügen
     *
     * @param inflater inflater
     */
    public void setOnDismissListener(Inflater inflater) {
        setOnDismissListener(setDismissListener(inflater));
    }

    public boolean isError() {
        return error;
    }

    void setError() {
        this.error = true;
    }

    /**
     * fügt je nach View einen Dismisslistener zum Dialog hinzu
     *
     * @param inflater inflater
     * @return Dismisslistener
     */
    private DialogInterface.OnDismissListener setDismissListener(final Inflater inflater) {
        return new DialogInterface.OnDismissListener() {
            /**
             * Führt den jeweiligen Buttonchanger des oben übergebenen Inflaters aus
             * @param dialog Dialog
             */
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (inflater instanceof DeviceViewInflater) {
                    ((DeviceViewInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
                }
                if (inflater instanceof ScenarioViewInflater) {
                    ((ScenarioViewInflater) inflater).buttonChanger(Config.INT_UNSET_ID);
                }
                if (inflater instanceof TimestampDeviceRowInflater) {
                    ((TimestampDeviceRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
                }
                if (inflater instanceof ScenarioTimestampRowInflater) {
                    ((ScenarioTimestampRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID);
                }
                if (inflater instanceof ScenarioDeviceRowInflater) {
                    ((ScenarioDeviceRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
                }
            }
        };
    }

    void updateDatasets() {
        if (inflater instanceof DeviceViewInflater) {
            ((DeviceViewInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
        }
        if (inflater instanceof ScenarioViewInflater) {
            ((ScenarioViewInflater) inflater).buttonChanger(Config.INT_UNSET_ID);
        }
        if (inflater instanceof TimestampDeviceRowInflater) {
            ((TimestampDeviceRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
        }
        if (inflater instanceof ScenarioTimestampRowInflater) {
            ((ScenarioTimestampRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID);
        }
        if (inflater instanceof ScenarioDeviceRowInflater) {
            ((ScenarioDeviceRowInflater) inflater).buttonChanger(Config.INT_UNSET_ID, Config.STRING_EMPTY);
        }
    }

    /**
     * Beim Schließen des Dialogs wird keine weitere Aktion ausgeführt
     *
     * @return OnClickListener
     */
    public DialogInterface.OnClickListener setCancelButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDatasets();
                dialog.cancel();
            }
        };
    }

    /**
     * Verarbeitung des Strings der die Musikliste beinhaltet
     */
    String[] addPlaylist(String table) {
        RequestHandler rh = new RequestHandler();
        String resultMusic = rh.getPlayList(table);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(resultMusic);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            String[] playList = new String[result.length()];
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                int id = jo.getInt(Config.TAG_ID);
                String title = jo.getString(Config.TAG_NAME).trim();
                playList[id] = title;
            }
            return playList;
        } catch (JSONException e) {
            e.printStackTrace();
            fatalError(getContext());
            return null;
        }
    }

}
