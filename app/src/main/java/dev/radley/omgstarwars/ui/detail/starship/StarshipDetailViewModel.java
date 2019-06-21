package dev.radley.omgstarwars.ui.detail.starship;


import android.app.Application;

import androidx.annotation.NonNull;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.ui.detail.vehicle.VehicleDetailViewModel;


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

    @Override
    public int getHeroFallbackRes() {
        return R.drawable.generic_starship;
    }
}
