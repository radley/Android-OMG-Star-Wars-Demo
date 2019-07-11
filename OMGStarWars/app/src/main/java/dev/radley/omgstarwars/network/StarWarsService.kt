package dev.radley.omgstarwars.network


import javax.inject.Inject
import javax.inject.Singleton

import dev.radley.omgstarwars.dagger.DaggerApiComponent

/**
 * Service for loadind data from StarWars API service
 */
@Singleton
class StarWarsService {

    @Inject
    lateinit var api: StarWarsApi

    init {
        DaggerApiComponent.create().inject(this)
    }
}