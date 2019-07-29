package dev.radley.omgstarwars.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0002\b\u00030\u000ej\u0006\u0012\u0002\b\u0003`\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0002J\u0006\u0010\u001e\u001a\u00020\u001aJ\b\u0010\u001f\u001a\u00020\u001aH\u0002J\b\u0010 \u001a\u00020\u001aH\u0002J\b\u0010!\u001a\u00020\u001aH\u0002J\b\u0010\"\u001a\u00020\u001aH\u0002J\b\u0010#\u001a\u00020\u001aH\u0002J\b\u0010$\u001a\u00020\u001aH\u0002J\u0006\u0010%\u001a\u00020\bJ\u0006\u0010&\u001a\u00020\u0004J\u000e\u0010\'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\bJ$\u0010)\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u001c0*2\u0006\u0010+\u001a\u00020\u0004J\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000b0*J\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0*J\u0012\u0010.\u001a\u00020\b2\b\u0010/\u001a\u0004\u0018\u00010\u0004H\u0002J\u0006\u00100\u001a\u00020\u000bJ\b\u00101\u001a\u00020\u001aH\u0002J\u0006\u00102\u001a\u00020\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u00063"}, d2 = {"Ldev/radley/omgstarwars/viewmodels/CategoryViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "categoryId", "", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "defaultPage", "", "hasLoadError", "Landroidx/lifecycle/MutableLiveData;", "", "isLoading", "liveData", "Ljava/util/ArrayList;", "Ldev/radley/omgstarwars/models/SWModel;", "modelList", "nextUrl", "page", "service", "Ldev/radley/omgstarwars/network/StarWarsService;", "getService", "()Ldev/radley/omgstarwars/network/StarWarsService;", "setService", "(Ldev/radley/omgstarwars/network/StarWarsService;)V", "applyResults", "", "results", "Lkotlin/collections/ArrayList;", "next", "dispose", "fetchFilms", "fetchPeople", "fetchPlanets", "fetchSpecies", "fetchStarships", "fetchVehicles", "getCount", "getId", "getItem", "position", "getList", "Landroidx/lifecycle/LiveData;", "category", "getLoadError", "getLoading", "getNextPageInt", "url", "hasNextPage", "loadData", "loadNextPage", "app_debug"})
public final class CategoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dev.radley.omgstarwars.network.StarWarsService service;
    private final io.reactivex.disposables.CompositeDisposable compositeDisposable = null;
    private final int defaultPage = 1;
    private int page;
    private java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> modelList;
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> liveData;
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> hasLoadError;
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isLoading;
    private java.lang.String nextUrl;
    private java.lang.String categoryId;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.network.StarWarsService getService() {
        return null;
    }
    
    public final void setService(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsService p0) {
    }
    
    /**
     * Instantiate liveData, assign categoryId, and load data
     *
     * @param category String
     * @return liveData list
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<dev.radley.omgstarwars.models.SWModel>> getList(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    /**
     * Reports error state
     *
     * @return boolean
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoadError() {
        return null;
    }
    
    /**
     * Reports isLoading state
     *
     * @return boolean
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoading() {
        return null;
    }
    
    /**
     * Called when recycler view scrolls to bottom
     * - loads nextUrl page of data if there is more
     */
    public final void loadNextPage() {
    }
    
    public final boolean hasNextPage() {
        return false;
    }
    
    /**
     * Take out the trash...
     */
    public final void dispose() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.models.SWModel getItem(int position) {
        return null;
    }
    
    public final int getCount() {
        return 0;
    }
    
    /**
     * Load data based on categoryId
     */
    private final void loadData() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchFilms() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchPeople() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchPlanets() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchSpecies() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchStarships() {
    }
    
    /**
     * Load by page
     * - return results to applyResults()
     * - report error to hasLoadError
     */
    private final void fetchVehicles() {
    }
    
    /**
     * - called by observable onSuccess
     * - adds various model types as base SWModel to modelList
     * - clears isLoading & error states
     * - sets value to liveData
     *
     * @param results Arraylist
     * @param next    String
     */
    private final void applyResults(java.util.ArrayList<?> results, java.lang.String next) {
    }
    
    /**
     * - updates `nextUrl` to `url`
     * - if `nextUrl` exists, parses `url` for `page` number
     *
     * @param url String
     * @return nextUrl page integer (if any)
     */
    private final int getNextPageInt(java.lang.String url) {
        return 0;
    }
    
    public CategoryViewModel() {
        super();
    }
}