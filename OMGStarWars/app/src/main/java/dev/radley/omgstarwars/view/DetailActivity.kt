package dev.radley.omgstarwars.view

import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.adapters.RelatedAdapter
import dev.radley.omgstarwars.bundle.DetailExtras
import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.view.detail.description.*
import dev.radley.omgstarwars.view.detail.hero.HeroLayout
import dev.radley.omgstarwars.view.detail.hero.SquareHeroLayout
import dev.radley.omgstarwars.view.detail.hero.TallHeroLayout
import dev.radley.omgstarwars.view.detail.hero.WideHeroLayout
import dev.radley.omgstarwars.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import java.util.*
import kotlin.math.ceil
import kotlin.math.roundToInt
import android.text.style.StyleSpan



class DetailActivity : AppCompatActivity() {


    private lateinit var layout: LinearLayout
    private lateinit var descriptionLayout: DescriptionLayout
    private lateinit var heroLayout: HeroLayout
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        if (DetailExtras.hasAll(intent, DetailExtras.MODEL)) {
            viewModel.setModel(intent.getSerializableExtra(DetailExtras.MODEL))
            layout = findViewById(R.id.details_layout)

            setupToolbar()
            addHeroLayout()
            addDescriptionLayout()
            addRelatedLists()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = " "
    }

    private fun addHeroLayout() {

        heroLayout = when (viewModel.getCategory()) {

            Category.PLANETS -> SquareHeroLayout(this, viewModel.getModel())

            Category.STARSHIPS, Category.VEHICLES -> WideHeroLayout(this, viewModel.getModel())

            else -> TallHeroLayout(this, viewModel.getModel())
        }

        layout.addView(heroLayout)

        Timber.d("scrollView = $scrollView")

        var targetY = 0
        var isShow = true

        val vto: ViewTreeObserver = layout.viewTreeObserver

        heroLayout.title.doOnLayout {

            Timber.d("doOnLayout")

            // get target y position
            val titleLocation = heroLayout.title.getLocationOnScreen()
            val layoutLocation = layout.getLocationOnScreen()
            targetY = titleLocation.y - layoutLocation.y + dpToPx(24) // status bar height
        }

        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

            if (scrollY > targetY) {

                supportActionBar!!.title = viewModel.getTitle()
                isShow = true

            } else if (isShow) {
                supportActionBar!!.title = " "//there should a space between double quote
                isShow = false
            }
        })

    }

    fun dpToPx(dp: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    fun View.getLocationOnScreen(): Point {
        val location = IntArray(2)
        this.getLocationOnScreen(location)
        return Point(location[0], location[1])
    }

    private fun addDescriptionLayout() {

        when (viewModel.getCategory()) {

            Category.FILMS -> descriptionLayout = FilmDescriptionLayout(this, viewModel.getModel())

            Category.PEOPLE -> {
                descriptionLayout = PeopleDescriptionLayout(this, viewModel.getModel())
                addHomeWorldTextLink()
                addSpeciesTextLink()
            }

            Category.PLANETS -> descriptionLayout = PlanetDescriptionLayout(this, viewModel.getModel())

            Category.SPECIES -> {
                descriptionLayout = SpeciesDescriptionLayout(this, viewModel.getModel())
                addHomeWorldTextLink()
            }

            Category.STARSHIPS -> descriptionLayout = StarshipDescriptionLayout(this, viewModel.getModel())

            Category.VEHICLES -> descriptionLayout = VehicleDescriptionLayout(this, viewModel.getModel())
        }

        layout.addView(descriptionLayout)
    }

    private fun addRelatedLists() {

        addRelatedFilms()
        addRelatedPeople()
        addRelatedSpecies()
        addRelatedStarships()
        addRelatedVehicles()
        addRelatedPlanets()
    }

    private fun addRelatedFilms() {

        if (viewModel.getFilms() == null || viewModel.getFilms()!!.size <= 0) {
            return
        }


        var filmsPlaceholder = ""

        // add enough room for more than one row of text to avoid jumping layout
        val count = viewModel.getFilms()!!.size
        if (count > 2) {
            for (i in 1 until ceil(count / 2f).toInt()) {
                filmsPlaceholder += " \n"
            }
        }

        val heroFilms = heroLayout.findViewById<TextView>(R.id.heroFilms)

        heroFilms.visibility = View.VISIBLE
        heroFilms.text = filmsPlaceholder

        val recyclerView = getRelatedListView(viewModel.getRelatedFilmsTitle())
        viewModel.getFilmsList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter

            val boldSpan = StyleSpan(android.graphics.Typeface.BOLD)

            var filmsText = ""
            var start = 0;

            if (list.size > 0) {

                //filmsText += getString(R.string.episode_title, FormatUtils.IntegerToRomanNumeral((list[0] as Film).episodeId), (list[0] as Film).title)
                filmsText += getString(R.string.episode_title_formatted, FormatUtils.IntegerToRomanNumeral((list[0] as Film).episodeId), (list[0] as Film).title)
            }

            if (list.size > 1) {
                for (i in 1 until list.size) {

//                    filmsText += "\n"
//                    filmsText += getString(R.string.episode_title, FormatUtils.IntegerToRomanNumeral((list[i] as Film).episodeId), (list[i] as Film).title)

                    filmsText += "<br/>"
                    filmsText += getString(R.string.episode_title_formatted, FormatUtils.IntegerToRomanNumeral((list[i] as Film).episodeId), (list[i] as Film).title)
                }
            }

            //heroFilms.text = filmsText
            heroFilms.text = Html.fromHtml(filmsText, Build.VERSION.SDK_INT)
        }))
    }

    private fun addRelatedPeople() {

        if (viewModel.getPeople() == null || viewModel.getPeople()!!.size <= 0) {
            return
        }

        val recyclerView = getRelatedListView(viewModel.getRelatedPeopleTitle())
        viewModel.getPeopleList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
        }))
    }

    private fun addRelatedPlanets() {

        if (viewModel.getPlanets() == null || viewModel.getPlanets()!!.size <= 0) {
            return
        }

        val recyclerView = getRelatedListView(viewModel.getRelatedPlanetsTitle())
        viewModel.getPlanetsList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
        }))
    }

    private fun addRelatedSpecies() {

        if (viewModel.getModel() is People ||
                viewModel.getSpecies() == null ||
                viewModel.getSpecies()!!.size <= 0) {
            return
        }

        val recyclerView = getRelatedListView(viewModel.getRelatedSpeciesTitle())
        viewModel.getSpeciesList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
        }))
    }

    private fun addRelatedStarships() {

        if (viewModel.getStarships() == null || viewModel.getStarships()!!.size <= 0) {
            return
        }

        val recyclerView = getRelatedListView(viewModel.getRelatedStarshipsTitle())
        viewModel.getStarshipsList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
        }))
    }

    private fun addRelatedVehicles() {

        if (viewModel.getVehicles() == null || viewModel.getVehicles()!!.size <= 0) {
            return
        }

        val recyclerView = getRelatedListView(viewModel.getRelatedVehiclesTitle())
        viewModel.getVehiclesList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
        }))
    }

    private fun addHomeWorldTextLink() {

        if (viewModel.getHomeWorld() == null)
            return

        val homeWorldText = descriptionLayout.findViewById<TextView>(R.id.homeworld)

        if (viewModel.getHomeWorld()!!.substring(0, 4) != "http") {
            homeWorldText.text = viewModel.getHomeWorld()
            return
        }

        val id = FormatUtils.getId(viewModel.getHomeWorld()!!)

        viewModel.getHomeWorlds(id).observe(this, Observer { planet ->


            val textLink = getString(R.string.link_text, getColor(R.color.text_link_color), planet.title)

            homeWorldText.text = Html.fromHtml(textLink, Build.VERSION.SDK_INT)
            homeWorldText.setOnClickListener { startDetailActivity(planet) }

            heroLayout.subtitle.text = planet.title
        })
    }

    private fun addSpeciesTextLink() {


        if (viewModel.getSingleSpecies() == null)
            return

        val speciesText = descriptionLayout.findViewById<TextView>(R.id.species)

        if (viewModel.getSingleSpecies()!!.substring(0, 4) != "http") {
            speciesText.text = viewModel.getSingleSpecies()
            return
        }

        val id = FormatUtils.getId(viewModel.getSingleSpecies()!!)

        viewModel.getSingleSpecies(id).observe(this, Observer { species ->


            val textLink = getString(R.string.link_text, getColor(R.color.text_link_color), species.title)

            speciesText.text = Html.fromHtml(textLink, Build.VERSION.SDK_INT)
            speciesText.setOnClickListener { startDetailActivity(species) }
        })
    }


    private fun startDetailActivity(item: SWModel) {

        startActivity(DetailExtras.getIntent(this, item))
    }


    private fun getRelatedListView(title: String): RecyclerView {

        val factory = LayoutInflater.from(this)
        val view = factory.inflate(R.layout.view_detail_related_list, layout, false)
        layout.addView(view)

//        (view.findViewById<View>(R.id.title) as TextView).text = String.format("%s!", title)
        (view.findViewById<View>(R.id.title) as TextView).text = title
        return view.findViewById(R.id.recycler_view)
    }
}