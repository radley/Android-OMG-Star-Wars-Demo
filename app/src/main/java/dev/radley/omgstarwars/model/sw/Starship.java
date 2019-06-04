package dev.radley.omgstarwars.model.sw;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dev.radley.omgstarwars.R;

/**
 * Created by Oleur on 22/12/2014.
 * Starship model represents a single transport craft that has hyperdrive capability.
 */
public class Starship extends Vehicle implements Serializable {

    @SerializedName("starship_class")
    public String starshipClass;

    @SerializedName("hyperdrive_rating")
    public String hyperdriveRating;

    @SerializedName("MGLT")
    public String mglt;

    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_wide;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_starship;
    }
}
