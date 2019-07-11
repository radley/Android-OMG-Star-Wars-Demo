package dev.radley.omgstarwars.models


import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

/**
 * Species model represents a type of person or character within the Star Wars Universe.
 */
class Species : SWModel(), Serializable {

    var classification: String = ""
    var designation: String = ""
    var language: String = ""

    @SerializedName("average_height")
    var averageHeight: String = ""

    @SerializedName("average_lifespan")
    var averageLifespan: String = ""

    @SerializedName("eye_colors")
    var eyeColors: String = ""

    @SerializedName("hair_colors")
    var hairColors: String = ""

    @SerializedName("skin_colors")
    var skinColors: String = ""

    @SerializedName("homeworld")
    var homeWorld: String = ""

    @SerializedName("people")
    var peopleUrls: ArrayList<String>? = null

    @SerializedName("films")
    override var relatedFilms: ArrayList<String>? = null
}