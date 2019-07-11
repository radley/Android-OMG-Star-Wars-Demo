package dev.radley.omgstarwars.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0004J\u000e\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u001fJ\u0011\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006\u00a2\u0006\u0002\u0010\"J\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0$J\u000e\u0010%\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0$J\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\r0$J\u0006\u0010(\u001a\u00020\u0004J\b\u0010)\u001a\u00020\u001bH\u0002J\b\u0010*\u001a\u00020\u001bH\u0002J\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020\u001bH\u0002J$\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020201002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J$\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020501002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J$\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020701002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J$\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020901002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J$\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020;01002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J$\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020=01002\u0006\u00103\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u000e\u0010>\u001a\u00020\u001b2\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010?\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u0004J\u0016\u0010@\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004J\u0014\u0010A\u001a\u00020\u001b2\n\u0010B\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\u00158\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u00a8\u0006C"}, d2 = {"Ldev/radley/omgstarwars/viewmodels/SearchViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "category", "", "categoryIds", "", "[Ljava/lang/String;", "categoryTitles", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "error", "Landroidx/lifecycle/MutableLiveData;", "", "liveData", "Ljava/util/ArrayList;", "Ldev/radley/omgstarwars/models/SWModel;", "loading", "modelList", "query", "service", "Ldev/radley/omgstarwars/network/StarWarsService;", "getService", "()Ldev/radley/omgstarwars/network/StarWarsService;", "setService", "(Ldev/radley/omgstarwars/network/StarWarsService;)V", "dispose", "", "getCategory", "getCategoryByPosition", "position", "", "getCategoryPosition", "getCategoryTitles", "()[Ljava/lang/String;", "getError", "Landroidx/lifecycle/LiveData;", "getItem", "getList", "getLoading", "getQuery", "loadData", "onSearchComplete", "onSearchError", "t", "", "reset", "searchFilms", "Lio/reactivex/Observable;", "Ldev/radley/omgstarwars/models/SWModelList;", "Ldev/radley/omgstarwars/models/Film;", "page", "searchPeople", "Ldev/radley/omgstarwars/models/People;", "searchPlanets", "Ldev/radley/omgstarwars/models/Planet;", "searchSpecies", "Ldev/radley/omgstarwars/models/Species;", "searchStarships", "Ldev/radley/omgstarwars/models/Starship;", "searchVehicles", "Ldev/radley/omgstarwars/models/Vehicle;", "setCategory", "setQuery", "setQueryAndCategory", "updateModelList", "list", "app_debug"})
public final class SearchViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dev.radley.omgstarwars.network.StarWarsService service;
    private java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> modelList;
    private final io.reactivex.disposables.CompositeDisposable compositeDisposable = null;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> liveData;
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> error;
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> loading;
    private final java.lang.String[] categoryIds = null;
    private final java.lang.String[] categoryTitles = null;
    private java.lang.String category;
    private java.lang.String query;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.network.StarWarsService getService() {
        return null;
    }
    
    public final void setService(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsService p0) {
    }
    
    public final void setQueryAndCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    /**
     * Update category and loads new data if liveData is ready
     *
     * @param category String
     */
    public final void setCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategoryByPosition(int position) {
        return null;
    }
    
    public final int getCategoryPosition() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getCategoryTitles() {
        return null;
    }
    
    /**
     * - updates query value if new value
     * - loads new data if liveData is ready and query string has more than one character
     *
     * @param query String
     */
    public final void setQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.models.SWModel getItem(int position) {
        return null;
    }
    
    /**
     * - clears observers & list data
     * - sets error state to false
     */
    private final void reset() {
    }
    
    /**
     * - clears observers
     */
    public final void dispose() {
    }
    
    /**
     * - instantiates liveData if null
     * - loads data
     *
     * @return liveData
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getList() {
        return null;
    }
    
    /**
     * Instantiate error state observable
     *
     * @return error LiveData
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getError() {
        return null;
    }
    
    /**
     * Instantiate loading state observable
     *
     * @return loading LiveData
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoading() {
        return null;
    }
    
    /**
     * - resets data
     * - treigger loading state
     * - loads observable and creates disposableObserver based on category
     * - results forwarded to local methods
     */
    private final void loadData() {
    }
    
    /**
     * Updates modelList using base model class
     *
     * @param list ArrayList
     */
    private final void updateModelList(java.util.ArrayList<?> list) {
    }
    
    /**
     * Called after search finishes concatenated loops
     * - sorts list for best query match
     * - clears loading state
     * - updates liveData
     */
    private final void onSearchComplete() {
    }
    
    /**
     * Called on search error
     * - sets error state to true
     * - sets loading state to false
     * - clears model list
     *
     * @param t Throwable
     */
    private final void onSearchError(java.lang.Throwable t) {
    }
    
    /**
     * Search films based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Film>>
     *   </Film>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Film>> searchFilms(int page, java.lang.String query) {
        return null;
    }
    
    /**
     * Search people based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><People>>
     *   </People>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.People>> searchPeople(int page, java.lang.String query) {
        return null;
    }
    
    /**
     * Search species based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Species>>
     *   </Species>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Species>> searchSpecies(int page, java.lang.String query) {
        return null;
    }
    
    /**
     * Search planets based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Planet>>
     *   </Planet>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Planet>> searchPlanets(int page, java.lang.String query) {
        return null;
    }
    
    /**
     * Search starships based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Starship>>
     *   </Starship>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Starship>> searchStarships(int page, java.lang.String query) {
        return null;
    }
    
    /**
     * Search vehicles based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Vehicle>>
     *   </Vehicle>
     */
    private final io.reactivex.Observable<dev.radley.omgstarwars.models.SWModelList<dev.radley.omgstarwars.models.Vehicle>> searchVehicles(int page, java.lang.String query) {
        return null;
    }
    
    public SearchViewModel() {
        super();
    }
}