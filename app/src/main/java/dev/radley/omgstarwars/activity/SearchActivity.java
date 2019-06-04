package dev.radley.omgstarwars.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.bundle.SearchIntentUtil;
import dev.radley.omgstarwars.component.SearchViewManager;
import dev.radley.omgstarwars.fragment.BaseCategoryFragment;
import dev.radley.omgstarwars.fragment.FilmsFragment;
import dev.radley.omgstarwars.fragment.PeopleFragment;
import dev.radley.omgstarwars.fragment.PlanetsFragment;
import dev.radley.omgstarwars.fragment.SpeciesFragment;
import dev.radley.omgstarwars.fragment.StarshipsFragment;
import dev.radley.omgstarwars.fragment.VehiclesFragment;
import dev.radley.omgstarwars.model.SpinnerCategory;
import dev.radley.omgstarwars.model.SpinnerCategoryList;

public class SearchActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected AppBarLayout mAppBarLayout;
    protected ArrayAdapter<String> mSearchAdapter;
    protected ArrayList<SpinnerCategory> mCategories;
    protected ArrayList<Object> mResultList;
    protected BaseCategoryFragment mCurrentFragment;
    protected SearchViewManager mSearchViewManager;
    protected Spinner mSpinner;
    protected String mCategory;
    protected String mQuery;
    protected TextView mResultsText;



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

        Intent intent = getIntent();
        mQuery = intent.getStringExtra(SearchIntentUtil.QUERY);
        mCategory = intent.getStringExtra(SearchIntentUtil.CATEGORY);

        Bundle bundle = getIntent().getExtras();
        mResultList = (ArrayList<Object>) bundle.getSerializable(SearchIntentUtil.RESULT_LIST);

        mResultsText = (TextView) findViewById(R.id.results_text);
        String myString = getResources().getQuantityString(R.plurals.result_count, mResultList.size(), mResultList.size(), mQuery);
        mResultsText.setText(myString);

        initCategories();
        initLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchViewManager = new SearchViewManager(this, searchView);

        mSearchViewManager.addSearchViewListener(new SearchViewManager.SearchViewListener() {


            @Override
            public void onObjectReady(String title) {
                // Code to handle object ready
            }

            @Override
            public void onClearSearchResults() {
                mResultsText.setText("");
                mCurrentFragment.clear();
            }

            @Override
            public void onLoadComplete(ArrayList<Object> data, String query) {

                mResultList = data;
                mQuery = query;

                Log.d(OmgSWUtil.tag, "onLoadComplete()");
                Log.d(OmgSWUtil.tag, "mQuery: " +mQuery);
                Log.d(OmgSWUtil.tag, "mResultList: " +mResultList);

                String myString = getResources().getQuantityString(R.plurals.result_count, mResultList.size(), mResultList.size(), mQuery);
                mResultsText.setText(myString);

                mCurrentFragment.updateList(mResultList);
            }
        });

        mSearchViewManager.init(mCategory);
        searchView.setQuery(mQuery, false);

        return true;
    }

    protected void initCategories() {

        mCategories = SpinnerCategoryList.getCategories(this);
    }


    protected void initLayout() {

        ArrayList<SpinnerCategory> spinnerArray = SpinnerCategoryList.getCategories(this);
        ArrayAdapter<SpinnerCategory> adapter = new ArrayAdapter<SpinnerCategory>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setSelection(getCategoryIndexFromId(mCategory));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(!mCategory.equals(mCategories.get(position).getId())) {
                    mCategory = mCategories.get(position).getId();
                    mSearchViewManager.updateCategory(mCategory);
                    mResultList.clear();
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

    protected void loadCategoryFragment() {


        // SW API only searches within categories
        if (mCategory.equals((mActivity.getString(R.string.category_id_films)))) {
            mCurrentFragment = new FilmsFragment();

        } else if (mCategory.equals((mActivity.getString(R.string.category_id_people)))) {
            mCurrentFragment = new PeopleFragment();

        } else if (mCategory.equals((mActivity.getString(R.string.category_id_planets)))) {
            mCurrentFragment = new PlanetsFragment();

        } else if (mCategory.equals((mActivity.getString(R.string.category_id_species)))) {
            mCurrentFragment = new SpeciesFragment();

        } else if (mCategory.equals((mActivity.getString(R.string.category_id_starships)))) {
            mCurrentFragment = new StarshipsFragment();

        } else if (mCategory.equals((mActivity.getString(R.string.category_id_vehicles)))) {
            mCurrentFragment = new VehiclesFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(SearchIntentUtil.CATEGORY, mCategory);
        bundle.putSerializable(SearchIntentUtil.RESULT_LIST, mResultList);
        mCurrentFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, mCurrentFragment);
        ft.commit();
    }


    protected int getCategoryIndexFromId(String id) {

        int i;

        for (i = 0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getId().equals(id))
                break;
        }

        return i;
    }

    protected int getCategoryIdFromTitle(String id) {

        int i;

        for (i = 0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getId().equals(id))
                break;
        }

        return i;
    }
}
