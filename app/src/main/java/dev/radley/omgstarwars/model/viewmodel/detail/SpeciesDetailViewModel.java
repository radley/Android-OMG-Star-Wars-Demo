package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.model.sw.Species;


public class SpeciesDetailViewModel extends BaseDetailViewModel {


    public SpeciesDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String getClassification() {
        return ((Species) mModel).classification;
    }

    public String getDesignation() {
        return ((Species) mModel).designation;
    }

    public String getSkinColors() {
        return ((Species) mModel).skinColors;
    }

    public String getHairColors() {
        return ((Species) mModel).hairColors;
    }

    public String getEyeColors() {
        return ((Species) mModel).eyeColors;
    }

    public String getLanguage() {
        return ((Species) mModel).language;
    }

    public String getAverageHeight() {

        String averageHeight = ((Species) mModel).averageHeight;
        if(!averageHeight.equals("n/a") && !averageHeight.equals("unknown"))
            averageHeight += " cm";

        return averageHeight;
    }

    public String getAverageLifespan() {

        String averageLifespan = ((Species) mModel).averageLifespan;
        if(!averageLifespan.equals("n/a") && !averageLifespan.equals("unknown"))
            averageLifespan += " years";

        return averageLifespan;
    }

    public String getHomeworldUrl() {
        return ((Species) mModel).homeWorld;
    }

    @Override
    public boolean hasRelatedFilms() {
        return (((Species) mModel).filmsUrls != null && ((Species) mModel).filmsUrls.size() > 0);
    }

    public ArrayList<String> getFilmsUrls() {
        return ((Species) mModel).filmsUrls;
    }

}
