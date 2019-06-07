package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.Film;


public class FilmDetailViewModel extends BaseDetailViewModel {


    public FilmDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public String getTitle() {
        return ((Film) mModel).title;
    }

    public String getDirector() {
        return ((Film) mModel).director;
    }

    public String getProducer() {
        return ((Film) mModel).producer;
    }

    public String getOpeningCrawl() {
        return ((Film) mModel).openingCrawl;
    }

    public String getDateCreated() {

        String date = Instant.parse(((Film) mModel).created)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"));

        return date;
    }

    @Override
    public String getPeopleRowTitle() {
        return mApplication.getString(R.string.category_characters);
    }

    @Override
    public boolean hasRelatedPeople() {
        return (((Film) mModel).charactersUrls != null && ((Film) mModel).charactersUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedSpecies() {
        return (((Film) mModel).speciesUrls != null && ((Film) mModel).speciesUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedStarships() {
        return (((Film) mModel).starshipsUrls != null && ((Film) mModel).starshipsUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedVehicles() {
        return (((Film) mModel).vehiclesUrls != null && ((Film) mModel).vehiclesUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedPlanets() {
        return (((Film) mModel).planetsUrls != null && ((Film) mModel).planetsUrls.size() > 0);
    }

    public ArrayList<String> getPeopleUrls() {
        return ((Film) mModel).charactersUrls;
    }

    public ArrayList<String> getSpeciesUrls() {
        return ((Film) mModel).speciesUrls;
    }

    public ArrayList<String> getStarshipUrls() {
        return ((Film) mModel).starshipsUrls;
    }

    public ArrayList<String> getVehicleUrls() {
        return ((Film) mModel).vehiclesUrls;
    }

    public ArrayList<String> getPlanetUrls() {
        return ((Film) mModel).planetsUrls;
    }

}
