package com.example.hal9000.smarthome.Abstract;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hal9000.smarthome.DataSet.CameraDataSet;
import com.example.hal9000.smarthome.DataSet.DeviceDataSet;
import com.example.hal9000.smarthome.DataSet.DoorDataSet;
import com.example.hal9000.smarthome.DataSet.DryerDataSet;
import com.example.hal9000.smarthome.DataSet.HeaterDataSet;
import com.example.hal9000.smarthome.DataSet.LightDataSet;
import com.example.hal9000.smarthome.DataSet.OvenDataSet;
import com.example.hal9000.smarthome.DataSet.PcDataSet;
import com.example.hal9000.smarthome.DataSet.SpeakerDataSet;
import com.example.hal9000.smarthome.DataSet.StoveDataSet;
import com.example.hal9000.smarthome.DataSet.TvDataSet;
import com.example.hal9000.smarthome.DataSet.WallDataSet;
import com.example.hal9000.smarthome.DataSet.WasherDataSet;
import com.example.hal9000.smarthome.DataSet.WaterDataSet;
import com.example.hal9000.smarthome.Dialogs.DialogActivity;
import com.example.hal9000.smarthome.Dialogs.CameraSettings;
import com.example.hal9000.smarthome.Dialogs.DialogListener;
import com.example.hal9000.smarthome.Dialogs.DoorSettings;
import com.example.hal9000.smarthome.Dialogs.DryerSettings;
import com.example.hal9000.smarthome.Dialogs.HeaterSettings;
import com.example.hal9000.smarthome.Dialogs.MusicSettings;
import com.example.hal9000.smarthome.Dialogs.OvenSettings;
import com.example.hal9000.smarthome.Dialogs.StoveSettings;
import com.example.hal9000.smarthome.Dialogs.WasherSettings;
import com.example.hal9000.smarthome.Dialogs.WaterSettings;
import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Helper.Tag;
import com.example.hal9000.smarthome.R;

import java.util.ArrayList;

/**
 * Abstrakte Klasse die Konstruktoren und Methoden den spezifischen Inflatern vererbt
 */
@SuppressWarnings("JavaDoc")
public abstract class Inflater {

    protected final int rowID;
    protected final LinearLayout parentView;
    protected final Context context;
    protected final String category;
    protected final LayoutInflater inflater;
    protected String deviceType;
    protected String deviceRoom;
    protected String scenarioName;

    private final android.support.v4.app.FragmentManager fragmentManager;

    /**
     * Konstruktor
     *
     * @param parentView obergeordete View
     * @param inflater   altueller Inflater
     * @param context    aktueller Kontext
     * @param deviceType Gerätetyp speaker/light/etc...
     * @param deviceRoom Geräteraum z.B. BedRoom/Office/etc..
     */
    protected Inflater(LinearLayout parentView, LayoutInflater inflater, Context context, String deviceType, String deviceRoom, FragmentManager fragmentManager) {
        this(R.layout.dynamic_device_row, parentView, inflater, context, Config.CATEGORY_DEVICE, fragmentManager);
        this.deviceType = deviceType;
        this.deviceRoom = deviceRoom;
    }

    /**
     * Konstruktor
     *
     * @param rowID      rowid
     * @param parentView obergeordete View
     * @param inflater   altueller Inflater
     * @param context    aktueller Context
     * @param category   device oder timestamp oder scenario
     */
    protected Inflater(int rowID, LinearLayout parentView, LayoutInflater inflater, Context context, String category, android.support.v4.app.FragmentManager fragmentManager) {
        this.rowID = rowID;
        this.parentView = parentView;
        this.inflater = inflater;
        this.context = context;
        this.category = category;
        this.fragmentManager = fragmentManager;

    }

    /**
     * Konstruktor
     *
     * @param rowID
     * @param parentView
     * @param inflater
     * @param context
     * @param scenarioName
     */
    protected Inflater(int rowID, LinearLayout parentView, LayoutInflater inflater, String scenarioName, Context context, android.support.v4.app.FragmentManager fragmentManager) {
        this(rowID, parentView, inflater, context, Config.CATEGORY_SCENARIO, fragmentManager);
        this.scenarioName = scenarioName;
    }

    /**
     * Methode um aus dem Typ und Status das darzustellende Bild zu ermitteln
     *
     * @param typ    Gerätetyp  z.B. 'light','speaker',...
     * @param status Gerätestatus  Aus = 0  Ein = 1
     * @return Bild id
     */
    private int getImageId(String typ, int status) {
        switch (typ) {
            case Config.STRING_TYPE_EN_LIGHT:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.lighton;
                } else {
                    return R.drawable.lightoff;
                }
            case Config.STRING_TYPE_EN_SPEAKER:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.speakeron;
                } else {
                    return R.drawable.speakeroff;
                }
            case Config.STRING_TYPE_EN_SHUTTERS:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.shuttersdown;
                } else {
                    return R.drawable.shutters;
                }
            case Config.STRING_TYPE_EN_DOOR:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.doorclose;
                } else {
                    return R.drawable.dooropen;
                }
            case Config.STRING_TYPE_EN_WINDOW:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.windowopen;
                } else {
                    return R.drawable.windowclose;
                }
            case Config.STRING_TYPE_EN_HEATER:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.heateron;
                } else {
                    return R.drawable.heateroff;
                }
            case Config.CATEGORY_SCENARIO:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.radioon;
                } else {
                    return R.drawable.radiooff;
                }
            case Config.STRING_TAG_SWITCH_IMAGE:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.switchon;
                } else {
                    return R.drawable.switchoff;
                }
            case Config.STRING_TYPE_EN_CAMERA:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.camon;
                } else {
                    return R.drawable.camoff;
                }
            case Config.STRING_TYPE_EN_WATER:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.wateron;
                } else {
                    return R.drawable.wateroff;
                }
            case Config.STRING_TYPE_EN_DRYER:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.dryer_on;
                } else {
                    return R.drawable.washer_off;
                }
            case Config.STRING_TYPE_EN_OVEN:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.oven_on;
                } else {
                    return R.drawable.oven_off;
                }
            case Config.STRING_TYPE_EN_STOVE:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.stove_on;
                } else {
                    return R.drawable.stove_off;
                }
            case Config.STRING_TYPE_EN_TV:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.tv_on;
                } else {
                    return R.drawable.tv_off;
                }
            case Config.STRING_TYPE_EN_WALL:
                return -1;
            case Config.STRING_TYPE_EN_WASHER:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.washer_on;
                } else {
                    return R.drawable.washer_off;
                }
            case Config.STRING_TYPE_EN_PC:
                if (status == Config.INT_STATUS_EIN) {
                    return R.drawable.pc_on;
                } else {
                    return R.drawable.pc_off;
                }

            default:
                return -1;
        }
    }

    /**
     * Methode um das Anzeigebild einer ImageView zu wechseln
     *
     * @param typ      Gerätetyp z.B. 'light','speaker',...
     * @param status   Gerätestatus  Aus = 0  Ein = 1
     * @param imSwitch ImageView bei der das Anzeigebild gewechselt werden soll
     */
    protected void switchImage(String typ, int status, ImageView imSwitch) {
        int imageId = getImageId(typ, status);
        if (imageId != -1) {
            imSwitch.setImageResource(imageId);
        } else {
            imSwitch.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Klicken des Einstellungsbuttons bei den Lautsprechern
     * Musikdialog öffnen
     *
     * @param speaker Lautsprecherdatensatz
     */
    private void clickSettingsSpeaker(SpeakerDataSet speaker) {
        int id = speaker.getId();
        int intensity = speaker.getVolume();
        int songId = speaker.getSongid();
        MusicSettings editSpeaker = new MusicSettings(context, intensity, songId, id, inflater, this);
        createDialog(editSpeaker);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Heizungen
     *
     * @param heater Heizungsdatensatz
     */
    private void clickSettingsHeater(HeaterDataSet heater) {
        int id = heater.getId();
        int temperature = heater.getTemperature();
        HeaterSettings editHeater = new HeaterSettings(context, temperature, id, inflater, this);
        createDialog(editHeater);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Türen
     *
     * @param door Türdatensatz
     */
    private void clickSettingsDoor(DoorDataSet door) {
        int id = door.getId();
        String password = door.getPassword();
        DoorSettings editDoor = new DoorSettings(context, password, id, inflater, this);
        createDialog(editDoor);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Lampen
     *
     * @param light Lampendatensatz
     */
    private void clickSettingsLight(LightDataSet light) {
        /*String currentColor = light.getColor();
        int id = light.getId();
        int currentIntensity = light.getIntensity();
        LightSettings editLight = new LightSettings(context, Color.parseColor(currentColor), currentIntensity, id,this);
        editLight.setOnDismissListener(this);
        editLight.show();
        */
        DialogActivity dialog = new DialogActivity();
        dialog.setArguments(light, this, inflater);
        dialog.show(fragmentManager, "missiles");
    }

    /**
     * Klicken des Einstellungsbuttons bei den Sicherheitskameras
     *
     * @param cam Kamera
     */
    private void clickSettingsCamera(CameraDataSet cam) {
        CameraSettings editCamera = new CameraSettings(context, cam, inflater, this);
        createDialog(editCamera);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Sicherheitskameras
     *
     * @param water wasserstrahlen
     */
    private void clickSettingsWater(WaterDataSet water) {
        int id = water.getId();
        int intensity = water.getIntensity();
        int temperature = water.getTemperature();
        WaterSettings editWater = new WaterSettings(context, temperature, intensity, id, inflater, this);
        createDialog(editWater);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param dryer Trockner
     */
    private void clickSettingsDryer(DryerDataSet dryer) {
        int id = dryer.getId();
        int amount = dryer.getAmount();
        int clothes = dryer.getClothes();
        DryerSettings editDryer = new DryerSettings(context, amount, clothes, id, inflater, this);
        createDialog(editDryer);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param oven Ofen
     */
    private void clickSettingsOven(OvenDataSet oven) {
        int id = oven.getId();
        int temperature = oven.getTemperature();
        int duration = oven.getDuration();
        OvenSettings editOven = new OvenSettings(context, temperature, duration, id, inflater, this);
        createDialog(editOven);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param pc PC
     */
    private void clickSettingsPc(PcDataSet pc) {
        DialogActivity dialog = new DialogActivity();
        dialog.setArguments(pc, this, inflater);
        dialog.show(fragmentManager, "missiles");
    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param stove Herd
     */
    private void clickSettingsStove(StoveDataSet stove) {
        int id = stove.getId();
        int temperature = stove.getTemperature();
        int duration = stove.getDuration();
        StoveSettings editStove = new StoveSettings(context, temperature, duration, id, inflater, this);
        createDialog(editStove);
    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param tv Fernseher
     */
    private void clickSettingsTv(TvDataSet tv) {
        DialogActivity dialog = new DialogActivity();
        dialog.setArguments(tv, this, inflater);
        dialog.show(fragmentManager, "missiles");

    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param wall Wand
     */
    private void clickSettingsWall(WallDataSet wall) {
        /*int id = wall.getId();
        String color = wall.getColor();
        int pictureid = wall.getPictureid();
        WallSettings editWall = new WallSettings(context,Color.parseColor(color),pictureid,id);
        editWall.setOnDismissListener(this);
        editWall.show();
        */
        DialogActivity dialog = new DialogActivity();
        dialog.setArguments(wall, this, inflater);
        dialog.show(fragmentManager, "missiles");

    }

    /**
     * Klicken des Einstellungsbuttons bei den Trocknern
     *
     * @param washer Waschmachine
     */
    private void clickSettingsWasher(WasherDataSet washer) {
        int id = washer.getId();
        int amount = washer.getAmount();
        int clothes = washer.getClothes();
        WasherSettings editDryer = new WasherSettings(context, amount, clothes, id, inflater, this);
        createDialog(editDryer);
    }


    /**
     * Öffnen eines Dialogs
     *
     * @param settings dialoglistener
     */
    private void createDialog(DialogListener settings) {
        if (!settings.isError()) {
            settings.setOnDismissListener(this);
            settings.show();
        }
    }

    /**
     * Methode um bei Fenstern und Rollladen den Settingsbutton zu löschen bzw. auszublenden
     *
     * @param imageView aktueller Settingsbutton
     * @param type      Gerätetyp der ImageView
     */
    protected void deleteSettingsButton(ImageView imageView, String type) {
        if (type.equals(Config.STRING_TYPE_EN_WINDOW) || type.equals(Config.STRING_TYPE_EN_SHUTTERS)) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Spezifiche Clicksettings Methode ausführen
     *
     * @param type Gerätetyp
     * @return onclickListener für Settings
     */
    protected View.OnClickListener clickSettings(final String type) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tag tag = (Tag) v.getTag();
                DeviceDataSet dataSet = tag.getDataSet();
                switch (type) {
                    case Config.STRING_TYPE_EN_LIGHT:
                        clickSettingsLight((LightDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_SPEAKER:
                        clickSettingsSpeaker((SpeakerDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_SHUTTERS:
                        break;
                    case Config.STRING_TYPE_EN_DOOR:
                        clickSettingsDoor((DoorDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_WINDOW:
                        break;
                    case Config.STRING_TYPE_EN_HEATER:
                        clickSettingsHeater((HeaterDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_CAMERA:
                        clickSettingsCamera((CameraDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_WATER:
                        clickSettingsWater((WaterDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_DRYER:
                        clickSettingsDryer((DryerDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_OVEN:
                        clickSettingsOven((OvenDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_PC:
                        clickSettingsPc((PcDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_STOVE:
                        clickSettingsStove((StoveDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_TV:
                        clickSettingsTv((TvDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_WALL:
                        clickSettingsWall((WallDataSet) dataSet);
                        break;
                    case Config.STRING_TYPE_EN_WASHER:
                        clickSettingsWasher((WasherDataSet) dataSet);
                        break;
                }
            }
        };
    }

    /**
     * Wechsel zur richtigen Parentview
     *
     * @param oldContext aktueller Kontext
     * @param newClass   Klasse zu der gewechselt werden soll
     * @return parentActivity
     */
    public Intent getParentActivityIntentImpl(Context oldContext, Class newClass) {
        Intent i;
        i = new Intent(oldContext, newClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return i;
    }

    protected ArrayList<View> findViewWithTagRecursively(ViewGroup root) {
        ArrayList<View> allViews = new ArrayList<>();

        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = root.getChildAt(i);
            if (childView instanceof ViewGroup) {
                allViews.addAll(findViewWithTagRecursively((ViewGroup) childView));
            } else {
                final Object tagView = childView.getTag();
                if (tagView != null && tagView instanceof Tag) {
                    allViews.add(childView);
                }
            }
        }
        return allViews;
    }
}
