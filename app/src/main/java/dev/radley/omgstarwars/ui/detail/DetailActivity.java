package dev.radley.omgstarwars.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.model.Vehicle;
import dev.radley.omgstarwars.ui.model.UIModel;
import dev.radley.omgstarwars.util.Util;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


public class DetailActivity extends AppCompatActivity {


    private ActionBar mActionBar;
    private LinearLayout mLayout;
    private View mDetailView;
    private RelatedViewModel mViewModel;
    private SWModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();
        setupToolbar();

        Intent intent = getIntent();
        mModel = (SWModel) intent.getSerializableExtra(DetailExtras.MODEL);

        mViewModel = ViewModelProviders.of(this).get(RelatedViewModel.class);
        mViewModel.setModel(mModel);

        mLayout = findViewById(R.id.details_layout);

        mActionBar.setTitle(mModel.getTitle());
        updateHeroImage(mModel.getImagePath(), UIModel.getFallbackImage(mModel.getCategoryId()));
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

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("");
        }
    }

    protected void populateDetails() {

        LayoutInflater factory = LayoutInflater.from(this);

        switch (mModel.getCategoryId()) {
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
        mDetailView = factory.inflate(view, null);
        mLayout.addView(mDetailView, 0);

    }


    private void addFilmFacts() {
        ((TextView) mDetailView.findViewById(R.id.director)).setText(((Film) mModel).director);
        ((TextView) mDetailView.findViewById(R.id.producer)).setText(((Film) mModel).producer);
        ((TextView) mDetailView.findViewById(R.id.opening_crawl)).setText(((Film) mModel).openingCrawl);

        ((TextView) mDetailView.findViewById(R.id.release_date)).setText(getFormattedDate(((Film) mModel).created));
    }

    private void addPeopleFacts() {
        ((TextView) mDetailView.findViewById(R.id.dob)).setText(((People) mModel).birthYear);
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(((People) mModel).hairColor);
        ((TextView) mDetailView.findViewById(R.id.skin_color)).setText(((People) mModel).skinColor);
        ((TextView) mDetailView.findViewById(R.id.gender)).setText(((People) mModel).gender);

        ((TextView) mDetailView.findViewById(R.id.height)).setText(getFormattedHeightCm(((People) mModel).height));
        ((TextView) mDetailView.findViewById(R.id.mass)).setText(getFormattedKg(((People) mModel).mass));

        addHomeWorld(((People) mModel).homeWorldUrl);
        addSingleSpecies(mModel.getSpecies().get(0));
    }

    private void addPlanetFacts() {
        ((TextView) mDetailView.findViewById(R.id.climate)).setText(((Planet) mModel).climate);
        ((TextView) mDetailView.findViewById(R.id.gravity)).setText(((Planet) mModel).gravity);
        ((TextView) mDetailView.findViewById(R.id.terrain)).setText(((Planet) mModel).terrain);

        ((TextView) mDetailView.findViewById(R.id.population)).setText(getFormattedNumber(((Planet) mModel).population));
        ((TextView) mDetailView.findViewById(R.id.rotation_period)).setText(getFormattedDays(((Planet) mModel).rotationPeriod));
        ((TextView) mDetailView.findViewById(R.id.orbital_period)).setText(getFormattedDays(((Planet) mModel).orbitalPeriod));
        ((TextView) mDetailView.findViewById(R.id.diameter)).setText(getFormattedDistance(((Planet) mModel).diameter));
        ((TextView) mDetailView.findViewById(R.id.surface_water)).setText(getFormattedPercentage(((Planet) mModel).surfaceWater));
    }

    private void addSpeciesFacts() {
        ((TextView) mDetailView.findViewById(R.id.classification)).setText(((Species) mModel).classification);
        ((TextView) mDetailView.findViewById(R.id.designation)).setText(((Species) mModel).designation);
        ((TextView) mDetailView.findViewById(R.id.skin_colors)).setText(((Species) mModel).skinColors);
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(((Species) mModel).hairColors);
        ((TextView) mDetailView.findViewById(R.id.eye_colors)).setText(((Species) mModel).eyeColors);
        ((TextView) mDetailView.findViewById(R.id.language)).setText(((Species) mModel).language);

        ((TextView) mDetailView.findViewById(R.id.average_height)).setText(getFormattedHeightCm(((Species) mModel).averageHeight));
        ((TextView) mDetailView.findViewById(R.id.average_lifespan)).setText(getFormattedYears(((Species) mModel).averageLifespan));

        addHomeWorld(((Species) mModel).homeWorld);
    }


    private void addStarshipFacts() {

        ((TextView) mDetailView.findViewById(R.id.hyperdrive_rating)).setText(((Starship) mModel).hyperdriveRating);
        ((TextView) mDetailView.findViewById(R.id.mglt)).setText(((Starship) mModel).mglt);
        ((TextView) mDetailView.findViewById(R.id.starship_class)).setText(((Starship) mModel).starshipClass);
        addVehicleFacts();
    }

    private void addVehicleFacts() {
        ((TextView) mDetailView.findViewById(R.id.model)).setText(((Vehicle) mModel).model);
        ((TextView) mDetailView.findViewById(R.id.manufacturer)).setText(((Vehicle) mModel).manufacturer);

        ((TextView) mDetailView.findViewById(R.id.length)).setText(getFormattedLengthM(((Vehicle) mModel).length));
        ((TextView) mDetailView.findViewById(R.id.cargo_capacity)).setText(getFormattedTonnage(((Vehicle) mModel).cargoCapacity));
        ((TextView) mDetailView.findViewById(R.id.cost_in_credits)).setText(getFormattedCredits(((Vehicle) mModel).costInCredits));
        ((TextView) mDetailView.findViewById(R.id.max_atmosphering_speed)).setText(getFormattedSpeedKph(((Vehicle) mModel).maxAtmospheringSpeed));


        ((TextView) mDetailView.findViewById(R.id.crew)).setText(getFormattedNumber(((Vehicle) mModel).crew));
        ((TextView) mDetailView.findViewById(R.id.passengers)).setText(getFormattedNumber(((Vehicle) mModel).passengers));
        ((TextView) mDetailView.findViewById(R.id.consumables)).setText(getFormattedNumber(((Vehicle) mModel).consumables));
    }

    String getFormattedDate(String date) {

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

    protected void addRelatedLists() {

        addRelatedFilms();
        addRelatedPeople();
        addRelatedSpecies();
        addRelatedPlanets();
        addRelatedStartships();
        addRelatedVehicles();

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
        mLayout.addView(view);

        ((TextView) view.findViewById(R.id.title)).setText(String.format("%s!", title));
        return view.findViewById(R.id.recycler_view);
    }


    protected void addRelatedFilms() {

        if(mModel.getFilms() == null || mModel.getFilms().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedFilmsTitle());
        mViewModel.getFilms(mModel.getFilms()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedPeople() {

        if(mModel.getPeople() == null || mModel.getPeople().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedPeopleTitle());
        mViewModel.getPeople(mModel.getPeople()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedSpecies() {

        if(mModel instanceof People ||
                mModel.getSpecies() == null ||
                mModel.getSpecies().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedSpeciesTitle());
        mViewModel.getSpecies(mModel.getSpecies()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedPlanets() {

        if(mModel.getPlanets() == null || mModel.getPlanets().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedPlanetsTitle());
        mViewModel.getPlanets(mModel.getPlanets()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedStartships() {

        if(mModel.getStarships() == null || mModel.getStarships().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedStarshipsTitle());
        mViewModel.getStarships(mModel.getStarships()).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, this::startDetailActivity);
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addRelatedVehicles() {

        if(mModel.getVehicles() == null || mModel.getVehicles().size() <= 0){
            return;
        }

        RecyclerView recyclerView = getRelatedList(mModel.getRelatedVehiclesTitle());
        mViewModel.getVehicles(mModel.getVehicles()).observe(this, list -> {

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

        final TextView homeWorldText = mDetailView.findViewById(R.id.homeworld);

        if (!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Util.getId(homeWorldUrl);

        mViewModel.getHomeWorlds(id).observe(this, (Planet planet) -> {

            homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>" + planet.getTitle() + "</u></font>"));
            homeWorldText.setOnClickListener(v -> startDetailActivity(planet));
        });
    }

    protected void addSingleSpecies(String speciesUrl) {

        if(speciesUrl == null)
            return;

        final TextView speciesText = mDetailView.findViewById(R.id.species);

        if (!speciesUrl.substring(0, 4).equals("http")) {
            speciesText.setText(speciesUrl);
            return;
        }

        int id = Util.getId(speciesUrl);

        mViewModel.getSingleSpecies(id).observe(this, (Species species) -> {

            speciesText.setText(Html.fromHtml("<font color='#00be3d'><u>" + species.getTitle() + "</u></font>"));
            speciesText.setOnClickListener(v -> startDetailActivity(species));
        });
    }

    public interface OnItemClickListener {
        void onItemClick(SWModel item);
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
