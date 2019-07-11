package dev.radley.omgstarwars.viewmodels

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.Category

/**
 * Default image resources for Star Wars cards
 */
object SWImage {

    fun getPlaceholderImage(id: String): Int {
        when (id) {
            Category.FILMS, Category.PEOPLE, Category.SPECIES -> return R.drawable.placeholder_tall

            Category.STARSHIPS, Category.VEHICLES -> return R.drawable.placeholder_wide
        }
        // planets & fallback
        return R.drawable.placeholder_square
    }

    fun getFallbackImage(id: String): Int {
        when (id) {
            Category.FILMS -> return R.drawable.placeholder_tall
            Category.PEOPLE -> return R.drawable.generic_people
            Category.PLANETS -> return R.drawable.generic_planet
            Category.SPECIES -> return R.drawable.generic_species
            Category.STARSHIPS -> return R.drawable.generic_starship
            Category.VEHICLES -> return R.drawable.generic_vehicle
        }
        // fallback
        return R.drawable.placeholder_square
    }
}
