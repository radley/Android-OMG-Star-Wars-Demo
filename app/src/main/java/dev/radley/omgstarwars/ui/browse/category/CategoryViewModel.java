package dev.radley.omgstarwars.ui.browse.category;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.model.Vehicle;
import dev.radley.omgstarwars.network.api.StarWarsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CategoryViewModel extends ViewModel {


    private ArrayList<SWModel> mSWModelList = new ArrayList<>();
    private int DEFAULT_PAGE = 1;
    private int mCount = 0;
    private int mPage = DEFAULT_PAGE;
    private String mCategory;

    private StarWarsService service = new StarWarsService();

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<SWModel>> mLiveData;

    public LiveData<ArrayList<SWModel>> getList(String category) {

        mCategory = category;

        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();

            loadData();
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if (mPage > 0) {
            loadData();
        }
    }


    public int getCount() {
        return mCount;
    }

    public String getId() {
        return mCategory;
    }

    public SWModel getItem(int position) {
        return mSWModelList.get(position);
    }


    private void loadData() {

        Timber.d("loadData()");
        Timber.d("mCategory = %s", mCategory);


        switch (mCategory) {
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

        service.getApi().getFilmsByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Film>>() {

                    @Override
                    public void onSuccess(SWModelList<Film> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);

                            // sort films by episode
                            Collections.sort(list.results, new Comparator<SWModel>() {
                                public int compare(SWModel o1, SWModel o2) {
                                    return ((Film) o1).episodeId - ((Film) o2).episodeId;
                                }
                            });

                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private void fetchPeople() {

        service.getApi().getPeopleByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<People>>() {

                    @Override
                    public void onSuccess(SWModelList<People> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);

                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private void fetchPlanets() {

        service.getApi().getPlanetsByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Planet>>() {

                    @Override
                    public void onSuccess(SWModelList<Planet> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);

                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private void fetchSpecies() {

        service.getApi().getSpeciesByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Species>>() {

                    @Override
                    public void onSuccess(SWModelList<Species> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);


                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private void fetchStarships() {

        service.getApi().getStarshipsByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Starship>>() {

                    @Override
                    public void onSuccess(SWModelList<Starship> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);


                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private void fetchVehicles() {

        service.getApi().getVehiclesByPage(mPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SWModelList<Vehicle>>() {

                    @Override
                    public void onSuccess(SWModelList<Vehicle> list) {

                        if (list != null) {
                            mCount = list.count;
                            mPage = getNextPage(list.next);

                            mSWModelList.addAll(list.results);
                            mLiveData.setValue(mSWModelList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //countryError.setValue(true);
                    }
                });
    }

    private int getNextPage(String url) {

        if (url == null)
            return 0;

        Uri uri = Uri.parse(url);
        Set<String> args = uri.getQueryParameterNames();

        if (uri.getQueryParameter("page") != null) {
            return Integer.parseInt(uri.getQueryParameter("page"));
        }

        // default
        return DEFAULT_PAGE;
    }

}
