package dev.radley.omgstarwars.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Starship model represents a single transport craft that has hyperdrive capability.
 */
class Starship : Vehicle(), Serializable {

    @SerializedName("starship_class")
    var starshipClass: String = ""

    @SerializedName("hyperdrive_rating")
    var hyperdriveRating: String = ""

    @SerializedName("MGLT")
    var mglt: String = ""
}
