package dev.radley.omgstarwars.detail;


import android.widget.TextView;

import com.swapi.models.Vehicle;

import java.io.Serializable;

import dev.radley.omgstarwars.R;

public class VehicleActivity extends BaseDetailActivity {

    protected Vehicle mVehicle;

    @Override
    protected void loadResource(Serializable resource) {

        mVehicle = (Vehicle)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mVehicle.name +"!");

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_vehicle);



        ((TextView) mDetailView.findViewById(R.id.model)).setText(mVehicle.model);
        ((TextView) mDetailView.findViewById(R.id.manufacturer)).setText(mVehicle.manufacturer);
        ((TextView) mDetailView.findViewById(R.id.crew)).setText(getFormattedNumber(mVehicle.crew));
        ((TextView) mDetailView.findViewById(R.id.passengers)).setText(getFormattedNumber(mVehicle.passengers));
        ((TextView) mDetailView.findViewById(R.id.consumables)).setText(getFormattedNumber(mVehicle.consumables));

        String length = getFormattedNumber(mVehicle.length);

        if(!length.equals("n/a") && !length.equals("unknown")) {
            length += " m";
        }
        ((TextView) mDetailView.findViewById(R.id.length)).setText(length);


        String cargo = getFormattedNumber(mVehicle.cargoCapacity);

        if(!cargo.equals("n/a") && !cargo.equals("unknown")) {
            cargo += "  metric tons";
        }
        ((TextView) mDetailView.findViewById(R.id.cargo_capacity)).setText(cargo);


        String price = getFormattedNumber(mVehicle.costInCredits);

        if(!price.equals("n/a") && !price.equals("unknown")) {
            price += " CR";
        }
        ((TextView) mDetailView.findViewById(R.id.cost_in_credits)).setText(price);


        String airspeed = getFormattedNumber(mVehicle.maxAtmospheringSpeed);

        if(!airspeed.equals("n/a") && !airspeed.equals("unknown")) {
            airspeed += " kph";
        }
        ((TextView) mDetailView.findViewById(R.id.max_atmosphering_speed)).setText(airspeed);
        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mVehicle.filmsUrls != null && mVehicle.filmsUrls.size() > 0)
            addFilmsList(mVehicle.filmsUrls);

        if(mVehicle.pilotsUrls != null && mVehicle.pilotsUrls.size() > 0)
            addPeopleList(mVehicle.pilotsUrls);



    }

}
