package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.model.sw.People;


public class PeopleDetailViewModel extends BaseDetailViewModel {


    public PeopleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String getBirthYear() {
        return ((People) mModel).birthYear;
    }

    public String getHairColor() {
        return ((People) mModel).hairColor;
    }

    public String getSkinColor() {
        return ((People) mModel).skinColor;
    }

    public String getGender() {
        return ((People) mModel).gender;
    }

    public String getHomeworldUrl() {
        return ((People) mModel).homeWorldUrl;
    }

    public String getSpecies() {
        return ((People) mModel).speciesUrls.get(0);
    }

    public String getHeight() {

        String height = ((People) mModel).height;
        if(!height.equals("n/a") && !height.equals("unknown"))
            height += " cm";

        return height;
    }

    public String getMass() {

        String mass = ((People) mModel).mass;
        if(!mass.equals("n/a") && !mass.equals("unknown"))
            mass += " kg";

        return mass;
    }

    @Override
    public boolean hasRelatedFilms() {
        return (((People) mModel).filmsUrls != null && ((People) mModel).filmsUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedStarships() {
        return (((People) mModel).starshipsUrls != null && ((People) mModel).starshipsUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedVehicles() {
        return (((People) mModel).vehiclesUrls != null && ((People) mModel).vehiclesUrls.size() > 0);
    }

    public ArrayList<String> getFilmsUrls() {
        return ((People) mModel).filmsUrls;
    }

    public ArrayList<String> getStarshipUrls() {
        return ((People) mModel).starshipsUrls;
    }

    public ArrayList<String> getVehicleUrls() {
        return ((People) mModel).vehiclesUrls;
    }
}
