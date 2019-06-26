package dev.radley.omgstarwars.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dev.radley.omgstarwars.di.DaggerApiComponent;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.SWModelList;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.utilities.SortUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CategoryViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    private ArrayList<SWModel> modelList;
    private CompositeDisposable compositeDisposable;
    private int DEFAULT_PAGE = 1;
    private int page = DEFAULT_PAGE;
    private MutableLiveData<ArrayList<SWModel>> liveData;
    private MutableLiveData<Boolean> loadError;
    private MutableLiveData<Boolean> loading;
    private String next;
    private String category;

    /**
     * ViewModel for CategoryFragment
     * - instantiate models
     */
    CategoryViewModel() {

        DaggerApiComponent.create().inject(this);
        modelList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();

        liveData = new MutableLiveData<>();
        loadError = new MutableLiveData<>();
        loading = new MutableLiveData<>();
    }

    /**
     * Instantiate liveData, assign category, and load data
     *
     * @param category String
     * @return liveData list
     */
    public LiveData<ArrayList<SWModel>> getList(String category) {

        this.category = category;
        loadData();
        return liveData;
    }

    /**
     * Reports error state
     *
     * @return boolean
     */
    public LiveData<Boolean> getLoadError() {
        return loadError;
    }

    /**
     * Reports loading state
     *
     * @return boolean
     */
    public LiveData<Boolean> getLoading() {
        return loading;
    }

    /**
     * Called when recycler view scrolls to bottom
     * - loads next page of data if there is more
     */
    public void getNextPage() {

        if (next != null) {
            loadData();
        }
    }

    /**
     * Take out the trash...
     */
    public void clear() {
        compositeDisposable.dispose();
    }

    public String getId() {
        return category;
    }

    public SWModel getItem(int position) {
        return modelList.get(position);
    }

    /**
     * Load data based on category
     */
    private void loadData() {

        loading.setValue(true);

        switch (category) {
            case "films":
                fetchFilms();
                break;
            case "people":
                fetchPeople();
                break;
            case "species":
                fetchSpecies();
                break;
            case "planets":
                fetchPlanets();
                break;
            case "starships":
                fetchStarships();
                break;
            case "vehicles":
                fetchVehicles();
                break;
        }
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchFilms() {

        compositeDisposable.add(service.getApi().getFilmsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Film>>() {

                    @Override
                    public void onSuccess(SWModelList<Film> list) {

                        if (list != null) {

                            ArrayList<Film> films = SortUtils.sortFilmsByEpisode(list.results);
                            applyResults(films, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchPeople() {

        compositeDisposable.add(service.getApi().getPeopleByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<People>>() {

                    @Override
                    public void onSuccess(SWModelList<People> list) {

                        if (list != null) {
                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchPlanets() {

        compositeDisposable.add(service.getApi().getPlanetsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Planet>>() {

                    @Override
                    public void onSuccess(SWModelList<Planet> list) {

                        if (list != null) {
                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchSpecies() {

        compositeDisposable.add(service.getApi().getSpeciesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Species>>() {

                    @Override
                    public void onSuccess(SWModelList<Species> list) {

                        if (list != null) {
                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchStarships() {

        compositeDisposable.add(service.getApi().getStarshipsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Starship>>() {

                    @Override
                    public void onSuccess(SWModelList<Starship> list) {

                        if (list != null) {
                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private void fetchVehicles() {

        compositeDisposable.add(service.getApi().getVehiclesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Vehicle>>() {

                    @Override
                    public void onSuccess(SWModelList<Vehicle> list) {

                        if (list != null) {
                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                    }
                }));
    }

    /**
     * - called by observable onSuccess
     * - adds various model types as base SWModel to modelList
     * - clears loading & error states
     * - sets value to liveData
     *
     * @param results Arraylist<?>
     * @param next    String
     */
    private void applyResults(ArrayList<?> results, String next) {

        page = getNextPage(next);

        for (int i = 0; i < results.size(); i++) {
            Object item = results.get(i);
            if (item instanceof SWModel) {
                modelList.add((SWModel) item);
            }
        }

        loading.setValue(false);
        loadError.setValue(false);
        liveData.setValue(modelList);
    }

    /**
     * - updates <code>next</code> to <code>url</code>
     * - if <code>next</code> exists, parses <code>url</code> for <code>page</code> number
     *
     * @param url String
     * @return next page integer (if any)
     */
    private int getNextPage(String url) {

        next = url;

        if (url == null)
            return DEFAULT_PAGE;

        Uri uri = Uri.parse(url);

        if (uri.getQueryParameter("page") != null) {
            return Integer.parseInt(Objects.requireNonNull(uri.getQueryParameter("page")));
        }

        // default
        return DEFAULT_PAGE;
    }

}
