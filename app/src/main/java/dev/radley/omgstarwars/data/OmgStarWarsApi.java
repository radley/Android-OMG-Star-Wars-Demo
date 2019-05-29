package dev.radley.omgstarwars.data;

import com.swapi.APIConstants;
import com.swapi.sw.StarWars;
import com.swapi.sw.StarWarsApi;
import com.swapi.sw.StarWarsOkClient;

import retrofit.RestAdapter;

// replaces com.swapi.sw.StarWarsApi to allow search
public class OmgStarWarsApi {

    private OmgStarWars mSwApi;
    private static OmgStarWarsApi sInstance;

    private OmgStarWarsApi() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new StarWarsOkClient())
                .setEndpoint(APIConstants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mSwApi = restAdapter.create(OmgStarWars.class);
    }

    public static void init() {
        sInstance = new OmgStarWarsApi();
    }

    public static OmgStarWars getApi() {
        return sInstance.mSwApi;
    }

    public void setApi(OmgStarWars starWarsApi) {
        sInstance.mSwApi = starWarsApi;
    }
}
