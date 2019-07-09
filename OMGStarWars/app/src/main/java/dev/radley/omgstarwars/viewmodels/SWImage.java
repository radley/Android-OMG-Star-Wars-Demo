package dev.radley.omgstarwars.viewmodels;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.CategoryOld;

/**
 * Default image resources for Star Wars cards
 */
public class SWImage {

    public static int getPlaceholderImage(String id) {
        switch (id) {
            case CategoryOld.FILMS:
            case CategoryOld.PEOPLE:
            case CategoryOld.SPECIES:
                return R.drawable.placeholder_tall;

            case CategoryOld.STARSHIPS:
            case CategoryOld.VEHICLES:
                return R.drawable.placeholder_wide;
        }
        // planets & fallback
        return R.drawable.placeholder_square;
    }

    public static int getFallbackImage(String id) {
        switch (id) {
            case CategoryOld.FILMS:
                return R.drawable.placeholder_tall;
            case CategoryOld.PEOPLE:
                return R.drawable.generic_people;
            case CategoryOld.PLANETS:
                return R.drawable.generic_planet;
            case CategoryOld.SPECIES:
                return R.drawable.generic_species;
            case CategoryOld.STARSHIPS:
                return R.drawable.generic_starship;
            case CategoryOld.VEHICLES:
                return R.drawable.generic_vehicle;
        }
        // fallback
        return R.drawable.placeholder_square;
    }
}
