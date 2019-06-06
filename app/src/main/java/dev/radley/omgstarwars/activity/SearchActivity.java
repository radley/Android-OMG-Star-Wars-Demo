package dev.radley.omgstarwars.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.fragment.BaseCategoryFragment;
import dev.radley.omgstarwars.model.SpinnerCategory;
import dev.radley.omgstarwars.model.SpinnerCategoryList;
import dev.radley.omgstarwars.model.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity implements BaseCategoryFragment.OnHeadlineSelectedListener {

    protected Activity mActivity;
    
    protected BaseCategoryFragment mCurrentFragment;
    protected SearchView mSearchView;
    protected SearchViewModel mViewModel;
    protected Spinner mSpinner;
    protected TextView mResultsText;
    protected Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mActivity = this;
        mViewModel = new SearchViewModel(this);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        
        if(intent.hasExtra(SearchExtras.QUERY)) {
            mViewModel.setQuery(intent.getStringExtra(SearchExtras.QUERY));
        }

        if(intent.hasExtra(SearchExtras.CATEGORY)) {
            mViewModel.setCategory(intent.getStringExtra(SearchExtras.CATEGORY));
        }

        mResultsText = (TextView) findViewById(R.id.results_text);
        initLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setQueryHint("Search " + mViewModel.getCategory() + "...");
        mSearchView.setQuery(mViewModel.getQuery(), false);
        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {

                // remove spaces and symbols
                final String newQuery = Util.getTrimmedQuery(query);

                mHandler.removeCallbacksAndMessages(null);

                mResultsText.setText("");

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(!mViewModel.getQuery().equals(newQuery)) {

                            mViewModel.setQuery(Util.getTrimmedQuery(query));
                            mResultsText.setText(getString(R.string.search_delay_message));
                            mCurrentFragment.getResultsFor(query);
                        }

                    }
                }, 500);

                return true;
            }
        });

        mSearchView.clearFocus();

        return true;
    }

    public void onResultUpdate(int count) {

        if(mViewModel.getQuery().length() < 2) {
            mResultsText.setText("");
        } else {
            mResultsText.setText(mViewModel.getResultsDescription(count));
        }
    }

    protected void initLayout() {

        ArrayList<SpinnerCategory> spinnerArray = SpinnerCategoryList.getCategories(this);
        ArrayAdapter<SpinnerCategory> adapter = new ArrayAdapter<SpinnerCategory>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setSelection(mViewModel.getCategoryIndex());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(!mViewModel.getCategory().equals(mViewModel.getCategories().get(position).getId())) {

                    mViewModel.setCategory(mViewModel.getCategories().get(position).getId());
                    mSearchView.clearFocus();
                    mResultsText.setText("");
                    loadCategoryFragment();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        loadCategoryFragment();
    }

    protected void hideKeyboard() {
        if (isKeybordShowing(this, this.getCurrentFocus())) {
            onBackPressed();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof BaseCategoryFragment) {
            BaseCategoryFragment headlinesFragment = (BaseCategoryFragment) fragment;
            headlinesFragment.setOnHeadlineSelectedListener(this);
        }
    }


    protected void loadCategoryFragment() {

        mCurrentFragment = mViewModel.getCategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString(SearchExtras.QUERY, mViewModel.getQuery());
        mCurrentFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, mCurrentFragment);
        ft.commit();
    }

    public boolean isKeybordShowing(Context context, View view) {
        try {
            InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return keyboard.isActive();
        } catch (Exception ex) {
            Log.e("keyboardHide", "cannot hide keyboard", ex);
            return false;
        }
    }

}
