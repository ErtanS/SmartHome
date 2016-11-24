package com.example.hal9000.smarthome.Helper;

/**
 * The type String helper.
 */
public abstract class StringHelper {

    /**
     * Schneidet das Wort an "position" aus und ersetzt "_" durch Leerzeichen
     *
     * @param item     das zu Schneidende wort
     * @param position Position des Worts das entfernt werden soll
     * @return Gesamter String
     */
    public static String stringCutter(String item, int position) {
        String newString = "";

        String[] words = item.split("_");

        for (int i = 0; i < words.length; i++) {
            if (i != position) {
                String title = setUmlauts(words[i]);
                newString = newString + title + Config.STRING_SPACE;
            }
        }

        newString = newString.trim();
        return newString;
    }

    /**
     * Umlaute wieder einfügen
     *
     * @param gerWord Wort ohne Umlaute
     * @return wort mit Umlauten
     */
    private static String setUmlauts(String gerWord) {
        gerWord = gerWord.replace("ae", "ä");
        gerWord = gerWord.replace("ue", "ü");
        gerWord = gerWord.replace("oe", "ö");
        return gerWord;
    }
}
