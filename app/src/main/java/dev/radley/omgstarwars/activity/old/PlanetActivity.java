package dev.radley.omgstarwars.activity.old;


import android.widget.TextView;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.Planet;

public class PlanetActivity extends BaseDetailActivity {

    protected Planet mPlanet;

    @Override
    protected void loadResource(Serializable resource) {

        mPlanet = (Planet)resource;
        updateHero();
    }

    @Override
    protected void updateHero() {
        mActionBar.setTitle(mPlanet.getTitle());
        updateHeroImage(mPlanet.getImageAsset(), mPlanet.getPlaceholderRes(), mPlanet.getFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_planet);

        ((TextView) mDetailView.findViewById(R.id.climate)).setText(mPlanet.climate);
        ((TextView) mDetailView.findViewById(R.id.gravity)).setText(mPlanet.gravity);
        ((TextView) mDetailView.findViewById(R.id.terrain)).setText(mPlanet.terrain);
        ((TextView) mDetailView.findViewById(R.id.population)).setText(getFormattedNumber(mPlanet.population));

        String rotationPeriod = mPlanet.rotationPeriod;
        if(!rotationPeriod.equals("n/a") && !rotationPeriod.equals("unknown"))
            rotationPeriod += " days";

        ((TextView) mDetailView.findViewById(R.id.rotation_period)).setText(rotationPeriod);

        String orbitalPeriod = mPlanet.orbitalPeriod;
        if(!orbitalPeriod.equals("n/a") && !orbitalPeriod.equals("unknown"))
            orbitalPeriod += " days";

        ((TextView) mDetailView.findViewById(R.id.orbital_period)).setText(orbitalPeriod);

        String diameter = mPlanet.diameter;
        if(!diameter.equals("n/a") && !diameter.equals("unknown"))
            diameter += " km";

        ((TextView) mDetailView.findViewById(R.id.diameter)).setText(diameter);

        String surfaceWater = mPlanet.surfaceWater;
        if(!surfaceWater.equals("n/a") && !surfaceWater.equals("unknown"))
            surfaceWater += "%";


        ((TextView) mDetailView.findViewById(R.id.surface_water)).setText(surfaceWater);

        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mPlanet.filmsUrls != null && mPlanet.filmsUrls.size() > 0)
            addFilmsList(mPlanet.filmsUrls);

        if(mPlanet.residentsUrls != null && mPlanet.residentsUrls.size() > 0)
            addPeopleList(mPlanet.residentsUrls);



    }

}
