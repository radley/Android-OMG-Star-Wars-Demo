package dev.radley.omgstarwars.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.di.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.SWModelList;
import dev.radley.omgstarwars.utilities.SortUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for SearchActivity
 */
public class SearchViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    private ArrayList<SWModel> modelList = new ArrayList<>();
    private String category;
    private String query;
    private String[] categoryIds;
    private String[] categoryTitles;

    private MutableLiveData<ArrayList<SWModel>> liveData;
    private MutableLiveData<Boolean> error;
    private MutableLiveData<Boolean> loading;


    /**
     * Instantiate service and state models
     */
    public SearchViewModel() {

        DaggerApiComponent.create().inject(this);
        error = new MutableLiveData<>();
        loading = new MutableLiveData<>();
    }

    /**
     * Get category ids & titles
     *
     * @param ids String[]
     * @param titles String[]
     */
    public void init(String[] ids, String[] titles) {

        categoryIds = ids;
        categoryTitles = titles;
    }

    /**
     * Update category and load new data if liveData is ready
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
     * Update query value
     * - clear everything if too short
     * - otherwise load new data if liveData is ready
     *
     * @param query String
     */
    public void setQuery(String query) {

        this.query = query;

        if(query.length() < 2) {
            clear();

        } else if(liveData != null) {
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
     * Clear modelList and liveData
     */
    private void clear() {
        modelList.clear();
        liveData.setValue(modelList);
    }


    /**
     * Instantiate liveData and then load data
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
     * Instantiate error state liveData
     *
     * @return error LiveData
     */
    public LiveData<Boolean> getError() {
        return error;
    }

    /**
     * Instantiate loading state liveData
     *
     * @return loading LiveData
     */
    public LiveData<Boolean> getLoading() {
        return loading;
    }



    /**
     * - create observer and create observable based on category
     * - get results from observable and add to modelList
     */
    private void loadData() {

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

                // fresh start
                error.setValue(false);
                modelList.clear();
                liveData.setValue(modelList);
                loading.setValue(true);
            }

            @Override
            public void onNext(Object list) {

                for(int i = 0; i < ((SWModelList) list).results.size(); i++){

                    // add various model types as SWModel to list
                    Object item = ((SWModelList) list).results.get(i);
                    if(item instanceof SWModel){
                        modelList.add((SWModel) item);
                    }
                }

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

            @Override
            public void onError(Throwable e) {

                // clear list and show error
                error.setValue(true);
                loading.setValue(false);

                modelList.clear();
                liveData.setValue(modelList);
            }

            @Override
            public void onComplete() {

            }
        };

        // subscribe to observable based on category
        switch (category) {
            case "films":
                Observable<SWModelList<Film>> filmObservable = searchFilms(1, query);
                filmObservable.subscribe(observer);
                break;

            case "people":
                Observable<SWModelList<People>> peopleObservable = searchPeople(1, query);
                peopleObservable.subscribe(observer);
                break;

            case "planets":
                Observable<SWModelList<Planet>> planetsObservable = searchPlanets(1, query);
                planetsObservable.subscribe(observer);
                break;

            case "species":
                Observable<SWModelList<Species>> speciesObservable = searchSpecies(1, query);
                speciesObservable.subscribe(observer);
                break;

            case "starships":
                Observable<SWModelList<Starship>> starshipsObservable = searchStarships(1, query);
                starshipsObservable.subscribe(observer);
                break;

            case "vehicles":
                Observable<SWModelList<Vehicle>> vehiclesObservable = searchVehicles(1, query);
                vehiclesObservable.subscribe(observer);
                break;

        }
    }

    /**
     * Search films based on query
     * - Get all results pages before returning Observable
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
     * - Get all results pages before returning Observable
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
     * - Get all results pages before returning Observable
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
     * - Get all results pages before returning Observable
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
     * - Get all results pages before returning Observable
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
     * - Get all results pages before returning Observable
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
