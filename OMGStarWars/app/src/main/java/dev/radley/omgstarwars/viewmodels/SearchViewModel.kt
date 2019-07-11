package dev.radley.omgstarwars.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.radley.omgstarwars.dagger.DaggerApiComponent
import dev.radley.omgstarwars.models.*
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.utilities.SortUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    @Inject
    lateinit var service: StarWarsService

    private var modelList = ArrayList<SWModel>()
    private val compositeDisposable = CompositeDisposable()

    private var liveData = MutableLiveData<ArrayList<SWModel>>()
    private var error = MutableLiveData<Boolean>()
    private var loading = MutableLiveData<Boolean>()

    private val categoryIds = Category.categories
    private val categoryTitles = Category.categoryTitles
    private var category = categoryIds[0]
    private var query = ""

    /**
     * - inject service
     * -
     */
    init {
        DaggerApiComponent.create().inject(this)
    }

    fun setQueryAndCategory(query: String, category: String) {
        this.query = query
        this.category = category
    }

    /**
     * Update category and loads new data if liveData is ready
     *
     * @param category String
     */
    fun setCategory(category: String) {

        this.category = category
        loadData()
    }

    fun getCategory(): String {
        return category
    }

    fun getCategoryByPosition(position: Int): String {
        return categoryIds[position]
    }

    fun getCategoryPosition(): Int {

        for (i in categoryIds.indices)
            if (categoryIds[i] == category) {
                return i
            }
        return -1
    }

    fun getCategoryTitles(): Array<String> {
        return categoryTitles
    }


    /**
     * - updates query value if new value
     * - loads new data if liveData is ready and query string has more than one character
     *
     * @param query String
     */
    fun setQuery(query: String) {

        if (this.query == query) {
            return
        }

        this.query = query

        if (query.length > 1) {
            loadData()
        }
    }

    fun getQuery(): String {
        return query
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
        liveData.value = modelList
        error.value = false
    }

    /**
     * - clears observers
     */
    fun dispose() {

        compositeDisposable.dispose()
    }


    /**
     * - instantiates liveData if null
     * - loads data
     *
     * @return liveData
     */
    fun getList(): LiveData<ArrayList<SWModel>> {

        loadData()
        return liveData
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
        when (category) {

            Category.FILMS -> {
                Timber.d("films")
                compositeDisposable.add(searchFilms(1, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<SWModelList<Film>>() {

                            override fun onNext(list: SWModelList<Film>) {
                                Timber.d("onNext")
                                updateModelList(list.results!!)
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

            Category.PEOPLE -> compositeDisposable.add(searchPeople(1, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<SWModelList<People>>() {

                        override fun onNext(list: SWModelList<People>) {
                            updateModelList(list.results!!)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.PLANETS -> compositeDisposable.add(searchPlanets(1, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<SWModelList<Planet>>() {

                        override fun onNext(list: SWModelList<Planet>) {
                            updateModelList(list.results!!)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.SPECIES -> compositeDisposable.add(searchSpecies(1, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<SWModelList<Species>>() {

                        override fun onNext(list: SWModelList<Species>) {
                            updateModelList(list.results!!)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.STARSHIPS -> compositeDisposable.add(searchStarships(1, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<SWModelList<Starship>>() {

                        override fun onNext(list: SWModelList<Starship>) {
                            updateModelList(list.results!!)
                        }

                        override fun onError(t: Throwable) {
                            onSearchError(t)
                        }

                        override fun onComplete() {
                            onSearchComplete()
                        }
                    }))

            Category.VEHICLES -> compositeDisposable.add(searchVehicles(1, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<SWModelList<Vehicle>>() {

                        override fun onNext(list: SWModelList<Vehicle>) {
                            updateModelList(list.results!!)
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
            val sortList = SortUtils.sortForBestQueryMatch(modelList, query)

            // must keep adapter reference
            modelList.clear()
            modelList.addAll(sortList)
        }

        loading.value = false
        liveData.value = modelList
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
        liveData.value = modelList
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

        return service.api.searchFilms(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchFilms(page + 1, query))
                }
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

        return service.api.searchPeople(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchPeople(page + 1, query))
                }
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

        return service.api.searchSpecies(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchSpecies(page + 1, query))
                }
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

        return service.api.searchPlanets(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchPlanets(page + 1, query))
                }
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

        return service.api.searchStarships(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchStarships(page + 1, query))
                }
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

        return service.api.searchVehicles(page, query)
                .concatMap { response ->
                    if (response.next == null) {
                        Observable.just(response)
                    } else Observable.just(response).concatWith(searchVehicles(page + 1, query))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}