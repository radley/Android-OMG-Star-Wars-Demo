package dev.radley.omgstarwars.component;

import android.app.Activity;
import android.os.Handler;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class SWSearchViewMgr {

    protected Activity mActivity;


    protected ArrayList<Object> mResultItems;
    protected Handler mDelayHandler = new Handler();
    protected int mPage;
    protected SearchView mSearchView;

    protected String mCategory;
    protected String mQuery;

    private SearchViewListener listener;


    public SWSearchViewMgr(Activity activity, SearchView searchView) {

        mActivity = activity;
        mSearchView = searchView;


        mResultItems = new ArrayList<>();

        this.listener = null;
    }


    public interface SearchViewListener {

        public void onObjectReady(String title);
        public void onClearSearchResults();
        public void onLoadComplete(ArrayList<Object> data, String query);
    }

    public void addSearchViewListener(SearchViewListener listener) {
        this.listener = listener;
    }

    public void updateCategory(String category) {

        mCategory = category;
        mResultItems.clear();

        mSearchView.setQueryHint("Search " + mCategory + "...");
        mSearchView.setQuery("", false);
    }

    public void init(String category) {

        mCategory = category;

        mSearchView.setQueryHint("Search " + mCategory + "...");
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                mQuery = query;

                mDelayHandler.removeCallbacksAndMessages(null);

                mDelayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSearchLoop();
                    }
                }, 500);

                return true;

            }
        });
    }


    protected void startSearchLoop() {

        mPage = 1;

//        // SW API only searches within categories
//        if (mCategory.equals((mActivity.getString(R.string.category_id_films)))) {
//            searchFilms();
//
//        } else if (mCategory.equals((mActivity.getString(R.string.category_id_people)))) {
//            searchPeople();
//
//        } else if (mCategory.equals((mActivity.getString(R.string.category_id_planets)))) {
//            searchPlanets();
//
//        } else if (mCategory.equals((mActivity.getString(R.string.category_id_species)))) {
//            searchSpecies();
//
//        } else if (mCategory.equals((mActivity.getString(R.string.category_id_starships)))) {
//            searchStarships();
//
//        } else if (mCategory.equals((mActivity.getString(R.string.category_id_vehicles)))) {
//            searchVehicles();
//        }
    }

    protected void clearSearchResults() {

        mResultItems.clear();
        listener.onClearSearchResults();
    }

    protected void onSearchLoopComplete() {

        listener.onLoadComplete(mResultItems, mQuery);
    }
}
