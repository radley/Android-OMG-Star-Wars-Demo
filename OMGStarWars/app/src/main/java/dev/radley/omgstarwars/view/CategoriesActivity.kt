package dev.radley.omgstarwars.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.bundle.SearchExtras
import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.activity_categories.*
import timber.log.Timber


class CategoriesActivity : AppCompatActivity() {

    private var categories = Category.categories
    private var category = categories[0]

    private lateinit var pagerAdapter: CategoriesPagerAdapter
    private lateinit var searchView: SearchView

    private val tabTypeface = Typeface.create("sans-serif-light",Typeface.NORMAL)
    private val tabTypefaceSelected = Typeface.create("sans-serif-black",Typeface.NORMAL)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_categories)
        setupToolbar()
        setupLayout()
//        updateHeroImage()

        Timber.d("onCreate()")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_categories, menu)
        searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        setupSearchView()
        return true
    }

    private fun setupSearchView() {

//        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
//
//            if (hasFocus) {
//                searchView.setBackgroundColor(getColor(R.color.transparentPrimary))
//            } else {
//                searchView.background = null
//            }
//        }

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

    var currentPage = 0

    private fun setupLayout() {
        pagerAdapter = CategoriesPagerAdapter(supportFragmentManager)

        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 6 // TODO add this line for recording video demo
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                updateCategory(position)

                val fragment = viewPager.adapter!!.instantiateItem(viewPager, viewPager.currentItem) as CategoryFragment

                if(position > currentPage) {
                    val controller = AnimationUtils.loadLayoutAnimation(fragment.activity, R.anim.category_layout_right_to_left_animation)
                    fragment.recyclerView.layoutAnimation = controller
                } else {
                    val controller = AnimationUtils.loadLayoutAnimation(fragment.activity, R.anim.category_layout_left_to_right_animation)
                    fragment.recyclerView.layoutAnimation = controller
                }

                currentPage = position
                fragment.recyclerView.startLayoutAnimation()
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        tabLayout.setupWithViewPager(viewPager)

        /* Tabs need to use a custom textview to be able to have:
         *
         * - margins for first & last tabs that match overall layout: 16 (sides) + 4 (card) = 20
         * - bold selected tab text
         *
         */
        for (i in 0 until tabLayout.tabCount) {

            val tab = tabLayout.getTabAt(i)
            if (tab != null) {

                val tabTextView = TextView(this)
                tab.customView = tabTextView

                tabTextView.typeface = tabTypeface
                tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                tabTextView.text = tab.text
                tabTextView.isAllCaps = true
                tabTextView.textSize = 24f
                tabTextView.setTextColor(getColor(R.color.text_low_emphasis_color))

                val p = tabTextView.layoutParams as ViewGroup.MarginLayoutParams

                when (i) {
                    0 -> {
                        tabTextView.typeface = tabTypefaceSelected
                        tabTextView.setTextColor(getColor(R.color.tab_text_color_selected))
                        p.setMargins(28, 0, 8, 0)
                        tabTextView.requestLayout()
                    }
                    tabLayout.tabCount - 1 -> {
                        tabTextView.typeface = tabTypeface
                        tabTextView.setTextColor(getColor(R.color.text_low_emphasis_color))
                        p.setMargins(8, 0, 28, 0)
                        tabTextView.requestLayout()
                    }
                    else -> {
                        tabTextView.typeface = tabTypeface
                        tabTextView.setTextColor(getColor(R.color.text_low_emphasis_color))
                        p.setMargins(8, 0, 8, 0)
                        tabTextView.requestLayout()
                    }
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                // customize selected tab text style
                val vg = tabLayout.getChildAt(0) as ViewGroup
                val vgTab = vg.getChildAt(tab.position) as ViewGroup
                val tabChildsCount = vgTab.childCount

                for (i in 0 until tabChildsCount) {
                    val tabViewChild = vgTab.getChildAt(i)
                    if (tabViewChild is TextView) {
                        tabViewChild.typeface = tabTypefaceSelected
                        tabViewChild.setTextColor(getColor(R.color.tab_text_color_selected))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                // customize unselected tab text style
                tab?.let {

                    val vg = tabLayout.getChildAt(0) as ViewGroup
                    val vgTab = vg.getChildAt(it.position) as ViewGroup
                    val tabChildsCount = vgTab.childCount

                    for (i in 0 until tabChildsCount) {
                        val tabViewChild = vgTab.getChildAt(i)
                        if (tabViewChild is TextView) {
                            tabViewChild.typeface = tabTypeface
                            tabViewChild.setTextColor(getColor(R.color.text_low_emphasis_color))
                        }
                    }
                }

                val fragment = viewPager.adapter!!.instantiateItem(viewPager, viewPager.currentItem) as CategoryFragment
                fragment.recyclerView.smoothScrollToPosition(0)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                val fragment = viewPager.adapter!!.instantiateItem(viewPager, viewPager.currentItem) as CategoryFragment
                fragment.recyclerView.smoothScrollToPosition(0)
            }
        })
    }

    inner class CategoriesPagerAdapter(fragmentManager: FragmentManager) :
            FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
        searchView.queryHint = getString(R.string.search_query_hint, category)
    }
}