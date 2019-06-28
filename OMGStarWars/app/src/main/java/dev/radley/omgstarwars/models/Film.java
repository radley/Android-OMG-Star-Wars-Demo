package dev.radley.omgstarwars.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Film model represents a Star Wars single film.
 */
public class Film extends SWModel implements Serializable {

    public String title;
    public String director;
    public String producer;


    @SerializedName("episode_id")
    public int episodeId;

    @SerializedName("opening_crawl")
    public String openingCrawl;

    @SerializedName("species")
    public ArrayList<String> speciesUrls;

    @SerializedName("starships")
    public ArrayList<String> starshipsUrls;

    @SerializedName("vehicles")
    public ArrayList<String> vehiclesUrls;

    @SerializedName("planets")
    public ArrayList<String> planetsUrls;

    @SerializedName("characters")
    public ArrayList<String> charactersUrls;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ArrayList<String> getRelatedPeople() {
        return charactersUrls;
    }

    @Override
    public ArrayList<String> getRelatedSpecies() {
        return speciesUrls;
    }

    @Override
    public ArrayList<String> getRelatedStarships() {
        return starshipsUrls;
    }

    @Override
    public ArrayList<String> getRelatedVehicles() {
        return vehiclesUrls;
    }

    @Override
    public ArrayList<String> getPlanets() {
        return planetsUrls;
    }

    @Override
    public String getRelatedPeopleTitle() {
        return "Characters";
    }


}