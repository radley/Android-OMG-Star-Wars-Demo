package dev.radley.omgstarwars.models

import com.google.gson.annotations.SerializedName
import dev.radley.omgstarwars.utilities.FormatUtils

import java.io.Serializable
import java.util.ArrayList

/**
 * Planet model represents a large mass, planet or planetoid in the Star Wars Universe, at the time of 0 ABY.
 */
class Planet : SWModel(), Serializable {

    var diameter: String = ""
    var gravity: String = ""
    var population: String = ""
    var climate: String = ""
    var terrain: String = ""

    override val subtitle: String
        get() = "Population: " + FormatUtils.getFormattedNumber(population)

    @SerializedName("rotation_period")
    var rotationPeriod: String = ""

    @SerializedName("orbital_period")
    var orbitalPeriod: String = ""

    @SerializedName("surface_water")
    var surfaceWater: String = ""

    @SerializedName("residents")
    override var relatedPeople: ArrayList<String>? = null

    @SerializedName("films")
    override var relatedFilms: ArrayList<String>? = null

    override val relatedPeopleTitle: String
        get() = "Residents"
}
