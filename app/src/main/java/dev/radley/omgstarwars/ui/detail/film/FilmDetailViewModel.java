package dev.radley.omgstarwars.ui.detail.film;


import android.app.Application;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.ui.detail.common.BaseDetailViewModel;


public class FilmDetailViewModel extends BaseDetailViewModel {


    FilmDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public String getTitle() {
        return ((Film) mModel).title;
    }

    String getDirector() {
        return ((Film) mModel).director;
    }

    String getProducer() {
        return ((Film) mModel).producer;
    }

    String getOpeningCrawl() {
        return ((Film) mModel).openingCrawl;
    }

    String getDateCreated() {

        return Instant.parse(((Film) mModel).created)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
    }

    @Override
    public String getPeopleRowTitle() {
        return mApplication.getString(R.string.category_characters);
    }

    public int getHeroPlaceholderRes() {
        return R.drawable.placeholder_tall;
    }

    public int getHeroFallbackRes() {
        return R.drawable.placeholder_tall;
    }

    @Override
    public boolean hasRelatedPeople() {
        return (mModel.getPeople() != null && mModel.getPeople().size() > 0);
    }

    @Override
    public boolean hasRelatedSpecies() {
        return (mModel.getSpecies() != null && mModel.getSpecies().size() > 0);
    }

    @Override
    public boolean hasRelatedStarships() {
        return (mModel.getStarships() != null && mModel.getStarships().size() > 0);
    }

    @Override
    public boolean hasRelatedVehicles() {
        return (mModel.getVehicles() != null && mModel.getVehicles().size() > 0);
    }

    @Override
    public boolean hasRelatedPlanets() {
        return (mModel.getPlanets() != null && mModel.getPlanets().size() > 0);
    }
}
