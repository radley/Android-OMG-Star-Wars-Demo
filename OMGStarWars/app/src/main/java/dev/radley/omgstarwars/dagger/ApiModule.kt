package dev.radley.omgstarwars.dagger

import dagger.Module
import dagger.Provides
import dev.radley.omgstarwars.network.StarWarsApi
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.utilities.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Modules
 */
@Module
internal class ApiModule {

    @Provides
    fun provideStarWarsApi(): StarWarsApi {

        return Retrofit.Builder()
                .baseUrl(Constants.SWAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(StarWarsApi::class.java)
    }

    @Provides
    fun provideStarWarsService(): StarWarsService {
        return StarWarsService()
    }
}
