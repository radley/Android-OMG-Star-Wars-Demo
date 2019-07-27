package dev.radley.omgstarwars.models

object Category {


    const val FILMS = "films"
    const val PEOPLE = "people"
    const val PLANETS = "planets"
    const val SPECIES = "species"
    const val STARSHIPS = "starships"
    const val VEHICLES = "vehicles"

    val categories = arrayOf(FILMS, PEOPLE, SPECIES, STARSHIPS, VEHICLES, PLANETS)
    val categoryTitles = arrayOf("Films", "People", "Species", "Starships", "Vehicles", "Planets")

}