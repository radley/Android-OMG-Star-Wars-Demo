package dev.radley.omgstarwars.detail;


import android.util.Log;
import android.widget.TextView;

import com.swapi.models.Planet;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.SWUtil;

public class PlanetActivity extends DetailActivity{

    protected Planet mPlanet;

    @Override
    protected void loadResource(Serializable resource) {

        mPlanet = (Planet)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mPlanet.name);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_planet);



        ((TextView) mDetailView.findViewById(R.id.rotation_period)).setText(mPlanet.rotationPeriod + " days");
        ((TextView) mDetailView.findViewById(R.id.orbital_period)).setText(mPlanet.orbitalPeriod + " days");
        ((TextView) mDetailView.findViewById(R.id.diameter)).setText(getFormattedNumber(mPlanet.diameter) + " km");
        ((TextView) mDetailView.findViewById(R.id.climate)).setText(mPlanet.climate);
        ((TextView) mDetailView.findViewById(R.id.gravity)).setText(mPlanet.gravity);
        ((TextView) mDetailView.findViewById(R.id.terrain)).setText(mPlanet.terrain);
        ((TextView) mDetailView.findViewById(R.id.surface_water)).setText(mPlanet.surfaceWater +"%");
        ((TextView) mDetailView.findViewById(R.id.population)).setText(getFormattedNumber(mPlanet.population));

        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mPlanet.filmsUrls: " + mPlanet.filmsUrls);
        Log.d(SWUtil.getTag(), "mPlanet.residentsUrls: " + mPlanet.residentsUrls);

        if(mPlanet.filmsUrls != null && mPlanet.filmsUrls.size() > 0)
            addFilmsList(mPlanet.filmsUrls);

        if(mPlanet.residentsUrls != null && mPlanet.residentsUrls.size() > 0)
            addPeopleList(mPlanet.residentsUrls);



    }

}
