package dev.radley.omgstarwars.ui.detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import dev.radley.omgstarwars.network.api.StarWarsService;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.Species;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.model.Vehicle;
import dev.radley.omgstarwars.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;


public class RelatedViewModel extends ViewModel {

    protected SWModel mModel;

    private MutableLiveData<ArrayList<SWModel>> mFilmsData;
    private MutableLiveData<ArrayList<SWModel>> mPeopleData;
    private MutableLiveData<ArrayList<SWModel>> mSpeciesData;
    private MutableLiveData<ArrayList<SWModel>> mPlanetsData;
    private MutableLiveData<ArrayList<SWModel>> mStarshipsData;
    private MutableLiveData<ArrayList<SWModel>> mVehiclesData;

    private MutableLiveData<Planet> mHomeWorldData;
    private MutableLiveData<Species> mSingleSpeciesData;

    private StarWarsService service;

    public RelatedViewModel() {
        service = new StarWarsService();
    }

    public void setModel(Serializable resource) {
        mModel = (SWModel) resource;
    }


    public LiveData<ArrayList<SWModel>> getFilms(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mFilmsData == null) {
            mFilmsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Film> call = service.getApi().getFilm(id);
                call.enqueue(new Callback<Film>() {

                    @Override
                    public void onResponse(@NotNull Call<Film> call, @NotNull retrofit2.Response<Film> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mFilmsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Film> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mFilmsData;
    }

    public LiveData<ArrayList<SWModel>> getPeople(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mPeopleData == null) {
            mPeopleData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<People> call = service.getApi().getPeople(id);
                call.enqueue(new Callback<People>() {

                    @Override
                    public void onResponse(@NotNull Call<People> call, @NotNull retrofit2.Response<People> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mPeopleData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<People> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mPeopleData;
    }

    public LiveData<ArrayList<SWModel>> getSpecies(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mSpeciesData == null) {
            mSpeciesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Species> call = service.getApi().getSpecies(id);
                call.enqueue(new Callback<Species>() {

                    @Override
                    public void onResponse(@NotNull Call<Species> call, @NotNull retrofit2.Response<Species> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mSpeciesData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Species> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mSpeciesData;
    }

    public LiveData<ArrayList<SWModel>> getPlanets(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mPlanetsData == null) {
            mPlanetsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Planet> call = service.getApi().getPlanet(id);
                call.enqueue(new Callback<Planet>() {

                    @Override
                    public void onResponse(@NotNull Call<Planet> call, @NotNull retrofit2.Response<Planet> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mPlanetsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Planet> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mPlanetsData;
    }

    public LiveData<ArrayList<SWModel>> getStarships(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mStarshipsData == null) {
            mStarshipsData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Starship> call = service.getApi().getStarship(id);
                call.enqueue(new Callback<Starship>() {

                    @Override
                    public void onResponse(@NotNull Call<Starship> call, @NotNull retrofit2.Response<Starship> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mStarshipsData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Starship> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mStarshipsData;
    }

    public LiveData<ArrayList<SWModel>> getVehicles(ArrayList<String> urlList) {

        ArrayList<SWModel> list = new ArrayList<>();

        //if the list is null
        if (mVehiclesData == null) {
            mVehiclesData = new MutableLiveData<>();

            for (int i = 0; i < urlList.size(); i++) {

                final int id = Util.getId(urlList.get(i));

                Call<Vehicle> call = service.getApi().getVehicle(id);
                call.enqueue(new Callback<Vehicle>() {

                    @Override
                    public void onResponse(@NotNull Call<Vehicle> call, @NotNull retrofit2.Response<Vehicle> response) {

                        list.add(response.body());

                        if(list.size() == urlList.size()) {
                            mVehiclesData.setValue(list);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Vehicle> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
            }
        }

        return mVehiclesData;
    }

    public LiveData<Planet> getHomeWorlds(int id) {


        //if the list is null
        if (mHomeWorldData == null) {
            mHomeWorldData = new MutableLiveData<>();


            Call<Planet> call = service.getApi().getPlanet(id);
            call.enqueue(new Callback<Planet>() {

                @Override
                public void onResponse(@NotNull Call<Planet> call, @NotNull retrofit2.Response<Planet> response) {
                    mHomeWorldData.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Planet> call, @NotNull Throwable t) {
                    Timber.e("error: %s", t.getMessage());
                }
            });
        }

        return mHomeWorldData;
    }

    public LiveData<Species> getSingleSpecies(int id) {

        //if the list is null
        if (mSingleSpeciesData == null) {
            mSingleSpeciesData = new MutableLiveData<>();


            Call<Species> call = service.getApi().getSpecies(id);
            call.enqueue(new Callback<Species>() {

                @Override
                public void onResponse(@NotNull Call<Species> call, @NotNull retrofit2.Response<Species> response) {
                    mSingleSpeciesData.setValue(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<Species> call, @NotNull Throwable t) {
                    Timber.e("error: %s", t.getMessage());
                }
            });
        }

        return mSingleSpeciesData;
    }
}
