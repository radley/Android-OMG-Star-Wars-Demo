package dev.radley.omgstarwars.detail;


import android.widget.TextView;

import com.swapi.models.Starship;

import java.io.Serializable;

import dev.radley.omgstarwars.R;

public class StarshipActivity extends BaseDetailActivity {

    protected Starship mStarship;

    @Override
    protected void loadResource(Serializable resource) {

        mStarship = (Starship)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mStarship.name +"!");

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_starship);



        ((TextView) mDetailView.findViewById(R.id.model)).setText(mStarship.model);
        ((TextView) mDetailView.findViewById(R.id.starship_class)).setText(mStarship.starshipClass);
        ((TextView) mDetailView.findViewById(R.id.manufacturer)).setText(mStarship.manufacturer);
        ((TextView) mDetailView.findViewById(R.id.crew)).setText(getFormattedNumber(mStarship.crew));
        ((TextView) mDetailView.findViewById(R.id.passengers)).setText(getFormattedNumber(mStarship.passengers));
        ((TextView) mDetailView.findViewById(R.id.consumables)).setText(getFormattedNumber(mStarship.consumables));
        ((TextView) mDetailView.findViewById(R.id.hyperdrive_rating)).setText(mStarship.hyperdriveRating);
        ((TextView) mDetailView.findViewById(R.id.mglt)).setText(mStarship.mglt);


        String length = getFormattedNumber(mStarship.length);

        if(!length.equals("n/a") && !length.equals("unknown")) {
            length += " m";
        }
        ((TextView) mDetailView.findViewById(R.id.length)).setText(length);


        String cargo = getFormattedNumber(mStarship.cargoCapacity);

        if(!cargo.equals("n/a") && !cargo.equals("unknown")) {
            cargo += "  metric tons";
        }
        ((TextView) mDetailView.findViewById(R.id.cargo_capacity)).setText(cargo);


        String price = getFormattedNumber(mStarship.costInCredits);

        if(!price.equals("n/a") && !price.equals("unknown")) {
            price += " CR";
        }
        ((TextView) mDetailView.findViewById(R.id.cost_in_credits)).setText(price);


        String airspeed = getFormattedNumber(mStarship.maxAtmospheringSpeed);

        if(!airspeed.equals("n/a") && !airspeed.equals("unknown")) {
            airspeed += " kph";
        }
        ((TextView) mDetailView.findViewById(R.id.max_atmosphering_speed)).setText(airspeed);

        addListViews();
    }

    @Override

    protected void addListViews() {

        if(mStarship.filmsUrls != null && mStarship.filmsUrls.size() > 0)
            addFilmsList(mStarship.filmsUrls);

        if(mStarship.pilotsUrls != null && mStarship.pilotsUrls.size() > 0)
            addPeopleList(mStarship.pilotsUrls);



    }

}
