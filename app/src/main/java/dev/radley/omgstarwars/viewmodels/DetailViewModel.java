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
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.People;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Species;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.utilities.FormatUtils;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

/**
 * ViewModel for DetailAtivity
 */
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


    /**
     * Inject viewModel
     */
    public DetailViewModel() {

        DaggerApiComponent.create().inject(this);
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

    public String getHomeWorld()
    {
        if(model instanceof People) {
            return ((People) model).homeWorldUrl;
        } else if(model instanceof Species) {
            return ((Species) model).homeWorld;
        }

        return null;
    }

    public String getSingleSpecies()
    {
        if(model instanceof People) {
            return ((People) model).getRelatedSpecies().get(0);
        }

        return null;
    }

    /**
     * Returns related films list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getFilms() {
        return model.getRelatedFilms();
    }

    /**
     * Returns related people list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getPeople() {
        return model.getRelatedPeople();
    }

    /**
     * Returns related planets list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getPlanets() {
        return model.getPlanets();
    }

    /**
     * Returns related species list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getSpecies() {
        return model.getRelatedSpecies();
    }

    /**
     * Returns related starhips list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getStarships() {
        return model.getRelatedStarships();
    }

    /**
     * Returns related vehicles list from model (if any)
     * @return ArrayList<String>
     */
    public ArrayList<String> getVehicles() {
        return model.getRelatedVehicles();
    }


    /**
     * Returns model-specific title for related films
     *
     * @return String
     */
    public String getRelatedFilmsTitle() {
        return model.getRelatedFilmsTitle();
    }

    /**
     * Returns model-specific title for related people
     *
     * @return String
     */
    public String getRelatedPeopleTitle() {
        return model.getRelatedPeopleTitle();
    }

    /**
     * Returns model-specific title for related planets
     *
     * @return String
     */
    public String getRelatedPlanetsTitle() {
        return model.getRelatedPlanetsTitle();
    }

    /**
     * Returns model-specific title for related species
     *
     * @return String
     */
    public String getRelatedSpeciesTitle() {
        return model.getRelatedSpeciesTitle();
    }

    /**
     * Returns model-specific title for related starships
     *
     * @return String
     */
    public String getRelatedStarshipsTitle() {
        return model.getRelatedStarshipsTitle();
    }

    /**
     * Returns model-specific title for related vehicles
     *
     * @return String
     */
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

                Call<Film> call = service.getApi().getFilm(id);
                call.enqueue(new Callback<Film>() {

                    @Override
                    public void onResponse(@NotNull Call<Film> call, @NotNull retrofit2.Response<Film> response) {

                        list.add(response.body());
                        filmsData.setValue(list);
                    }

                    // ignore if missing (let others continue)
                    @Override
                    public void onFailure(@NotNull Call<Film> call, @NotNull Throwable t) {
                        Timber.e("error: %s", t.getMessage());
                    }
                });
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

                Call<People> call = service.getApi().getPeople(id);
                call.enqueue(new Callback<People>() {

                    @Override
                    public void onResponse(@NotNull Call<People> call, @NotNull retrofit2.Response<People> response) {

                        list.add(response.body());
                        peopleData.setValue(list);
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

                Call<Planet> call = service.getApi().getPlanet(id);
                call.enqueue(new Callback<Planet>() {

                    @Override
                    public void onResponse(@NotNull Call<Planet> call, @NotNull retrofit2.Response<Planet> response) {

                        list.add(response.body());
                        planetsData.setValue(list);
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

                Call<Species> call = service.getApi().getSpecies(id);
                call.enqueue(new Callback<Species>() {

                    @Override
                    public void onResponse(@NotNull Call<Species> call, @NotNull retrofit2.Response<Species> response) {

                        list.add(response.body());
                        speciesData.setValue(list);
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

                Call<Starship> call = service.getApi().getStarship(id);
                call.enqueue(new Callback<Starship>() {

                    @Override
                    public void onResponse(@NotNull Call<Starship> call, @NotNull retrofit2.Response<Starship> response) {

                        list.add(response.body());
                        starshipsData.setValue(list);
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

                Call<Vehicle> call = service.getApi().getVehicle(id);
                call.enqueue(new Callback<Vehicle>() {

                    @Override
                    public void onResponse(@NotNull Call<Vehicle> call, @NotNull retrofit2.Response<Vehicle> response) {

                        list.add(response.body());
                        vehiclesData.setValue(list);
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

    /**
     * Get Planet from homeworld id
     *
     * @param id int
     * @return homeWorldData
     */
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

    /**
     * Get Species from homeworld id
     *
     * @param id int
     * @return singleSpeciesData
     */
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
