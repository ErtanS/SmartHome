package com.example.hal9000.smarthome;


        import android.app.ActivityManager;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;

        import com.example.hal9000.smarthome.Dialogs.DialogListener;
        import com.example.hal9000.smarthome.Dialogs.Settings;
        import com.example.hal9000.smarthome.Helper.Config;
        import com.example.hal9000.smarthome.Views.HomeScreen.HomeScreen;
        import com.google.firebase.messaging.FirebaseMessagingService;
        import com.google.firebase.messaging.RemoteMessage;

/**
 * Service der die eingehenden Nachrichten verwaltet
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Bundle bundle = new Bundle();
    /**
     * Aufgerufene Methode, wenn eine Nachricht Empfangen wurde
     * @param remoteMessage Eingehende Nachricht
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: 26.07.2016 Nachrichtentext sollte noch bearbeitet werden z.B. Trockner in der Waschkueche ist fertig 
        String message = remoteMessage.getData().get("message");
        System.out.println("--------------------------------"+message);
        if(message.contains("washer:")){
            String[] text = message.split(":");
            String notificaitonText = text[text.length-1] + " ist fertig.";
            System.out.println("--------------------------------"+notificaitonText);
            showNotification("SmartHome - Waschmaschine",notificaitonText,R.drawable.washer_on);
        }
        else if(message.contains("dryer:")){
            String[] text = message.split(":");
            String notificaitonText = text[text.length-1] + " ist fertig.";
            System.out.println("--------------------------------"+notificaitonText);
            showNotification("SmartHome - Trockner",notificaitonText,R.drawable.dryer_on);
        }
        else if(message.contains("oven:")){
            String[] text = message.split(":");
            String notificaitonText = text[text.length-1] + " ist fertig.";
            System.out.println("--------------------------------"+notificaitonText);
            showNotification("SmartHome - Ofen",notificaitonText,R.drawable.oven_on);
        }
        else if(message.contains("stove:")) {
            String[] text = message.split(":");
            String notificaitonText = text[text.length - 1] + " ist fertig.";
            System.out.println("--------------------------------" + notificaitonText);
            showNotification("SmartHome - Herd", notificaitonText, R.drawable.oven_on);
        }
        else if (message.contains("camera:")){
            String[] text = message.split(":");
            String cameraName = text[1];
            String pictureName = text[2];

            createBundle(cameraName,pictureName);
            showNotification("SmartHome - Sicherheitskamera","ALARM",R.drawable.camon);

        }
    }

    /**
     * Erstellen einer Pushnotification mit Icon + Beschreibungstext
     */
    private void showNotification(String title, String notificationText, int icon) {
        //Activity die gestartet wird, wenn die Pushnachricht angeklickt wird
        Intent i = new Intent(this,HomeScreen.class);

        i.putExtras(bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(notificationText)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent);
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }

    private void createBundle(String cameraName, String pictureName){
        bundle = new Bundle();
        bundle.putString(Config.TAG_NAME,pictureName);
        bundle.putString(Config.STRING_TYPE_EN_CAMERA, cameraName);

    }



}