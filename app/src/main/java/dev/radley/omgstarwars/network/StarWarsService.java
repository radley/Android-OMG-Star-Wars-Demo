package dev.radley.omgstarwars.network;


import javax.inject.Inject;
import javax.inject.Singleton;

import dev.radley.omgstarwars.dagger.DaggerApiComponent;

/**
 * Service for loadind data from StarWars API service
 */
@Singleton
public class StarWarsService {

    private String BASE_URL = "https://swapi.co/api/";
    private static volatile StarWarsService instance = null;

    @Inject
    StarWarsApi api;

    public StarWarsService() {

        DaggerApiComponent.create().inject(this);
    }

    public static StarWarsService getInstance() {
        if (instance == null) {
            synchronized(StarWarsService.class) {
                if (instance == null) {
                    instance = new StarWarsService();
                }
            }
        }
        return instance;
    }

    public StarWarsApi getApi() {
        return api;
    }
}
