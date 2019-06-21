package dev.radley.omgstarwars.network.api;

import io.reactivex.Observer;


public class ApiBatcher {

    private StarWarsService mSwApi;
    private static ApiBatcher sService;

    ApiBatcher() {
        StarWarsService.init();
    }

    public static void init() {
        sService = new ApiBatcher();
    }


    public static void fetchFilmsPage(int page, Observer observer) {
//        Observable<SWModelList<Film>> observable = getFilmsByPage(page);
//        observable.subscribe(observer);
    }

    public static void fetchPeoplePage(int page, Observer observer) {
//        Observable<SWModelList<People>> observable = getPeopleByPage(page);
//        observable.subscribe(observer);
    }

    public static void fetchSpeciesPage(int page, Observer observer) {
//        Observable<SWModelList<Species>> observable = getSpeciesByPage(page);
//        observable.subscribe(observer);
    }

    public static void fetchStarshipsPage(int page, Observer observer) {
//        Observable<SWModelList<Starship>> observable = getStarshipsByPage(page);
//        observable.subscribe(observer);
    }

    public static void fetchVehiclesPage(int page, Observer observer) {
//        Observable<SWModelList<Vehicle>> observable = getVehiclesByPage(page);
//        observable.subscribe(observer);
    }

    public static void fetchPlanetsPage(int page, Observer observer) {
//        Observable<SWModelList<Planet>> observable = getPlanetsByPage(page);
//        observable.subscribe(observer);
    }


//    private static Observable<SWModelList<Film>> getFilmsByPage(int page) {
//
//        return StarWarsService.getApiInstance().getFilmsByPage(page)
//                .flatMap(new Function<SWModelList<Film>, Observable<SWModelList<Film>>>() {
//
//                    @Override
//                    public Observable<SWModelList<Film>> apply(SWModelList<Film> response) {
//                        return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private static Observable<SWModelList<People>> getPeopleByPage(final int page) {
//
//        return StarWarsService.getApiInstance().getPeopleByPage(page)
//                .flatMap(new Function<SWModelList<People>, Observable<SWModelList<People>>>() {
//
//                    @Override
//                    public Observable<SWModelList<People>> apply(SWModelList<People> response) {
//                            return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private static Observable<SWModelList<Species>> getSpeciesByPage(final int page) {
//
//        return StarWarsService.getApiInstance().getSpeciesByPage(page)
//                .flatMap(new Function<SWModelList<Species>, Observable<SWModelList<Species>>>() {
//
//                    @Override
//                    public Observable<SWModelList<Species>> apply(SWModelList<Species> response) {
//                        return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private static Observable<SWModelList<Starship>> getStarshipsByPage(final int page) {
//
//        return StarWarsService.getApiInstance().getStarshipsByPage(page)
//                .flatMap(new Function<SWModelList<Starship>, Observable<SWModelList<Starship>>>() {
//
//                    @Override
//                    public Observable<SWModelList<Starship>> apply(SWModelList<Starship> response) {
//                        return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private static Observable<SWModelList<Vehicle>> getVehiclesByPage(final int page) {
//
//        return StarWarsService.getApiInstance().getVehiclesByPage(page)
//                .flatMap(new Function<SWModelList<Vehicle>, Observable<SWModelList<Vehicle>>>() {
//
//                    @Override
//                    public Observable<SWModelList<Vehicle>> apply(SWModelList<Vehicle> response) {
//                        return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private static Observable<SWModelList<Planet>> getPlanetsByPage(final int page) {
//
//        return StarWarsService.getApiInstance().getPlanetsByPage(page)
//                .flatMap(new Function<SWModelList<Planet>, Observable<SWModelList<Planet>>>() {
//
//                    @Override
//                    public Observable<SWModelList<Planet>> apply(SWModelList<Planet> response) {
//                        return Observable.just(response);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//
//    private static Observable<SWModelList<Film>> searchFilms(final int page, final String query) {
//
//        return StarWarsService.getApiInstance().searchForFilms(page, query)
//                .concatMap(new Function< SWModelList<Film>, Observable< SWModelList<Film> > >() {
//
//                    @Override
//                    public Observable<SWModelList<Film>> apply(SWModelList<Film> response) {
//                        // Terminal case.
//                        if (response.next == null) {
//                            return Observable.just(response);
//                        }
//                        return Observable.just(response).concatWith(searchFilms(page + 1, query));
//                    }
//
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}
