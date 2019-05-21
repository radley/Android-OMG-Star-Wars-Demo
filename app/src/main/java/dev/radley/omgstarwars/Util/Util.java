package dev.radley.omgstarwars.Util;

public class Util {
    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    protected static String logTag = "OMGSW";
    protected static String swapiEndpoint = "https://swapi.co/api";

    private Util() {
    }

    public static String getTag() {
        return logTag;
    }

    public static String getEndpoint() {
        return swapiEndpoint;
    }
}
