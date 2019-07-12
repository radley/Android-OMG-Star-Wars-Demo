package dev.radley.omgstarwars.network


import javax.inject.Inject
import javax.inject.Singleton

import dev.radley.omgstarwars.dagger.DaggerApiComponent
import dev.radley.omgstarwars.models.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Service for loading data from StarWars API service
 */
@Singleton
class StarWarsService {

    @Inject
    lateinit var api: StarWarsApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getFilmsByPage(page: Int): Single<SWModelList<Film>> {
        return api.getFilmsByPage(page)
    }

    fun getPeopleByPage(page: Int): Single<SWModelList<People>> {
        return api.getPeopleByPage(page)
    }

    fun getPlanetsByPage(page: Int): Single<SWModelList<Planet>> {
        return api.getPlanetsByPage(page)
    }

    fun getSpeciesByPage(page: Int): Single<SWModelList<Species>> {
        return api.getSpeciesByPage(page)
    }

    fun getStarshipsByPage(page: Int): Single<SWModelList<Starship>> {
        return api.getStarshipsByPage(page)
    }

    fun getVehiclesByPage(page: Int): Single<SWModelList<Vehicle>> {
        return api.getVehiclesByPage(page)
    }

    fun getFilm(id: Int): Single<Film> {
        return api.getFilm(id)
    }

    fun getPeople(id: Int): Single<People> {
        return api.getPeople(id)
    }

    fun getPlanet(id: Int): Single<Planet> {
        return api.getPlanet(id)
    }

    fun getSpecies(id: Int): Single<Species> {
        return api.getSpecies(id)
    }

    fun getStarship(id: Int): Single<Starship> {
        return api.getStarship(id)
    }

    fun getVehicle(id: Int): Single<Vehicle> {
        return api.getVehicle(id)
    }

    fun searchFilms(page: Int, query: String): Observable<SWModelList<Film>> {
        return api.searchFilms(page, query)
    }

    fun searchPeople(page: Int, query: String): Observable<SWModelList<People>> {
        return api.searchPeople(page, query)
    }

    fun searchPlanets(page: Int, query: String): Observable<SWModelList<Planet>> {
        return api.searchPlanets(page, query)
    }

    fun searchSpecies(page: Int, query: String): Observable<SWModelList<Species>> {
        return api.searchSpecies(page, query)
    }

    fun searchStarships(page: Int, query: String): Observable<SWModelList<Starship>> {
        return api.searchStarships(page, query)
    }

    fun searchVehicles(page: Int, query: String): Observable<SWModelList<Vehicle>> {
        return api.searchVehicles(page, query)
    }
}