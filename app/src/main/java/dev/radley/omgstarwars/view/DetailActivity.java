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
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.utilities.FormatUtils;
import dev.radley.omgstarwars.viewmodels.DetailViewModel;
import dev.radley.omgstarwars.viewmodels.SWCard;

public class DetailActivity extends AppCompatActivity {


    private ActionBar actionBar;
    private LinearLayout layout;
    private View detailView;
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

        addFactView();
        addRelatedLists();
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
     * Add "facts" detail layout based on category
     */
    protected void addFactView() {

        switch (viewModel.getCategory()) {
            case "films":
                addFilmFacts();
                break;
            case "people":
                addPeopleFacts();
                break;
            case "planets":
                addPlanetFacts();
                break;
            case "species":
                addSpeciesFacts();
                break;
            case "starships":
                addStarshipFacts();
                break;
            case "vehicles":
                addVehicleFacts();
                break;
        }

    }

    /**
     * Function shortcut for adding layout resource
     *
     * @param view layout resource id
     */
    protected void insertDetailView(int view) {

        LayoutInflater factory = LayoutInflater.from(this);
        detailView = factory.inflate(view, null);
        layout.addView(detailView, 0);
    }

    /**
     * Function shortcut for applying text to textView
     * makes it more readable
     *
     * @param id   int
     * @param text String
     */
    private void setText(int id, String text) {
        ((TextView) detailView.findViewById(id)).setText(text);
    }

    /**
     * Add facts layout and fill out text
     */
    private void addFilmFacts() {

        insertDetailView(R.layout.view_detail_film);

        Film film = (Film) viewModel.getModel();

        setText(R.id.director, film.director);
        setText(R.id.producer, film.producer);
        setText(R.id.opening_crawl, film.openingCrawl);
        setText(R.id.release_date, FormatUtils.getFormattedDate(this, film.created));
    }

    /**
     * Add facts layout and fill out text
     */
    private void addPeopleFacts() {

        insertDetailView(R.layout.view_detail_person);

        People people = (People) viewModel.getModel();

        setText(R.id.dob, people.birthYear);
        setText(R.id.hair_color, people.hairColor);
        setText(R.id.skin_color, people.skinColor);
        setText(R.id.gender, people.gender);
        setText(R.id.height, FormatUtils.getFormattedHeightCm(this, people.height));
        setText(R.id.mass, FormatUtils.getFormattedKg(this, people.mass));

        addHomeWorld(people.homeWorldUrl);
        addSingleSpecies(people.getRelatedSpecies().get(0));
    }

    /**
     * Add facts layout and fill out text
     */
    private void addPlanetFacts() {

        insertDetailView(R.layout.view_detail_planet);

        Planet planet = (Planet) viewModel.getModel();

        setText(R.id.climate, planet.climate);
        setText(R.id.gravity, planet.gravity);
        setText(R.id.terrain, planet.terrain);
        setText(R.id.population, FormatUtils.getFormattedNumber(planet.population));
        setText(R.id.rotation_period, FormatUtils.getFormattedDays(this, planet.rotationPeriod));
        setText(R.id.orbital_period, FormatUtils.getFormattedDays(this, planet.orbitalPeriod));
        setText(R.id.diameter, FormatUtils.getFormattedDistance(this, planet.diameter));
        setText(R.id.surface_water, FormatUtils.getFormattedPercentage(this, planet.surfaceWater));
    }

    /**
     * Add facts layout and fill out text
     */
    private void addSpeciesFacts() {

        insertDetailView(R.layout.view_detail_species);

        Species species = (Species) viewModel.getModel();

        setText(R.id.classification, species.classification);
        setText(R.id.designation, species.designation);
        setText(R.id.skin_colors, species.skinColors);
        setText(R.id.hair_color, species.hairColors);
        setText(R.id.eye_colors, species.eyeColors);
        setText(R.id.language, species.language);
        setText(R.id.average_height, FormatUtils.getFormattedHeightCm(this, species.averageHeight));
        setText(R.id.average_lifespan, FormatUtils.getFormattedYears(this, species.averageLifespan));

        addHomeWorld(species.homeWorld);
    }

    /**
     * Add facts layout and fill out text
     */
    private void addStarshipFacts() {

        insertDetailView(R.layout.view_detail_starship);

        Starship starship = (Starship) viewModel.getModel();

        setText(R.id.hyperdrive_rating, starship.hyperdriveRating);
        setText(R.id.mglt, starship.mglt);
        setText(R.id.starship_class, starship.starshipClass);

        setText(R.id.model, starship.model);
        setText(R.id.manufacturer, starship.manufacturer);
        setText(R.id.length, FormatUtils.getFormattedLengthM(this, starship.length));
        setText(R.id.cargo_capacity, FormatUtils.getFormattedTonnage(this, starship.cargoCapacity));
        setText(R.id.cost_in_credits, FormatUtils.getFormattedCredits(this, starship.costInCredits));
        setText(R.id.max_atmosphering_speed, FormatUtils.getFormattedSpeedKph(this, starship.maxAtmospheringSpeed));
        setText(R.id.crew, FormatUtils.getFormattedNumber(starship.crew));
        setText(R.id.passengers, FormatUtils.getFormattedNumber(starship.passengers));
        setText(R.id.consumables, FormatUtils.getFormattedNumber(starship.consumables));
    }

    /**
     * Add facts layout and fill out text
     */
    private void addVehicleFacts() {

        insertDetailView(R.layout.view_detail_vehicle);

        Vehicle vehicle = (Vehicle) viewModel.getModel();

        setText(R.id.model, vehicle.model);
        setText(R.id.manufacturer, vehicle.manufacturer);
        setText(R.id.length, FormatUtils.getFormattedLengthM(this, vehicle.length));
        setText(R.id.cargo_capacity, FormatUtils.getFormattedTonnage(this, vehicle.cargoCapacity));
        setText(R.id.cost_in_credits, FormatUtils.getFormattedCredits(this, vehicle.costInCredits));
        setText(R.id.max_atmosphering_speed, FormatUtils.getFormattedSpeedKph(this, vehicle.maxAtmospheringSpeed));
        setText(R.id.crew, FormatUtils.getFormattedNumber(vehicle.crew));
        setText(R.id.passengers, FormatUtils.getFormattedNumber(vehicle.passengers));
        setText(R.id.consumables, FormatUtils.getFormattedNumber(vehicle.consumables));
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
     * @param homeWorldUrl String
     */
    protected void addHomeWorld(String homeWorldUrl) {

        if (homeWorldUrl == null)
            return;

        final TextView homeWorldText = detailView.findViewById(R.id.homeworld);

        if (!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = FormatUtils.getId(homeWorldUrl);

        viewModel.getHomeWorlds(id).observe(this, (Planet planet) -> {

            homeWorldText.setText(Html.fromHtml(getString(R.string.link_text, planet.getTitle()), Build.VERSION.SDK_INT));
            homeWorldText.setOnClickListener(v -> startDetailActivity(planet));
        });
    }

    /**
     * Adds species text link (if available)
     *
     * @param speciesUrl String
     */
    protected void addSingleSpecies(String speciesUrl) {

        if (speciesUrl == null)
            return;

        final TextView speciesText = detailView.findViewById(R.id.species);

        if (!speciesUrl.substring(0, 4).equals("http")) {
            speciesText.setText(speciesUrl);
            return;
        }

        int id = FormatUtils.getId(speciesUrl);

        viewModel.getSingleSpecies(id).observe(this, (Species species) -> {

            speciesText.setText(Html.fromHtml(getString(R.string.link_text, species.getTitle()), Build.VERSION.SDK_INT));
            speciesText.setOnClickListener(v -> startDetailActivity(species));
        });
    }
}
