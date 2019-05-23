package dev.radley.omgstarwars.detail;


import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.swapi.models.People;
import com.swapi.models.Planet;
import com.swapi.sw.StarWarsApi;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.SWUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PeopleActivity extends DetailActivity{

    protected People mPerson;

    @Override
    protected void loadResource(Serializable resource) {

        mPerson = (People)resource;
    }

    @Override
    protected void updateTitle() {
        mActionBar.setTitle(mPerson.name);

    }

    @Override
    protected void populateDetails() {

        insertDetailView(R.layout.view_detail_person);

        ((TextView) mDetailView.findViewById(R.id.dob)).setText(mPerson.birthYear);
        ((TextView) mDetailView.findViewById(R.id.height)).setText(mPerson.height + " cm");
        ((TextView) mDetailView.findViewById(R.id.mass)).setText(mPerson.mass + " kg");
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mPerson.hairColor);
        ((TextView) mDetailView.findViewById(R.id.skin_color)).setText(mPerson.skinColor);
        ((TextView) mDetailView.findViewById(R.id.gender)).setText(mPerson.gender);

        addHomeWorld(mPerson.homeWorldUrl);
        addBasicSpecies(mPerson.speciesUrls.get(0));

        addListViews();
    }

    @Override
    protected void addListViews() {

        Log.d(SWUtil.getTag(), "mPerson.filmsUrls: " + mPerson.filmsUrls);
        Log.d(SWUtil.getTag(), "mPerson.speciesUrls: " + mPerson.speciesUrls);
        Log.d(SWUtil.getTag(), "mPerson.starshipsUrls: " + mPerson.starshipsUrls);
        Log.d(SWUtil.getTag(), "mPerson.vehiclesUrls: " + mPerson.vehiclesUrls);

        if(mPerson.filmsUrls != null && mPerson.filmsUrls.size() > 0)
            addFilmsList(mPerson.filmsUrls);

        if(mPerson.starshipsUrls != null && mPerson.starshipsUrls.size() > 0)
            addStarshipsList(mPerson.starshipsUrls);

        if(mPerson.vehiclesUrls != null && mPerson.vehiclesUrls.size() > 0)
            addVehiclesList(mPerson.vehiclesUrls);

    }



}
