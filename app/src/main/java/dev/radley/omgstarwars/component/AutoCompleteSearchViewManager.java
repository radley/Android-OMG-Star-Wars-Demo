package dev.radley.omgstarwars.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.SearchView;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.activity.SearchActivity;
import dev.radley.omgstarwars.bundle.DetailIntentUtil;
import dev.radley.omgstarwars.bundle.SearchIntentUtil;
import dev.radley.omgstarwars.model.sw.SWModel;

public class AutoCompleteSearchViewManager extends SearchViewManager {

    protected ArrayAdapter<String> mAutoCompleteAdapter;
    protected SearchView.SearchAutoComplete mSearchAutoComplete;


    public AutoCompleteSearchViewManager(Activity activity, SearchView searchView) {

        super(activity, searchView);

    }

    @Override
    public void init(String category) {

        mCategory = category;

        // add background color when expanded
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
                intent.putExtra(SearchIntentUtil.QUERY, query);
                intent.putExtra(SearchIntentUtil.CATEGORY, mCategory);

                Bundle bundle = new Bundle();
                bundle.putSerializable(SearchIntentUtil.RESULT_LIST, (Serializable) mResultItems);
                intent.putExtras(bundle);

                mSearchView.setQuery("", false);
                mSearchView.setIconified(true);

                mActivity.startActivity(intent);

                Log.d(OmgSWUtil.tag, "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {

                mQuery = searchTerm;

                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSearchLoop();
                    }
                }, 300);

                return true;

            }
        });

        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        mAutoCompleteAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mResultTitles);
        mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);

        // on auto-complete item click
        mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {

                onSearchItemClick(itemIndex);
            }
        });
    }

    protected void onSearchItemClick(int index) {

        Intent intent = DetailIntentUtil.getIntent(mActivity, mCategory, (SWModel) mResultItems.get(index));

        mSearchView.setQuery("", false);
        mSearchView.setIconified(true);

        mActivity.startActivity(intent);
    }

    @Override
    protected void clearSearchResults() {

        if(mQuery.length() < 2) {
            mResultTitles.clear();
            mResultItems.clear();

            mAutoCompleteAdapter.clear();
            mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);
        }
    }


    @Override
    protected void onSearchLoopComplete() {

        // HACK: SearchAutoComplete expects to filter a single list, not display many pre-filtered lists.
        // This mean we have to update it manually each time...
        // also means it might not update consistently.

        Log.d(OmgSWUtil.tag, "onSearchLoopComplete()");

        mAutoCompleteAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mResultTitles);
        mSearchAutoComplete.setAdapter(mAutoCompleteAdapter);
    }
}
