package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class PlanetsViewModel extends ViewModel {
    
    private ArrayList<Planet> mPlanetsList;
    private Call<SWModelList<Planet>> mCall;
    private int mCount;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Planet>> mLiveData;

    public PlanetsViewModel() {
        mPlanetsList = new ArrayList<Planet>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<Planet>> getPlanets(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<Planet>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadPlanets();
            } else {
                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mPlanetsList.size() < mCount) {
            mPage++;
            loadPlanets();
        }
    }

    public String getCategoryId(int position) {
        return mPlanetsList.get(position).getCategoryId();
    }

    public Planet getItem(int position) {
        return mPlanetsList.get(position);
    }

    protected void loadPlanets() {

        Log.d(Util.tag, "loadPlanets()");

        mCall = StarWarsApi.getApi().getAllPlanets(mPage);
        mCall.enqueue(new Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Planet> list) {

        Log.d(Util.tag, "onLoadSuccess()");

        if(mCount <= 0) {
            mCount = list.count;
        }

        for (Object object : list.results) {
            mPlanetsList.add(((Planet) object));
        }

        mLiveData.setValue(mPlanetsList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCall != null && mCall.isExecuted())
            mCall.cancel();

        mPlanetsList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mPlanetsList);
            return;
        }

        getPlanetsByPage(mPage);
    }

    protected void getPlanetsByPage(int page) {
        mCall = StarWarsApi.getApi().searchPlanets(mPage, mQuery);
        mCall.enqueue(new retrofit2.Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {

                onPlanetsSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onPlanetsSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mPlanetsList.add(((Planet) object));
        }

        if(list.next != null) {
            mPage++;
            getPlanetsByPage(mPage);
        } else {
            mLiveData.setValue(mPlanetsList);
        }
    }
}
