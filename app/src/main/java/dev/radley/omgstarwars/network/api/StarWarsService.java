package dev.radley.omgstarwars.network.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// replaces com.swapi.sw.StarWarsService to allow search

public class StarWarsService {

    public static final String BASE_URL = "https://swapi.co/api/";

    private StarWarsApi api;

    private static StarWarsService sInstance;

    public StarWarsService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(StarWarsApi.class);
    }

    public static void init() {
        sInstance = new StarWarsService();
    }

    public static StarWarsApi getApiInstance() {
        return sInstance.api;
    }

    public StarWarsApi getApi() {
        return api;
    }

    public void setApi(StarWarsApi starWarsApi) {
        sInstance.api = starWarsApi;
    }
}
