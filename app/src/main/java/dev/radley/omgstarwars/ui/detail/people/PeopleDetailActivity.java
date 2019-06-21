package dev.radley.omgstarwars.ui.detail.people;


import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.ui.detail.common.BaseDetailActivity;

public class PeopleDetailActivity extends BaseDetailActivity {

    protected PeopleDetailViewModel mViewModel;

    @Override
    protected void init(Serializable resource) {

        mViewModel = ViewModelProviders.of(this).get(PeopleDetailViewModel.class);
        mViewModel.setModel(resource);

        mActionBar.setTitle(mViewModel.getTitle());
        updateHeroImage(mViewModel.getHeroImage(), mViewModel.getHeroPlaceholderRes(), mViewModel.getHeroFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_person);

        ((TextView) mDetailView.findViewById(R.id.dob)).setText(mViewModel.getBirthYear());
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mViewModel.getHairColor());
        ((TextView) mDetailView.findViewById(R.id.skin_color)).setText(mViewModel.getSkinColor());
        ((TextView) mDetailView.findViewById(R.id.gender)).setText(mViewModel.getGender());
        ((TextView) mDetailView.findViewById(R.id.height)).setText(mViewModel.getHeight());
        ((TextView) mDetailView.findViewById(R.id.mass)).setText(mViewModel.getMass());

        addHomeWorld(mViewModel.getHomeworldUrl());
        addSingleSpecies(mViewModel.getSpecies());

        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mViewModel.hasRelatedFilms()) {
            addFilmsList(mViewModel.getFilmUrls(), mViewModel.getFilmsRowTitle());
        }

        if(mViewModel.hasRelatedStarships()) {
            addStarshipsList(mViewModel.getStarshipUrls(), mViewModel.getStarshipsRowTitle());
        }

        if(mViewModel.hasRelatedVehicles()) {
            addVehiclesList(mViewModel.getVehicleUrls(), mViewModel.getVehiclesRowTitle());
        }

    }



}
