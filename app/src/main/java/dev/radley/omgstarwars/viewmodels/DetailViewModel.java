package dev.radley.omgstarwars.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import dev.radley.omgstarwars.di.DaggerApiComponent;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.People;
import dev.radley.omgstarwars.data.Planet;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.Species;
import dev.radley.omgstarwars.data.Starship;
import dev.radley.omgstarwars.data.Vehicle;
import dev.radley.omgstarwars.utilities.Util;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;


public class DetailViewModel extends ViewModel {

    @Inject
    StarWarsService service;

    protected SWModel model;

    private MutableLiveData<ArrayList<SWModel>> filmsData;
    private MutableLiveData<ArrayList<SWModel>> peopleData;
    private MutableLiveData<ArrayList<SWModel>> speciesData;
    private MutableLiveData<ArrayList<SWModel>> planetsData;
    private MutableLiveData<ArrayList<SWModel>> starshipsData;
    private MutableLiveData<ArrayList<SWModel>> vehiclesData;

    private MutableLiveData<Planet> homeWorldData;
    private MutableLiveData<Species> singleSpeciesData;


    public DetailViewModel() {

        DaggerApiComponent.create().inject(this);
    }

    public void setModel(Serializable resource) {
        model = (SWModel) resource;
    }


    public LiveData<ArrayList<SWModel>> getFilms(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (filmsData == null) {
            filmsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Film> call = service.getApi().getFilm(id);
                call.enqueue(new Callback<Film>() {

                    @Override
                    public void onResponse(@NotNull Call<Film> call, @NotNull retrofit2.Response<Film> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            filmsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Film> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return filmsData;
    }

    public LiveData<ArrayList<SWModel>> getPeople(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (peopleData == null) {
            peopleData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<People> call = service.getApi().getPeople(id);
                call.enqueue(new Callback<People>() {

                    @Override
                    public void onResponse(@NotNull Call<People> call, @NotNull retrofit2.Response<People> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            peopleData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<People> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return peopleData;
    }

    public LiveData<ArrayList<SWModel>> getSpecies(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (speciesData == null) {
            speciesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Species> call = service.getApi().getSpecies(id);
                call.enqueue(new Callback<Species>() {

                    @Override
                    public void onResponse(@NotNull Call<Species> call, @NotNull retrofit2.Response<Species> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            speciesData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Species> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return speciesData;
    }

    public LiveData<ArrayList<SWModel>> getPlanets(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (planetsData == null) {
            planetsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Planet> call = service.getApi().getPlanet(id);
                call.enqueue(new Callback<Planet>() {

                    @Override
                    public void onResponse(@NotNull Call<Planet> call, @NotNull retrofit2.Response<Planet> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            planetsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Planet> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return planetsData;
    }

    public LiveData<ArrayList<SWModel>> getStarships(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (starshipsData == null) {
            starshipsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Starship> call = service.getApi().getStarship(id);
                call.enqueue(new Callback<Starship>() {

                    @Override
                    public void onResponse(@NotNull Call<Starship> call, @NotNull retrofit2.Response<Starship> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            starshipsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Starship> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return starshipsData;
    }

    public LiveData<ArrayList<SWModel>> getVehicles(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (vehiclesData == null) {
            vehiclesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Vehicle> call = service.getApi().getVehicle(id);
                call.enqueue(new Callback<Vehicle>() {

                    @Override
                    public void onResponse(@NotNull Call<Vehicle> call, @NotNull retrofit2.Response<Vehicle> response) {

                        list.add(response.body());
                        if(list.size() == urlList.size()) {
                            vehiclesData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Vehicle> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return vehiclesData;
    }

    public LiveData<Planet> getHomeWorlds(int id) {


        //if the list is null
        if (homeWorldData == null) {
            homeWorldData = new MutableLiveData<>();

            Call<Planet> call = service.getApi().getPlanet(id);
            call.enqueue(new Callback<Planet>() {

                @Override
                public void onResponse(@NotNull Call<Planet> call, @NotNull retrofit2.Response<Planet> response) {
                    homeWorldData.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Planet> call, @NotNull Throwable t) {
                    Timber.e("error: %s", t.getMessage());
                }
            });
        }

        return homeWorldData;
    }

    public LiveData<Species> getSingleSpecies(int id) {

        //if the list is null
        if (singleSpeciesData == null) {
            singleSpeciesData = new MutableLiveData<>();

            Call<Species> call = service.getApi().getSpecies(id);
            call.enqueue(new Callback<Species>() {

                @Override
                public void onResponse(@NotNull Call<Species> call, @NotNull retrofit2.Response<Species> response) {
                    singleSpeciesData.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Species> call, @NotNull Throwable t) {
                    Timber.e("error: %s", t.getMessage());
                }
            });
        }

        return singleSpeciesData;
    }
}
