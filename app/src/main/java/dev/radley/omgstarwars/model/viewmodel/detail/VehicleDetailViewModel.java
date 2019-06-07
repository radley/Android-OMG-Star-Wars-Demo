package dev.radley.omgstarwars.model.viewmodel.detail;


import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.Vehicle;


public class VehicleDetailViewModel extends BaseDetailViewModel {


    public VehicleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String getModel() {
        return ((Vehicle) mModel).model;
    }
    public String getManufacturer() {
        return ((Vehicle) mModel).manufacturer;
    }
    public String getCrew() {
        return ((Vehicle) mModel).crew;
    }
    public String getPassengers() {
        return ((Vehicle) mModel).passengers;
    }
    public String getConsumables() {
        return ((Vehicle) mModel).consumables;
    }
    
    public String getLength() {

        String length = getFormattedNumber(((Vehicle) mModel).length);

        if(!length.equals("n/a") && !length.equals("unknown")) {
            length += " m";
        }

        return length;
    }

    public String getCargoCapacity() {

        String cargo = getFormattedNumber(((Vehicle) mModel).cargoCapacity);

        if(!cargo.equals("n/a") && !cargo.equals("unknown")) {
            cargo += "  metric tons";
        }

        return cargo;
    }

    public String getCostInCredits() {

        String price = getFormattedNumber(((Vehicle) mModel).costInCredits);

        if(!price.equals("n/a") && !price.equals("unknown")) {
            price += " CR";
        }

        return price;
    }

    public String getMaxAtmospheringSpeed() {

        String airspeed = getFormattedNumber(((Vehicle) mModel).maxAtmospheringSpeed);

        if(!airspeed.equals("n/a") && !airspeed.equals("unknown")) {
            airspeed += " kph";
        }

        return airspeed;
    }

    @Override
    public String getPeopleRowTitle() {
        return mApplication.getString(R.string.category_pilots);
    }

    @Override
    public boolean hasRelatedFilms() {
        return (((Vehicle) mModel).filmsUrls != null && ((Vehicle) mModel).filmsUrls.size() > 0);
    }


    @Override
    public boolean hasRelatedPeople() {
        return (((Vehicle) mModel).pilotsUrls != null && ((Vehicle) mModel).pilotsUrls.size() > 0);
    }

    public ArrayList<String> getFilmsUrls() {
        return ((Vehicle) mModel).filmsUrls;
    }

    public ArrayList<String> getPeopleUrls() {
        return ((Vehicle) mModel).pilotsUrls;
    }


}
