package dev.radley.omgstarwars.ui.detail.common;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.ui.detail.film.FilmDetailActivity;
import dev.radley.omgstarwars.ui.detail.people.PeopleDetailActivity;
import dev.radley.omgstarwars.ui.detail.planet.PlanetDetailActivity;
import dev.radley.omgstarwars.ui.detail.RelatedAdapter;
import dev.radley.omgstarwars.ui.detail.RelatedViewModel;
import dev.radley.omgstarwars.ui.detail.species.SpeciesDetailActivity;
import dev.radley.omgstarwars.ui.detail.starship.StarshipDetailActivity;
import dev.radley.omgstarwars.ui.detail.vehicle.VehicleDetailActivity;
import dev.radley.omgstarwars.util.Util;

public abstract class BaseDetailActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected ActionBar mActionBar;
    protected Toolbar mToolbar;
    protected LinearLayout mLayout;
    protected View mDetailView;
    protected RelatedViewModel mRelatedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupFullscreen();
        setupToolbar();

        mActivity = this;

        mRelatedViewModel = ViewModelProviders.of(this).get(RelatedViewModel.class);

        Intent intent = getIntent();
        init(intent.getSerializableExtra(DetailExtras.MODEL));

        mLayout = findViewById(R.id.details_layout);
        populateDetails();
    }

    protected abstract void init(Serializable resource);

    protected abstract void populateDetails();

    protected abstract void addListViews();


    private void setupFullscreen() {

        getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(getResources().getColor(R.color.transparentPrimaryDark, null));
    }

    private void setupToolbar() {
        setContentView(R.layout.activity_detail);
        mToolbar = findViewById(R.id.toolbar);
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

    protected void updateHeroImage(String imagePath, int placeholder, int fallback) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .error(fallback);

        ImageView imageView = (ImageView) findViewById(R.id.hero_image);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(imagePath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    protected void insertDetailView(int view) {

        LayoutInflater factory = LayoutInflater.from(this);
        mDetailView = factory.inflate(view, null);
        mLayout.addView(mDetailView, 0);

    }

    @SuppressLint("DefaultLocale")
    protected String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        } catch (NumberFormatException error) {
            return value;
        }
    }

    private void startDetailActivity(Class myClass, SWModel item) {
        final Intent intent = new Intent(mActivity, myClass);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(DetailExtras.MODEL, item);
        startActivity(intent);
    }

    private RecyclerView getRelatedList(String title) {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.view_detail_related_list, null);
        mLayout.addView(view);

        ((TextView) view.findViewById(R.id.title)).setText(title);
        return view.findViewById(R.id.recycler_view);
    }


    protected void addFilmsList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getFilms(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(FilmDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addPeopleList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getPeople(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(PeopleDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addSpeciesList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getSpecies(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(SpeciesDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addPlanetsList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getPlanets(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(PlanetDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addStarshipsList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getStarships(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(StarshipDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }

    protected void addVehiclesList(ArrayList<String> urlList, String title) {

        RecyclerView recyclerView = getRelatedList(title);
        mRelatedViewModel.getVehicles(urlList).observe(this, list -> {

            RelatedAdapter adapter = new RelatedAdapter(list, item -> {

                startDetailActivity(VehicleDetailActivity.class, item);
            });
            recyclerView.setAdapter(adapter);

        });
    }


    protected void addHomeWorld(String homeWorldUrl) {

        if(homeWorldUrl == null)
            return;

        final TextView homeWorldText = (TextView) mDetailView.findViewById(R.id.homeworld);

        if (!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Util.getId(homeWorldUrl);

        mRelatedViewModel.getHomeWorlds(id).observe(this, (Planet planet) -> {

            homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>" + planet.getTitle() + "</u></font>"));
            homeWorldText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, PlanetDetailActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, planet);
                    startActivity(intent);
                }
            });
        });
    }

    protected void addSingleSpecies(String speciesUrl) {

        if(speciesUrl == null)
            return;

        final TextView speciesText = (TextView) mDetailView.findViewById(R.id.species);

        if (!speciesUrl.substring(0, 4).equals("http")) {
            speciesText.setText(speciesUrl);
            return;
        }

        int id = Util.getId(speciesUrl);

        mRelatedViewModel.getSingleSpecies(id).observe(this, (Species species) -> {

            speciesText.setText(Html.fromHtml("<font color='#00be3d'><u>" + species.getTitle() + "</u></font>"));
            speciesText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, SpeciesDetailActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, species);
                    startActivity(intent);
                }
            });
        });
    }


    public interface OnItemClickListener {
        void onItemClick(SWModel item);
    }

}

