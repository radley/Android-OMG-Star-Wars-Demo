package dev.radley.omgstarwars.Util;


import android.util.Log;

public class SWUtil {
    private static final SWUtil ourInstance = new SWUtil();

    public static SWUtil getInstance() {
        return ourInstance;
    }

    protected static String logTag = "OMGSW";
    protected static String API_URL = "https://swapi.co/api/";
    protected static String ASSETS_URL = "file:///android_asset/";

    private SWUtil() {
    }

    public static String getTag() {
        return logTag;
    }

    public static String getApi() {
        return API_URL;
    }
    public static String getAssets() {
        return ASSETS_URL;
    }

    public static String getId(String url) {
        String string[] = url.split("/");
        return string[string.length-1];
    }

    public static String getAssetImage(String category, String url) {
        return ASSETS_URL +category +"/" +getId(url) +".jpg";
    }
}
