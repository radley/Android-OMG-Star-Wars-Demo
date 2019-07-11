package dev.radley.omgstarwars.network


import javax.inject.Inject
import javax.inject.Singleton

import dev.radley.omgstarwars.dagger.DaggerApiComponent

/**
 * Service for loadind data from StarWars API service
 */
@Singleton
class StarWarsService {

    private val BASE_URL = "https://swapi.co/api/"

    @Inject
    lateinit var api: StarWarsApi

    init {
        DaggerApiComponent.create().inject(this)
    }
}