package dev.radley.omgstarwars.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.ui.model.UIModel;

public class DetailViewModel extends AndroidViewModel {

    private SWModel mModel;
    protected Application mApplication;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        mApplication = application;
    }

    public void setModel(SWModel resource) {
        mModel = resource;
    }

    public String getTitle() {
        return mModel.getTitle();
    }

    public String getHeroImage() {
        return mModel.getImagePath();
    }

    public int getHeroPlaceholderRes() {
        return UIModel.getPlaceholderImage(mModel.getCategoryId());
    }

    public int getHeroFallbackRes() {
        return UIModel.getFallbackImage(mModel.getCategoryId());
    }


    public boolean hasRelatedFilms() {
        return (mModel.getFilms() != null && mModel.getFilms().size() > 0);
    }
    
    public boolean hasRelatedPeople() {
        return (mModel.getPeople() != null && mModel.getPeople().size() > 0);
    }
    
    public boolean hasRelatedSpecies() {
        return (mModel.getSpecies() != null && mModel.getSpecies().size() > 0);
    }
    
    public boolean hasRelatedStarships() {
        return (mModel.getStarships() != null && mModel.getStarships().size() > 0);
    }
    
    public boolean hasRelatedVehicles() {
        return (mModel.getVehicles() != null && mModel.getVehicles().size() > 0);
    }
    
    public boolean hasRelatedPlanets() {
        return (mModel.getPlanets() != null && mModel.getPlanets().size() > 0);
    }

    public ArrayList<String> getFilms() {
        return mModel.getFilms();
    }

    public ArrayList<String> getPeople() {
        return mModel.getPeople();
    }

    public ArrayList<String> getSpecies() {
        return mModel.getPeople();
    }

    public ArrayList<String> getStarships() {
        return mModel.getStarships();
    }

    public ArrayList<String> getVehicles() {
        return mModel.getVehicles();
    }

    public ArrayList<String> getPlanets() {
        return mModel.getPlanets();
    }

    public String getFilmsTitle() {
        return mModel.getRelatedFilmsTitle();
    }

    public String getPeopleTitle() {
        return mModel.getRelatedPeopleTitle();
    }

    public String getPlanetsTitle() {
        return mModel.getRelatedPlanetsTitle();
    }

    public String getSpeciesTitle() {
        return mModel.getRelatedSpeciesTitle();
    }

    public String getStarshipsTitle() {
        return mModel.getRelatedStarshipsTitle();
    }

    public String getVehiclesTitle() {
        return mModel.getRelatedVehiclesTitle();
    }
}
