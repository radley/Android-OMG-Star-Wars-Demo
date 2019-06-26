package dev.radley.omgstarwars.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.utilities.Constants;
import dev.radley.omgstarwars.viewmodels.CategoriesViewModel;
import dev.radley.omgstarwars.utilities.FormatUtils;

public class CategoriesActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private AppBarLayout.OnOffsetChangedListener appBarOffsetListener;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private TabLayout tabLayout;
    private TabLayout.ViewPagerOnTabSelectedListener tabListener;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener pageChangeListener;

    private static CategoriesViewModel viewModel;

    /**
     * Instatiate layouts and viewModel
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories);
        setupToolbar();

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);

        setupLayout();
    }

    /**
     * Start listeners
     */
    @Override
    public void onStart() {
        super.onStart();

        // add listeners
        viewPager.addOnPageChangeListener(pageChangeListener);
        tabLayout.addOnTabSelectedListener(tabListener);
        appBarLayout.addOnOffsetChangedListener(appBarOffsetListener);
    }

    /**
     * Stop listeners
     */
    @Override
    public void onStop() {
        super.onStop();

        // remove listeners
        viewPager.removeOnPageChangeListener(pageChangeListener);
        tabLayout.removeOnTabSelectedListener(tabListener);
        appBarLayout.removeOnOffsetChangedListener(appBarOffsetListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_categories, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        return true;
    }

    /**
     * Setup searchview in toolbar
     */
    private void setupSearchView() {

        // add background color to searchView, only while expanded
        // (set this before calling first setIconified() so initial focus is established)
        //
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    searchView.setBackgroundColor(getColor(R.color.transparentPrimary));
                } else {
                    searchView.setBackground(null);
                }
            }
        });

        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                // open search activity on submit
                startSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                // TODO could do autocomplete results
                // but that's a bit much for this exercise

                return true;
            }
        });
    }

    /**
     * setup toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    /**
     * Tell viewModel category has changed
     * update searchView hint and dispose any query text
     *
     * @param position
     */
    private void updateCategory(int position) {

        viewModel.setCategory(position);
        updateHeroImage();

        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setQuery("", false);
    }

    /**
     * Instantiate views
     */
    private void setupLayout() {

        // setup view pager
        viewPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        pageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateCategory(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };

        // setup tabs
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

            // scroll to top on tab double-tap
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                CategoryFragment fragment = (CategoryFragment) Objects.requireNonNull(viewPager.getAdapter())
                        .instantiateItem(viewPager, viewPager.getCurrentItem());

                fragment.getRecyclerView().smoothScrollToPosition(0);
            }
        };

        // setup parallax hero header
        appBarLayout = findViewById(R.id.app_bar);
        appBarOffsetListener = (appBarLayout, verticalOffset) -> {

            // show background onionskin behind tabs when app bar is extended
            if (Math.abs(verticalOffset) < 24) {
                tabLayout.setBackgroundColor(getApplicationContext().getColor(R.color.transparentPrimaryDark));

            } else {
                tabLayout.setBackground(null);
            }
        };

        updateHeroImage();
    }

    /**
     * Clear searchView and start SearchActivity with query & category
     *
     * @param query String
     */
    private void startSearchActivity(String query) {

        searchView.setQuery("", false);
        searchView.setIconified(true);

        startActivity(SearchExtras.getIntent(this, FormatUtils.getTrimmedQuery(query), viewModel.getCategory()));
    }

    /**
     * Adapter for page tabs
     */
    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return viewModel.getCategoryCount();
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.newInstance(viewModel.getCategory(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return viewModel.getCategoryTitle(position);
        }
    }

    /**
     * Update top hero background image when tab page changes
     */
    private void updateHeroImage() {

        // default image
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_hero);

        ImageView imageView = findViewById(R.id.hero_image);

        // load with fade in
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Constants.HERO_ASSETS_PATH + viewModel.getCategory() + ".jpg"))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
