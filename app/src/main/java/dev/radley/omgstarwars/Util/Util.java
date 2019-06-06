package dev.radley.omgstarwars.Util;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    public static String tag = "OMGSW";
    public static String ASSETS_URL = "file:///android_asset/";

    public static String getId(String url) {
        String string[] = url.split("/");
        return string[string.length-1];
    }

    public static String getAssetImage(String category, String url) {
        return ASSETS_URL +category +"/" +getId(url) +".jpg";
    }
}
