package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class FilmsViewModel extends CaegoryViewModel {

    private Call<SWModelList<Film>> mCallFilm;

    public Film getItem(int position) {
        return (Film) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallFilm = StarWarsApi.getApi().getAllFilms(mPage);
        mCallFilm.enqueue(new Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Film> list) {

        mCount = list.count;

        // sort by episode
        Collections.sort(list.results, new Comparator<Film>() {
            public int compare(Film o1, Film o2) {
                return o1.episodeId - o2.episodeId;
            }
        });

        for (Object object : list.results) {
            mSWModelList.add(((Film) object));
        }

        mLiveData.setValue(mSWModelList);
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
        mCallFilm = StarWarsApi.getApi().searchFilms(mPage, mQuery);
        mCallFilm.enqueue(new retrofit2.Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });

    }


}
