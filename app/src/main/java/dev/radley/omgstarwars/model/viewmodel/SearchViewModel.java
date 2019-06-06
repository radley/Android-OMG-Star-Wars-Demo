package dev.radley.omgstarwars.model.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.fragment.BaseCategoryFragment;
import dev.radley.omgstarwars.fragment.FilmsFragment;
import dev.radley.omgstarwars.fragment.PeopleFragment;
import dev.radley.omgstarwars.fragment.PlanetsFragment;
import dev.radley.omgstarwars.fragment.SpeciesFragment;
import dev.radley.omgstarwars.fragment.StarshipsFragment;
import dev.radley.omgstarwars.fragment.VehiclesFragment;
import dev.radley.omgstarwars.model.SpinnerCategory;

public class SearchViewModel extends ViewModel {

    protected String mCategory;
    protected String mQuery;
    protected Context mContext;
    protected ArrayList<Object> mResultList;
    public MutableLiveData<String> mQueryString = new MutableLiveData<String>();

    protected ArrayList<SpinnerCategory> mCategories;

    public SearchViewModel(Context context) {
        super();

        mContext = context;
        mCategories = new ArrayList<SpinnerCategory>();

        initCategories();
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCategory() {
        return mCategory;
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
        if(count >= 0)
            return mContext.getResources().getQuantityString(R.plurals.result_count, count, count, mQuery);

        return mContext.getResources().getString(R.string.search_error);
    }

    public int getCategoryIndex() {

        int i;

        for (i = 0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getId().equals(mCategory))
                break;
        }

        return i;
    }

    public BaseCategoryFragment getCategoryFragment() {

        BaseCategoryFragment fragment;
        
        if (mCategory.equals((mContext.getString(R.string.category_id_films)))) {
            fragment = new FilmsFragment();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_people)))) {
            fragment = new PeopleFragment();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_planets)))) {
            fragment = new PlanetsFragment();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_species)))) {
            fragment = new SpeciesFragment();

        } else if (mCategory.equals((mContext.getString(R.string.category_id_starships)))) {
            fragment = new StarshipsFragment();

        } else {
            fragment = new VehiclesFragment();
        }

        return fragment;
    }

    public ArrayList<SpinnerCategory> getCategories() {
        return mCategories;
    }

    protected void initCategories() {

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_films),
                mContext.getString(R.string.category_films)));

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_people),
                mContext.getString(R.string.category_people)));

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_species),
                mContext.getString(R.string.category_species)));

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_starships),
                mContext.getString(R.string.category_starships)));

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_vehicles),
                mContext.getString(R.string.category_vehicles)));

        mCategories.add(new SpinnerCategory(mContext.getString(R.string.category_id_planets),
                mContext.getString(R.string.category_planets)));

    }
}
