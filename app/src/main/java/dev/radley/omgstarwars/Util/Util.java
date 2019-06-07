package dev.radley.omgstarwars.Util;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static String tag = "OMGSW";
    public static String ASSETS_URL = "file:///android_asset/";

    public static int getId(String url) {
        String string[] = url.split("/");
        return Integer.parseInt(string[string.length-1]);
    }

    public static String getTrimmedQuery(String query) {
        query = query.replaceAll("[^a-zA-Z0-9]", "");
        return query.toLowerCase();
    }
}
