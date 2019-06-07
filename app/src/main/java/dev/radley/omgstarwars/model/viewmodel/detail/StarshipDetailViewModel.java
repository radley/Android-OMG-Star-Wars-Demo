package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;

import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;


public class StarshipDetailViewModel extends VehicleDetailViewModel {


    public StarshipDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String getHyperdriveRating() {
        return ((Starship) mModel).hyperdriveRating;
    }
    public String getMglt() {
        return ((Starship) mModel).mglt;
    }
}
