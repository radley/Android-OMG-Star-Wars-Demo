package dev.radley.omgstarwars.ui.browse;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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
import dev.radley.omgstarwars.common.Constants;
import dev.radley.omgstarwars.component.AutoCompleteSearchViewManager;
import dev.radley.omgstarwars.ui.browse.category.CategoryFragment;

public class BrowseCategoriesActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private AppBarLayout.OnOffsetChangedListener mAppBarOffsetListener;
    private AutoCompleteSearchViewManager mSearchViewManager;

    private TabLayout mTabLayout;
    private TabLayout.ViewPagerOnTabSelectedListener mTabListener;
    private ViewPager mPager;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private static String[] mCategoryIds;
    private static String[] mCategoryTitles;
    private String mCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browse);
        setupToolbar();

        // default categories
        mCategoryIds = getResources().getStringArray(R.array.category_ids);
        mCategoryTitles = getResources().getStringArray(R.array.category_titles);
        mCategory = mCategoryIds[0];

        setupLayout();
    }

    @Override
    public void onStart() {
        super.onStart();

        // add listeners
        mPager.addOnPageChangeListener(mPageChangeListener);
        mTabLayout.addOnTabSelectedListener(mTabListener);
        mAppBarLayout.addOnOffsetChangedListener(mAppBarOffsetListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        // remove listeners
        mPager.removeOnPageChangeListener(mPageChangeListener);
        mTabLayout.removeOnTabSelectedListener(mTabListener);
        mAppBarLayout.removeOnOffsetChangedListener(mAppBarOffsetListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_categories, menu);
        //mSearchViewManager = new AutoCompleteSearchViewManager(this, (SearchView) menu.findItem(R.id.action_search).getActionView());
        //mSearchViewManager.init(sViewModel.getCurrentCategory());
        return true;
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {

        // setup view pager
        mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                mCategory = mCategoryIds[position];
                updateHeroImage();

                if(mSearchViewManager != null)
                    mSearchViewManager.updateCategory(mCategory);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };

        // setup tabs
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mPager);
        mTabListener = new TabLayout.ViewPagerOnTabSelectedListener(mPager) {
            // scroll to top on double-click
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                CategoryFragment fragment = (CategoryFragment) Objects.requireNonNull(mPager.getAdapter())
                        .instantiateItem(mPager, mPager.getCurrentItem());

                fragment.getRecyclerView().smoothScrollToPosition(0);
            }
        };

        // setup parallax hero header
        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarOffsetListener = (appBarLayout, verticalOffset) -> {

            // show background color when open
            if (Math.abs(verticalOffset) < 24) {
                mTabLayout.setBackgroundColor(getApplicationContext().getColor(R.color.transparentPrimaryDark));

            } else {
                mTabLayout.setBackground(null);
            }
        };

        updateHeroImage();
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return mCategoryIds.length;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.newInstance(mCategoryIds[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategoryTitles[position];
        }
    }

    private void updateHeroImage() {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_hero);

        ImageView imageView = findViewById(R.id.hero_image);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Constants.HERO_ASSETS_PATH + mCategory + ".jpg"))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
