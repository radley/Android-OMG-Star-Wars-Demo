package dev.radley.omgstarwars.di;


import dagger.Module;
import dagger.Provides;
import dev.radley.omgstarwars.network.StarWarsApi;
import dev.radley.omgstarwars.network.StarWarsService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class ApiModule {

    private String BASE_URL = "https://swapi.co/api/";

    @Provides
    StarWarsApi provideStarWarsApi() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(StarWarsApi.class);
    }

    @Provides
    StarWarsService provideStarWarsService() {
        return new StarWarsService();
    }
}
