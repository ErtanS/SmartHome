package com.example.hal9000.smarthome.Helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.hal9000.smarthome.Helper.Config;
import com.example.hal9000.smarthome.Views.HomeScreen.HomeScreen;

/**
 * Allgemeine Klasse zur Fehlerbehandlung
 */
public class ErrorHandler {

    /**
     * Fängt Fehler beim Ausführen der PHP-Datei bzw. bei der Verbindung zur Datenbank ab.
     *
     * @param ctx aktueller Context
     * @param msg Text zur Überprüfung
     * @return  true, wenn die Datei nicht korrekt ausgeführt werden konnte(Fehler)
     *          false, ohne Fehler
     */
    public static boolean catchError(Context ctx, String msg) {
        if (msg.contains(Config.ERROR)) {
            if (!msg.contains(Config.ERROR_NO_CONNECTION)) {
                createToast(ctx, msg);
            }
            return true;
        }
        return false;
    }

    /**
     * Erstellen und anzeigen eines Toasts
     *
     * @param ctx Kontext
     * @param msg Nachricht die angezeigt werden soll
     */
    public static void createToast(Context ctx, String msg) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Fataler Fehler bei dem das Programm zum Homescreen zurück wechseln soll
     *
     * @param ctx Kontext
     */
    public static void fatalError(Context ctx) {
        Intent homeScreen = new Intent(ctx, HomeScreen.class);
        homeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(homeScreen);
        createToast(ctx, Config.ERROR_NO_CONNECTION);
    }
}
