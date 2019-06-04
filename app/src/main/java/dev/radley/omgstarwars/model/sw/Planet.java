package dev.radley.omgstarwars.model.sw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;

/**
 * Created by Oleur on 22/12/2014.
 * Planet model represents a large mass, planet or planetoid in the Star Wars Universe, at the time of 0 ABY.
 */
public class Planet extends SWModel implements Serializable {

    public String diameter;
    public String gravity;
    public String population;
    public String climate;
    public String terrain;

    @SerializedName("rotation_period")
    public String rotationPeriod;

    @SerializedName("orbital_period")
    public String orbitalPeriod;

    @SerializedName("surface_water")
    public String surfaceWater;

    @SerializedName("residents")
    public ArrayList<String> residentsUrls;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_square;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_planet;
    }
}
