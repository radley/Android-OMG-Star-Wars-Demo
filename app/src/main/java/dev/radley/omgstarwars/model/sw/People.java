package dev.radley.omgstarwars.model.sw;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;

/**
 * Created by Oleur on 21/12/2014.
 * People model represents an individual person or character within the Star Wars universe.
 */
public class People extends SWModel implements Serializable {

    public String gender;
    public String height;
    public String mass;

    @SerializedName("birth_year")
    public String birthYear;

    @SerializedName("hair_color")
    public String hairColor;

    @SerializedName("homeworld")
    public String homeWorldUrl;

    @SerializedName("skin_color")
    public String skinColor;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    @SerializedName("species")
    public ArrayList<String> speciesUrls;

    @SerializedName("starships")
    public ArrayList<String> starshipsUrls;

    @SerializedName("vehicles")
    public ArrayList<String> vehiclesUrls;

    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_tall;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_people;
    }
}
