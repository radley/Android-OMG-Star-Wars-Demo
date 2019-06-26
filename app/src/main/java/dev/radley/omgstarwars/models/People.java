package dev.radley.omgstarwars.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
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
    public ArrayList<String> getRelatedFilms() {
        return filmsUrls;
    }

    @Override
    public ArrayList<String> getRelatedSpecies() {
        return speciesUrls;
    }

    @Override
    public ArrayList<String> getRelatedStarships() {
        return starshipsUrls;
    }

    @Override
    public ArrayList<String> getRelatedVehicles() {
        return vehiclesUrls;
    }
}
