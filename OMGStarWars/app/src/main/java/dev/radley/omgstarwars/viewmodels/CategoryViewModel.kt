package dev.radley.omgstarwars.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.radley.omgstarwars.dagger.DaggerApiComponent
import dev.radley.omgstarwars.models.*
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.utilities.SortUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.collections.ArrayList

class CategoryViewModel : ViewModel() {

    @Inject
    lateinit var service: StarWarsService


    private val compositeDisposable = CompositeDisposable()

    private val defaultPage = 1
    private var page = defaultPage

    private var modelList = ArrayList<SWModel>()
    private var liveData = MutableLiveData<ArrayList<SWModel>>()
    private var loadError = MutableLiveData<Boolean>()
    private var loading = MutableLiveData<Boolean>()


    private var nextUrl: String? = null
    private var category: String = ""

    init {
        DaggerApiComponent.create().inject(this)
    }

    /**
     * Instantiate liveData, assign category, and load data
     *
     * @param category String
     * @return liveData list
     */
    fun getList(category: String): LiveData<ArrayList<SWModel>> {

        this.category = category
        loadData()
        return liveData
    }


    /**
     * Reports error state
     *
     * @return boolean
     */
    fun getLoadError(): LiveData<Boolean> {
        return loadError
    }

    /**
     * Reports loading state
     *
     * @return boolean
     */
    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    /**
     * Called when recycler view scrolls to bottom
     * - loads nextUrl page of data if there is more
     */
    fun getNextPage() {

        if (nextUrl != null) {
            loadData()
        }
    }

    /**
     * Take out the trash...
     */
    fun dispose() {
        compositeDisposable.dispose()
    }

    fun getId(): String {
        return category
    }

    fun getItem(position: Int): SWModel {
        return modelList[position]
    }

    /**
     * Load data based on category
     */
    private fun loadData() {

        loading.value = true

        when (category) {
            Category.FILMS -> fetchFilms()
            Category.PEOPLE -> fetchPeople()
            Category.SPECIES -> fetchSpecies()
            Category.PLANETS -> fetchPlanets()
            Category.STARSHIPS -> fetchStarships()
            Category.VEHICLES -> fetchVehicles()
        }
    }


    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchFilms() {

        compositeDisposable.add(
                service.api.getFilmsByPage(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<SWModelList<Film>>() {

                            override fun onSuccess(list: SWModelList<Film>) {

                                list.results?.let {
                                    val films = SortUtils.sortFilmsByEpisode(it)
                                    applyResults(films, list.next)
                                }
                            }

                            override fun onError(e: Throwable) {
                                loadError.value = true
                            }
                        }))
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchPeople() {

        compositeDisposable.add(service.api.getPeopleByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<People>>() {

                    override fun onSuccess(list: SWModelList<People>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                    }
                }))
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchPlanets() {

        compositeDisposable.add(service.api.getPlanetsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Planet>>() {

                    override fun onSuccess(list: SWModelList<Planet>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                    }
                }))
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchSpecies() {

        compositeDisposable.add(service.api.getSpeciesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Species>>() {

                    override fun onSuccess(list: SWModelList<Species>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                    }
                }))
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchStarships() {

        compositeDisposable.add(service.api.getStarshipsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Starship>>() {

                    override fun onSuccess(list: SWModelList<Starship>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                    }
                }))
    }

    /**
     * Load by page
     * - return results to applyResults()
     * - report error to loadError
     */
    private fun fetchVehicles() {

        compositeDisposable.add(service.api.getVehiclesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Vehicle>>() {

                    override fun onSuccess(list: SWModelList<Vehicle>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                    }
                }))
    }

    /**
     * - called by observable onSuccess
     * - adds various model types as base SWModel to modelList
     * - clears loading & error states
     * - sets value to liveData
     *
     * @param results Arraylist
     * @param next    String
     */
    private fun applyResults(results: ArrayList<*>, next: String?) {

        page = getNextPage(next)

        for (i in results.indices) {
            val item = results[i]
            if (item is SWModel) {
                modelList.add(item)
            }
        }

        loading.value = false
        loadError.value = false
        liveData.value = modelList
    }

    /**
     * - updates `nextUrl` to `url`
     * - if `nextUrl` exists, parses `url` for `page` number
     *
     * @param url String
     * @return nextUrl page integer (if any)
     */
    private fun getNextPage(url: String?): Int {

        nextUrl = url

        if (url == null)
            return defaultPage

        val uri = Uri.parse(url)
        uri.getQueryParameter("page")?.let { return Integer.parseInt(it) }

        return defaultPage
    }

}