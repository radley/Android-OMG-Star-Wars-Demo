package dev.radley.omgstarwars.model.viewmodel.detail;


import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;


public class StarshipDetailViewModel extends VehicleDetailViewModel {


    public String getHyperdriveRating() {
        return ((Starship) mModel).hyperdriveRating;
    }
    public String getMglt() {
        return ((Starship) mModel).mglt;
    }
}
