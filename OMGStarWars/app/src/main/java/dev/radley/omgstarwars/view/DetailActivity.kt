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
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
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
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.view.detailview.*
import dev.radley.omgstarwars.viewmodels.DetailViewModel
import dev.radley.omgstarwars.viewmodels.SWImage
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout
import java.util.*

class DetailActivity : AppCompatActivity() {


    private lateinit var layout: LinearLayout
    private lateinit var detailView: DetailView
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFullscreen()
        setupToolbar()

        val intent = intent

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.setModel(intent.getSerializableExtra(DetailExtras.MODEL))

        layout = findViewById(R.id.details_layout)

        supportActionBar!!.title = viewModel.getTitle()
        updateHeroImage(viewModel.getImage(), SWImage.getFallbackImage(viewModel.getCategory()))

        addDetailView()
        addRelatedLists()
    }

    public override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }

    private fun setupFullscreen() {

        //TODO why isn't status bar transparent?

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(R.color.transparentPrimaryDark, null)
    }

    private fun setupToolbar() {
        setContentView(R.layout.activity_detail)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)

        mToolbar.setNavigationOnClickListener { finish() }

        // Third-party CollapsingToolbarLayout won't load typeface via styles, must do it here
        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val typeface = ResourcesCompat.getFont(this, R.font.passion_one)
        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface)
        collapsingToolbarLayout.setExpandedTitleTypeface(typeface)

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

        val recyclerView = getRelatedListView(viewModel.getRelatedFilmsTitle())
        viewModel.getFilmsList().observe(this, Observer(fun(list: ArrayList<SWModel>) {

            val adapter = RelatedAdapter(list) { item: SWModel -> startDetailActivity(item) }
            recyclerView.adapter = adapter
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

        (view.findViewById<View>(R.id.title) as TextView).text = String.format("%s!", title)
        return view.findViewById(R.id.recycler_view)
    }
}