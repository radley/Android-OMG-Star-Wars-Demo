package dev.radley.omgstarwars.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dev.radley.omgstarwars.models.Category;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.dagger.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.SWModelList;
import dev.radley.omgstarwars.utilities.SortUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ViewModel for SearchActivity
 *
 * - provies liveData for list, loading, and error
 * - directs search based on category and query
 * - allows user to tap result item and view more in DetailActivity
 *      - passes item's model in Bundle
 *
 * Swapi search results are not optimized, so we grab ALL search result pages using concatMap
 * and then sort them for best match:
 *
 * 1. Explicit query string match (i.e for "star" query -> "star destroyer", "death star")
 *    - sort by index position (i.e "star destroyer" before "death star")
 *
 * 2. Title contains query string (i.e "starfighter", "starship")
 *    - sort by index position (i.e "jedi starfighter" before "naboo royal starship")
 *
 * 3. Starship special case (swapi also searches in Starship.model)
 *    a. Explicit query string match in .model
 *    b. <code>.model</code> title contains query string
 *
 * 4. anything left over (shouldn't happen, but future proof)
 *
 */
public class SearchViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    private ArrayList<SWModel> modelList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<ArrayList<SWModel>> liveData;
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private static String[] categoryIds = Category.categoryIds;
    private static String[] categoryTitles = Category.categoryTitles;
    private String category = categoryIds[0];
    private String query = "";

    /**
     * - inject service
     * -
     */
    public SearchViewModel() {

        DaggerApiComponent.create().inject(this);
    }

    /**
     * Update category and loads new data if liveData is ready
     *
     * @param category String
     */
    public void setCategory(String category) {

        this.category = category;
        if(liveData != null) {
            loadData();
        }
    }
    public String getCategory() {
        return category;
    }

    public String getCategoryByPosition(int position) {
        return categoryIds[position];
    }

    public int getCategoryPosition() {

        for (int i = 0; i < categoryIds.length; i++)
            if (categoryIds[i].equals(category)) {
                return i;
            }
        return -1;
    }

    public String[] getCategoryTitles() {
        return categoryTitles;
    }


    /**
     * - updates query value if new value
     * - loads new data if liveData is ready and query string has more than one character
     *
     * @param query String
     */
    public void setQuery(String query) {

        if(this.query.equals(query)) {
            return;
        }

        this.query = query;

        if(liveData != null && query.length() > 1) {
            loadData();
        }
    }

    public String getQuery() {
        return query;
    }

    public SWModel getItem(int position) {
        return modelList.get(position);
    }

    /**
     * - clears observers & list data
     * - sets error state to false
     */
    private void reset() {

        compositeDisposable.clear();
        modelList.clear();
        liveData.setValue(modelList);
        error.setValue(false);
    }

    /**
     * - clears observers
     */
    public void dispose() {

        compositeDisposable.dispose();
    }


    /**
     * - instantiates liveData if null
     * - loads data
     *
     * @return liveData
     */
    public LiveData<ArrayList<SWModel>> getList() {

        if(liveData == null) {
            liveData = new MutableLiveData<>();
        }

        loadData();
        return liveData;
    }

    /**
     * Instantiate error state observable
     *
     * @return error LiveData
     */
    public LiveData<Boolean> getError() {
        return error;
    }
    

    /**
     * Instantiate loading state observable
     *
     * @return loading LiveData
     */
    public LiveData<Boolean> getLoading() {
        return loading;
    }


    /**
     * - resets data
     * - treigger loading state
     * - loads observable and creates disposableObserver based on category
     * - results forwarded to local methods
     */
    private void loadData() {

        Timber.d("loadData");

        reset();
        loading.setValue(true);

        // subscribe to observable based on category
        switch (category) {

            case Category.FILMS:
                Timber.d("films");
                compositeDisposable.add(searchFilms(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<Film>>() {

                            @Override
                            public void onNext(SWModelList<Film> list) {
                                Timber.d("onNext");
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.d("onError");
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                Timber.d("onComplete");
                                onSearchComplete();
                            }
                        }));
                break;

            case Category.PEOPLE:
                compositeDisposable.add(searchPeople(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<People>>() {

                            @Override
                            public void onNext(SWModelList<People> list) {
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                onSearchComplete();
                            }
                        }));
                break;

            case Category.PLANETS:
                compositeDisposable.add(searchPlanets(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<Planet>>() {

                            @Override
                            public void onNext(SWModelList<Planet> list) {
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                onSearchComplete();
                            }
                        }));
                break;

            case Category.SPECIES:
                compositeDisposable.add(searchSpecies(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<Species>>() {

                            @Override
                            public void onNext(SWModelList<Species> list) {
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                onSearchComplete();
                            }
                        }));
                break;

            case Category.STARSHIPS:
                compositeDisposable.add(searchStarships(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<Starship>>() {

                            @Override
                            public void onNext(SWModelList<Starship> list) {
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                onSearchComplete();
                            }
                        }));
                break;

            case Category.VEHICLES:
                compositeDisposable.add(searchVehicles(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SWModelList<Vehicle>>() {

                            @Override
                            public void onNext(SWModelList<Vehicle> list) {
                                updateModelList(list.results);
                            }

                            @Override
                            public void onError(Throwable t) {
                                onSearchError(t);
                            }

                            @Override
                            public void onComplete() {
                                onSearchComplete();
                            }
                        }));
                break;

        }
    }

    /**
     * Updates modelList using base model class
     * 
     * @param list ArrayList<?>
     */
    private void updateModelList(ArrayList<?> list) {

        Timber.d("updateModelList");

        for(int i = 0; i < list.size(); i++){

            // add various model types as SWModel to list
            Object item = list.get(i);
            if(item instanceof SWModel){
                modelList.add((SWModel) item);
            }
        }
    }

    /**
     * Called after search finishes concatenated loops
     * - sorts list for best query match
     * - clears loading state
     * - updates liveData
     */
    private void onSearchComplete() {

        Timber.d("onSearchComplete");

        // sort model list based on result matches
        if(modelList.size() > 0) {
            ArrayList<SWModel> sortList = SortUtils.sortForBestQueryMatch(modelList, query);

            // must keep adapter reference
            modelList.clear();
            modelList.addAll(sortList);
        }

        loading.setValue(false);
        liveData.setValue(modelList);
    }

    /**
     * Called on search error
     * - sets error state to true
     * - sets loading state to false
     * - clears model list
     * 
     * @param t Throwable
     */
    private void onSearchError(Throwable t) {

        Timber.e("error: %s", t.getMessage());

        // reset list and show error
        error.setValue(true);
        loading.setValue(false);

        modelList.clear();
        liveData.setValue(modelList);
    }

    /**
     * Search films based on query
     * - concats result pages
     *
      * @param page int
     * @param query String
     * @return Observable<SWModelList<Film>>
     */
    private Observable<SWModelList<Film>> searchFilms(final int page, final String query) {

        return service.getApi().searchFilms(page, query)
                .concatMap((Function<SWModelList<Film>, Observable<SWModelList<Film>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchFilms(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Search people based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList<People>>
     */
    private Observable<SWModelList<People>> searchPeople(final int page, final String query) {

        return service.getApi().searchPeople(page, query)
                .concatMap((Function<SWModelList<People>, Observable<SWModelList<People>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchPeople(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Search species based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList<Species>>
     */
    private Observable<SWModelList<Species>> searchSpecies(final int page, final String query) {

        return service.getApi().searchSpecies(page, query)
                .concatMap((Function<SWModelList<Species>, Observable<SWModelList<Species>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchSpecies(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Search planets based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList<Planet>>
     */
    private Observable<SWModelList<Planet>> searchPlanets(final int page, final String query) {

        return service.getApi().searchPlanets(page, query)
                .concatMap((Function<SWModelList<Planet>, Observable<SWModelList<Planet>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchPlanets(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Search starships based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList<Starship>>
     */
    private Observable<SWModelList<Starship>> searchStarships(final int page, final String query) {

        return service.getApi().searchStarships(page, query)
                .concatMap((Function<SWModelList<Starship>, Observable<SWModelList<Starship>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchStarships(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Search vehicles based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList<Vehicle>>
     */
    private Observable<SWModelList<Vehicle>> searchVehicles(final int page, final String query) {

        return service.getApi().searchVehicles(page, query)
                .concatMap((Function<SWModelList<Vehicle>, Observable<SWModelList<Vehicle>>>) response -> {

                    if (response.next == null) {
                        return Observable.just(response);
                    }

                    return Observable.just(response).concatWith(searchVehicles(page + 1, query));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
