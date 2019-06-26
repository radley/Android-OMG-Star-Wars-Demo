package dev.radley.omgstarwars.network;

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



public interface StarWarsApi {


    @GET("films")
    Single<SWModelList<Film>> getFilmsByPage(@Query("page") int var1);

    @GET("people")
    Single<SWModelList<People>> getPeopleByPage(@Query("page") int var1);

    @GET("planets")
    Single<SWModelList<Planet>> getPlanetsByPage(@Query("page") int var1);

    @GET("species")
    Single<SWModelList<Species>> getSpeciesByPage(@Query("page") int var1);

    @GET("starships")
    Single<SWModelList<Starship>> getStarshipsByPage(@Query("page") int var1);

    @GET("vehicles")
    Single<SWModelList<Vehicle>> getVehiclesByPage(@Query("page") int var1);


    @GET("films")
    Observable<SWModelList<Film>> searchFilms(@Query("page") int page, @Query("search") String term);

    @GET("people")
    Observable<SWModelList<People>> searchPeople(@Query("page") int page, @Query("search") String term);

    @GET("planets")
    Observable<SWModelList<Planet>> searchPlanets(@Query("page") int page, @Query("search") String term);

    @GET("species")
    Observable<SWModelList<Species>> searchSpecies(@Query("page") int page, @Query("search") String term);

    @GET("starships")
    Observable<SWModelList<Starship>> searchStarships(@Query("page") int page, @Query("search") String term);

    @GET("vehicles")
    Observable<SWModelList<Vehicle>> searchVehicles(@Query("page") int page, @Query("search") String term);



    @GET("people/{id}")
    Single<People> getPeople(@Path("id") int var1);

    @GET("films/{id}")
    Single<Film> getFilm(@Path("id") int var1);

    @GET("planets/{id}")
    Single<Planet> getPlanet(@Path("id") int var1);

    @GET("starships/{id}")
    Single<Starship> getStarship(@Path("id") int var1);

    @GET("vehicles/{id}")
    Single<Vehicle> getVehicle(@Path("id") int var1);

    @GET("species/{id}")
    Single<Species> getSpecies(@Path("id") int var1);
}
