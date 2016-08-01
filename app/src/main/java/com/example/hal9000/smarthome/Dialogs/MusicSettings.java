package com.example.hal9000.smarthome.Dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;


import com.example.hal9000.smarthome.Abstract.Inflater;
import com.example.hal9000.smarthome.Database.RequestHandler;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.ErrorHandler;
import com.example.hal9000.smarthome.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hal9000.smarthome.Helper.ErrorHandler.catchError;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.createToast;
import static com.example.hal9000.smarthome.Helper.ErrorHandler.fatalError;

public class MusicSettings extends DialogListener {

    private final RequestHandler rh;
    private String[] playList;

    /**
     * Konstruktor
     *
     * @param context   Kontext
     * @param intensity Lautstärke
     * @param songId    Songid
     * @param id        Id des Geräts
     * @param layoutInflater  inflater
     */
    public MusicSettings(Context context, int intensity, int songId, int id, LayoutInflater layoutInflater, Inflater inflater) {
        super(context,inflater);
        @SuppressLint("InflateParams") View layout = layoutInflater.inflate(R.layout.music_settings, null);
        createVolumeBar(layout, id, intensity);
        createSongList(layout, id, context, songId);
        rh = new RequestHandler();
        setView(layout);
        setPositiveButton("Schließen", setCancelButton());
    }

    /**
     * Zuweisung des Lautstärkereglers an ein layout Element
     * Übertragung der aktuellen Lautstärke an die Datenbank nachdem der Regler benutzt wurde
     *
     * @param layout    aktuelle View
     * @param id        Id
     * @param intensity Lautstärke zum Start
     */
    private void createVolumeBar(View layout, final int id, int intensity) {
        final SeekBar volumeBar = (SeekBar) layout.findViewById(R.id.skVolume);
        if (volumeBar != null) {
            volumeBar.setProgress(intensity);
            volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    String msgSingle = rh.updateSingleValue(Config.STRING_TYPE_EN_SPEAKER, Config.TAG_VOLUME, Integer.toString(volumeBar.getProgress()), id);
                    catchError(getContext(), msgSingle);
                }
            });
        }
    }


    /**
     * Auflistung der Musiktitelliste
     * Sendet Update an die Datenbank, wenn ein Titel ausgewählt wurde
     *
     */
    private void createSongList(View layout, final int dataId, final Context context, int songId) {
        ListView songList = (ListView) layout.findViewById(R.id.listView);
        playList = addPlaylist(Config.TAG_MUSIC);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1,playList);

        if (songList != null && playList!=null && playList.length>0) {
            songList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            songList.setAdapter(adapter);
            songList.setItemChecked(songId, true);
            songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /**
                 * Lied wechseln
                 * Aktualisiert die Werte anschließend in der Datenbank
                 * @param parent Parent
                 * @param view view
                 * @param position Position die geklickt wurde
                 * @param id Id
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String result = Integer.toString(position);
                    String msgStop = rh.updateSingleValue(Config.STRING_TYPE_EN_SPEAKER, Config.TAG_STOP, Integer.toString(Config.INT_STATUS_EIN), dataId);
                    String msgSongid = rh.updateSingleValue(Config.STRING_TYPE_EN_SPEAKER, Config.TAG_SONGID, result, dataId);
                    String msgPlaystate = rh.updateSingleValue(Config.STRING_TYPE_EN_SPEAKER, Config.TAG_STATE, Integer.toString(Config.INT_STATUS_EIN), dataId);
                    catchError(context, msgStop);
                    catchError(context, msgSongid);
                    catchError(context, msgPlaystate);

                }
            });
        }
        else{
            createToast(context,Config.ERROR_MUSIC);
            setError(true);
        }
    }


}

