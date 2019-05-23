package dev.radley.omgstarwars.categories;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.swapi.models.Planet;

import java.util.ArrayList;
import java.util.Timer;

import dev.radley.omgstarwars.Util.SWUtil;
import dev.radley.omgstarwars.categories.fragments.CategoryFragment;
import dev.radley.omgstarwars.categories.fragments.FilmsFragment;
import dev.radley.omgstarwars.categories.fragments.PeopleFragment;
import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.categories.fragments.PlanetsFragment;
import dev.radley.omgstarwars.categories.fragments.SpeciesFragment;
import dev.radley.omgstarwars.categories.fragments.StarshipsFragment;
import dev.radley.omgstarwars.categories.fragments.VehiclesFragment;
import dev.radley.omgstarwars.categories.model.Category;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CategoriesActivity extends AppCompatActivity {

    private ArrayList<Planet> mPlanetList;

    protected AppBarLayout mAppBarLayout;
    protected Handler mHandler = new Handler();
    protected PagerAdapter mPagerAdapter;
    protected SearchView mSearchView;
    protected SearchView.SearchAutoComplete mSearchAutoComplete;
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
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar,null));

        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initCategories();
        initLayout();
        updateHeroImage(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_categories, menu);
        mSearchView = (SearchView) menu.findItem( R.id.action_search).getActionView();
        mSearchView.setQueryHint("Search for " + getResources().getString(R.string.category_id_films) +"...");
        //mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        initSearch();
        return true;
    }



    protected void initSearch() {

        // needs background color
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

        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {

            Timer timer = new Timer();

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {

                mQueryString = searchTerm;
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Put your call to the server here (with mQueryString)
                    }
                }, 300);
                return true;
            }
        });
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
            Log.d(SWUtil.getTag(), "mCategories.get(position) = " + mCategories.get(position));
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

        mCategories.add(new Category(getString(R.string.category_id_planets),
                getString(R.string.category_planets), new PlanetsFragment()));

        mCategories.add(new Category(getString(R.string.category_id_species),
                getString(R.string.category_species), new SpeciesFragment()));

        mCategories.add(new Category(getString(R.string.category_id_starships),
                getString(R.string.category_starships), new StarshipsFragment()));

        mCategories.add(new Category(getString(R.string.category_id_vehicles),
                getString(R.string.category_vehicles), new VehiclesFragment()));

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
                mSearchView.setQueryHint("Search for " + mCategories.get(position).getId() +"...");
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
                CategoryFragment fragment = (CategoryFragment) mPager.getAdapter()
                        .instantiateItem(mPager, mPager.getCurrentItem());

                fragment.getRecyclerView().smoothScrollToPosition(0);
            }
        });

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0) {
                    mTabLayout.setBackground(null);
                } else {
                    mTabLayout.setBackgroundColor(getApplicationContext().getColor(R.color.statusBar));
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
                .load(Uri.parse("file:///android_asset/categories/" +id+ ".jpg"))
                .transition(withCrossFade(factory))
                .into(imageView);
    }

}
