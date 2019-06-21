package dev.radley.omgstarwars.ui.search.spinner;

import android.app.Activity;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;


public class SpinnerCategoryList {

    private static ArrayList<SpinnerCategory> sCategories = new ArrayList<>();

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