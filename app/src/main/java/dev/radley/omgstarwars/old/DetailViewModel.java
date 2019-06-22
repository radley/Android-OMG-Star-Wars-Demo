package dev.radley.omgstarwars.old;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.data.SWModel;

public class DetailViewModel extends ViewModel {

    private SWModel model;

    public void setModel(SWModel resource) {
        model = resource;
    }

    public String getTitle() {
        return model.getTitle();
    }

    public ArrayList<String> getFilms() {
        return model.getFilms();
    }

    public ArrayList<String> getPeople() {
        return model.getPeople();
    }

    public ArrayList<String> getSpecies() {
        return model.getPeople();
    }

    public ArrayList<String> getStarships() {
        return model.getStarships();
    }

    public ArrayList<String> getVehicles() {
        return model.getVehicles();
    }
}
