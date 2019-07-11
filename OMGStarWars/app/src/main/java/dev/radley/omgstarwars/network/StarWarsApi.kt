package dev.radley.omgstarwars.network

import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.Planet
import dev.radley.omgstarwars.models.SWModelList
import dev.radley.omgstarwars.models.Species
import dev.radley.omgstarwars.models.Starship
import dev.radley.omgstarwars.models.Vehicle

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * API interface for StarWars API service
 */
interface StarWarsApi {


    // lists by page

    @GET(Category.FILMS)
    fun getFilmsByPage(@Query("page") var1: Int): Single<SWModelList<Film>>

    @GET(Category.PEOPLE)
    fun getPeopleByPage(@Query("page") var1: Int): Single<SWModelList<People>>

    @GET(Category.PLANETS)
    fun getPlanetsByPage(@Query("page") var1: Int): Single<SWModelList<Planet>>

    @GET(Category.SPECIES)
    fun getSpeciesByPage(@Query("page") var1: Int): Single<SWModelList<Species>>

    @GET(Category.STARSHIPS)
    fun getStarshipsByPage(@Query("page") var1: Int): Single<SWModelList<Starship>>

    @GET(Category.VEHICLES)
    fun getVehiclesByPage(@Query("page") var1: Int): Single<SWModelList<Vehicle>>


    // search results by page

    @GET(Category.FILMS)
    fun searchFilms(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<Film>>

    @GET(Category.PEOPLE)
    fun searchPeople(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<People>>

    @GET(Category.PLANETS)
    fun searchPlanets(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<Planet>>

    @GET(Category.SPECIES)
    fun searchSpecies(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<Species>>

    @GET(Category.STARSHIPS)
    fun searchStarships(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<Starship>>

    @GET(Category.VEHICLES)
    fun searchVehicles(@Query("page") page: Int, @Query("search") term: String): Observable<SWModelList<Vehicle>>


    // individual items

    @GET(Category.FILMS + "/{id}")
    fun getFilm(@Path("id") var1: Int): Single<Film>

    @GET(Category.PEOPLE + "/{id}")
    fun getPeople(@Path("id") var1: Int): Single<People>

    @GET(Category.PLANETS + "/{id}")
    fun getPlanet(@Path("id") var1: Int): Single<Planet>

    @GET(Category.SPECIES + "/{id}")
    fun getSpecies(@Path("id") var1: Int): Single<Species>

    @GET(Category.STARSHIPS + "/{id}")
    fun getStarship(@Path("id") var1: Int): Single<Starship>

    @GET(Category.VEHICLES + "/{id}")
    fun getVehicle(@Path("id") var1: Int): Single<Vehicle>

}
