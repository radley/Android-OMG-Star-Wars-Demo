package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.SWModel;


public abstract class BaseDetailViewModel extends AndroidViewModel {

    protected SWModel mModel;
    protected Application mApplication;

    public BaseDetailViewModel(@NonNull Application application) {
        super(application);

        mApplication = application;
    }

    public void setModel(Serializable resource) {
        mModel = (SWModel) resource;
        ;
    }

    public String getTitle() {
        return mModel.name;
    }

    public String getHeroImage() {
        return mModel.getImageAsset();
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
