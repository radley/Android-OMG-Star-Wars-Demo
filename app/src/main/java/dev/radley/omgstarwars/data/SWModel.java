package dev.radley.omgstarwars.data;


import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.utilities.Constants;

public class SWModel implements Serializable {

    public String name;
    public String url;
    public String created;
    public String edited;

    public String getTitle() {
        return name;
    }

    public String getId() {

        // example: https://swapi.co/api/films/2/

        String[] string = url.split("/");
        return string[string.length-1];
    }

    public String getCategoryId() {

        // example: https://swapi.co/api/films/2/

        String[] string = url.split("/");
        return string[string.length-2];
    }

    public String getImagePath() {

        // example: file:///android_asset/films/2.jpg

        return Constants.ASSETS_PATH + getCategoryId() +"/"+ getId() +".jpg";
    }

    public ArrayList<String> getFilms() {
        return null;
    }

    public ArrayList<String> getPeople() {
        return null;
    }

    public ArrayList<String> getSpecies() {
        return null;
    }

    public ArrayList<String> getStarships() {
        return null;
    }

    public ArrayList<String> getVehicles() {
        return null;
    }

    public ArrayList<String> getPlanets() {
        return null;
    }

    public String getRelatedFilmsTitle() {
        return "Films";
    }

    public String getRelatedPeopleTitle() {
        return "People!";
    }

    public String getRelatedPlanetsTitle() {
        return "Planets";
    }

    public String getRelatedSpeciesTitle() {
        return "Species";
    }

    public String getRelatedStarshipsTitle() {
        return "Starships";
    }

    public String getRelatedVehiclesTitle() {
        return "Vehicles";
    }
}
