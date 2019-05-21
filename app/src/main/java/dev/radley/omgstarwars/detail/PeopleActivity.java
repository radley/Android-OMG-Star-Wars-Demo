package dev.radley.omgstarwars.detail;


import android.util.Log;

import com.swapi.models.People;
import com.swapi.sw.StarWarsApi;

import java.io.Serializable;

import dev.radley.omgstarwars.Util.Util;
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

}
