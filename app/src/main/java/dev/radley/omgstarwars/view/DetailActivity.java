package dev.radley.omgstarwars.view;

import android.annotation.SuppressLint;
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

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.People;
import dev.radley.omgstarwars.data.Planet;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.Species;
import dev.radley.omgstarwars.data.Starship;
import dev.radley.omgstarwars.data.Vehicle;
import dev.radley.omgstarwars.adapters.RelatedAdapter;
import dev.radley.omgstarwars.utilities.SWCard;
import dev.radley.omgstarwars.utilities.Util;
import dev.radley.omgstarwars.viewmodels.DetailViewModel;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


public class DetailActivity extends AppCompatActivity {


    private ActionBar actionBar;
    private LinearLayout layout;
    private View detailView;
    private DetailViewModel viewModel;

    // TODO move to view model
    private SWModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();
        setupToolbar();

        Intent intent = getIntent();
        model = (SWModel) intent.getSerializableExtra(DetailExtras.MODEL);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.setModel(model);

        layout = findViewById(R.id.details_layout);

        actionBar.setTitle(model.getTitle());
        updateHeroImage(model.getImagePath(), SWCard.getFallbackImage(model.getCategoryId()));
        populateDetails();
        addRelatedLists();
    }


    private void setupFullscreen() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(getResources().getColor(R.color.transparentPrimaryDark, null));
    }

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

    protected void populateDetails() {

        switch (model.getCategoryId()) {
            case "films":
                insertDetailView(R.layout.view_detail_film);
                addFilmFacts();
                break;
            case "people":
                insertDetailView(R.layout.view_detail_person);
                addPeopleFacts();
                break;
            case "planets":
                insertDetailView(R.layout.view_detail_planet);
                addPlanetFacts();
                break;
            case "species":
                insertDetailView(R.layout.view_detail_species);
                addSpeciesFacts();
                break;
            case "starships":
                insertDetailView(R.layout.view_detail_starship);
                addStarshipFacts();
                break;
            case "vehicles":
                insertDetailView(R.layout.view_detail_vehicle);
                addVehicleFacts();
                break;
        }

    }

    protected void insertDetailView(int view) {

        LayoutInflater factory = LayoutInflater.from(this);
        detailView = factory.inflate(view, null);
        layout.addView(detailView, 0);

    }

    private void setText(int id, String text) {
        ((TextView) detailView.findViewById(id)).setText(text);
    }

    private void addFilmFacts() {

        Film film = (Film) model;

        setText(R.id.director, film.director);
        setText(R.id.producer, film.producer);
        setText(R.id.opening_crawl, film.openingCrawl);
        setText(R.id.release_date, getFormattedDate(film.created));
    }

    private void addPeopleFacts() {

        People people = (People) model;

        setText(R.id.dob, people.birthYear);
        setText(R.id.hair_color, people.hairColor);
        setText(R.id.skin_color, people.skinColor);
        setText(R.id.gender, people.gender);
        setText(R.id.height, getFormattedHeightCm(people.height));
        setText(R.id.mass, getFormattedKg(people.mass));

        addHomeWorld(((People) model).homeWorldUrl);
        addSingleSpecies(model.getSpecies().get(0));
    }

    private void addPlanetFacts() {

        Planet planet = (Planet) model;

        setText(R.id.climate, planet.climate);
        setText(R.id.gravity, planet.gravity);
        setText(R.id.terrain, planet.terrain);
        setText(R.id.population, getFormattedNumber(planet.population));
        setText(R.id.rotation_period, getFormattedDays(planet.rotationPeriod));
        setText(R.id.orbital_period, getFormattedDays(planet.orbitalPeriod));
        setText(R.id.diameter, getFormattedDistance(planet.diameter));
        setText(R.id.surface_water, getFormattedPercentage(planet.surfaceWater));
    }

    private void addSpeciesFacts() {

        Species species = (Species) model;

        setText(R.id.classification, species.classification);
        setText(R.id.designation, species.designation);
        setText(R.id.skin_colors, species.skinColors);
        setText(R.id.hair_color, species.hairColors);
        setText(R.id.eye_colors, species.eyeColors);
        setText(R.id.language, species.language);
        setText(R.id.average_height, getFormattedHeightCm(species.averageHeight));
        setText(R.id.average_lifespan, getFormattedYears(species.averageLifespan));

        addHomeWorld(((Species) model).homeWorld);
    }


    private void addStarshipFacts() {

        Starship starship = (Starship) model;

        setText(R.id.hyperdrive_rating, starship.hyperdriveRating);
        setText(R.id.mglt, starship.mglt);
        setText(R.id.starship_class, starship.starshipClass);

        addVehicleFacts();
    }

    private void addVehicleFacts() {

        Vehicle vehicle = (Vehicle) model;

        setText(R.id.model, vehicle.model);
        setText(R.id.manufacturer, vehicle.manufacturer);
        setText(R.id.length, getFormattedLengthM(vehicle.length));
        setText(R.id.cargo_capacity, getFormattedTonnage(vehicle.cargoCapacity));
        setText(R.id.cost_in_credits, getFormattedCredits(vehicle.costInCredits));
        setText(R.id.max_atmosphering_speed, getFormattedSpeedKph(vehicle.maxAtmospheringSpeed));
        setText(R.id.crew, getFormattedNumber(vehicle.crew));
        setText(R.id.passengers, getFormattedNumber(vehicle.passengers));
        setText(R.id.consumables, getFormattedNumber(vehicle.consumables));
    }


    protected void addRelatedLists() {

        addRelatedFilms();
        addRelatedPeople();
        addRelatedSpecies();
        addRelatedStartships();
        addRelatedVehicles();
        addRelatedPlanets();
    }

    protected void updateHeroImage(String imagePath, int fallback) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .error(fallback);

        ImageView imageView = findViewById(R.id.hero_image);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(imagePath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }




    private RecyclerView getRelatedList(String title) {

        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.view_detail_related_list, null);
        layout.addView(view);

        ((TextView) view.findViewById(R.id.title)).setText(String.format("%s!", title));
        return view.findViewById(R.id.recycler_view);
    }


    protected void addRelatedFilms() {

        if(model.getFilms() == null || model.getFilms().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedFilmsTitle());
        viewModel.getFilms(model.getFilms()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedPeople() {

        if(model.getPeople() == null || model.getPeople().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedPeopleTitle());
        viewModel.getPeople(model.getPeople()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedSpecies() {

        if(model instanceof People ||
                model.getSpecies() == null ||
                model.getSpecies().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedSpeciesTitle());
        viewModel.getSpecies(model.getSpecies()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedPlanets() {

        if(model.getPlanets() == null || model.getPlanets().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedPlanetsTitle());
        viewModel.getPlanets(model.getPlanets()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedStartships() {

        if(model.getStarships() == null || model.getStarships().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedStarshipsTitle());
        viewModel.getStarships(model.getStarships()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedVehicles() {

        if(model.getVehicles() == null || model.getVehicles().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(model.getRelatedVehiclesTitle());
        viewModel.getVehicles(model.getVehicles()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    private void startDetailActivity(SWModel item) {

        startActivity(DetailExtras.getIntent(this, item));
    }


    protected void addHomeWorld(String homeWorldUrl) {

        if(homeWorldUrl == null)
            return;

        final TextView homeWorldText = detailView.findViewById(R.id.homeworld);

        if (!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Util.getId(homeWorldUrl);

        viewModel.getHomeWorlds(id).observe(this, (Planet planet) -> {

            homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>" + planet.getTitle() + "</u></font>",
                    Build.VERSION.SDK_INT));
            homeWorldText.setOnClickListener(v -> startDetailActivity(planet));
        });
    }

    protected void addSingleSpecies(String speciesUrl) {

        if(speciesUrl == null)
            return;

        final TextView speciesText = detailView.findViewById(R.id.species);

        if (!speciesUrl.substring(0, 4).equals("http")) {
            speciesText.setText(speciesUrl);
            return;
        }

        int id = Util.getId(speciesUrl);

        viewModel.getSingleSpecies(id).observe(this, (Species species) -> {

            speciesText.setText(Html.fromHtml("<font color='#00be3d'><u>" + species.getTitle() + "</u></font>",
                    Build.VERSION.SDK_INT));
            speciesText.setOnClickListener(v -> startDetailActivity(species));
        });
    }

    private String getFormattedDate(String date) {

        return Instant.parse(date)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern(getResources().getString(R.string.detail_date_format)));
    }

    @SuppressLint("DefaultLocale")
    private String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        } catch (NumberFormatException error) {
            return value;
        }
    }


    private String getFormattedHeightCm(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_height_cm, value);
    }

    private String getFormattedKg(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_mass_kg, getFormattedNumber(value));
    }

    private String getFormattedDays(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_period_days, getFormattedNumber(value));
    }

    private String getFormattedDistance(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_distance_km, getFormattedNumber(value));
    }

    private String getFormattedPercentage(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_percent, value);
    }

    private String getFormattedYears(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_duraction_years, getFormattedNumber(value));
    }

    private String getFormattedLengthM(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_length_m, getFormattedNumber(value));
    }

    private String getFormattedTonnage(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_tonnage, getFormattedNumber(value));
    }

    private String getFormattedCredits(String value) {

        if(isUnknown(value))
            return value;

        return (getString(R.string.detail_credits, getFormattedNumber(value)));
    }

    private String getFormattedSpeedKph(String value) {

        if(isUnknown(value))
            return value;

        return getString(R.string.detail_speed_kph, getFormattedNumber(value));
    }

    private boolean isUnknown(String value) {
        return value.equals("") ||
                value.toLowerCase().equals(getString(R.string.detail_na)) ||
                value.toLowerCase().equals(getString(R.string.unknown));

    }

}
