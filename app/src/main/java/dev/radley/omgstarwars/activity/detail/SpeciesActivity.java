package dev.radley.omgstarwars.activity.detail;


import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.viewmodel.detail.SpeciesDetailViewModel;

public class SpeciesActivity extends BaseDetailActivity {

    protected SpeciesDetailViewModel mViewModel;

    @Override
    protected void init(Serializable resource) {

        mViewModel = ViewModelProviders.of(this).get(SpeciesDetailViewModel.class);
        mViewModel.setModel(resource);

        mActionBar.setTitle(mViewModel.getTitle());
        updateHeroImage(mViewModel.getHeroImage(), mViewModel.getHeroPlaceholderRes(), mViewModel.getHeroFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_species);

        ((TextView) mDetailView.findViewById(R.id.classification)).setText(mViewModel.getClassification());
        ((TextView) mDetailView.findViewById(R.id.designation)).setText(mViewModel.getDesignation());
        ((TextView) mDetailView.findViewById(R.id.skin_colors)).setText(mViewModel.getSkinColors());
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mViewModel.getHairColors());
        ((TextView) mDetailView.findViewById(R.id.eye_colors)).setText(mViewModel.getEyeColors());
        ((TextView) mDetailView.findViewById(R.id.language)).setText(mViewModel.getLanguage());
        ((TextView) mDetailView.findViewById(R.id.average_height)).setText(mViewModel.getAverageHeight());
        ((TextView) mDetailView.findViewById(R.id.average_lifespan)).setText(mViewModel.getAverageLifespan());

        addHomeWorld(mViewModel.getHomeworldUrl());
        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mViewModel.hasRelatedFilms()) {
            addFilmList(mViewModel.getFilmsUrls(), mViewModel.getFilmsRowTitle());
        }
    }
}
