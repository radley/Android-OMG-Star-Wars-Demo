package dev.radley.omgstarwars.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.adapters.RelatedAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.models.Category;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.utilities.FormatUtils;
import dev.radley.omgstarwars.view.detailview.DetailView;
import dev.radley.omgstarwars.view.detailview.FilmDetailView;
import dev.radley.omgstarwars.view.detailview.PeopleDetailView;
import dev.radley.omgstarwars.view.detailview.PlanetDetailView;
import dev.radley.omgstarwars.view.detailview.SpeciesDetailView;
import dev.radley.omgstarwars.view.detailview.StarshipDetailView;
import dev.radley.omgstarwars.view.detailview.VehicleDetailView;
import dev.radley.omgstarwars.viewmodels.DetailViewModel;
import dev.radley.omgstarwars.viewmodels.SWCard;

public class DetailActivity extends AppCompatActivity {


    private ActionBar actionBar;
    private LinearLayout layout;
    private DetailView detailView;
    private DetailViewModel viewModel;

    /**
     * - setup layout views
     * - crete viewModel and pass it SWModel object
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();
        setupToolbar();

        Intent intent = getIntent();

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.setModel(intent.getSerializableExtra(DetailExtras.MODEL));

        layout = findViewById(R.id.details_layout);

        actionBar.setTitle(viewModel.getTitle());
        updateHeroImage(viewModel.getImage(), SWCard.getFallbackImage(viewModel.getCategory()));

        addDetailView();
        addRelatedLists();
    }

    /**
     * Clear viewModel on exit
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.dispose();
    }


    /**
     * Make view fullscreen
     */
    private void setupFullscreen() {

        //TODO why isn't status bar transparent?

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(getResources().getColor(R.color.transparentPrimaryDark, null));
    }

    /**
     * Setup toolbar and allow it to show multiple lines of text when extended
     */
    private void setupToolbar() {
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(v -> finish());

        // Third-party CollapsingToolbarLayout won't load typeface via styles, must do it here
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.passion_one);
        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        collapsingToolbarLayout.setExpandedTitleTypeface(typeface);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("");
        }
    }

    /**
     * Add detail view based on category
     */
    protected void addDetailView() {

        switch (viewModel.getCategory()) {

            case Category.FILMS:
                detailView = new FilmDetailView(this, viewModel.getModel());
                break;

            case Category.PEOPLE:
                detailView = new PeopleDetailView(this, viewModel.getModel());
                addHomeWorldTextLink();
                addSpeciesTextLink();
                break;

            case Category.PLANETS:
                detailView = new PlanetDetailView(this, viewModel.getModel());
                break;

            case Category.SPECIES:
                detailView = new SpeciesDetailView(this, viewModel.getModel());
                addHomeWorldTextLink();
                break;

            case Category.STARSHIPS:
                detailView = new StarshipDetailView(this, viewModel.getModel());
                break;

            case Category.VEHICLES:
                detailView = new VehicleDetailView(this, viewModel.getModel());
                break;
        }

        layout.addView(detailView);
    }


    /**
     * Add related items lists (if any) in this order...
     */
    protected void addRelatedLists() {

        addRelatedFilms();
        addRelatedPeople();
        addRelatedSpecies();
        addRelatedStarships();
        addRelatedVehicles();
        addRelatedPlanets();
    }

    /**
     * Update the large backgroun hero image
     *
     * @param imagePath String
     * @param fallback  int (resource)
     */
    protected void updateHeroImage(String imagePath, int fallback) {

        // placeholder
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .error(fallback);

        ImageView imageView = findViewById(R.id.hero_image);

        // load image and fade in
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(imagePath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * Loads related list view into main layout and returns it
     * - populates title to match model id
     * - ex. "pilots" instead of "people"
     * - adds a "!" because OMG...!
     *
     * @param title String
     * @return RecyclerView
     */
    private RecyclerView getRelatedListView(String title) {

        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.view_detail_related_list, layout, false);
        layout.addView(view);

        ((TextView) view.findViewById(R.id.title)).setText(String.format("%s!", title));
        return view.findViewById(R.id.recycler_view);
    }

    /**
     * Add related films row (if needed) and pings viewModel for update
     */
    protected void addRelatedFilms() {

        if (viewModel.getFilms() == null || viewModel.getFilms().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedFilmsTitle());
        viewModel.getFilmsList(viewModel.getFilms()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Add related people row (if needed) and pings viewModel for update
     */
    protected void addRelatedPeople() {

        if (viewModel.getPeople() == null || viewModel.getPeople().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedPeopleTitle());
        viewModel.getPeopleList(viewModel.getPeople()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Add related species row (if needed) and pings viewModel for update
     */
    protected void addRelatedSpecies() {

        if (viewModel.getModel() instanceof People ||
                viewModel.getSpecies() == null ||
                viewModel.getSpecies().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedSpeciesTitle());
        viewModel.getSpeciesList(viewModel.getSpecies()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Add related planets row (if needed) and pings viewModel for update
     */
    protected void addRelatedPlanets() {

        if (viewModel.getPlanets() == null || viewModel.getPlanets().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedPlanetsTitle());
        viewModel.getPlanetsList(viewModel.getPlanets()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Add related starships row (if needed) and pings viewModel for update
     */
    protected void addRelatedStarships() {

        if (viewModel.getStarships() == null || viewModel.getStarships().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedStarshipsTitle());
        viewModel.getStarshipsList(viewModel.getStarships()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Add related vehicles row (if needed) and pings viewModel for update
     */
    protected void addRelatedVehicles() {

        if (viewModel.getVehicles() == null || viewModel.getVehicles().size() <= 0) {
            return;
        }

        RecyclerView recyclerView = getRelatedListView(viewModel.getRelatedVehiclesTitle());
        viewModel.getVehiclesList(viewModel.getVehicles()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Opens detailActivity based on related item or text link tap
     *
     * @param item SWModel
     */
    private void startDetailActivity(SWModel item) {

        startActivity(DetailExtras.getIntent(this, item));
    }


    /**
     * Adds homeworld text link (if available)
     *
     */
    protected void addHomeWorldTextLink() {

        if (viewModel.getHomeWorld() == null)
            return;

        final TextView homeWorldText = detailView.findViewById(R.id.homeworld);

        if (!viewModel.getHomeWorld().substring(0, 4).equals("http")) {
            homeWorldText.setText(viewModel.getHomeWorld());
            return;
        }

        int id = FormatUtils.getId(viewModel.getHomeWorld());

        viewModel.getHomeWorlds(id).observe(this, (Planet planet) -> {

            homeWorldText.setText(Html.fromHtml(getString(R.string.link_text, planet.getTitle()), Build.VERSION.SDK_INT));
            homeWorldText.setOnClickListener(v -> startDetailActivity(planet));
        });
    }

    /**
     * Adds species text link (if available)
     *
     */
    protected void addSpeciesTextLink() {


        if (viewModel.getSingleSpecies() == null)
            return;

        final TextView speciesText = detailView.findViewById(R.id.species);

        if (!viewModel.getSingleSpecies().substring(0, 4).equals("http")) {
            speciesText.setText(viewModel.getSingleSpecies());
            return;
        }

        int id = FormatUtils.getId(viewModel.getSingleSpecies());

        viewModel.getSingleSpecies(id).observe(this, (Species species) -> {

            speciesText.setText(Html.fromHtml(getString(R.string.link_text, species.getTitle()), Build.VERSION.SDK_INT));
            speciesText.setOnClickListener(v -> startDetailActivity(species));
        });
    }
}
