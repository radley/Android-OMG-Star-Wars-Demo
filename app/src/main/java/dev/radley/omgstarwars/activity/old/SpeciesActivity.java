package dev.radley.omgstarwars.activity.old;


import android.widget.TextView;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.Species;

public class SpeciesActivity extends BaseDetailActivity {

    protected Species mSpecies;

    @Override
    protected void loadResource(Serializable resource) {

        mSpecies = (Species)resource;
        updateHero();
    }

    @Override
    protected void updateHero() {
        mActionBar.setTitle(mSpecies.getTitle());
        updateHeroImage(mSpecies.getImageAsset(), mSpecies.getPlaceholderRes(), mSpecies.getFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_species);

        ((TextView) mDetailView.findViewById(R.id.classification)).setText(mSpecies.classification);
        ((TextView) mDetailView.findViewById(R.id.designation)).setText(mSpecies.designation);
        ((TextView) mDetailView.findViewById(R.id.skin_colors)).setText(mSpecies.skinColors);
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mSpecies.hairColors);
        ((TextView) mDetailView.findViewById(R.id.eye_colors)).setText(mSpecies.eyeColors);
        ((TextView) mDetailView.findViewById(R.id.language)).setText(mSpecies.language);

        String averageHeight = mSpecies.averageHeight;
        if(!averageHeight.equals("n/a") && !averageHeight.equals("unknown"))
            averageHeight += " cm";

        ((TextView) mDetailView.findViewById(R.id.average_height)).setText(averageHeight);

        String averageLifespan = mSpecies.averageHeight;
        if(!averageLifespan.equals("n/a") && !averageLifespan.equals("unknown"))
            averageLifespan += " years";

        ((TextView) mDetailView.findViewById(R.id.average_lifespan)).setText(averageLifespan);

        addHomeWorld(mSpecies.homeWorld);
        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mSpecies.filmsUrls != null && mSpecies.filmsUrls.size() > 0)
            addFilmsList(mSpecies.filmsUrls);


    }

}
