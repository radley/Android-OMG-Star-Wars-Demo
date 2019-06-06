/*
 * Copyright (C) 2018 Radley Marx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.radley.omgstarwars.bundle;

import android.app.Activity;
import android.content.Intent;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.activity.detail.FilmActivity;
import dev.radley.omgstarwars.activity.detail.PeopleActivity;
import dev.radley.omgstarwars.activity.detail.PlanetActivity;
import dev.radley.omgstarwars.activity.detail.SpeciesActivity;
import dev.radley.omgstarwars.activity.detail.StarshipActivity;
import dev.radley.omgstarwars.activity.detail.VehicleActivity;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;

/**
 * Holding intent extra names and utility methods for intent handling.
 */
public class DetailExtras {
    public static final String MODEL = "MODEL";
    public static final String IMAGE_URL = "IMAGE_URL";

    private static Intent intent;


    /**
     * Checks if all extras are present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return <code>true</code> if all extras are present, else <code>false</code>.
     */
    public static boolean hasAll(Intent intent, String... extras) {
        for (String extra : extras) {
            if (!intent.hasExtra(extra)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any extra is present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return <code>true</code> if any checked extra is present, else <code>false</code>.
     */
    public static boolean hasAny(Intent intent, String... extras) {
        for (String extra : extras) {
            if (intent.hasExtra(extra)) {
                return true;
            }
        }
        return false;
    }

    public static Intent getIntent(Activity activity, String category, SWModel item) {

        if (category.equals((activity.getString(R.string.category_id_films)))) {

            Film film = (Film) item;

            intent = new Intent(activity, FilmActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, film);

        } else if (category.equals((activity.getString(R.string.category_id_people)))) {

            People people = (People) item;

            intent = new Intent(activity, PeopleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, people);


        } else if (category.equals((activity.getString(R.string.category_id_planets)))) {

            Planet planet = (Planet) item;

            intent = new Intent(activity, PlanetActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, planet);

        } else if (category.equals((activity.getString(R.string.category_id_species)))) {

            Species species = (Species) item;

            intent = new Intent(activity, SpeciesActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, species);

        } else if (category.equals((activity.getString(R.string.category_id_starships)))) {

            Starship starship = (Starship) item;

            intent = new Intent(activity, StarshipActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, starship);

        } else if (category.equals((activity.getString(R.string.category_id_vehicles)))) {

            Vehicle vehicle = (Vehicle) item;

            intent = new Intent(activity, VehicleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailExtras.MODEL, vehicle);

        } else {
            // will never happen
            intent = new Intent();
        }

        return intent;
    }

}
