package dev.radley.omgstarwars.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dev.radley.omgstarwars.di.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.SWModelList;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    private ArrayList<SWModel> modelList;
    private String category;
    private String query;
    private String[] categoryIds;
    private String[] categoryTitles;

    private MutableLiveData<ArrayList<SWModel>> liveData;



    public SearchViewModel() {

        DaggerApiComponent.create().inject(this);

        modelList = new ArrayList<>();
        liveData = new MutableLiveData<>();
    }

    public void init(String[] ids, String[] titles) {

        categoryIds = ids;
        categoryTitles = titles;
        category = categoryIds[0];

    }

    public LiveData<ArrayList<SWModel>> getList(String category) {

        this.category = category;
        loadData();

        return liveData;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public int getCurrentCategoryPosition() {

        for (int i = 0; i < categoryIds.length; i++)
            if (categoryIds[i].equals(category)) {
                return i;
            }
        return -1;
    }

    public String[] getCategoryTitles() {
        return categoryTitles;
    }
    public String getCategory(int position) {
        return categoryIds[position];
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public String getQuery() {
        return query;
    }

    public SWModel getItem(int position) {
        return modelList.get(position);
    }


    private void loadData() {

        //TODO loading state

        switch (category) {
//            case "films":
//                fetchFilms();
//                break;

//            case "people":
//                fetchPeople();
//                break;
//            case "species":
//                fetchSpecies();
//                break;
//            case "planets":
//                fetchPlanets();
//                break;
//            case "starships":
//                fetchStarships();
//                break;
//            case "vehicles":
//                fetchVehicles();
//                break;
        }
    }

    public void fetchFilms(Observer observer) {
        Observable<SWModelList<Film>> filmObservable = searchFilms(1, query);
        filmObservable.subscribe(observer);
    }

    private Observable<SWModelList<Film>> searchFilms(final int page, final String query) {

        return service.getApi().searchForFilms(page, query)
                .concatMap(new Function< SWModelList<Film>, Observable< SWModelList<Film> > >() {

                    @Override
                    public Observable<SWModelList<Film>> apply(SWModelList<Film> response) {


                        if (response.next == null) {
                            return Observable.just(response);
                        }

                        return Observable.just(response).concatWith(searchFilms(page + 1, query));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
