package dev.radley.omgstarwars.view

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.bundle.SearchExtras
import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.utilities.Constants
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.activity_categories.*
import timber.log.Timber
import kotlin.math.abs

class CategoriesActivity : AppCompatActivity() {

    private var categories = Category.categories
    private var category = categories[0]

    private lateinit var pagerAdapter: CategoriesPagerAdapter
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_categories)
        setupToolbar()
        setupLayout()
        updateHeroImage()

        Timber.d("onCreate()")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_categories, menu)
        searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        setupSearchView()
        return true
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->

            if (hasFocus) {
                searchView.setBackgroundColor(getColor(R.color.transparentPrimary))
            } else {
                searchView.background = null
            }
        }

        searchView.queryHint = getString(R.string.search_query_hint, category)
        searchView.isIconified = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                startSearchActivity(query)
                return false
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }


    private fun setupLayout() {
        pagerAdapter = CategoriesPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                updateCategory(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {

                Timber.d("onTabReselected()")
                val fragment = viewPager.adapter!!.instantiateItem(viewPager, viewPager.currentItem) as CategoryFragment
                fragment.recyclerView.smoothScrollToPosition(0)
            }
        })

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->

            // show background onionskin behind tabs when app bar is extended
            if (abs(verticalOffset) < 24) {
                tabLayout.setBackgroundColor(applicationContext.getColor(R.color.transparentPrimaryDark))

            } else {
                tabLayout.background = null
            }
        })
    }

    inner class CategoriesPagerAdapter(fragmentManager: FragmentManager) :
            FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() = categories.size
        override fun getPageTitle(position: Int) = categories[position].capitalize()
        override fun getItem(position: Int): Fragment = CategoryFragment.newInstance(categories[position])
    }

    private fun startSearchActivity(query: String) {
        searchView.setQuery("", false)
        searchView.isIconified = true

        startActivity(SearchExtras.getIntent(this, FormatUtils.getTrimmedQuery(query), category))
    }

    private fun updateCategory(position: Int) {
        category = categories[position]
        updateHeroImage()
    }

    private fun updateHeroImage() {

        // placeholder image
        val requestOptions = RequestOptions()
                .placeholder(R.drawable.placeholder_hero)

        // load with fade in
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Constants.HERO_ASSETS_PATH + category + ".jpg"))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(heroImage)
    }
}