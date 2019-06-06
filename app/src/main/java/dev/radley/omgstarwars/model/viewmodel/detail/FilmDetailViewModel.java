package dev.radley.omgstarwars.model.viewmodel.detail;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.Film;


public class FilmDetailViewModel extends DetailViewModel {


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

}
