package dev.radley.omgstarwars.model.sw;


import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;

public class SWModel implements Serializable {

    public String name;
    public String url;
    public String categoryId;

    public String created;
    public String edited;

    public String getTitle() {
        return name;
    }

    public String getId() {

        // example: https://swapi.co/api/films/2/

        String string[] = url.split("/");
        return string[string.length-1];
    }

    public String getCategoryId() {

        // example: https://swapi.co/api/films/2/

        String string[] = url.split("/");
        return string[string.length-2];
    }

    public String getImageAsset() {

        // example: file:///android_asset/films/2.jpg

        return Util.ASSETS_URL + getCategoryId() +"/"+ getId() +".jpg";
    }
}
