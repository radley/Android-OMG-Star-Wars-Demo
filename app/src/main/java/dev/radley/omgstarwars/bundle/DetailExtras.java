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
import dev.radley.omgstarwars.ui.detail.DetailActivity;
import dev.radley.omgstarwars.ui.detail.film.FilmDetailActivity;
import dev.radley.omgstarwars.ui.detail.people.PeopleDetailActivity;
import dev.radley.omgstarwars.ui.detail.planet.PlanetDetailActivity;
import dev.radley.omgstarwars.ui.detail.species.SpeciesDetailActivity;
import dev.radley.omgstarwars.ui.detail.starship.StarshipDetailActivity;
import dev.radley.omgstarwars.ui.detail.vehicle.VehicleDetailActivity;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.model.Vehicle;

/**
 * Holding sIntent extra names and utility methods for sIntent handling.
 */
public class DetailExtras {
    public static final String MODEL = "MODEL";

    private static Intent sIntent;


    /**
     * Checks if all extras are present in an sIntent.
     *
     * @param intent The sIntent to check.
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
     * Checks if any extra is present in an sIntent.
     *
     * @param intent The sIntent to check.
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

    public static Intent getIntent(Activity activity, SWModel item) {

        sIntent = new Intent(activity, DetailActivity.class);
        sIntent.setAction(Intent.ACTION_VIEW);
        sIntent.putExtra(DetailExtras.MODEL, item);
//
//        if (category.equals((activity.getString(R.string.category_id_films)))) {
//
//            Film film = (Film) item;
//
//            sIntent = new Intent(activity, FilmDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, film);
//
//        } else if (category.equals((activity.getString(R.string.category_id_people)))) {
//
//            People people = (People) item;
//
//            sIntent = new Intent(activity, PeopleDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, people);
//
//
//        } else if (category.equals((activity.getString(R.string.category_id_planets)))) {
//
//            Planet planet = (Planet) item;
//
//            sIntent = new Intent(activity, PlanetDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, planet);
//
//        } else if (category.equals((activity.getString(R.string.category_id_species)))) {
//
//            Species species = (Species) item;
//
//            sIntent = new Intent(activity, SpeciesDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, species);
//
//        } else if (category.equals((activity.getString(R.string.category_id_starships)))) {
//
//            Starship starship = (Starship) item;
//
//            sIntent = new Intent(activity, StarshipDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, starship);
//
//        } else if (category.equals((activity.getString(R.string.category_id_vehicles)))) {
//
//            Vehicle vehicle = (Vehicle) item;
//
//            sIntent = new Intent(activity, VehicleDetailActivity.class);
//            sIntent.setAction(Intent.ACTION_VIEW);
//            sIntent.putExtra(DetailExtras.MODEL, vehicle);
//
//        } else {
//            // will never happen
//            sIntent = new Intent();
//        }

        return sIntent;
    }

}
