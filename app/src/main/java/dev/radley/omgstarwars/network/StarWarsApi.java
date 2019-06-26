package dev.radley.omgstarwars.network;

import dev.radley.omgstarwars.models.Category;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModelList;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * API interface for StarWars API service
 */
public interface StarWarsApi {


    // lists by page

    @GET(Category.FILMS)
    Single<SWModelList<Film>> getFilmsByPage(@Query("page") int var1);

    @GET(Category.PEOPLE)
    Single<SWModelList<People>> getPeopleByPage(@Query("page") int var1);

    @GET(Category.PLANETS)
    Single<SWModelList<Planet>> getPlanetsByPage(@Query("page") int var1);

    @GET(Category.SPECIES)
    Single<SWModelList<Species>> getSpeciesByPage(@Query("page") int var1);

    @GET(Category.STARSHIPS)
    Single<SWModelList<Starship>> getStarshipsByPage(@Query("page") int var1);

    @GET(Category.VEHICLES)
    Single<SWModelList<Vehicle>> getVehiclesByPage(@Query("page") int var1);


    // search results by page

    @GET(Category.FILMS)
    Observable<SWModelList<Film>> searchFilms(@Query("page") int page, @Query("search") String term);

    @GET(Category.PEOPLE)
    Observable<SWModelList<People>> searchPeople(@Query("page") int page, @Query("search") String term);

    @GET(Category.PLANETS)
    Observable<SWModelList<Planet>> searchPlanets(@Query("page") int page, @Query("search") String term);

    @GET(Category.SPECIES)
    Observable<SWModelList<Species>> searchSpecies(@Query("page") int page, @Query("search") String term);

    @GET(Category.STARSHIPS)
    Observable<SWModelList<Starship>> searchStarships(@Query("page") int page, @Query("search") String term);

    @GET(Category.VEHICLES)
    Observable<SWModelList<Vehicle>> searchVehicles(@Query("page") int page, @Query("search") String term);


    // individual items

    @GET(Category.FILMS + "/{id}")
    Single<Film> getFilm(@Path("id") int var1);

    @GET(Category.PEOPLE + "/{id}")
    Single<People> getPeople(@Path("id") int var1);

    @GET(Category.PLANETS + "/{id}")
    Single<Planet> getPlanet(@Path("id") int var1);

    @GET(Category.SPECIES + "/{id}")
    Single<Species> getSpecies(@Path("id") int var1);

    @GET(Category.STARSHIPS + "/{id}")
    Single<Starship> getStarship(@Path("id") int var1);

    @GET(Category.VEHICLES + "/{id}")
    Single<Vehicle> getVehicle(@Path("id") int var1);

}
