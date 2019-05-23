package dev.radley.omgstarwars.detail;


import android.util.Log;
import android.widget.TextView;

import com.swapi.models.Starship;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.SWUtil;

public class StarshipActivity extends DetailActivity{

    protected Starship mStarship;

    @Override
    protected void loadResource(Serializable resource) {

        mStarship = (Starship)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mStarship.name);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_starship);



        ((TextView) mDetailView.findViewById(R.id.model)).setText(mStarship.model);
        ((TextView) mDetailView.findViewById(R.id.starship_class)).setText(mStarship.starshipClass);
        ((TextView) mDetailView.findViewById(R.id.manufacturer)).setText(mStarship.manufacturer);
        ((TextView) mDetailView.findViewById(R.id.cost_in_credits)).setText(getFormattedNumber(mStarship.costInCredits) +" CR");
        ((TextView) mDetailView.findViewById(R.id.length)).setText(getFormattedNumber(mStarship.length) + " m");
        ((TextView) mDetailView.findViewById(R.id.cargo_capacity)).setText(getFormattedNumber(mStarship.cargoCapacity) + " metric tons");
        ((TextView) mDetailView.findViewById(R.id.crew)).setText(getFormattedNumber(mStarship.crew));
        ((TextView) mDetailView.findViewById(R.id.passengers)).setText(getFormattedNumber(mStarship.passengers));
        ((TextView) mDetailView.findViewById(R.id.consumables)).setText(getFormattedNumber(mStarship.consumables));
        ((TextView) mDetailView.findViewById(R.id.hyperdrive_rating)).setText(mStarship.hyperdriveRating);
        ((TextView) mDetailView.findViewById(R.id.mglt)).setText(mStarship.mglt);

        String airspeed = getFormattedNumber(mStarship.maxAtmospheringSpeed);

        if(!airspeed.equals("n/a")) {
            airspeed += " kph";
        }

        ((TextView) mDetailView.findViewById(R.id.max_atmosphering_speed)).setText(airspeed);

        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mStarship.filmsUrls: " + mStarship.filmsUrls);
        Log.d(SWUtil.getTag(), "mStarship.pilotsUrls: " + mStarship.pilotsUrls);

        if(mStarship.filmsUrls != null && mStarship.filmsUrls.size() > 0)
            addFilmsList(mStarship.filmsUrls);

        if(mStarship.pilotsUrls != null && mStarship.pilotsUrls.size() > 0)
            addPeopleList(mStarship.pilotsUrls);



    }

}
