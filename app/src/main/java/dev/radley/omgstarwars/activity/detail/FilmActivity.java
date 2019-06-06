package dev.radley.omgstarwars.activity.detail;

import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.viewmodel.detail.FilmDetailViewModel;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FilmActivity extends BaseDetailActivity {

    protected FilmDetailViewModel mViewModel;


    @Override
    protected void init(Serializable resource) {

        mViewModel = ViewModelProviders.of(this).get(FilmDetailViewModel.class);
        mViewModel.setModel(resource);

        mActionBar.setTitle(mViewModel.getTitle());
        updateHeroImage(mViewModel.getHeroImage(), mViewModel.getHeroPlaceholderRes(), mViewModel.getHeroFallbackRes());
    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_film);

        ((TextView) mDetailView.findViewById(R.id.release_date)).setText(mViewModel.getDateCreated());
        ((TextView) mDetailView.findViewById(R.id.director)).setText(mViewModel.getDirector());
        ((TextView) mDetailView.findViewById(R.id.producer)).setText(mViewModel.getProducer());
        ((TextView) mDetailView.findViewById(R.id.opening_crawl)).setText(mViewModel.getOpeningCrawl());

        addListViews();
    }


    @Override
    protected void addListViews() {

        if(mViewModel.hasRelatedPeople()){
//            addPeopleList();
        }

        if(mViewModel.hasRelatedSpecies()) {
//            addSpeciesList();
        }

        if(mViewModel.hasRelatedStarships()) {
//            addStarshipsList();
        }

        if(mViewModel.hasRelatedVehicles()) {
//            addVehiclesList();
        }

        if(mViewModel.hasRelatedPlanets()) {
//            addPlanetsList();
        }
    }
}
