package dev.radley.omgstarwars.network.api;

import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.model.Vehicle;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



// upgraded from com.swapi.sw.StarWarsApi to add search and Retrofit 2

public interface StarWarsApi {

    // new
    @GET("films")
    Single<SWModelList<Film>> getFilmsByPage(@Query("page") int var1);

    @GET("people")
    Single<SWModelList<People>> getPeopleByPage(@Query("page") int var1);

    @GET("species")
    Single<SWModelList<Species>> getSpeciesByPage(@Query("page") int var1);

    @GET("starships")
    Single<SWModelList<Starship>> getStarshipsByPage(@Query("page") int var1);

    @GET("vehicles")
    Single<SWModelList<Vehicle>> getVehiclesByPage(@Query("page") int var1);

    @GET("planets")
    Single<SWModelList<Planet>> getPlanetsByPage(@Query("page") int var1);



    @GET("films")
    Observable<SWModelList<Film>> searchForFilms(@Query("page") int page, @Query("search") String term);


    // old
    @GET("films")
    Call<SWModelList<Film>> searchFilms(@Query("page") int page, @Query("search") String term);

    @GET("people")
    Call<SWModelList<People>> searchPeople(@Query("page") int page, @Query("search") String term);

    @GET("planet")
    Call<SWModelList<Planet>> searchPlanets(@Query("page") int page, @Query("search") String term);

    @GET("species")
    Call<SWModelList<Species>> searchSpecies(@Query("page") int page, @Query("search") String term);

    @GET("starships")
    Call<SWModelList<Starship>> searchStarships(@Query("page") int page, @Query("search") String term);

    @GET("vehicles")
    Call<SWModelList<Vehicle>> searchVehicles(@Query("page") int page, @Query("search") String term);

    @GET("people")
    Call<SWModelList<People>> getAllPeople(@Query("page") int var1);

    @GET("people/{id}")
    Call<People> getPeople(@Path("id") int var1);

    @GET("films")
    Call<SWModelList<Film>> getAllFilms(@Query("page") int var1);

    @GET("films/{id}")
    Call<Film> getFilm(@Path("id") int var1);

    @GET("starships")
    Call<SWModelList<Starship>> getAllStarships(@Query("page") int var1);

    @GET("starships/{id}")
    Call<Starship> getStarship(@Path("id") int var1);

    @GET("vehicles")
    Call<SWModelList<Vehicle>> getAllVehicles(@Query("page") int var1);

    @GET("vehicles/{id}")
    Call<Vehicle> getVehicle(@Path("id") int var1);

    @GET("species")
    Call<SWModelList<Species>> getAllSpecies(@Query("page") int var1);

    @GET("species/{id}")
    Call<Species> getSpecies(@Path("id") int var1);

    @GET("planets")
    Call<SWModelList<Planet>> getAllPlanets(@Query("page") int var1);

    @GET("planets/{id}")
    Call<Planet> getPlanet(@Path("id") int var1);

}
