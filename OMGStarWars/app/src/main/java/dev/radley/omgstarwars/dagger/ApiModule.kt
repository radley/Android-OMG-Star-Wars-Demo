package dev.radley.omgstarwars.dagger


import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dev.radley.omgstarwars.network.StarWarsApi
import dev.radley.omgstarwars.network.StarWarsService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Modules
 */
@Module
internal class ApiModule {

    private val BASE_URL = "https://swapi.co/api/"

    @Provides
    fun provideStarWarsApi(): StarWarsApi {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
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
