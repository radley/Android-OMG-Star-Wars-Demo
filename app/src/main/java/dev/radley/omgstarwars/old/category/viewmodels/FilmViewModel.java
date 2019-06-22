package dev.radley.omgstarwars.old.category.viewmodels;

import dev.radley.omgstarwars.data.Film;
import dev.radley.omgstarwars.data.SWModelList;
import dev.radley.omgstarwars.network.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class FilmViewModel extends BaseCategoryViewModel {

    private Call<SWModelList<Film>> mCallFilm;

    public Film getItem(int position) {
        return (Film) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallFilm = service.getApi().getAllFilms(mPage);
        mCallFilm.enqueue(new Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Timber.d("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<Film> list) {

        if(list != null) {
            mCount = list.count;

            // sort by episode
//            Collections.sort(list.results, new Comparator<Film>() {
//                public int compare(Film o1, Film o2) {
//                    return o1.episodeId - o2.episodeId;
//                }
//            });

            mSWModelList.addAll(list.results);

            if(list.next != null) {
                mPage = getIntFromNextPageUrl(list.next);
            } else {
                resetPageCounter();
            }

            mLiveData.setValue(mSWModelList);
        }
    }


    public void search(String query) {

        mQuery = query;

        if(mCallFilm != null && mCallFilm.isExecuted())
            mCallFilm.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {

        Timber.d("searchByPage()" );

        mCallFilm = service.getApi().searchFilms(mPage, mQuery);
        mCallFilm.enqueue(new retrofit2.Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });

    }


}
