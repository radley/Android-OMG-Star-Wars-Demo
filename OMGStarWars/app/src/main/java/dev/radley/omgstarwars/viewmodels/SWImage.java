package dev.radley.omgstarwars.viewmodels;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.Category;

/**
 * Default image resources for Star Wars cards
 */
public class SWImage {

    public static int getPlaceholderImage(String id) {
        switch (id) {
            case Category.FILMS:
            case Category.PEOPLE:
            case Category.SPECIES:
                return R.drawable.placeholder_tall;

            case Category.STARSHIPS:
            case Category.VEHICLES:
                return R.drawable.placeholder_wide;
        }
        // planets & fallback
        return R.drawable.placeholder_square;
    }

    public static int getFallbackImage(String id) {
        switch (id) {
            case Category.FILMS:
                return R.drawable.placeholder_tall;
            case Category.PEOPLE:
                return R.drawable.generic_people;
            case Category.PLANETS:
                return R.drawable.generic_planet;
            case Category.SPECIES:
                return R.drawable.generic_species;
            case Category.STARSHIPS:
                return R.drawable.generic_starship;
            case Category.VEHICLES:
                return R.drawable.generic_vehicle;
        }
        // fallback
        return R.drawable.placeholder_square;
    }
}
