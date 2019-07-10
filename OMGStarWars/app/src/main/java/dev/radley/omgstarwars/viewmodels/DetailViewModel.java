package dev.radley.omgstarwars.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import dev.radley.omgstarwars.dagger.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.utilities.FormatUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ViewModel for Detail Activity
 *
 * - mostly manages related item lists
 * - provides liveData for existing related list
 * - displays custom list titles for models that have custom titles
 * - resolves finding homeworld and/or single species in models that have those values
 */
public class DetailViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    private CompositeDisposable compositeDisposable;
    protected SWModel model;

    private MutableLiveData<ArrayList<SWModel>> filmsData;
    private MutableLiveData<ArrayList<SWModel>> peopleData;
    private MutableLiveData<ArrayList<SWModel>> speciesData;
    private MutableLiveData<ArrayList<SWModel>> planetsData;
    private MutableLiveData<ArrayList<SWModel>> starshipsData;
    private MutableLiveData<ArrayList<SWModel>> vehiclesData;

    private MutableLiveData<Planet> homeWorldData;
    private MutableLiveData<Species> singleSpeciesData;


    /**
     * Inject viewModel
     */
    public DetailViewModel() {

        DaggerApiComponent.create().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Take out the trash...
     */
    public void dispose() {
        compositeDisposable.dispose();
    }

    public void setModel(Serializable resource) {
        model = (SWModel) resource;
    }

    public String getCategory() {
        return model.getCategoryId();
    }

    public SWModel getModel() {
        return model;
    }

    public String getTitle() {
        return model.getTitle();
    }

    public String getImage() {
        return model.getImagePath();
    }

    /**
     * Returns list from model (if any) of related items by type
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getFilms() {
        return model.getRelatedFilms();
    }

    public ArrayList<String> getPeople() {
        return model.getRelatedPeople();
    }

    public ArrayList<String> getPlanets() {
        return model.getPlanets();
    }

    public ArrayList<String> getSpecies() {
        return model.getRelatedSpecies();
    }

    public ArrayList<String> getStarships() {
        return model.getRelatedStarships();
    }

    public ArrayList<String> getVehicles() {
        return model.getRelatedVehicles();
    }

    /**
     * People & Species have homeworld value with url link to Planet
     *
     * @return String
     */
    public String getHomeWorld()
    {
        if(model instanceof People) {
            return ((People) model).homeWorldUrl;
        } else if(model instanceof Species) {
            return ((Species) model).homeWorld;
        }

        return null;
    }

    /**
     * People has species value with url link to Species
     *
     * @return String
     */
    public String getSingleSpecies()
    {
        if(model instanceof People) {
            return ((People) model).getRelatedSpecies().get(0);
        }

        return null;
    }


    /**
     * Returns model-specific titles
     *
     * @return String
     */
    public String getRelatedFilmsTitle() {
        return model.getRelatedFilmsTitle();
    }

    public String getRelatedPeopleTitle() {
        return model.getRelatedPeopleTitle();
    }

    public String getRelatedPlanetsTitle() {
        return model.getRelatedPlanetsTitle();
    }

    public String getRelatedSpeciesTitle() {
        return model.getRelatedSpeciesTitle();
    }

    public String getRelatedStarshipsTitle() {
        return model.getRelatedStarshipsTitle();
    }

    public String getRelatedVehiclesTitle() {
        return model.getRelatedVehiclesTitle();
    }


    /**
     * iterate through urls, get Film for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return filmsData
     */
    public LiveData<ArrayList<SWModel>> getFilmsList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();


        //if the list is null
        if (filmsData == null) {
            filmsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getFilm(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <Film>() {

                            @Override
                            public void onSuccess(Film item) {

                                list.add(item);
                                filmsData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return filmsData;
    }

    /**
     * iterate through urls, get People for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return peopleData
     */
    public LiveData<ArrayList<SWModel>> getPeopleList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (peopleData == null) {
            peopleData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getPeople(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <People>() {

                            @Override
                            public void onSuccess(People item) {

                                list.add(item);
                                peopleData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return peopleData;
    }

    /**
     * iterate through urls, get Planet for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return planetsData
     */
    public LiveData<ArrayList<SWModel>> getPlanetsList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (planetsData == null) {
            planetsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getPlanet(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <Planet>() {

                            @Override
                            public void onSuccess(Planet item) {

                                list.add(item);
                                planetsData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return planetsData;
    }

    /**
     * iterate through urls, get Species for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return speciesData
     */
    public LiveData<ArrayList<SWModel>> getSpeciesList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (speciesData == null) {
            speciesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getSpecies(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <Species>() {

                            @Override
                            public void onSuccess(Species item) {

                                list.add(item);
                                speciesData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return speciesData;
    }

    /**
     * iterate through urls, get Starship for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return starshipsData
     */
    public LiveData<ArrayList<SWModel>> getStarshipsList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (starshipsData == null) {
            starshipsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getStarship(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <Starship>() {

                            @Override
                            public void onSuccess(Starship item) {

                                list.add(item);
                                starshipsData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return starshipsData;
    }

    /**
     * iterate through urls, get Vehicle for each, & add to related list
     *
     * @param urlList ArrayList<String>
     * @return vehiclesData
     */
    public LiveData<ArrayList<SWModel>> getVehiclesList(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (vehiclesData == null) {
            vehiclesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = FormatUtils.getId(urlList.get(i));

                compositeDisposable.add(service.getApi().getVehicle(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver <Vehicle>() {

                            @Override
                            public void onSuccess(Vehicle item) {

                                list.add(item);
                                vehiclesData.setValue(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Timber.e("error: %s", t.getMessage());
                            }
                        }));
            }
        }

        return vehiclesData;
    }

    /**
     * Get Planet object for People/Species homeworld value
     *
     * @param id int
     * @return homeWorldData
     */
    public LiveData<Planet> getHomeWorlds(int id) {


        //if the list is null
        if (homeWorldData == null) {
            homeWorldData = new MutableLiveData<>();


            compositeDisposable.add(service.getApi().getPlanet(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver <Planet>() {

                        @Override
                        public void onSuccess(Planet item) {

                            homeWorldData.setValue(item);
                        }

                        @Override
                        public void onError(Throwable t) {
                            Timber.e("error: %s", t.getMessage());
                        }
                    }));
        }

        return homeWorldData;
    }

    /**
     * Get Species object for People species value
     *
     * @param id int
     * @return singleSpeciesData
     */
    public LiveData<Species> getSingleSpecies(int id) {

        //if the list is null
        if (singleSpeciesData == null) {
            singleSpeciesData = new MutableLiveData<>();

            compositeDisposable.add(service.getApi().getSpecies(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver <Species>() {

                        @Override
                        public void onSuccess(Species item) {

                            singleSpeciesData.setValue(item);
                        }

                        @Override
                        public void onError(Throwable t) {
                            Timber.e("error: %s", t.getMessage());
                        }
                    }));
        }

        return singleSpeciesData;
    }
}
