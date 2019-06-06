package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class FilmsViewModel extends ViewModel {

    private ArrayList<Film> mFilmList;
    private Call<SWModelList<Film>> mCallFilm;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Film>> mLiveData;

    public FilmsViewModel() {
        mFilmList = new ArrayList<Film>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<Film>> getFilms(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<Film>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadFilms();
            } else {

                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public String getCategoryId(int position) {
        return mFilmList.get(position).getCategoryId();
    }

    public Film getItem(int position) {
        return mFilmList.get(position);
    }

    protected void loadFilms() {

        mCallFilm = StarWarsApi.getApi().getAllFilms(mPage);
        mCallFilm.enqueue(new Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Film> list) {

        // sort by episode
        Collections.sort(list.results, new Comparator<Film>() {
            public int compare(Film o1, Film o2) {
                return o1.episodeId - o2.episodeId;
            }
        });

        for (Object object : list.results) {
            mFilmList.add(((Film) object));
        }

        mLiveData.setValue(mFilmList);
    }


    public void search(String query) {

        mQuery = query;

        if(mCallFilm != null && mCallFilm.isExecuted())
            mCallFilm.cancel();

        mFilmList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mFilmList);
            return;
        }

        getFilmsByPage(mPage);
    }

    protected void getFilmsByPage(int page) {
        mCallFilm = StarWarsApi.getApi().searchFilms(mPage, mQuery);
        mCallFilm.enqueue(new retrofit2.Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {

                onFilmSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onFilmSearchFailure();
            }
        });
    }

    protected void onFilmSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mFilmList.add(((Film) object));
        }

        if(list.next != null) {
            mPage++;
            getFilmsByPage(mPage);
        } else {

            mLiveData.setValue(mFilmList);
        }
    }

    protected void onFilmSearchFailure() {

        mLiveData.setValue(mFilmList);
    }
}
