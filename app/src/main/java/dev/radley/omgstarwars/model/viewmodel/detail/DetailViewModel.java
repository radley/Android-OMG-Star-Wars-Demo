package dev.radley.omgstarwars.model.viewmodel.detail;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.network.StarWarsApi;


public abstract class DetailViewModel extends ViewModel {


    protected SWModel mModel;

    public void setModel(Serializable resource) {
        mModel = (SWModel)resource;;
    }

    //public abstract int getHeroFallbackRes();

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

    protected String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        }

        catch(NumberFormatException error) { return value; }
    }
}
