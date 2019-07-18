package dev.radley.omgstarwars.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.adapters.RelatedAdapter
import dev.radley.omgstarwars.bundle.DetailExtras
import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.view.detailview.*
import dev.radley.omgstarwars.viewmodels.DetailViewModel
import dev.radley.omgstarwars.viewmodels.SWImage
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import java.util.*
import kotlin.math.ceil

class DetailActivity : AppCompatActivity() {


    private lateinit var layout: LinearLayout
    private lateinit var detailView: DetailView
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        setupToolbar()

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        if (DetailExtras.hasAll(intent, DetailExtras.MODEL)) {
            viewModel.setModel(intent.getSerializableExtra(DetailExtras.MODEL))
            layout = findViewById(R.id.details_layout)

            heroTitle.text = viewModel.getTitle()
            heroSubtitle.text = viewModel.getSubTitle()

            updateHeroImage(viewModel.getImage(), SWImage.getFallbackImage(viewModel.getCategory()))

            addDetailView()
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

        // Third-party CollapsingToolbarLayout won't load typeface via styles, must do it here
//        val typeface = ResourcesCompat.getFont(this, R.font.passion_one)
//        toolbarLayout.setCollapsedTitleTypeface(typeface)
//        toolbarLayout.setExpandedTitleTypeface(typeface)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""
    }

    private fun addDetailView() {

        when (viewModel.getCategory()) {

            Category.FILMS -> detailView = FilmDetailView(this, viewModel.getModel())

            Category.PEOPLE -> {
                detailView = PeopleDetailView(this, viewModel.getModel())
                addHomeWorldTextLink()
                addSpeciesTextLink()
            }

            Category.PLANETS -> detailView = PlanetDetailView(this, viewModel.getModel())

            Category.SPECIES -> {
                detailView = SpeciesDetailView(this, viewModel.getModel())
                addHomeWorldTextLink()
            }

            Category.STARSHIPS -> detailView = StarshipDetailView(this, viewModel.getModel())

            Category.VEHICLES -> detailView = VehicleDetailView(this, viewModel.getModel())
        }

        layout.addView(detailView)
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
            for(i in 1 until ceil(count / 2f).toInt()) {
                filmsPlaceholder += " \n"
            }
        }

        heroFilms.text = filmsPlaceholder

        val recyclerView = getRelatedListView(viewModel.getRelatedFilmsTitle())
        viewModel.getFilmsList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter

            var filmsText = ""

            if(list.size > 0) {
                filmsText = (list[0] as Film).title
            }

            if(list.size > 1) {
                for (i in 1 until list.size) {
                    filmsText += if(i % 2 == 0) { "\n" } else { " • " }
                    filmsText += (list[i] as Film).title
                }
            }

            heroFilms.text = filmsText
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

        val homeWorldText = detailView.findViewById<TextView>(R.id.homeworld)

        if (viewModel.getHomeWorld()!!.substring(0, 4) != "http") {
            homeWorldText.text = viewModel.getHomeWorld()
            return
        }

        val id = FormatUtils.getId(viewModel.getHomeWorld()!!)

        viewModel.getHomeWorlds(id).observe(this, Observer { planet ->
            homeWorldText.text = Html.fromHtml(getString(R.string.link_text, planet.title), Build.VERSION.SDK_INT)
            homeWorldText.setOnClickListener { startDetailActivity(planet) }

            heroSubtitle.text = planet.title
        })
    }

    private fun addSpeciesTextLink() {


        if (viewModel.getSingleSpecies() == null)
            return

        val speciesText = detailView.findViewById<TextView>(R.id.species)

        if (viewModel.getSingleSpecies()!!.substring(0, 4) != "http") {
            speciesText.text = viewModel.getSingleSpecies()
            return
        }

        val id = FormatUtils.getId(viewModel.getSingleSpecies()!!)

        viewModel.getSingleSpecies(id).observe(this, Observer { species ->
            speciesText.text = Html.fromHtml(getString(R.string.link_text, species.title), Build.VERSION.SDK_INT)
            speciesText.setOnClickListener { startDetailActivity(species) }
        })
    }


    private fun startDetailActivity(item: SWModel) {

        startActivity(DetailExtras.getIntent(this, item))
    }


    private fun updateHeroImage(imagePath: String, fallback: Int) {

        // placeholder
        val requestOptions = RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .error(fallback)

        val imageView = findViewById<ImageView>(R.id.heroImage)

        // load image and fade in
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(imagePath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
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