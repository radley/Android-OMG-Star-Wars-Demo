package dev.radley.omgstarwars.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import java.util.ArrayList

import javax.inject.Inject

import dev.radley.omgstarwars.models.Category
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.Planet
import dev.radley.omgstarwars.models.Species
import dev.radley.omgstarwars.models.Starship
import dev.radley.omgstarwars.models.Vehicle
import dev.radley.omgstarwars.dagger.DaggerApiComponent
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.SWModelList
import dev.radley.omgstarwars.utilities.SortUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * ViewModel for SearchActivity
 *
 * - provies liveData for list, loading, and error
 * - directs search based on category and query
 * - allows user to tap result item and view more in DetailActivity
 * - passes item's model in Bundle
 *
 * Swapi search results are not optimized, so we grab ALL search result pages using concatMap
 * and then sort them for best match:
 *
 * 1. Explicit query string match (i.e for "star" query -> "star destroyer", "death star")
 * - sort by index position (i.e "star destroyer" before "death star")
 *
 * 2. Title contains query string (i.e "starfighter", "starship")
 * - sort by index position (i.e "jedi starfighter" before "naboo royal starship")
 *
 * 3. Starship special case (swapi also searches in Starship.model)
 * a. Explicit query string match in .model
 * b. `.model` title contains query string
 *
 * 4. anything left over (shouldn't happen, but future proof)
 *
 */
class SearchViewModel : ViewModel() {

    @Inject
    internal var service: StarWarsService? = null

    private val modelList = ArrayList<SWModel>()
    private val compositeDisposable = CompositeDisposable()
    private var liveData: MutableLiveData<ArrayList<SWModel>>? = null
    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()
    /**
     * Update category and loads new data if liveData is ready
     *
     * @param category String
     */
    var category = categoryIds[0]
        set(category) {

            field = category
            if (liveData != null) {
                loadData()
            }
        }
    /**
     * - updates query value if new value
     * - loads new data if liveData is ready and query string has more than one character
     *
     * @param query String
     */
    var query = ""
        set(query) {

            if (this.query == query) {
                return
            }

            field = query

            if (liveData != null && query.length > 1) {
                loadData()
            }
        }

    val categoryPosition: Int
        get() {

            for (i in categoryIds.indices)
                if (categoryIds[i] == this.category) {
                    return i
                }
            return -1
        }


    /**
     * - instantiates liveData if null
     * - loads data
     *
     * @return liveData
     */
    val list: LiveData<ArrayList<SWModel>>
        get() {

            if (liveData == null) {
                liveData = MutableLiveData()
            }

            loadData()
            return liveData
        }

    /**
     * - inject service
     * -
     */
    init {

        DaggerApiComponent.create().inject(this)
    }

    fun getCategoryByPosition(position: Int): String {
        return categoryIds[position]
    }

    fun getCategoryTitles(): Array<String> {
        return categoryTitles
    }

    fun getItem(position: Int): SWModel {
        return modelList[position]
    }

    /**
     * - clears observers & list data
     * - sets error state to false
     */
    private fun reset() {

        compositeDisposable.clear()
        modelList.clear()
        liveData!!.value = modelList
        error.value = false
    }

    /**
     * - clears observers
     */
    fun dispose() {

        compositeDisposable.dispose()
    }

    /**
     * Instantiate error state observable
     *
     * @return error LiveData
     */
    fun getError(): LiveData<Boolean> {
        return error
    }


    /**
     * Instantiate loading state observable
     *
     * @return loading LiveData
     */
    fun getLoading(): LiveData<Boolean> {
        return loading
    }


    /**
     * - resets data
     * - treigger loading state
     * - loads observable and creates disposableObserver based on category
     * - results forwarded to local methods
     */
    private fun loadData() {

        Timber.d("loadData")

        reset()
        loading.value = true

        // subscribe to observable based on category
        when (this.category) {

            Category.FILMS -> {
                Timber.d("films")
                compositeDisposable.add(searchFilms(1, this.query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith<>(object : DisposableObserver<SWModelList<Film>>() {

                            override fun onNext(list: SWModelList<Film>) {
                                Timber.d("onNext")
                                updateModelList(list.results)
                            }

                            override fun onError(t: Throwable) {
                                Timber.d("onError")
                                onSearchError(t)
                            }

                            override fun onComplete() {
                                Timber.d("onComplete")
                                onSearchComplete()
                            }
                        }))
            }

            Category.PEOPLE -> compositeDisposable.add(searchPeople(1, this.query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<>(object : DisposableObserver<SWModelList<People>>() {

                        override fun onNext(list: SWModelList<People>) {
                            updateModelList(list.results)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.PLANETS -> compositeDisposable.add(searchPlanets(1, this.query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<>(object : DisposableObserver<SWModelList<Planet>>() {

                        override fun onNext(list: SWModelList<Planet>) {
                            updateModelList(list.results)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.SPECIES -> compositeDisposable.add(searchSpecies(1, this.query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<>(object : DisposableObserver<SWModelList<Species>>() {

                        override fun onNext(list: SWModelList<Species>) {
                            updateModelList(list.results)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.STARSHIPS -> compositeDisposable.add(searchStarships(1, this.query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<>(object : DisposableObserver<SWModelList<Starship>>() {

                        override fun onNext(list: SWModelList<Starship>) {
                            updateModelList(list.results)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.VEHICLES -> compositeDisposable.add(searchVehicles(1, this.query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith<>(object : DisposableObserver<SWModelList<Vehicle>>() {

                        override fun onNext(list: SWModelList<Vehicle>) {
                            updateModelList(list.results)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))
        }
    }

    /**
     * Updates modelList using base model class
     *
     * @param list ArrayList
     */
    private fun updateModelList(list: ArrayList<*>) {

        Timber.d("updateModelList")

        for (i in list.indices) {

            // add various model types as SWModel to list
            val item = list[i]
            if (item is SWModel) {
                modelList.add(item)
            }
        }
    }

    /**
     * Called after search finishes concatenated loops
     * - sorts list for best query match
     * - clears loading state
     * - updates liveData
     */
    private fun onSearchComplete() {

        Timber.d("onSearchComplete")

        // sort model list based on result matches
        if (modelList.size > 0) {
            val sortList = SortUtils.sortForBestQueryMatch(modelList, this.query)

            // must keep adapter reference
            modelList.clear()
            modelList.addAll(sortList)
        }

        loading.value = false
        liveData!!.value = modelList
    }

    /**
     * Called on search error
     * - sets error state to true
     * - sets loading state to false
     * - clears model list
     *
     * @param t Throwable
     */
    private fun onSearchError(t: Throwable) {

        Timber.e("error: %s", t.message)

        // reset list and show error
        error.value = true
        loading.value = false

        modelList.clear()
        liveData!!.value = modelList
    }

    /**
     * Search films based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Film>>
    </Film> */
    private fun searchFilms(page: Int, query: String): Observable<SWModelList<Film>> {

        return service!!.api.searchFilms(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchFilms(page, query)
                                .concatMap Observable . just < SWModelList < Film > > response
                    }

                    Observable.just<SWModelList<Film>>(response).concatWith(searchFilms(page + 1, query))
                } as Function<SWModelList<Film>, Observable<SWModelList<Film>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Search people based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><People>>
    </People> */
    private fun searchPeople(page: Int, query: String): Observable<SWModelList<People>> {

        return service!!.api.searchPeople(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchPeople(page, query)
                                .concatMap Observable . just < SWModelList < People > > response
                    }

                    Observable.just<SWModelList<People>>(response).concatWith(searchPeople(page + 1, query))
                } as Function<SWModelList<People>, Observable<SWModelList<People>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Search species based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Species>>
    </Species> */
    private fun searchSpecies(page: Int, query: String): Observable<SWModelList<Species>> {

        return service!!.api.searchSpecies(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchSpecies(page, query)
                                .concatMap Observable . just < SWModelList < Species > > response
                    }

                    Observable.just<SWModelList<Species>>(response).concatWith(searchSpecies(page + 1, query))
                } as Function<SWModelList<Species>, Observable<SWModelList<Species>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Search planets based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Planet>>
    </Planet> */
    private fun searchPlanets(page: Int, query: String): Observable<SWModelList<Planet>> {

        return service!!.api.searchPlanets(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchPlanets(page, query)
                                .concatMap Observable . just < SWModelList < Planet > > response
                    }

                    Observable.just<SWModelList<Planet>>(response).concatWith(searchPlanets(page + 1, query))
                } as Function<SWModelList<Planet>, Observable<SWModelList<Planet>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Search starships based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Starship>>
    </Starship> */
    private fun searchStarships(page: Int, query: String): Observable<SWModelList<Starship>> {

        return service!!.api.searchStarships(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchStarships(page, query)
                                .concatMap Observable . just < SWModelList < Starship > > response
                    }

                    Observable.just<SWModelList<Starship>>(response).concatWith(searchStarships(page + 1, query))
                } as Function<SWModelList<Starship>, Observable<SWModelList<Starship>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Search vehicles based on query
     * - concats result pages
     *
     * @param page int
     * @param query String
     * @return Observable<SWModelList></SWModelList><Vehicle>>
    </Vehicle> */
    private fun searchVehicles(page: Int, query: String): Observable<SWModelList<Vehicle>> {

        return service!!.api.searchVehicles(page, query)
                .concatMap({ response ->

                    if (response.next == null) {
                        return@service.getApi().searchVehicles(page, query)
                                .concatMap Observable . just < SWModelList < Vehicle > > response
                    }

                    Observable.just<SWModelList<Vehicle>>(response).concatWith(searchVehicles(page + 1, query))
                } as Function<SWModelList<Vehicle>, Observable<SWModelList<Vehicle>>>)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {

        private val categoryIds = Category.categories
        private val categoryTitles = Category.categoryTitles
    }
}
