package dev.radley.omgstarwars.component;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class SWSearchView extends SearchView {


    private String mCategory;
    private String mQuery;

    private ArrayList<Object> mResultItems = new ArrayList<>();
    private Handler mDelayHandler = new Handler();

    public SWSearchView(Context context) {
        super(context);
    }

    public void updateCategory(String category) {

        mCategory = category;
        mResultItems.clear();

        setQueryHint("Search " + mCategory + "...");
        setQuery("", false);
    }

    public String getQuery() {
        return mQuery;
    }

    public void init(String category, String query) {

        init(category);
        mQuery = query;
        doSearch();
    }

    public void init(String category) {

        mCategory = category;

        setQueryHint("Search " + mCategory + "...");
        setIconified(false);
        setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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
                        doSearch();
                    }
                }, 500);

                return true;

            }
        });
    }

    private void doSearch() {

    }
}
