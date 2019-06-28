package dev.radley.omgstarwars.models;


import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.utilities.Constants;

/**
 * Base model for swapi models
 */
public class SWModel implements Serializable {

    public String name;
    public String url;
    public String created;
    public String edited;

    /**
     * Models contain either name or title
     * This makes it uniform
     *
     * @return name or title
     */
    public String getTitle() {
        return name;
    }

    /**
     * Extract id value from url
     *
     * @return id String
     */
    public String getId() {

        // example: https://swapi.co/api/films/2/

        String[] string = url.split("/");
        return string[string.length-1];
    }

    /**
     * Extract category id from url
     *
     * @return category String
     */
    public String getCategoryId() {

        // example: https://swapi.co/api/films/2/

        String[] string = url.split("/");
        return string[string.length-2];
    }

    /**
     * Returns asset image path based on category & id
     *
     * @return path String
     */
    public String getImagePath() {

        // example: file:///android_asset/films/2.jpg

        return Constants.ASSETS_PATH + getCategoryId() +"/"+ getId() +".jpg";
    }

    /**
     * Returns related films list
     * Model class will override with value if true
     *
     * @return null or filmsUrl ArrayList
     */
    public ArrayList<String> getRelatedFilms() {
        return null;
    }

    /**
     * Returns related people list
     * Model class will override with value if true
     *
     * @return null or peopleUrl ArrayList
     */
    public ArrayList<String> getRelatedPeople() {
        return null;
    }

    /**
     * Returns related planets list
     * Model class will override with value if true
     *
     * @return null or planetsUrl ArrayList
     */
    public ArrayList<String> getPlanets() {
        return null;
    }

    /**
     * Returns related species list
     * Model class will override with value if true
     *
     * @return null or speciesUrl ArrayList
     */
    public ArrayList<String> getRelatedSpecies() {
        return null;
    }

    /**
     * Returns related starships list
     * Model class will override with value if true
     *
     * @return null or starshipsUrl ArrayList
     */
    public ArrayList<String> getRelatedStarships() {
        return null;
    }

    /**
     * Returns related vehicles list
     * Model class will override with value if true
     *
     * @return null or vehiclesUrl ArrayList
     */
    public ArrayList<String> getRelatedVehicles() {
        return null;
    }


    /**
     * Returns title for related films section
     *
     * @return "Films"
     */
    public String getRelatedFilmsTitle() {
        return "Films";
    }

    /**
     * Returns title for related films section
     * Model may override for special cases
     * "i.e. "Characters" or "Pilots"
     *
     * @return "People"
     */
    public String getRelatedPeopleTitle() {
        return "People";
    }

    /**
     * Returns title for related planets section
     *
     * @return "Planets"
     */
    public String getRelatedPlanetsTitle() {
        return "Planets";
    }

    /**
     * Returns title for related species section
     *
     * @return "Species"
     */
    public String getRelatedSpeciesTitle() {
        return "Species";
    }

    /**
     * Returns title for related starships section
     *
     * @return "Starships"
     */
    public String getRelatedStarshipsTitle() {
        return "Starships";
    }

    /**
     * Returns title for related vehicles section
     *
     * @return "Vehicles"
     */
    public String getRelatedVehiclesTitle() {
        return "Vehicles";
    }
}
