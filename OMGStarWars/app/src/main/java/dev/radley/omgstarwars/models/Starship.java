package dev.radley.omgstarwars.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Starship model represents a single transport craft that has hyperdrive capability.
 */
public class Starship extends Vehicle implements Serializable {

    @SerializedName("starship_class")
    public String starshipClass;

    @SerializedName("hyperdrive_rating")
    public String hyperdriveRating;

    @SerializedName("MGLT")
    public String mglt;
}
