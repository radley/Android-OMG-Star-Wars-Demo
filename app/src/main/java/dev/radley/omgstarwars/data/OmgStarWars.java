package dev.radley.omgstarwars.data;

import com.swapi.models.Film;
import com.swapi.models.People;
import com.swapi.models.Planet;
import com.swapi.models.Root;
import com.swapi.models.SWModelList;
import com.swapi.models.Species;
import com.swapi.models.Starship;
import com.swapi.models.Vehicle;
import com.swapi.sw.StarWars;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

// replaces com.swapi.sw.StarWars to allow search
public interface OmgStarWars {

    @GET("/films/")
    public void searchFilms(@Query("page") int page, @Query("search") String term,
                            Callback<SWModelList<Film>> callback);

    @GET("/people/")
    public void searchPeople(@Query("page") int page, @Query("search") String term,
                             Callback<SWModelList<People>> callback);

    @GET("/planet/")
    public void searchPlanets(@Query("page") int page, @Query("search") String term,
                             Callback<SWModelList<Planet>> callback);

    @GET("/species/")
    public void searchSpecies(@Query("page") int page, @Query("search") String term,
                              Callback<SWModelList<Species>> callback);

    @GET("/starships/")
    public void searchStarships(@Query("page") int page, @Query("search") String term,
                               Callback<SWModelList<Starship>> callback);

    @GET("/vehicles/")
    public void searchVehicles(@Query("page") int page, @Query("search") String term,
                              Callback<SWModelList<Vehicle>> callback);

    @GET("/")
    void getRootUrls(Callback<Root> var1);

    @GET("/people/")
    void getAllPeople(@Query("page") int var1, Callback<SWModelList<People>> var2);

    @GET("/people/{id}/")
    void getPeople(@Path("id") int var1, Callback<People> var2);

    @GET("/films/")
    void getAllFilms(@Query("page") int var1, Callback<SWModelList<Film>> var2);

    @GET("/films/{id}/")
    void getFilm(@Path("id") int var1, Callback<Film> var2);

    @GET("/starships")
    void getAllStarships(@Query("page") int var1, Callback<SWModelList<Starship>> var2);

    @GET("/starships/{id}/")
    void getStarship(@Path("id") int var1, Callback<Starship> var2);

    @GET("/vehicles/")
    void getAllVehicles(@Query("page") int var1, Callback<SWModelList<Vehicle>> var2);

    @GET("/vehicles/{id}/")
    void getVehicle(@Path("id") int var1, Callback<Vehicle> var2);

    @GET("/species/")
    void getAllSpecies(@Query("page") int var1, Callback<SWModelList<Species>> var2);

    @GET("/species/{id}/")
    void getSpecies(@Path("id") int var1, Callback<Species> var2);

    @GET("/planets/")
    void getAllPlanets(@Query("page") int var1, Callback<SWModelList<Planet>> var2);

    @GET("/planets/{id}/")
    void getPlanet(@Path("id") int var1, Callback<Planet> var2);

}
