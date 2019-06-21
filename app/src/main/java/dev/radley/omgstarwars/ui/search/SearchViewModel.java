package dev.radley.omgstarwars.ui.search;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.ui.search.category.fragments.BaseCategoryFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.FilmsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.PeopleFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.PlanetsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.SpeciesFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.StarshipsFragment;
import dev.radley.omgstarwars.ui.search.category.fragments.VehiclesFragment;

public class SearchViewModel extends ViewModel {

    private ArrayList<Object> mResultList;
    private Context mContext;
    private String mCategory;
    private String mQuery;
    private String[] mCategoryIds;
    private String[] mCategoryTitles;

    public MutableLiveData<String> mQueryString = new MutableLiveData<String>();



    public SearchViewModel(Context context) {
        super();

        mContext = context;
        mCategoryIds = mContext.getResources().getStringArray(R.array.category_ids);
        mCategoryTitles = mContext.getResources().getStringArray(R.array.category_titles);
        mCategory = mCategoryIds[0];
    }


    public void setCurrentCategory(String category) {
        mCategory = category;
    }

    public String getCurrentCategory() {
        return mCategory;
    }

    public int getCurrentCategoryPosition() {

        for (int i = 0; i < mCategoryIds.length; i++)
            if (mCategoryIds[i].equals(mCategory)) {
                return i;
            }
        return -1;
    }

    public String[] getCategoryTitles() {
        return mCategoryTitles;
    }

    public String getCurrentCategory(int position) {
        return mCategoryIds[position];
    }

    public void setQuery(String query) {

        mQuery = query;
    }

    public String getQuery() {
        return mQuery;
    }


    public void setResultList(ArrayList<Object> list) {
        mResultList = list;
    }

    public ArrayList<Object> getResultList() {
        return mResultList;
    }

    public String getResultsDescription(int count) {
        if (count >= 0)
            return mContext.getResources().getQuantityString(R.plurals.result_count, count, count, mQuery);

        return mContext.getResources().getString(R.string.search_error);
    }

    public BaseCategoryFragment getCategoryFragment() {

        if (mCategory.equals((mContext.getString(R.string.category_id_films)))) {
            return FilmsFragment.newInstance();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_people)))) {
            return PeopleFragment.newInstance();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_planets)))) {
            return PlanetsFragment.newInstance();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_species)))) {
            return SpeciesFragment.newInstance();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_starships)))) {
            return StarshipsFragment.newInstance();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_vehicles)))) {
            return VehiclesFragment.newInstance();
        }

        return null;
    }
}
