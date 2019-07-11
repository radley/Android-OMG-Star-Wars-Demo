package dev.radley.omgstarwars.models


import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

/**
 * People model represents an individual person or character within the Star Wars universe.
 */
class People : SWModel(), Serializable {

    var gender: String = ""
    var height: String = ""
    var mass: String = ""

    @SerializedName("birth_year")
    var birthYear: String = ""

    @SerializedName("hair_color")
    var hairColor: String = ""

    @SerializedName("homeworld")
    var homeWorldUrl: String = ""

    @SerializedName("skin_color")
    var skinColor: String = ""

    @SerializedName("films")
    override var relatedFilms: ArrayList<String>? = null

    @SerializedName("species")
    override var relatedSpecies: ArrayList<String>? = null

    @SerializedName("starships")
    override var relatedStarships: ArrayList<String>? = null

    @SerializedName("vehicles")
    override var relatedVehicles: ArrayList<String>? = null
}
