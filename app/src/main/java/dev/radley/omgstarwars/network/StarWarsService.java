package dev.radley.omgstarwars.network;


import javax.inject.Inject;

import dev.radley.omgstarwars.di.DaggerApiComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// replaces com.swapi.sw.StarWarsService to allow search

public class StarWarsService {

    private String BASE_URL = "https://swapi.co/api/";

    @Inject
    StarWarsApi api;

    public StarWarsService() {

        DaggerApiComponent.create().inject(this);
    }

    public StarWarsApi getApi() {
        return api;
    }
}
