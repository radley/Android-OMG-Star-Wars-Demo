package dev.radley.omgstarwars.ui;

import android.app.Activity;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.ui.search.category.fragments.FilmsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.PeopleFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.PlanetsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.SpeciesFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.StarshipsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.VehiclesFragment;


public class CategoryViewList {

    private static ArrayList<CategoryView> sCategories = new ArrayList<>();

    public static ArrayList getCategories(Activity activity) {

        if(sCategories.size() == 0) {

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_films),
                    activity.getString(R.string.category_films), new FilmsFragment()));

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_people),
                    activity.getString(R.string.category_people), new PeopleFragment()));

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_species),
                    activity.getString(R.string.category_species), new SpeciesFragment()));

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_starships),
                    activity.getString(R.string.category_starships), new StarshipsFragment()));

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_vehicles),
                    activity.getString(R.string.category_vehicles), new VehiclesFragment()));

            sCategories.add(new CategoryView(activity.getString(R.string.category_id_planets),
                    activity.getString(R.string.category_planets), new PlanetsFragment()));
        }

        return sCategories;
    }
}