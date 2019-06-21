package dev.radley.omgstarwars.ui.detail.common;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.network.model.SWModel;


public abstract class BaseDetailViewModel extends AndroidViewModel {

    protected SWModel mModel;
    protected Application mApplication;

    public BaseDetailViewModel(@NonNull Application application) {
        super(application);

        mApplication = application;
    }

    public void setModel(Serializable resource) {
        mModel = (SWModel) resource;
    }

    public String getTitle() {
        return mModel.name;
    }

    public String getHeroImage() {
        return mModel.getImagePath();
    }

    public int getHeroPlaceholderRes() {
        return R.drawable.placeholder_tall;
    }

    public int getHeroFallbackRes() {
        return R.drawable.placeholder_tall;
    }

    public boolean hasRelatedFilms() {
        return false;
    }

    public boolean hasRelatedPeople() {
        return false;
    }

    public boolean hasRelatedSpecies() {
        return false;
    }

    public boolean hasRelatedStarships() {
        return false;
    }

    public boolean hasRelatedVehicles() {
        return false;
    }

    public boolean hasRelatedPlanets() {
        return false;
    }

    public ArrayList<String> getFilmUrls() {
        return mModel.getFilms();
    }

    public ArrayList<String> getPeopleUrls() {
        return mModel.getPeople();
    }

    public ArrayList<String> getSpeciesUrls() {
        return mModel.getSpecies();
    }

    public ArrayList<String> getStarshipUrls() {
        return mModel.getStarships();
    }

    public ArrayList<String> getVehicleUrls() {
        return mModel.getVehicles();
    }

    public ArrayList<String> getPlanetUrls() {
        return mModel.getPlanets();
    }


    public String getFilmsRowTitle() {
        return mApplication.getString(R.string.category_films);
    }

    public String getPeopleRowTitle() {
        return mApplication.getString(R.string.category_people);
    }

    public String getPlanetsRowTitle() {
        return mApplication.getString(R.string.category_planets);
    }

    public String getSpeciesRowTitle() {
        return mApplication.getString(R.string.category_species);
    }

    public String getStarshipsRowTitle() {
        return mApplication.getString(R.string.category_starships);
    }

    public String getVehiclesRowTitle() {
        return mApplication.getString(R.string.category_vehicles);
    }


    protected String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        } catch (NumberFormatException error) {
            return value;
        }
    }
}
