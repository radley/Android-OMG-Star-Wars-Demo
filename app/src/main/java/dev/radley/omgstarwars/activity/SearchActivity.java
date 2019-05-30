package dev.radley.omgstarwars.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.Util.SearchIntentUtil;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;
import dev.radley.omgstarwars.network.OmgStarWarsApi;
import dev.radley.omgstarwars.fragment.FilmsFragment;
import dev.radley.omgstarwars.fragment.PeopleFragment;
import dev.radley.omgstarwars.fragment.PlanetsFragment;
import dev.radley.omgstarwars.fragment.SpeciesFragment;
import dev.radley.omgstarwars.fragment.StarshipsFragment;
import dev.radley.omgstarwars.fragment.VehiclesFragment;
import dev.radley.omgstarwars.model.CategoryView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

public class SearchActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected AppBarLayout mAppBarLayout;
    protected ArrayAdapter<String> mSearchAdapter;
    protected ArrayList<Object> mResultList;
    protected Handler mHandler = new Handler();
    protected int mPage;

    protected SearchView mSearchView;
    protected Spinner mSpinner;
    protected String mCurrentCategory;
    protected String mSearchTerm;
    protected String mQueryString;
    protected TextView mResultsText;

    protected static ArrayList<CategoryView> mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mActivity = this;

        Intent intent = getIntent();
        mQueryString = intent.getStringExtra(SearchIntentUtil.QUERY);
        mCurrentCategory = intent.getStringExtra(SearchIntentUtil.CATEGORY);

        Bundle bundle = getIntent().getExtras();
        mResultList = (ArrayList<Object>) bundle.getSerializable(SearchIntentUtil.RESULT_LIST);

        mResultsText = (TextView) findViewById(R.id.results_text);

        // complicated enough?
        String myString = getResources().getQuantityString(R.plurals.result_count, mResultList.size(), mResultList.size(), mQueryString);
        mResultsText.setText(Html.fromHtml(myString));

        OmgStarWarsApi.init();

        initCategories();
        initLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconified(false);

        initSearch();
        return true;
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public MyPagerAdapter(Context context, FragmentManager fragmentManager) {
            super(fragmentManager);
            mContext = context;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(OmgSWUtil.getTag(), "sCategories.get(position) = " + mCategories.get(position));
            return mCategories.get(position).getCategoryFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }
    }


    protected void initCategories() {

        mCategories = new ArrayList<>();

        mCategories.add(new CategoryView(getString(R.string.category_id_films),
                getString(R.string.category_films), new FilmsFragment()));

        mCategories.add(new CategoryView(getString(R.string.category_id_people),
                getString(R.string.category_people), new PeopleFragment()));

        mCategories.add(new CategoryView(getString(R.string.category_id_species),
                getString(R.string.category_species), new SpeciesFragment()));

        mCategories.add(new CategoryView(getString(R.string.category_id_starships),
                getString(R.string.category_starships), new StarshipsFragment()));

        mCategories.add(new CategoryView(getString(R.string.category_id_vehicles),
                getString(R.string.category_vehicles), new VehiclesFragment()));

        mCategories.add(new CategoryView(getString(R.string.category_id_planets),
                getString(R.string.category_planets), new PlanetsFragment()));

        List<String> spinnerArray =  new ArrayList<String>();

        for (CategoryView categoryView: mCategories) {
            spinnerArray.add(categoryView.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setSelection(getCategoryIndexFromId(mCurrentCategory));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {

                Log.i("renderSpinner -> ", "onItemSelected: " + myPosition + "/" + myID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        initSearch();
    }

    protected int getCategoryIndexFromId(String id) {

        int i;

        for (i=0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getId().equals(id))
                break;
        }

        return i;
    }

    protected int getCategoryIdFromTitle(String id) {

        int i;

        for (i=0; i < mCategories.size(); i++) {
            if (mCategories.get(i).getId().equals(id))
                break;
        }

        return i;
    }

    protected void initLayout() {

    }

    protected void initSearch() {

        if(mCurrentCategory == null || mSearchView == null) {
            return;
        }

        // add default hint
        mSearchView.setQueryHint("Search " + mCurrentCategory + "...");
        mSearchView.setQuery(mQueryString, false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // add query update delay
            Timer timer = new Timer();

            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d(OmgSWUtil.getTag(), "setOnQueryTextListener: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {

                mQueryString = searchTerm;

                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        onSearchTextChangeDelay(mQueryString);
                    }
                }, getResources().getInteger(R.integer.search_delay_ms));
                return true;
            }
        });

        //stop search view from collapsing (i.e. cheap hack)
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true; //returning true will stop search view from collapsing
            }
        });
    }

    protected void resetSearch() {
        mResultList = new ArrayList<>();
        mSearchView.setQueryHint("Search " + mCurrentCategory + "...");
        mSearchView.setQuery("", false);
    }

    protected Intent getDetailIntent(int index) {

        Intent intent;
        if (mCurrentCategory.equals((getString(R.string.category_id_films)))) {

            intent = new Intent(mActivity, FilmActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Film) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Film) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.tall_placeholder);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_people)))) {

            intent = new Intent(mActivity, PeopleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (People) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((People) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_people);


        } else if (mCurrentCategory.equals((getString(R.string.category_id_planets)))) {

            intent = new Intent(mActivity, PlanetActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Planet) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Planet) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_planet);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_species)))) {

            intent = new Intent(mActivity, SpeciesActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Species) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Species) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_species);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_starships)))) {

            intent = new Intent(mActivity, StarshipActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Starship) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Starship) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_starship);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_vehicles)))) {

            intent = new Intent(mActivity, VehicleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Vehicle) mResultList.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Vehicle) mResultList.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_vehicle);

        } else {
            // won't happen
            intent = new Intent();
        }

        return intent;
    }

    protected void onSearchTextChangeDelay(String searchTerm) {

        mResultList.removeAll(mResultList);
        mPage = 1;
        mSearchTerm = searchTerm;

        // SW API only searches within categories
        if (mCurrentCategory.equals((getString(R.string.category_id_films)))) {
            searchFilms();

        } else if (mCurrentCategory.equals((getString(R.string.category_id_people)))) {
            searchPeople();

        } else if (mCurrentCategory.equals((getString(R.string.category_id_planets)))) {
            searchPlanets();

        } else if (mCurrentCategory.equals((getString(R.string.category_id_species)))) {
            searchSpecies();

        } else if (mCurrentCategory.equals((getString(R.string.category_id_starships)))) {
            searchStarships();

        } else if (mCurrentCategory.equals((getString(R.string.category_id_vehicles)))) {
            searchVehicles();
        }
    }

    protected void populateSearchList() {

    }

    protected void searchFilms() {

        Call<SWModelList<Film>> call = OmgStarWarsApi.getApi().searchFilms(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onFilmSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onFilmSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((Film) object));
        }

        if(list.next != null) {
            mPage++;
            searchFilms();
        } else {
            populateSearchList();
        }
    }

    protected void searchPeople() {

        final ArrayList<String> data = new ArrayList<String>();

        Call<SWModelList<People>> call = OmgStarWarsApi.getApi().searchPeople(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {
                onPeopleSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onPeopleSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((People) object));
        }

        if(list.next != null) {
            mPage++;
            searchPeople();
        } else {
            populateSearchList();
        }
    }

    protected void searchPlanets() {

        final ArrayList<String> data = new ArrayList<String>();

        Call<SWModelList<Planet>> call = OmgStarWarsApi.getApi().searchPlanets(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {
                onPlanetsSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onPlanetsSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((Planet) object));
        }

        if(list.next != null) {
            mPage++;
            searchPlanets();
        } else {
            populateSearchList();
        }
    }

    protected void searchSpecies() {

        final ArrayList<String> data = new ArrayList<String>();

        Call<SWModelList<Species>> call = OmgStarWarsApi.getApi().searchSpecies(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {
                onSpeciesSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onSpeciesSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((Species) object));
        }

        if(list.next != null) {
            mPage++;
            searchSpecies();
        } else {
            populateSearchList();
        }
    }

    protected void searchStarships() {

        final ArrayList<String> data = new ArrayList<String>();

        Call<SWModelList<Starship>> call = OmgStarWarsApi.getApi().searchStarships(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {
                onStarshipsSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onStarshipsSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((Starship) object));
        }

        if(list.next != null) {
            mPage++;
            searchStarships();
        } else {
            populateSearchList();
        }
    }

    protected void searchVehicles() {

        final ArrayList<String> data = new ArrayList<String>();

        Call<SWModelList<Vehicle>> call = OmgStarWarsApi.getApi().searchVehicles(mPage, mSearchTerm);
        call.enqueue(new retrofit2.Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {
                onVehiclesSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onVehiclesSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mResultList.add(((Vehicle) object));
        }

        if(list.next != null) {
            mPage++;
            searchVehicles();
        } else {
            populateSearchList();
        }
    }
}
