package dev.radley.omgstarwars.detail;

import android.util.Log;
import android.widget.TextView;

import com.swapi.models.Film;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.SWUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FilmActivity extends DetailActivity{


    protected Film mFilm;

    @Override
    protected void loadResource(Serializable resource) {

        mFilm = (Film)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mFilm.title);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_film);

        String date = Instant.parse(mFilm.created)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"));

        ((TextView) mDetailView.findViewById(R.id.release_date)).setText(date);
        ((TextView) mDetailView.findViewById(R.id.director)).setText(mFilm.director);
        ((TextView) mDetailView.findViewById(R.id.producer)).setText(mFilm.producer);
        ((TextView) mDetailView.findViewById(R.id.opening_crawl)).setText(mFilm.openingCrawl);

        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mFilm.charactersUrls: " + mFilm.charactersUrls);
        Log.d(SWUtil.getTag(), "mFilm.speciesUrls: " + mFilm.speciesUrls);
        Log.d(SWUtil.getTag(), "mFilm.planetsUrls: " + mFilm.planetsUrls);
        Log.d(SWUtil.getTag(), "mFilm.starshipsUrls: " + mFilm.starshipsUrls);
        Log.d(SWUtil.getTag(), "mFilm.vehiclesUrls: " + mFilm.vehiclesUrls);

        if(mFilm.charactersUrls != null && mFilm.charactersUrls.size() > 0)
            addPeopleList(mFilm.charactersUrls);

        if(mFilm.speciesUrls != null && mFilm.speciesUrls.size() > 0)
            addSpeciesList(mFilm.speciesUrls);

        if(mFilm.planetsUrls != null && mFilm.planetsUrls.size() > 0)
            addPlanetsList(mFilm.planetsUrls);

        if(mFilm.starshipsUrls != null && mFilm.starshipsUrls.size() > 0)
            addStarshipsList(mFilm.starshipsUrls);

        if(mFilm.vehiclesUrls != null && mFilm.vehiclesUrls.size() > 0)
            addVehiclesList(mFilm.vehiclesUrls);

    }

}
