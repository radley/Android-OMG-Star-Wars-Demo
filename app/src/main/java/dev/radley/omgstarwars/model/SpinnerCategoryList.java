package dev.radley.omgstarwars.model;

import android.app.Activity;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.fragment.FilmsFragment;
import dev.radley.omgstarwars.fragment.PeopleFragment;
import dev.radley.omgstarwars.fragment.PlanetsFragment;
import dev.radley.omgstarwars.fragment.SpeciesFragment;
import dev.radley.omgstarwars.fragment.StarshipsFragment;
import dev.radley.omgstarwars.fragment.VehiclesFragment;


public class SpinnerCategoryList {

    protected static ArrayList<SpinnerCategory> sCategories = new ArrayList<>();

    public static ArrayList getCategories(Activity activity) {

        if(sCategories.size() == 0) {

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_films),
                    activity.getString(R.string.category_films)));

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_people),
                    activity.getString(R.string.category_people)));

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_species),
                    activity.getString(R.string.category_species)));

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_starships),
                    activity.getString(R.string.category_starships)));

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_vehicles),
                    activity.getString(R.string.category_vehicles)));

            sCategories.add(new SpinnerCategory(activity.getString(R.string.category_id_planets),
                    activity.getString(R.string.category_planets)));
        }

        return sCategories;
    }
}