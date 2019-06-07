package dev.radley.omgstarwars.activity.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;
import dev.radley.omgstarwars.model.viewmodel.detail.row.FilmsRowViewModel;
import dev.radley.omgstarwars.model.viewmodel.detail.row.PeopleRowViewModel;
import dev.radley.omgstarwars.model.viewmodel.detail.row.PlanetsRowViewModel;
import dev.radley.omgstarwars.model.viewmodel.detail.row.SpeciesRowViewModel;
import dev.radley.omgstarwars.model.viewmodel.detail.row.StarshipsRowViewModel;
import dev.radley.omgstarwars.model.viewmodel.detail.row.VehiclesRowViewModel;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public abstract class BaseDetailActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    protected Activity mActivity;
    protected Toolbar mToolbar;
    protected LinearLayout mLayout;
    protected View mDetailView;
    protected View mRootLayout;

    protected FilmsRowViewModel mFilmsRowViewModel;
    protected PeopleRowViewModel mPeopleRowViewModel;
    protected PlanetsRowViewModel mPlanetsRowViewModel;
    protected SpeciesRowViewModel mSpeciesRowViewModel;
    protected StarshipsRowViewModel mStarshipsRowViewModel;
    protected VehiclesRowViewModel mVehiclesRowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//
//        getWindow().setStatusBarColor(getResources().getColor(R.color.transparentPrimaryDark, null));

        setContentView(R.layout.activity_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        mActivity = this;

        StarWarsApi.init();

        Intent intent = getIntent();

        init(intent.getSerializableExtra(DetailExtras.MODEL));

        mLayout = (LinearLayout) findViewById(R.id.detail_layout);

        populateDetails();
    }

    protected abstract void init(Serializable resource);

    protected abstract void populateDetails();

    protected abstract void addListViews();

    protected void updateHeroImage(String imagePath, int placeholder, int fallback) {

        Log.d(Util.tag, "updateHeroImage()");
        Log.d(Util.tag, "imagePath = " +imagePath);
        Log.d(Util.tag, "placeholder = " +placeholder);
        Log.d(Util.tag, "fallback = " +fallback);

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

    protected View getRelatedListRow() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.view_detail_row, null);
        mLayout.addView(view);
        return view;
    }

    protected String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        } catch (NumberFormatException error) {
            return value;
        }
    }


    protected void addFilmList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mFilmsRowViewModel = ViewModelProviders.of(this).get(FilmsRowViewModel.class);
        mFilmsRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displayFilmList(list, row);

            }
        });


    }

    protected void displayFilmList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mFilmsRowViewModel.getPlaceholderRes())
                .error(mFilmsRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final Film film = (Film) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(film.title);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(film.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, FilmActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, film);
                    startActivity(intent);
                }
            });
        }
    }


    protected void addPeopleList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mPeopleRowViewModel = ViewModelProviders.of(this).get(PeopleRowViewModel.class);
        mPeopleRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displayPeopleList(list, row);

            }
        });
    }

    protected void displayPeopleList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mPeopleRowViewModel.getPlaceholderRes())
                .error(mPeopleRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final People people = (People) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(people.name);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(people.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, PeopleActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, people);

                    startActivity(intent);
                }
            });
        }
    }


    protected void addPlanetsList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mPlanetsRowViewModel = ViewModelProviders.of(this).get(PlanetsRowViewModel.class);
        mPlanetsRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displayPlanetList(list, row);

            }
        });
    }

    protected void displayPlanetList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mPlanetsRowViewModel.getPlaceholderRes())
                .error(mPlanetsRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final Planet planet = (Planet) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row_wide, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(planet.name);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(planet.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, PlanetActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, planet);
                    startActivity(intent);
                }
            });
        }
    }


    protected void addSpeciesList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mSpeciesRowViewModel = ViewModelProviders.of(this).get(SpeciesRowViewModel.class);
        mSpeciesRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displaySpeciesList(list, row);

            }
        });
    }

    protected void displaySpeciesList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mSpeciesRowViewModel.getPlaceholderRes())
                .error(mSpeciesRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final Species species = (Species) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(species.name);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(species.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, species);
                    startActivity(intent);
                }
            });
        }
    }


    protected void addStarshipsList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mStarshipsRowViewModel = ViewModelProviders.of(this).get(StarshipsRowViewModel.class);
        mStarshipsRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displayStarshipsList(list, row);

            }
        });
    }

    protected void displayStarshipsList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mStarshipsRowViewModel.getPlaceholderRes())
                .error(mStarshipsRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final Starship starship = (Starship) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row_wide, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(starship.name);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(starship.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, StarshipActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, starship);
                    startActivity(intent);
                }
            });
        }
    }


    protected void addVehiclesList(ArrayList<String> urlList, String title) {

        final View row = getRelatedListRow();
        ((TextView) row.findViewById(R.id.title)).setText(title);

        mVehiclesRowViewModel = ViewModelProviders.of(this).get(VehiclesRowViewModel.class);
        mVehiclesRowViewModel.getList(urlList).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {
                displayVehiclesList(list, row);

            }
        });
    }

    protected void displayVehiclesList(ArrayList<SWModel> list, View row) {

        //create LayoutInflator class
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = row.findViewById(R.id.horizontal_list);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(mVehiclesRowViewModel.getPlaceholderRes())
                .error(mVehiclesRowViewModel.getFallbackRes());

        for (int i = 0; i < list.size(); i++) {

            final Vehicle vehicle = (Vehicle) list.get(i);
            View view = (View) inflater.inflate(R.layout.card_row_wide, null);
            CardView card = view.findViewById(R.id.card);

            ((TextView) view.findViewById(R.id.title)).setText(vehicle.name);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            Glide.with(getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(vehicle.getImageAsset()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            layout.addView(view);

            card.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(mActivity, VehicleActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailExtras.MODEL, vehicle);
                    startActivity(intent);
                }
            });
        }
    }


    protected void addHomeWorld(String homeWorldUrl) {

        final TextView homeWorldText = (TextView) mDetailView.findViewById(R.id.homeworld);

        if (!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Util.getId(homeWorldUrl);

        Call<Planet> call = StarWarsApi.getApi().getPlanet(id);
        call.enqueue(new Callback<Planet>() {

            @Override
            public void onResponse(Call<Planet> call, retrofit2.Response<Planet> response) {

                final Planet planet = response.body();

                homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>" + planet.name + "</u></font>"));
                homeWorldText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, PlanetActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailExtras.MODEL, planet);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                homeWorldText.setText("unknown");
            }
        });
    }

    protected void addBasicSpecies(String speciesUrl) {

        final TextView textView = (TextView) mDetailView.findViewById(R.id.species);

        if (!speciesUrl.substring(0, 4).equals("http")) {
            textView.setText(speciesUrl);
            return;
        }

        int id = Util.getId(speciesUrl);

        Call<Species> call = StarWarsApi.getApi().getSpecies(id);
        call.enqueue(new Callback<Species>() {

            @Override
            public void onResponse(Call<Species> call, retrofit2.Response<Species> response) {

                final Species species = response.body();

                textView.setText(Html.fromHtml("<font color='#00be3d'><u>" + species.name + "</u></font>"));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailExtras.MODEL, species);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Species> call, Throwable t) {
                textView.setText("unknown");
            }
        });
    }

}

