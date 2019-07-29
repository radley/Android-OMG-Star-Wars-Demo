package dev.radley.omgstarwars.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.radley.omgstarwars.dagger.DaggerApiComponent
import dev.radley.omgstarwars.models.*
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.utilities.FormatUtils
import dev.radley.omgstarwars.utilities.SortUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.Serializable
import java.util.*
import javax.inject.Inject

class DetailViewModel : ViewModel() {

    @Inject
    lateinit var service: StarWarsService

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var model: SWModel

    private lateinit var filmsData: MutableLiveData<ArrayList<SWModel>>
    private lateinit var peopleData: MutableLiveData<ArrayList<SWModel>>
    private lateinit var speciesData: MutableLiveData<ArrayList<SWModel>>
    private lateinit var planetsData: MutableLiveData<ArrayList<SWModel>>
    private lateinit var starshipsData: MutableLiveData<ArrayList<SWModel>>
    private lateinit var vehiclesData: MutableLiveData<ArrayList<SWModel>>

    private lateinit var homeWorldData: MutableLiveData<Planet>
    private lateinit var singleSpeciesData: MutableLiveData<Species>


    init {
        DaggerApiComponent.create().inject(this)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

    fun setModel(resource: Serializable) {
        model = resource as SWModel
    }

    fun getCategory(): String {
        return model.categoryId
    }

    fun getModel(): SWModel {
        return model
    }

    fun getTitle(): String {
        return model.title
    }

    fun getSubTitle(): String {
        return model.subtitle
    }

    fun getImage(): String {
        return model.imagePath
    }

    /**
     * Returns list from model (if any) of related items by type
     *
     * @return ArrayList<String>
    </String> */
    fun getFilms(): ArrayList<String>? {
        return model.relatedFilms
    }

    fun getPeople(): ArrayList<String>? {
        return model.relatedPeople
    }

    fun getPlanets(): ArrayList<String>? {
        return model.planets
    }

    fun getSpecies(): ArrayList<String>? {
        return model.relatedSpecies
    }

    fun getStarships(): ArrayList<String>? {
        return model.relatedStarships
    }

    fun getVehicles(): ArrayList<String>? {
        return model.relatedVehicles
    }


    /**
     * People & Species have homeworld value with url link to Planet
     *
     * @return String
     */
    fun getHomeWorld(): String? {
        if (model is People) {
            return (model as People).homeWorldUrl
        } else if (model is Species) {
            return (model as Species).homeWorld
        }

        return null
    }

    /**
     * People has species value with url link to Species
     *
     * @return String
     */
    fun getSingleSpecies(): String? {
        return if (model is People && (model as People).relatedSpecies!!.size > 0) {
            (model as People).relatedSpecies!![0]
        } else null

    }


    /**
     * Returns model-specific titles
     *
     * @return String
     */
    fun getRelatedFilmsTitle(): String {
        return model.relatedFilmsTitle
    }

    fun getRelatedPeopleTitle(): String {
        return model.relatedPeopleTitle
    }

    fun getRelatedPlanetsTitle(): String {
        return model.relatedPlanetsTitle
    }

    fun getRelatedSpeciesTitle(): String {
        return model.relatedSpeciesTitle
    }

    fun getRelatedStarshipsTitle(): String {
        return model.relatedStarshipsTitle
    }

    fun getRelatedVehiclesTitle(): String {
        return model.relatedVehiclesTitle
    }


    /**
     * iterate through urls, get Film for each, & add to related list
     *
     * @return filmsData
    </String> */
    fun getFilmsList(): LiveData<ArrayList<SWModel>> {

        filmsData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getFilms()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getFilm(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Film>() {

                            override fun onSuccess(item: Film) {

                                list.add(item)

                                // wait until we have them all to sort and load
                                if(list.size == urlList.size) {

                                    val films = SortUtils.sortSWModelByEpisode(list)
                                    filmsData.value = films
                                }
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return filmsData
    }

    /**
     * iterate through urls, get People for each, & add to related list
     *
     * @return peopleData
    </String> */
    fun getPeopleList(): LiveData<ArrayList<SWModel>> {

        peopleData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getPeople()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getPeople(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<People>() {

                            override fun onSuccess(item: People) {

                                list.add(item)
                                peopleData.value = list
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return peopleData
    }

    /**
     * iterate through urls, get Planet for each, & add to related list
     *
     * @return planetsData
    </String> */
    fun getPlanetsList(): LiveData<ArrayList<SWModel>> {

        planetsData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getPlanets()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getPlanet(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Planet>() {

                            override fun onSuccess(item: Planet) {

                                list.add(item)
                                planetsData.value = list
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return planetsData
    }

    /**
     * iterate through urls, get Species for each, & add to related list
     *
     * @return speciesData
    </String> */
    fun getSpeciesList(): LiveData<ArrayList<SWModel>> {

        speciesData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getSpecies()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getSpecies(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Species>() {

                            override fun onSuccess(item: Species) {

                                list.add(item)
                                speciesData.value = list
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return speciesData
    }

    /**
     * iterate through urls, get Starship for each, & add to related list
     *
     * @return starshipsData
    </String> */
    fun getStarshipsList(): LiveData<ArrayList<SWModel>> {

        starshipsData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getStarships()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getStarship(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Starship>() {

                            override fun onSuccess(item: Starship) {

                                list.add(item)
                                starshipsData.value = list
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return starshipsData
    }

    /**
     * iterate through urls, get Vehicle for each, & add to related list
     *
     * @return vehiclesData
    </String> */
    fun getVehiclesList(): LiveData<ArrayList<SWModel>> {

        vehiclesData = MutableLiveData()
        val list = ArrayList<SWModel>()
        val urlList = getVehicles()

        urlList?.let {
            for (i in it.indices) {

                val id = FormatUtils.getId(it[i])

                compositeDisposable.add(service.getVehicle(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Vehicle>() {

                            override fun onSuccess(item: Vehicle) {

                                list.add(item)
                                vehiclesData.value = list
                            }

                            override fun onError(t: Throwable) {
                                Timber.e("error: %s", t.message)
                            }
                        }))
            }
        }

        return vehiclesData
    }

    /**
     * Get Planet object for People/Species homeworld value
     *
     * @param id int
     * @return homeWorldData
     */
    fun getHomeWorlds(id: Int): LiveData<Planet> {


        //if the list is null

        homeWorldData = MutableLiveData()


        compositeDisposable.add(service.getPlanet(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Planet>() {

                    override fun onSuccess(item: Planet) {
                        homeWorldData.value = item
                    }

                    override fun onError(t: Throwable) {
                        Timber.e("error: %s", t.message)
                    }
                }))


        return homeWorldData
    }

    /**
     * Get Species object for People species value
     *
     * @param id int
     * @return singleSpeciesData
     */
    fun getSingleSpecies(id: Int): LiveData<Species> {


        singleSpeciesData = MutableLiveData()

        compositeDisposable.add(service.getSpecies(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Species>() {

                    override fun onSuccess(item: Species) {
                        singleSpeciesData.value = item
                    }

                    override fun onError(t: Throwable) {
                        Timber.e("error: %s", t.message)
                    }
                }))


        return singleSpeciesData
    }
}