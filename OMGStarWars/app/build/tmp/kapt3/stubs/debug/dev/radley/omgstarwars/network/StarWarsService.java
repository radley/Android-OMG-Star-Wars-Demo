package dev.radley.omgstarwars.network;

import java.lang.System;

/**
 * Service for loading data from StarWars API service
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\n2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u000f0\n2\u0006\u0010\u0010\u001a\u00020\rJ\"\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\"\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\"\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\"\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\"\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#J\"\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u000f0!2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006)"}, d2 = {"Ldev/radley/omgstarwars/network/StarWarsService;", "", "()V", "api", "Ldev/radley/omgstarwars/network/StarWarsApi;", "getApi", "()Ldev/radley/omgstarwars/network/StarWarsApi;", "setApi", "(Ldev/radley/omgstarwars/network/StarWarsApi;)V", "getFilm", "Lio/reactivex/Single;", "Ldev/radley/omgstarwars/models/Film;", "id", "", "getFilmsByPage", "Ldev/radley/omgstarwars/models/SWModelList;", "page", "getPeople", "Ldev/radley/omgstarwars/models/People;", "getPeopleByPage", "getPlanet", "Ldev/radley/omgstarwars/models/Planet;", "getPlanetsByPage", "getSpecies", "Ldev/radley/omgstarwars/models/Species;", "getSpeciesByPage", "getStarship", "Ldev/radley/omgstarwars/models/Starship;", "getStarshipsByPage", "getVehicle", "Ldev/radley/omgstarwars/models/Vehicle;", "getVehiclesByPage", "searchFilms", "Lio/reactivex/Observable;", "query", "", "searchPeople", "searchPlanets", "searchSpecies", "searchStarships", "searchVehicles", "app_debug"})
@javax.inject.Singleton()
public final class StarWarsService {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dev.radley.omgstarwars.network.StarWarsApi api;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.network.StarWarsApi getApi() {
        return null;
    }
    
    public final void setApi(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsApi p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Film>> getFilmsByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.People>> getPeopleByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Planet>> getPlanetsByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Species>> getSpeciesByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Starship>> getStarshipsByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Vehicle>> getVehiclesByPage(int page) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.Film> getFilm(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.People> getPeople(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.Planet> getPlanet(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.Species> getSpecies(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.Starship> getStarship(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Single<dev.radley.omgstarwars.models.Vehicle> getVehicle(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Film>> searchFilms(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.People>> searchPeople(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Planet>> searchPlanets(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Species>> searchSpecies(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Starship>> searchStarships(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Vehicle>> searchVehicles(int page, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    public StarWarsService() {
        super();
    }
}