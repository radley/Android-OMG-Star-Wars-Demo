package dev.radley.omgstarwars.model.viewmodel.detail;


import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.model.sw.People;


public class PeopleDetailViewModel extends DetailViewModel {


    public String getBirthYear() {
        return ((People) mModel).birthYear;
    }

    public String getHairColor() {
        return ((People) mModel).hairColor;
    }

    public String getSkinColor() {
        return ((People) mModel).skinColor;
    }

    public String getGender() {
        return ((People) mModel).gender;
    }

    public String getHomeworldUrl() {
        return ((People) mModel).homeWorldUrl;
    }

    public String getSpecies() {
        return ((People) mModel).speciesUrls.get(0);
    }

    public String getHeight() {

        String height = ((People) mModel).height;
        if(!height.equals("n/a") && !height.equals("unknown"))
            height += " cm";

        return height;
    }

    public String getMass() {

        String mass = ((People) mModel).mass;
        if(!mass.equals("n/a") && !mass.equals("unknown"))
            mass += " kg";

        return mass;
    }
    

    @Override
    public boolean hasRelatedFilms() {
        return (((People) mModel).filmsUrls != null && ((People) mModel).filmsUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedSpecies() {
        return (((People) mModel).speciesUrls != null && ((People) mModel).speciesUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedStarships() {
        return (((People) mModel).starshipsUrls != null && ((People) mModel).starshipsUrls.size() > 0);
    }

    @Override
    public boolean hasRelatedVehicles() {
        return (((People) mModel).vehiclesUrls != null && ((People) mModel).vehiclesUrls.size() > 0);
    }

}
