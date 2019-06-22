package dev.radley.omgstarwars.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.People;
import dev.radley.omgstarwars.data.Planet;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.SWModelList;
import dev.radley.omgstarwars.data.Species;
import dev.radley.omgstarwars.data.Starship;
import dev.radley.omgstarwars.data.Vehicle;
import dev.radley.omgstarwars.di.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
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
    private String next;
    private String category;

    CategoryViewModel() {

        DaggerApiComponent.create().inject(this);
        modelList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        liveData = new MutableLiveData<>();
    }

    public LiveData<ArrayList<SWModel>> getList(String category) {

        this.category = category;
        loadData();
        return liveData;
    }

    public void getNextPage() {

        if (next != null) {
            loadData();
        }
    }

    public void clear() {
        compositeDisposable.dispose();
    }

    public String getId() {
        return category;
    }

    public SWModel getItem(int position) {
        return modelList.get(position);
    }

    private void loadData() {

        //TODO loading state

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

    private void fetchFilms() {

        compositeDisposable.add(service.getApi().getFilmsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Film>>() {

                    @Override
                    public void onSuccess(SWModelList<Film> list) {

                        if (list != null) {

                            // sort films by episode
                            Collections.sort(list.results, (Comparator<SWModel>) (o1, o2) ->
                                    ((Film) o1).episodeId - ((Film) o2).episodeId);

                            applyResults(list.results, list.next);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        // TODO error state
                        //countryError.setValue(true);
                    }
                }));


    }

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
                        //countryError.setValue(true);
                    }
                }));
    }

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
                        //countryError.setValue(true);
                    }
                }));
    }

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
                        //countryError.setValue(true);
                    }
                }));
    }

    private void applyResults(ArrayList<?> results, String next) {

        page = getNextPage(next);

        for(int i = 0; i < results.size(); i++){
            Object item = results.get(i);
            if(item instanceof SWModel){
                modelList.add((SWModel) item);
            }
        }

        liveData.setValue(modelList);
    }

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
                        //countryError.setValue(true);
                    }
                }));
    }

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
                        //countryError.setValue(true);
                    }
                }));
    }

    private int getNextPage(String url) {

        next = url;

        if (url == null)
            return DEFAULT_PAGE;

        Uri uri = Uri.parse(url);
        Set<String> args = uri.getQueryParameterNames();

        if (uri.getQueryParameter("page") != null) {
            return Integer.parseInt(Objects.requireNonNull(uri.getQueryParameter("page")));
        }

        // default
        return DEFAULT_PAGE;
    }

}
