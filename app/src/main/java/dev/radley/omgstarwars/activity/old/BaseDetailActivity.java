package dev.radley.omgstarwars.activity.old;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.activity.detail.FilmActivity;
import dev.radley.omgstarwars.activity.detail.PeopleActivity;
import dev.radley.omgstarwars.activity.detail.PlanetActivity;
import dev.radley.omgstarwars.activity.detail.SpeciesActivity;
import dev.radley.omgstarwars.activity.detail.StarshipActivity;
import dev.radley.omgstarwars.activity.detail.VehicleActivity;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(getResources().getColor(R.color.transparentPrimaryDark,null));

        StarWarsApi.init();

        setContentView(R.layout.activity_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRootLayout = (View) findViewById(R.id.root_layout);
        mRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("");
        }

        mActivity = this;

        Intent intent = getIntent();
        loadResource(intent.getSerializableExtra(DetailExtras.MODEL));
        mLayout = (LinearLayout) findViewById(R.id.detail_layout);
        populateDetails();
    }

    protected abstract void loadResource(Serializable resource);
    protected abstract void updateHero();
    protected abstract void populateDetails();
    protected abstract void addListViews();

    protected void updateHeroImage(String imagePath, int placeholder, int fallback) {

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(false).build();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .fallback(fallback);

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

    protected View getNewHorizontallist() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.view_detail_horizontal_list, null);
        mLayout.addView(view);
        return view;
    }

    protected String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        }

        catch(NumberFormatException error) { return value; }
    }

    protected void addFilmsList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_films));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .fallback(R.drawable.placeholder_tall);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<Film> call = StarWarsApi.getApi().getFilm(id);
            call.enqueue(new Callback<Film>() {


                @Override
                public void onResponse(Call<Film> call, retrofit2.Response<Film> response) {

                    final Film film = response.body();
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(film.title);
                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(Util.getAssetImage("films", film.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, FilmActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailExtras.MODEL, film);
                            intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("films", film.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Film> call, Throwable t) {
                    Log.d(Util.tag, "error: " + t.getMessage());
                }
            });
        }
    }


    protected void addPeopleList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_people));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .fallback(R.drawable.generic_people);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<People> call = StarWarsApi.getApi().getPeople(id);
            call.enqueue(new Callback<People>() {

                 @Override
                 public void onResponse(Call<People> call, retrofit2.Response<People> response) {

                     final People people = response.body();
                     final View view = (View) inflater.inflate(R.layout.card_list, null);
                     final CardView card = view.findViewById(R.id.card);

                     ((TextView) view.findViewById(R.id.title)).setText(people.name);
                     ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                     Glide.with(getApplicationContext())
                             .setDefaultRequestOptions(requestOptions)
                             .load(Uri.parse(Util.getAssetImage("people", people.url)))
                             .transition(DrawableTransitionOptions.withCrossFade())
                             .into(thumbnail);

                     layout.addView(view);

                     card.setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View v) {

                             final Intent intent = new Intent(mActivity, PeopleActivity.class);
                             intent.setAction(Intent.ACTION_VIEW);
                             intent.putExtra(DetailExtras.MODEL, people);
                             intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("people", people.url));

                             startActivity(intent);
                         }
                     });
                 }

                 @Override
                 public void onFailure(Call<People> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });

        }
    }


    protected void addPlanetsList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_planets));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_square)
                .fallback(R.drawable.generic_planet);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<Planet> call = StarWarsApi.getApi().getPlanet(id);
            call.enqueue(new Callback<Planet>() {

                 @Override
                 public void onResponse(Call<Planet> call, retrofit2.Response<Planet> response) {

                     final Planet planet = response.body();
                     final View view = (View) inflater.inflate(R.layout.card_list, null);
                     final CardView card = view.findViewById(R.id.card);

                     ((TextView) view.findViewById(R.id.title)).setText(planet.name);

                     ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                     Glide.with(getApplicationContext())
                             .setDefaultRequestOptions(requestOptions)
                             .load(Uri.parse(Util.getAssetImage("planets", planet.url)))
                             .transition(DrawableTransitionOptions.withCrossFade())
                             .into(thumbnail);

                     layout.addView(view);

                     card.setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View v) {

                             final Intent intent = new Intent(mActivity, PlanetActivity.class);
                             intent.setAction(Intent.ACTION_VIEW);
                             intent.putExtra(DetailExtras.MODEL, planet);
                             intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("planets", planet.url));

                             startActivity(intent);
                         }
                     });

                 }

                 @Override
                 public void onFailure(Call<Planet> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });
        }
    }

    protected void addSpeciesList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_species));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .fallback(R.drawable.generic_species);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<Species> call = StarWarsApi.getApi().getSpecies(id);
            call.enqueue(new Callback<Species>() {

                @Override
                public void onResponse(Call<Species> call, retrofit2.Response<Species> response) {

                    final Species species = response.body();
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(species.name);
                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(Util.getAssetImage("species", species.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailExtras.MODEL, species);
                            intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("species", species.url));

                            startActivity(intent);
                        }
                    });

                }

                @Override
                public void onFailure(Call<Species> call, Throwable t) {
                    Log.d(Util.tag, "error: " + t.getMessage());
                }
            });
        }
    }

    protected void addStarshipsList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_starships));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_wide)
                .fallback(R.drawable.generic_starship);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<Starship> call = StarWarsApi.getApi().getStarship(id);
            call.enqueue(new Callback<Starship>() {

                @Override
                public void onResponse(Call<Starship> call, retrofit2.Response<Starship> response) {

                    final Starship starship = response.body();
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(starship.name);

                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(Util.getAssetImage("starships", starship.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, StarshipActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailExtras.MODEL, starship);
                            intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("starships", starship.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Starship> call, Throwable t) {
                    Log.d(Util.tag, "error: " + t.getMessage());
                }
            });

        }
    }

    protected void addVehiclesList(ArrayList<String> urlList) {
        View view = getNewHorizontallist();
        ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.category_vehicles));

        //create LayoutInflator class
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = view.findViewById(R.id.horizontal_list);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.generic_vehicle);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(Util.getId(urlList.get(i)));

            Call<Vehicle> call = StarWarsApi.getApi().getVehicle(id);
            call.enqueue(new Callback<Vehicle>() {

                @Override
                public void onResponse(Call<Vehicle> call, retrofit2.Response<Vehicle> response) {

                    final Vehicle vehicle  = response.body();
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(vehicle.name);

                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(Util.getAssetImage("vehicles", vehicle.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, VehicleActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailExtras.MODEL, vehicle);
                            intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("vehicles", vehicle.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Vehicle> call, Throwable t) {
                    Log.d(Util.tag, "error: " + t.getMessage());
                }
            });
        }
    }

    protected void addHomeWorld (String homeWorldUrl) {

        final TextView homeWorldText = (TextView) mDetailView.findViewById(R.id.homeworld);

        if(!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Integer.parseInt(Util.getId(homeWorldUrl));

        Call<Planet> call = StarWarsApi.getApi().getPlanet(id);
        call.enqueue(new Callback<Planet>() {

            @Override
            public void onResponse(Call<Planet> call, retrofit2.Response<Planet> response) {

                final Planet planet = response.body();

                homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>"+planet.name+"</u></font>"));
                homeWorldText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, PlanetActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailExtras.MODEL, planet);
                        intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("planets", planet.url));

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

    protected void addBasicSpecies (String speciesUrl) {

        final TextView textView = (TextView) mDetailView.findViewById(R.id.species);

        if(!speciesUrl.substring(0, 4).equals("http")) {
            textView.setText(speciesUrl);
            return;
        }

        int id = Integer.parseInt(Util.getId(speciesUrl));

        Call<Species> call = StarWarsApi.getApi().getSpecies(id);
        call.enqueue(new Callback<Species>() {

            @Override
            public void onResponse(Call<Species> call, retrofit2.Response<Species> response) {

                final Species species = response.body();

                textView.setText(Html.fromHtml("<font color='#00be3d'><u>"+species.name+"</u></font>"));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailExtras.MODEL, species);
                        intent.putExtra(DetailExtras.IMAGE_URL, Util.getAssetImage("species", species.url));

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

