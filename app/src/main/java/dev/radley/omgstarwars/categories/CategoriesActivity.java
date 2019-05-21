package dev.radley.omgstarwars.categories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.swapi.models.Planet;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.categories.fragments.CategoryFragment;
import dev.radley.omgstarwars.categories.fragments.FilmsFragment;
import dev.radley.omgstarwars.categories.fragments.PeopleFragment;
import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.categories.model.Category;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CategoriesActivity extends AppCompatActivity {

    private ArrayList<Planet> mPlanetList;

    protected ViewPager mPager;
    protected PagerAdapter mPagerAdapter;
    protected TabLayout mTabLayout;
    protected static ArrayList<Category> mCategories = new ArrayList<Category>();


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
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public MyPagerAdapter(Context context, FragmentManager fragmentManager) {
            super(fragmentManager);
            mContext = context;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return mCategories.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            return mCategories.get(position).getCategoryFragment();
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position

            return mCategories.get(position).getName();
        }
    }


    protected void initCategories() {

        // fix for Apply Changes bug in emulator
        if(mCategories.size() > 0) return;

        mCategories.add(new Category(getString(R.string.category_id_films),
                getString(R.string.category_film), new FilmsFragment()));

        mCategories.add(new Category(getString(R.string.category_id_people),
                getString(R.string.category_people), new PeopleFragment()));

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
                Log.d(Util.getTag(), "mPager page: " + mPager.getCurrentItem());

                CategoryFragment fragment = (CategoryFragment) mPager.getAdapter()
                        .instantiateItem(mPager, mPager.getCurrentItem());

                fragment.getRecyclerView().smoothScrollToPosition(0);
            }
        });
    }

    public void updateHeroImage(int position) {

        String id = mCategories.get(position).getId();

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(false).build();

        // we're forced to add a placeholder here
        // because Glide will use placeholder from other instances (i.e. grid) if we don't
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
