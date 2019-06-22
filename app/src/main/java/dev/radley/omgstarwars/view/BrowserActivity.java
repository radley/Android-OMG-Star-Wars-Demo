package dev.radley.omgstarwars.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.Serializable;
import java.util.Objects;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.utilities.Constants;
import dev.radley.omgstarwars.viewmodels.BrowseCategoriesViewModel;
import dev.radley.omgstarwars.utilities.Util;

public class BrowserActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private AppBarLayout.OnOffsetChangedListener appBarOffsetListener;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private TabLayout tabLayout;
    private TabLayout.ViewPagerOnTabSelectedListener tabListener;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener pageChangeListener;

    private Handler delayHandler = new Handler();

    private static BrowseCategoriesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browser);
        setupToolbar();

        viewModel = ViewModelProviders.of(this).get(BrowseCategoriesViewModel.class);
        viewModel.init(getResources().getStringArray(R.array.category_ids),
                getResources().getStringArray(R.array.category_titles));

        setupLayout();
    }

    @Override
    public void onStart() {
        super.onStart();

        // add listeners
        viewPager.addOnPageChangeListener(pageChangeListener);
        tabLayout.addOnTabSelectedListener(tabListener);
        appBarLayout.addOnOffsetChangedListener(appBarOffsetListener);
    }

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

//        searchView = new SWSearchViewAutoComplete(this, (SearchView) menu.findItem(R.id.action_search).getActionView());
//        searchView.init(mCategory);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        return true;
    }


    private void setupSearchView() {

        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                //mQuery = query;

                delayHandler.removeCallbacksAndMessages(null);
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //doSearch();
                    }
                }, 500);

                return true;
            }
        });


        // add background color to searchView while expanded
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
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void updateCategory(int position) {

        viewModel.setCategory(position);
        updateHeroImage();

        searchView.setQueryHint(getString(R.string.search_query_hint, viewModel.getCategory()));
        searchView.setQuery("", false);
    }

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
            // scroll to top on double-click
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

            // show background color when open
            if (Math.abs(verticalOffset) < 24) {
                tabLayout.setBackgroundColor(getApplicationContext().getColor(R.color.transparentPrimaryDark));

            } else {
                tabLayout.setBackground(null);
            }
        };

        updateHeroImage();
    }

    private void startSearchActivity(String query) {

        final Intent intent = new Intent(this, SearchActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(SearchExtras.QUERY, Util.getTrimmedQuery(query));
        intent.putExtra(SearchExtras.CATEGORY, viewModel.getCategory());

        Bundle bundle = new Bundle();
        bundle.putSerializable(SearchExtras.RESULT_LIST, (Serializable) viewModel.getResultItems());
        intent.putExtras(bundle);

        searchView.setQuery("", false);
        searchView.setIconified(true);

        startActivity(intent);
    }

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

    private void updateHeroImage() {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_hero);

        ImageView imageView = findViewById(R.id.hero_image);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Constants.HERO_ASSETS_PATH + viewModel.getCategory() + ".jpg"))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
