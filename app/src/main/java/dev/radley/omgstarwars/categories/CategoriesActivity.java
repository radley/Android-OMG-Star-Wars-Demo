package dev.radley.omgstarwars.categories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.swapi.models.Film;
import com.swapi.models.People;
import com.swapi.models.Planet;
import com.swapi.models.SWModelList;
import com.swapi.models.Species;
import com.swapi.models.Starship;
import com.swapi.models.Vehicle;

import java.util.ArrayList;
import java.util.Timer;

import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.categories.fragments.BaseCategoryFragment;
import dev.radley.omgstarwars.categories.fragments.FilmsFragment;
import dev.radley.omgstarwars.categories.fragments.PeopleFragment;
import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.categories.fragments.PlanetsFragment;
import dev.radley.omgstarwars.categories.fragments.SpeciesFragment;
import dev.radley.omgstarwars.categories.fragments.StarshipsFragment;
import dev.radley.omgstarwars.categories.fragments.VehiclesFragment;
import dev.radley.omgstarwars.categories.model.Category;
import dev.radley.omgstarwars.data.OmgStarWarsApi;
import dev.radley.omgstarwars.detail.FilmActivity;
import dev.radley.omgstarwars.detail.PeopleActivity;
import dev.radley.omgstarwars.detail.PlanetActivity;
import dev.radley.omgstarwars.detail.SpeciesActivity;
import dev.radley.omgstarwars.detail.StarshipActivity;
import dev.radley.omgstarwars.detail.VehicleActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CategoriesActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected AppBarLayout mAppBarLayout;
    protected ArrayAdapter<String> mSearchAdapter;
    protected ArrayList<String> mSearchResultTitles;
    protected ArrayList<Object> mSearchResultItems;
    protected Handler mHandler = new Handler();
    protected int mPage;

    protected MenuItem mSearchItem;
    protected PagerAdapter mPagerAdapter;
    protected SearchView mSearchView;
    protected SearchView.SearchAutoComplete mSearchAutoComplete;
    protected String mCurrentCategory;
    protected String mSearchTerm;
    protected String mQueryString;
    protected TabLayout mTabLayout;
    protected ViewPager mPager;

    protected static ArrayList<Category> mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar, null));

        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActivity = this;

        OmgStarWarsApi.init();

        initCategories();
        initLayout();
        updateHeroImage(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_categories, menu);
        mSearchItem = (MenuItem) menu.findItem(R.id.action_search);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);

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
            Log.d(OmgSWUtil.getTag(), "mCategories.get(position) = " + mCategories.get(position));
            return mCategories.get(position).getCategoryFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategories.get(position).getName();
        }
    }


    protected void initCategories() {

        mCategories = new ArrayList<>();

        mCategories.add(new Category(getString(R.string.category_id_films),
                getString(R.string.category_films), new FilmsFragment()));

        mCategories.add(new Category(getString(R.string.category_id_people),
                getString(R.string.category_people), new PeopleFragment()));

        mCategories.add(new Category(getString(R.string.category_id_species),
                getString(R.string.category_species), new SpeciesFragment()));

        mCategories.add(new Category(getString(R.string.category_id_starships),
                getString(R.string.category_starships), new StarshipsFragment()));

        mCategories.add(new Category(getString(R.string.category_id_vehicles),
                getString(R.string.category_vehicles), new VehiclesFragment()));

        mCategories.add(new Category(getString(R.string.category_id_planets),
                getString(R.string.category_planets), new PlanetsFragment()));


        mCurrentCategory = getString(R.string.category_id_films);

    }

    protected void initLayout() {
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MyPagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateHeroImage(position);

                mCurrentCategory = mCategories.get(position).getId();
                resetSearch();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // Give the TabLayout the ViewPager
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager) {

            // scroll to top
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                BaseCategoryFragment fragment = (BaseCategoryFragment) mPager.getAdapter()
                        .instantiateItem(mPager, mPager.getCurrentItem());

                fragment.getRecyclerView().smoothScrollToPosition(0);
            }
        });

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                // only show Tabbar background color when extended (or close)
                if (Math.abs(verticalOffset) < 24) {
                    mTabLayout.setBackgroundColor(getApplicationContext().getColor(R.color.statusBar));

                } else {
                    mTabLayout.setBackground(null);
                }
            }
        });
    }

    public void updateHeroImage(int position) {

        String id = mCategories.get(position).getId();

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(false).build();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.hero_placeholder);

        ImageView imageView = (ImageView) findViewById(R.id.hero_image);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse("file:///android_asset/categories/" + id + ".jpg"))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    protected void initSearch() {

        mSearchResultTitles = new ArrayList<>();
        mSearchResultItems = new ArrayList<>();

        // add default hint
        mSearchView.setQueryHint("Search " + getString(R.string.category_id_films) + "...");

        // add background color when expanded
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSearchView.setBackgroundColor(getApplicationContext().getColor(R.color.transparentPrimary));
                } else {
                    mSearchView.setBackground(null);
                }
            }
        });

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
                }, 300);
                return true;
            }
        });


        // on auto-complete item click
        mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {

                final Intent intent = getDetailIntent(itemIndex);

                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                };

                Handler h = new Handler();
                h.postDelayed(r, 300);

                //mSearchView.setQuery("", true);
                //mSearchView.setIconified(true);
                //mSearchItem.collapseActionView();
            }
        });
    }

    protected Intent getDetailIntent(int index) {

        Intent intent;
        if (mCurrentCategory.equals((getString(R.string.category_id_films)))) {

            intent = new Intent(mActivity, FilmActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Film) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Film) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.tall_placeholder);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_people)))) {

            intent = new Intent(mActivity, PeopleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (People) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((People) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_people);


        } else if (mCurrentCategory.equals((getString(R.string.category_id_planets)))) {

            intent = new Intent(mActivity, PlanetActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Planet) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Planet) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_planet);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_species)))) {

            intent = new Intent(mActivity, SpeciesActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Species) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Species) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_species);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_starships)))) {

            intent = new Intent(mActivity, StarshipActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Starship) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Starship) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_starship);

        } else if (mCurrentCategory.equals((getString(R.string.category_id_vehicles)))) {

            intent = new Intent(mActivity, VehicleActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(DetailIntentUtil.RESOURCE, (Vehicle) mSearchResultItems.get(index));
            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage(mCurrentCategory,
                    ((Vehicle) mSearchResultItems.get(index)).url));
            intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_vehicle);

        } else {
            // won't happen
            intent = new Intent();
        }

        return intent;
    }


    protected void resetSearch() {
        mSearchResultTitles = new ArrayList<>();
        mSearchResultItems = new ArrayList<>();

        //mSearchItem.collapseActionView();
        //mSearchView.setIconified(true);

        // add default hint
        mSearchView.setQueryHint("Search " + mCurrentCategory + "...");
    }

    protected void onSearchTextChangeDelay(String searchTerm) {

        mSearchResultTitles.removeAll(mSearchResultTitles);
        mSearchResultItems.removeAll(mSearchResultItems);
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
        mSearchAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, mSearchResultTitles);
        mSearchAutoComplete.setAdapter(mSearchAdapter);
    }

    protected void searchFilms() {

        OmgStarWarsApi.getApi().searchFilms(mPage, mSearchTerm, new Callback<SWModelList<Film>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onFilmSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onFilmSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((Film) object));
            mSearchResultTitles.add(((Film) object).title);
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

        OmgStarWarsApi.getApi().searchPeople(mPage, mSearchTerm, new Callback<SWModelList<People>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onPeopleSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onPeopleSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((People) object));
            mSearchResultTitles.add(((People) object).name);
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

        OmgStarWarsApi.getApi().searchPlanets(mPage, mSearchTerm, new Callback<SWModelList<Planet>>() {

            @Override
            public void success(SWModelList list, Response response) {

                onPlanetsSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onPlanetsSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((Planet) object));
            mSearchResultTitles.add(((Planet) object).name);
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

        OmgStarWarsApi.getApi().searchSpecies(mPage, mSearchTerm, new Callback<SWModelList<Species>>() {

            @Override
            public void success(SWModelList list, Response response) {

                onSpeciesSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onSpeciesSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((Species) object));
            mSearchResultTitles.add(((Species) object).name);
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

        OmgStarWarsApi.getApi().searchStarships(mPage, mSearchTerm, new Callback<SWModelList<Starship>>() {

            @Override
            public void success(SWModelList list, Response response) {

                onStarshipsSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onStarshipsSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((Starship) object));
            mSearchResultTitles.add(((Starship) object).name);
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

        OmgStarWarsApi.getApi().searchVehicles(mPage, mSearchTerm, new Callback<SWModelList<Vehicle>>() {

            @Override
            public void success(SWModelList list, Response response) {

                onVehiclesSearchSuccess(list, response);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
            }
        });
    }

    protected void onVehiclesSearchSuccess(SWModelList list, Response response) {

        for (Object object : list.results) {
            mSearchResultItems.add(((Vehicle) object));
            mSearchResultTitles.add(((Vehicle) object).name);
        }

        if(list.next != null) {
            mPage++;
            searchVehicles();
        } else {
            populateSearchList();
        }
    }
}
