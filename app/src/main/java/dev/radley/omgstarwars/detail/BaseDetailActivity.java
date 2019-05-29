package dev.radley.omgstarwars.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.swapi.models.Film;
import com.swapi.models.People;
import com.swapi.models.Planet;
import com.swapi.models.Species;
import com.swapi.models.Starship;
import com.swapi.models.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.data.OmgStarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar,null));

        OmgStarWarsApi.init();

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
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setTitle("");

        mActivity = this;

        Intent intent = getIntent();
        loadResource(intent.getSerializableExtra(DetailIntentUtil.RESOURCE));
        updateHeroImage(intent.getStringExtra(DetailIntentUtil.IMAGE_URL), intent.getIntExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, 0));
        updateTitle();

        mLayout = (LinearLayout) findViewById(R.id.detail_layout);
        populateDetails();
    }

    protected abstract void loadResource(Serializable resource);
    protected abstract void updateTitle();
    protected abstract void populateDetails();
    protected abstract void addListViews();


    protected void updateHeroImage(String imagePath, int placeholder) {

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(false).build();

        // we're forced to add a placeholder here
        // because Glide will use placeholder from other instances (i.e. grid) if we don't
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder);

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
                .placeholder(R.drawable.tall_placeholder);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getFilm(id, new Callback<Film>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(Film item, Response response) {

                    final Film film = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.title);
                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("films", item.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, FilmActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, film);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("films", film.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
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
                .placeholder(R.drawable.placeholder_people);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getPeople(id, new Callback<People>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(People item, Response response) {

                    final People people = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.name);
                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("people", people.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, PeopleActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, people);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("people", people.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
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
                .placeholder(R.drawable.placeholder_planet);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getPlanet(id, new Callback<Planet>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(Planet item, Response response) {

                    final Planet planet = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.name);

                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("planets", item.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, PlanetActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, planet);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("planets", planet.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
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
                .placeholder(R.drawable.placeholder_species);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getSpecies(id, new Callback<Species>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(Species item, Response response) {

                    final Species species = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.name);
                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("species", item.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, species);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("species", species.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
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
                .placeholder(R.drawable.placeholder_starship);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getStarship(id, new Callback<Starship>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(Starship item, Response response) {

                    final Starship starship = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.name);

                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("starships", item.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, StarshipActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, starship);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("starships", starship.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
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
                .placeholder(R.drawable.placeholder_vehicle);

        for (int i = 0; i < urlList.size(); i++) {

            final int id = Integer.parseInt(OmgSWUtil.getId(urlList.get(i)));

            OmgStarWarsApi.getApi().getVehicle(id, new Callback<Vehicle>() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void success(Vehicle item, Response response) {

                    final Vehicle vehicle = item;
                    final View view = (View) inflater.inflate(R.layout.card_list, null);
                    final CardView card = view.findViewById(R.id.card);

                    ((TextView) view.findViewById(R.id.title)).setText(item.name);

                    ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                    Glide.with(thumbnail.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(Uri.parse(OmgSWUtil.getAssetImage("vehicles", item.url)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(thumbnail);

                    layout.addView(view);

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final Intent intent = new Intent(mActivity, VehicleActivity.class);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.putExtra(DetailIntentUtil.RESOURCE, vehicle);
                            intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("vehicles", vehicle.url));

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    //Something wrong
                    Log.d(OmgSWUtil.getTag(), "error: " + error);
                }
            });
        }
    }

    protected void addHomeWorld (String homeWorldUrl) {

        final TextView homeWorldText = (TextView) mDetailView.findViewById(R.id.homeworld);

        Log.d(OmgSWUtil.getTag(), "homeWorldUrl: " + homeWorldUrl);
        Log.d(OmgSWUtil.getTag(), "homeWorldUrl.substring(0, 4: " + homeWorldUrl.substring(0, 4));

        if(!homeWorldUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(homeWorldUrl);
            return;
        }

        int id = Integer.parseInt(OmgSWUtil.getId(homeWorldUrl));

        Log.d(OmgSWUtil.getTag(), "id: " + id);

        OmgStarWarsApi.getApi().getPlanet(id, new Callback<Planet>() {

            @Override
            public void success(Planet item, Response response) {

                final Planet planet = item;

                homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>"+item.name+"</u></font>"));
                homeWorldText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, PlanetActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailIntentUtil.RESOURCE, planet);
                        intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("planets", planet.url));

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                homeWorldText.setText("unknown");
            }
        });
    }

    protected void addBasicSpecies (String speciesUrl) {

        final TextView homeWorldText = (TextView) mDetailView.findViewById(R.id.species);

        Log.d(OmgSWUtil.getTag(), "speciesUrl: " + speciesUrl);
        Log.d(OmgSWUtil.getTag(), "speciesUrl.substring(0, 4: " + speciesUrl.substring(0, 4));

        if(!speciesUrl.substring(0, 4).equals("http")) {
            homeWorldText.setText(speciesUrl);
            return;
        }

        int id = Integer.parseInt(OmgSWUtil.getId(speciesUrl));

        Log.d(OmgSWUtil.getTag(), "id: " + id);

        OmgStarWarsApi.getApi().getSpecies(id, new Callback<Species>() {

            @Override
            public void success(Species item, Response response) {

                final Species species = item;

                homeWorldText.setText(Html.fromHtml("<font color='#00be3d'><u>"+item.name+"</u></font>"));
                homeWorldText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Intent intent = new Intent(mActivity, SpeciesActivity.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.putExtra(DetailIntentUtil.RESOURCE, species);
                        intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("species", species.url));

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                homeWorldText.setText("unknown");
            }
        });
    }

}

