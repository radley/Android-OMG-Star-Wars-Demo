package dev.radley.omgstarwars.utilities;


public class Util {


    public static int getId(String url) {
        String string[] = url.split("/");
        return Integer.parseInt(string[string.length-1]);
    }

    public static String getTrimmedQuery(String query) {

        // only remove outer whitespace
        query = query.trim();
        query = query.replaceAll("[^a-zA-Z0-9\\s]", "");
        return query.toLowerCase();
    }
}
