package dev.radley.omgstarwars.detail;


import android.util.Log;
import android.widget.TextView;

import com.swapi.models.Vehicle;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.SWUtil;

public class VehicleActivity extends DetailActivity{

    protected Vehicle mVehicle;

    @Override
    protected void loadResource(Serializable resource) {

        mVehicle = (Vehicle)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mVehicle.name);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_vehicle);



        ((TextView) mDetailView.findViewById(R.id.model)).setText(mVehicle.model);
        ((TextView) mDetailView.findViewById(R.id.manufacturer)).setText(mVehicle.manufacturer);
        ((TextView) mDetailView.findViewById(R.id.cost_in_credits)).setText(getFormattedNumber(mVehicle.costInCredits) +" CR");
        ((TextView) mDetailView.findViewById(R.id.length)).setText(getFormattedNumber(mVehicle.length) + " m");
        ((TextView) mDetailView.findViewById(R.id.cargo_capacity)).setText(getFormattedNumber(mVehicle.cargoCapacity) + " metric tons");
        ((TextView) mDetailView.findViewById(R.id.crew)).setText(getFormattedNumber(mVehicle.crew));
        ((TextView) mDetailView.findViewById(R.id.passengers)).setText(getFormattedNumber(mVehicle.passengers));
        ((TextView) mDetailView.findViewById(R.id.consumables)).setText(getFormattedNumber(mVehicle.consumables));

        String airspeed = getFormattedNumber(mVehicle.maxAtmospheringSpeed);

        if(!airspeed.equals("n/a")) {
            airspeed += " kph";
        }

        ((TextView) mDetailView.findViewById(R.id.max_atmosphering_speed)).setText(airspeed);

        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mVehicle.filmsUrls: " + mVehicle.filmsUrls);
        Log.d(SWUtil.getTag(), "mVehicle.pilotsUrls: " + mVehicle.pilotsUrls);

        if(mVehicle.filmsUrls != null && mVehicle.filmsUrls.size() > 0)
            addFilmsList(mVehicle.filmsUrls);

        if(mVehicle.pilotsUrls != null && mVehicle.pilotsUrls.size() > 0)
            addPeopleList(mVehicle.pilotsUrls);



    }

}
