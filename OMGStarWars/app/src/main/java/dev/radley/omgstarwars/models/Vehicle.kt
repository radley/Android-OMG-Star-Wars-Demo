package dev.radley.omgstarwars.models


import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

/**
 * Vehicle model represents a single transport craft that does not have hyperdrive capability.
 */
open class Vehicle : SWModel(), Serializable {

    var model: String = ""
    var manufacturer: String = ""
    var length: String = ""
    var crew: String = ""
    var passengers: String = ""
    var consumables: String = ""

    override val subtitle: String
        get() = model

    @SerializedName("vehicle_class")
    var vehicleClass: String = ""

    @SerializedName("cost_in_credits")
    var costInCredits: String = ""

    @SerializedName("max_atmosphering_speed")
    var maxAtmospheringSpeed: String = ""

    @SerializedName("cargo_capacity")
    var cargoCapacity: String = ""

    @SerializedName("pilots")
    override var relatedPeople: ArrayList<String>? = null

    @SerializedName("films")
    override var relatedFilms: ArrayList<String>? = null

    override val relatedPeopleTitle: String
        get() = "Pilots"
}