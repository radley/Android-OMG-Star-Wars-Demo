package dev.radley.omgstarwars.models

import java.io.Serializable
import java.util.ArrayList

/**
 * Generic list model
 */
class SWModelList<T> : Serializable {
    var count: Int = 0
    var next: String? = null
    var previous: String? = null
    var results: ArrayList<T>? = null
}