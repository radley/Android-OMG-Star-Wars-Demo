package dev.radley.omgstarwars.activity;


import android.widget.TextView;

import java.io.Serializable;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.model.sw.People;

public class PeopleActivity extends BaseDetailActivity {

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
        ((TextView) mDetailView.findViewById(R.id.hair_color)).setText(mPerson.hairColor);
        ((TextView) mDetailView.findViewById(R.id.skin_color)).setText(mPerson.skinColor);
        ((TextView) mDetailView.findViewById(R.id.gender)).setText(mPerson.gender);

        String height = mPerson.height;
        if(!height.equals("n/a") && !height.equals("unknown"))
            height += " cm";

        String mass = mPerson.mass;
        if(!mass.equals("n/a") && !mass.equals("unknown"))
            mass += " kg";

        ((TextView) mDetailView.findViewById(R.id.height)).setText(height);
        ((TextView) mDetailView.findViewById(R.id.mass)).setText(mass);

        addHomeWorld(mPerson.homeWorldUrl);
        addBasicSpecies(mPerson.speciesUrls.get(0));

        addListViews();
    }

    @Override
    protected void addListViews() {

        if(mPerson.filmsUrls != null && mPerson.filmsUrls.size() > 0)
            addFilmsList(mPerson.filmsUrls);

        if(mPerson.starshipsUrls != null && mPerson.starshipsUrls.size() > 0)
            addStarshipsList(mPerson.starshipsUrls);

        if(mPerson.vehiclesUrls != null && mPerson.vehiclesUrls.size() > 0)
            addVehiclesList(mPerson.vehiclesUrls);

    }



}
