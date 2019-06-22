package dev.radley.omgstarwars.network;

import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.SWModelList;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class Search {

    private static StarWarsService mService;
    private static Search sSearchObservables;

    Search() {
        mService = new StarWarsService();
    }


    private static Observable<SWModelList<Film>> searchFilms(final int page, final String query) {

        return mService.getApi().searchForFilms(page, query)
                .concatMap(new Function< SWModelList<Film>, Observable< SWModelList<Film> > >() {

                    @Override
                    public Observable<SWModelList<Film>> apply(SWModelList<Film> response) {


                        if (response.next == null) {
                            return Observable.just(response);
                        }

                        return Observable.just(response).concatWith(searchFilms(page + 1, query));
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
