package dev.radley.omgstarwars.ui.detail.planet;


import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.ui.detail.common.BaseDetailActivity;
import dev.radley.omgstarwars.ui.detail.planet.PlanetDetailViewModel;

public class PlanetDetailActivity extends BaseDetailActivity {

    protected PlanetDetailViewModel mViewModel;

    @Override
    protected void init(Serializable resource) {

        mViewModel = ViewModelProviders.of(this).get(PlanetDetailViewModel.class);
        mViewModel.setModel(resource);

        mActionBar.setTitle(mViewModel.getTitle());
        updateHeroImage(mViewModel.getHeroImage(), mViewModel.getHeroPlaceholderRes(), mViewModel.getHeroFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_planet);

        ((TextView) mDetailView.findViewById(R.id.climate)).setText(mViewModel.getClimate());
        ((TextView) mDetailView.findViewById(R.id.gravity)).setText(mViewModel.getGravity());
        ((TextView) mDetailView.findViewById(R.id.terrain)).setText(mViewModel.getTerrain());
        ((TextView) mDetailView.findViewById(R.id.population)).setText(getFormattedNumber(mViewModel.getPopulation()));
        ((TextView) mDetailView.findViewById(R.id.rotation_period)).setText(mViewModel.getRotationPeriod());
        ((TextView) mDetailView.findViewById(R.id.orbital_period)).setText(mViewModel.getOrbitalPeriod());
        ((TextView) mDetailView.findViewById(R.id.diameter)).setText(mViewModel.getDiameter());
        ((TextView) mDetailView.findViewById(R.id.surface_water)).setText(mViewModel.getSurfaceWater());

        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mViewModel.hasRelatedFilms()) {
            addFilmsList(mViewModel.getFilmUrls(), mViewModel.getFilmsRowTitle());
        }

        if(mViewModel.hasRelatedPeople()){
            addPeopleList(mViewModel.getPeopleUrls(), mViewModel.getPeopleRowTitle());
        }

    }

}
