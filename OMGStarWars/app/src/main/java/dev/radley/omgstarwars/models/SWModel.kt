package dev.radley.omgstarwars.models


import java.io.Serializable
import java.util.ArrayList

import dev.radley.omgstarwars.utilities.Constants

/**
 * Base model for swapi models
 */
open class SWModel : Serializable {

    /**
     * Models contain either name or title
     * This makes it uniform
     *
     * @return name or title
     */
    var name: String = ""
    var url: String = ""
    var created: String = ""
    var edited: String = ""


    open val title: String
        get() = name

    open val subtitle: String
        get() = ""


    /**
     * Extract id value from url
     *
     * @return id String
     */
    // example: https://swapi.co/api/films/2/
    val id: String
        get() {

            val string = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return string[string.size - 1]
        }

    /**
     * Extract category id from url
     *
     * @return category String
     */
    // example: https://swapi.co/api/films/2/
    val categoryId: String
        get() {

            val string = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return string[string.size - 2]
        }

    /**
     * Returns asset image path based on category & id
     *
     * @return path String
     */
    // example: file:///android_asset/films/2.jpg
    val imagePath: String
        get() = Constants.ASSETS_PATH + categoryId + "/" + id + ".jpg"

    /**
     * Returns related films list
     * Model class will override with value if true
     *
     * @return null or filmsUrl ArrayList
     */
    open val relatedFilms: ArrayList<String>?
        get() = null

    /**
     * Returns related people list
     * Model class will override with value if true
     *
     * @return null or peopleUrl ArrayList
     */
    open val relatedPeople: ArrayList<String>?
        get() = null

    /**
     * Returns related planets list
     * Model class will override with value if true
     *
     * @return null or planetsUrl ArrayList
     */
    open val planets: ArrayList<String>?
        get() = null

    /**
     * Returns related species list
     * Model class will override with value if true
     *
     * @return null or speciesUrl ArrayList
     */
    open val relatedSpecies: ArrayList<String>?
        get() = null

    /**
     * Returns related starships list
     * Model class will override with value if true
     *
     * @return null or starshipsUrl ArrayList
     */
    open val relatedStarships: ArrayList<String>?
        get() = null

    /**
     * Returns related vehicles list
     * Model class will override with value if true
     *
     * @return null or vehiclesUrl ArrayList
     */
    open val relatedVehicles: ArrayList<String>?
        get() = null


    /**
     * Returns title for related films section
     *
     * @return "Films"
     */
    val relatedFilmsTitle: String
        get() = "Films"

    /**
     * Returns title for related films section
     * Model may override for special cases
     * "i.e. "Characters" or "Pilots"
     *
     * @return "People"
     */
    open val relatedPeopleTitle: String
        get() = "People"

    /**
     * Returns title for related planets section
     *
     * @return "Planets"
     */
    val relatedPlanetsTitle: String
        get() = "Planets"

    /**
     * Returns title for related species section
     *
     * @return "Species"
     */
    val relatedSpeciesTitle: String
        get() = "Species"

    /**
     * Returns title for related starships section
     *
     * @return "Starships"
     */
    val relatedStarshipsTitle: String
        get() = "Starships"

    /**
     * Returns title for related vehicles section
     *
     * @return "Vehicles"
     */
    val relatedVehiclesTitle: String
        get() = "Vehicles"
}
