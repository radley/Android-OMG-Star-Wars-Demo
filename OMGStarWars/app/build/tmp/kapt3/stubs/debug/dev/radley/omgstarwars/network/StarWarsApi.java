package dev.radley.omgstarwars.network;

import java.lang.System;

/**
 * API interface for StarWars API service
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u001e\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J(\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'J(\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'J(\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'J(\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'J(\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'J(\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\b0\u00192\b\b\u0001\u0010\u001a\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\'\u00a8\u0006\""}, d2 = {"Ldev/radley/omgstarwars/network/StarWarsApi;", "", "getFilm", "Lio/reactivex/Single;", "Ldev/radley/omgstarwars/models/Film;", "var1", "", "getFilmsByPage", "Ldev/radley/omgstarwars/models/SWModelList;", "getPeople", "Ldev/radley/omgstarwars/models/People;", "getPeopleByPage", "getPlanet", "Ldev/radley/omgstarwars/models/Planet;", "getPlanetsByPage", "getSpecies", "Ldev/radley/omgstarwars/models/Species;", "getSpeciesByPage", "getStarship", "Ldev/radley/omgstarwars/models/Starship;", "getStarshipsByPage", "getVehicle", "Ldev/radley/omgstarwars/models/Vehicle;", "getVehiclesByPage", "searchFilms", "Lio/reactivex/Observable;", "page", "term", "", "searchPeople", "searchPlanets", "searchSpecies", "searchStarships", "searchVehicles", "app_debug"})
public abstract interface StarWarsApi {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "films")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Film>> getFilmsByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "people")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.People>> getPeopleByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "planets")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Planet>> getPlanetsByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "species")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Species>> getSpeciesByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "starships")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Starship>> getStarshipsByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "vehicles")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Vehicle>> getVehiclesByPage(@retrofit2.http.Query(value = "page")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "films")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Film>> searchFilms(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "people")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.People>> searchPeople(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "planets")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Planet>> searchPlanets(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "species")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Species>> searchSpecies(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "starships")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Starship>> searchStarships(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "vehicles")
    public abstract io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Vehicle>> searchVehicles(@retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "search")
    java.lang.String term);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "films/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.Film> getFilm(@retrofit2.http.Path(value = "id")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "people/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.People> getPeople(@retrofit2.http.Path(value = "id")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "planets/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.Planet> getPlanet(@retrofit2.http.Path(value = "id")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "species/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.Species> getSpecies(@retrofit2.http.Path(value = "id")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "starships/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.Starship> getStarship(@retrofit2.http.Path(value = "id")
    int var1);
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "vehicles/{id}")
    public abstract io.reactivex.Single<dev.radley.omgstarwars.models.Vehicle> getVehicle(@retrofit2.http.Path(value = "id")
    int var1);
}