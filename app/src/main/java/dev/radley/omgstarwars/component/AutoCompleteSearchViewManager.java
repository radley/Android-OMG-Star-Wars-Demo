package dev.radley.omgstarwars.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.util.Util;
import dev.radley.omgstarwars.ui.search.SearchActivity;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.network.model.SWModel;
import timber.log.Timber;


public class AutoCompleteSearchViewManager extends SearchViewManager {

    protected ArrayAdapter<String> mAutoCompleteAdapter;
    protected ArrayList<String> mResultTitles;
    protected SearchView.SearchAutoComplete mSearchAutoComplete;


    public AutoCompleteSearchViewManager(Activity activity, SearchView searchView) {

        super(activity, searchView);
        mResultTitles = new ArrayList<String>();
    }

    @Override
    public void init(String category) {

        mCategory = category;
        mSearchView.setQueryHint("Search " + mCategory + "...");

        // add background color to searchView while expanded
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSearchView.setBackgroundColor(mActivity.getColor(R.color.transparentPrimary));
                } else {
                    mSearchView.setBackground(null);
                }
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                final Intent intent = new Intent(mActivity, SearchActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(SearchExtras.QUERY, Util.getTrimmedQuery(query));
                intent.putExtra(SearchExtras.CATEGORY, mCategory);

                Bundle bundle = new Bundle();
                bundle.putSerializable(SearchExtras.RESULT_LIST, (Serializable) mResultItems);
                intent.putExtras(bundle);

                mSearchView.setQuery("", false);
                mSearchView.setIconified(true);

                mActivity.startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                mQuery = Util.getTrimmedQuery(query);

                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSearchLoop();
                    }
                }, 500);

                return true;
            }
        });

        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        mSearchAutoComplete.setThreshold(1);
    }

    protected void initAutoCompleteList() {

        Timber.d("initAutoCompleteList()");

        mAutoCompleteAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mResultTitles);
        mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);
        mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {

                onSearchItemClick(itemIndex);
            }
        });
    }

    @Override
    public void updateCategory(String category) {

        super.updateCategory(category);
        mResultTitles.clear();

        if(mAutoCompleteAdapter != null)
            mAutoCompleteAdapter.notifyDataSetChanged();
    }

    protected void onSearchItemClick(int index) {

        Intent intent = DetailExtras.getIntent(mActivity, (SWModel) mResultItems.get(index));

        mSearchView.setQuery("", false);

        // bug in AndroidX - icon won't revert to right side
        // leftover bug from original searchView, was fixed in appCompat version
        mSearchView.setIconified(true);

        mActivity.startActivity(intent);
    }

    @Override
    protected void clearSearchResults() {

        mResultTitles.clear();
        mResultItems.clear();

        if(mAutoCompleteAdapter != null)
            mAutoCompleteAdapter.notifyDataSetChanged();

        // notifyDataSetChanged doesn't always work
        //mAutoCompleteAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mResultTitles);
        //mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);
    }


    @Override
    protected void onSearchLoopComplete() {

        Timber.d("onSearchLoopComplete()");

        mResultTitles.clear();

        if(mResultItems.size() > 0) {
            for (Object object : mResultItems) {
                mResultTitles.add(((SWModel) object).getTitle());
            }
        }

        if(mAutoCompleteAdapter != null) {

            // notifyDataSetChanged doesn't always work
            mAutoCompleteAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mResultTitles);
            mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);

        } else {
            initAutoCompleteList();
        }
    }
}
