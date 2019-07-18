package dev.radley.omgstarwars.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

/**
 * Film model represents a Star Wars single film.
 */
class Film : SWModel(), Serializable {

    override var title: String = ""
    var director: String = ""
    var producer: String = ""

    override val subtitle: String
        get() = "Episode $episodeId"


    @SerializedName("episode_id")
    var episodeId: Int = 0

    @SerializedName("opening_crawl")
    var openingCrawl: String = ""

    @SerializedName("species")
    override var relatedSpecies: ArrayList<String>? = null

    @SerializedName("starships")
    override var relatedStarships: ArrayList<String>? = null

    @SerializedName("vehicles")
    override var relatedVehicles: ArrayList<String>? = null

    @SerializedName("planets")
    override var planets: ArrayList<String>? = null

    @SerializedName("characters")
    override var relatedPeople: ArrayList<String>? = null

    override val relatedPeopleTitle: String
        get() = "Characters"


}