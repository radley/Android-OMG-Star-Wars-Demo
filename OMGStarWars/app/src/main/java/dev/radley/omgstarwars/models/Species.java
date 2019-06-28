package dev.radley.omgstarwars.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Species model represents a type of person or character within the Star Wars Universe.
 */
public class Species extends SWModel implements Serializable {

    public String classification;
    public String designation;
    public String language;

    @SerializedName("average_height")
    public String averageHeight;

    @SerializedName("average_lifespan")
    public String averageLifespan;

    @SerializedName("eye_colors")
    public String eyeColors;

    @SerializedName("hair_colors")
    public String hairColors;

    @SerializedName("skin_colors")
    public String skinColors;

    @SerializedName("homeworld")
    public String homeWorld;

    @SerializedName("people")
    public ArrayList<String> peopleUrls;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    @Override
    public ArrayList<String> getRelatedFilms() {
        return filmsUrls;
    }
}