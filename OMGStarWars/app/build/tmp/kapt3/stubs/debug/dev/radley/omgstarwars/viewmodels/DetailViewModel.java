package dev.radley.omgstarwars.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\b\u0010 \u001a\u0004\u0018\u00010\u001cJ\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020\u001cJ\u0006\u0010%\u001a\u00020\bJ\u000e\u0010&\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\u0006\u0010*\u001a\u00020\u001cJ\u0006\u0010+\u001a\u00020\u001cJ\u0006\u0010,\u001a\u00020\u001cJ\u0006\u0010-\u001a\u00020\u001cJ\u0006\u0010.\u001a\u00020\u001cJ\u0006\u0010/\u001a\u00020\u001cJ\b\u00100\u001a\u0004\u0018\u00010\u001cJ\u0014\u00100\u001a\b\u0012\u0004\u0012\u00020\u00150\u001f2\u0006\u0010\"\u001a\u00020#J\u000e\u00101\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\u000e\u00103\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\u0006\u00105\u001a\u00020\u001cJ\u0006\u00106\u001a\u00020\u001cJ\u000e\u00107\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0007J\u0012\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u001fJ\u000e\u00109\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020;R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u00020\u000f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Ldev/radley/omgstarwars/viewmodels/DetailViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "filmsData", "Landroidx/lifecycle/MutableLiveData;", "Ljava/util/ArrayList;", "Ldev/radley/omgstarwars/models/SWModel;", "homeWorldData", "Ldev/radley/omgstarwars/models/Planet;", "model", "peopleData", "planetsData", "service", "Ldev/radley/omgstarwars/network/StarWarsService;", "getService", "()Ldev/radley/omgstarwars/network/StarWarsService;", "setService", "(Ldev/radley/omgstarwars/network/StarWarsService;)V", "singleSpeciesData", "Ldev/radley/omgstarwars/models/Species;", "speciesData", "starshipsData", "vehiclesData", "dispose", "", "getCategory", "", "getFilms", "getFilmsList", "Landroidx/lifecycle/LiveData;", "getHomeWorld", "getHomeWorlds", "id", "", "getImage", "getModel", "getPeople", "getPeopleList", "getPlanets", "getPlanetsList", "getRelatedFilmsTitle", "getRelatedPeopleTitle", "getRelatedPlanetsTitle", "getRelatedSpeciesTitle", "getRelatedStarshipsTitle", "getRelatedVehiclesTitle", "getSingleSpecies", "getSpecies", "getSpeciesList", "getStarships", "getStarshipsList", "getSubTitle", "getTitle", "getVehicles", "getVehiclesList", "setModel", "resource", "Ljava/io/Serializable;", "app_debug"})
public final class DetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dev.radley.omgstarwars.network.StarWarsService service;
    private io.reactivex.disposables.CompositeDisposable compositeDisposable;
    private dev.radley.omgstarwars.models.SWModel model;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> filmsData;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> peopleData;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> speciesData;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> planetsData;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> starshipsData;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> vehiclesData;
    private androidx.lifecycle.MutableLiveData<dev.radley.omgstarwars.models.Planet> homeWorldData;
    private androidx.lifecycle.MutableLiveData<dev.radley.omgstarwars.models.Species> singleSpeciesData;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.network.StarWarsService getService() {
        return null;
    }
    
    public final void setService(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsService p0) {
    }
    
    public final void dispose() {
    }
    
    public final void setModel(@org.jetbrains.annotations.NotNull()
    java.io.Serializable resource) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.models.SWModel getModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImage() {
        return null;
    }
    
    /**
     * Returns list from model (if any) of related items by type
     *
     * @return ArrayList<String>
     *   </String>
     */
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getFilms() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getPeople() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getPlanets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getSpecies() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getStarships() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getVehicles() {
        return null;
    }
    
    /**
     * People & Species have homeworld value with url link to Planet
     *
     * @return String
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHomeWorld() {
        return null;
    }
    
    /**
     * People has species value with url link to Species
     *
     * @return String
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSingleSpecies() {
        return null;
    }
    
    /**
     * Returns model-specific titles
     *
     * @return String
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedFilmsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedPeopleTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedPlanetsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedSpeciesTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedStarshipsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedVehiclesTitle() {
        return null;
    }
    
    /**
     * iterate through urls, get Film for each, & add to related list
     *
     * @return filmsData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getFilmsList() {
        return null;
    }
    
    /**
     * iterate through urls, get People for each, & add to related list
     *
     * @return peopleData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getPeopleList() {
        return null;
    }
    
    /**
     * iterate through urls, get Planet for each, & add to related list
     *
     * @return planetsData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getPlanetsList() {
        return null;
    }
    
    /**
     * iterate through urls, get Species for each, & add to related list
     *
     * @return speciesData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getSpeciesList() {
        return null;
    }
    
    /**
     * iterate through urls, get Starship for each, & add to related list
     *
     * @return starshipsData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getStarshipsList() {
        return null;
    }
    
    /**
     * iterate through urls, get Vehicle for each, & add to related list
     *
     * @return vehiclesData
     *   </String>
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getVehiclesList() {
        return null;
    }
    
    /**
     * Get Planet object for People/Species homeworld value
     *
     * @param id int
     * @return homeWorldData
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<dev.radley.omgstarwars.models.Planet> getHomeWorlds(int id) {
        return null;
    }
    
    /**
     * Get Species object for People species value
     *
     * @param id int
     * @return singleSpeciesData
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<dev.radley.omgstarwars.models.Species> getSingleSpecies(int id) {
        return null;
    }
    
    public DetailViewModel() {
        super();
    }
}