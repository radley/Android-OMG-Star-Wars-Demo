package dev.radley.omgstarwars.Util;


public class OmgSWUtil {

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
