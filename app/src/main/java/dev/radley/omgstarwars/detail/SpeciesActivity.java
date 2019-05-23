package dev.radley.omgstarwars.detail;


import android.util.Log;
import android.widget.TextView;

import com.swapi.models.Species;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.SWUtil;

public class SpeciesActivity extends DetailActivity{

    protected Species mSpecies;

    @Override
    protected void loadResource(Serializable resource) {

        mSpecies = (Species)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mSpecies.name);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_species);

        ((TextView) mDetailView.findViewById(R.id.classification)).setText(mSpecies.classification);
        ((TextView) mDetailView.findViewById(R.id.designation)).setText(mSpecies.designation);
        ((TextView) mDetailView.findViewById(R.id.average_height)).setText(mSpecies.averageHeight + " cm");
        ((TextView) mDetailView.findViewById(R.id.skin_colors)).setText(mSpecies.skinColors);
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mSpecies.hairColors);
        ((TextView) mDetailView.findViewById(R.id.eye_colors)).setText(mSpecies.eyeColors);
        ((TextView) mDetailView.findViewById(R.id.average_lifespan)).setText(mSpecies.averageLifespan);
        ((TextView) mDetailView.findViewById(R.id.language)).setText(mSpecies.language);

        addHomeWorld(mSpecies.homeWorld);
        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mSpecies.filmsUrls: " + mSpecies.filmsUrls);

        if(mSpecies.filmsUrls != null && mSpecies.filmsUrls.size() > 0)
            addFilmsList(mSpecies.filmsUrls);


    }

}
