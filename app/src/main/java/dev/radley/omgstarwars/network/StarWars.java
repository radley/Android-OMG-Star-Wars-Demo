package dev.radley.omgstarwars.network;

import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.Root;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.Vehicle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


// upgraded from com.swapi.sw.StarWars to add search and Retrofit 2

public interface StarWars {


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

    @GET("/")
    Call<Root> getRootUrls();

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




//    @GET("/people/")
//    void getAllPeople(@Query("page") int var1, Callback<SWModelList<People>> var2);
//
//    @GET("/people/{id}/")
//    void getPeople(@Path("id") int var1, Callback<People> var2);

//    @GET("/films/")
//    void getAllFilms(@Query("page") int var1, Callback<SWModelList<Film>> var2);
//
//    @GET("/films/{id}/")
//    void getFilm(@Path("id") int var1, Callback<Film> var2);

//    @GET("/starships")
//    void getAllStarships(@Query("page") int var1, Callback<SWModelList<Starship>> var2);
//
//    @GET("/starships/{id}/")
//    void getItem(@Path("id") int var1, Callback<Starship> var2);

//    @GET("/vehicles/")
//    void getAllVehicles(@Query("page") int var1, Callback<SWModelList<Vehicle>> var2);
//
//    @GET("/vehicles/{id}/")
//    void getVehicle(@Path("id") int var1, Callback<Vehicle> var2);

//    @GET("/species/")
//    void getAllSpecies(@Query("page") int var1, Callback<SWModelList<Species>> var2);
//
//    @GET("/species/{id}/")
//    void getSpecies(@Path("id") int var1, Callback<Species> var2);

//    @GET("/planets/")
//    void getAllPlanets(@Query("page") int var1, Callback<SWModelList<Planet>> var2);
//
//    @GET("/planets/{id}/")
//    void getItem(@Path("id") int var1, Callback<Planet> var2);


    //    @GET("/films/")
//    public void search(@Query("page") int page, @Query("search") String term,
//                            Callback<SWModelList<Film>> mSearchCallback);

//    @GET("/people/")
//    public void search(@Query("page") int page, @Query("search") String term,
//                             Callback<SWModelList<People>> mSearchCallback);

//    @GET("/planet/")
//    public void searchPlanets(@Query("page") int page, @Query("search") String term,
//                             Callback<SWModelList<Planet>> mSearchCallback);

//    @GET("/species/")
//    public void searchSpecies(@Query("page") int page, @Query("search") String term,
//                              Callback<SWModelList<Species>> mSearchCallback);

//    @GET("/starships/")
//    public void searchStarships(@Query("page") int page, @Query("search") String term,
//                               Callback<SWModelList<Starship>> mSearchCallback);

//    @GET("/vehicles/")
//    public void searchVehicles(@Query("page") int page, @Query("search") String term,
//                              Callback<SWModelList<Vehicle>> mSearchCallback);

//    @GET("/")
//    void getRootUrls(Callback<Root> var1);

}
