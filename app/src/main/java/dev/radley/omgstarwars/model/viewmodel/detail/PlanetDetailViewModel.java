package dev.radley.omgstarwars.model.viewmodel.detail;


import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.model.sw.Planet;


public class PlanetDetailViewModel extends DetailViewModel {


    public String getClimate() {
        return ((Planet) mModel).climate;
    }
    public String getGravity() {
        return ((Planet) mModel).gravity;
    }
    public String getTerrain() {
        return ((Planet) mModel).terrain;
    }
    public String getPopulation() {
        return ((Planet) mModel).population;
    }
    
    public String getRotationPeriod() {

        String rotationPeriod = ((Planet) mModel).rotationPeriod;
        if(!rotationPeriod.equals("n/a") && !rotationPeriod.equals("unknown"))
            rotationPeriod += " days";

        return rotationPeriod;
    }

    public String getOrbitalPeriod() {

        String orbitalPeriod = ((Planet) mModel).orbitalPeriod;
        if(!orbitalPeriod.equals("n/a") && !orbitalPeriod.equals("unknown"))
            orbitalPeriod += " days";

        return orbitalPeriod;
    }

    public String getDiameter() {

        String diameter = ((Planet) mModel).diameter;
        if(!diameter.equals("n/a") && !diameter.equals("unknown"))
            diameter += " km";

        return diameter;
    }

    public String getSurfaceWater() {

        String surfaceWater = ((Planet) mModel).surfaceWater;
        if(!surfaceWater.equals("n/a") && !surfaceWater.equals("unknown"))
            surfaceWater += "%";

        return surfaceWater;
    }

    @Override
    public boolean hasRelatedFilms() {
        return (((Planet) mModel).filmsUrls != null && ((Planet) mModel).filmsUrls.size() > 0);
    }


    @Override
    public boolean hasRelatedPeople() {
        return (((Planet) mModel).residentsUrls != null && ((Planet) mModel).residentsUrls.size() > 0);
    }


}
