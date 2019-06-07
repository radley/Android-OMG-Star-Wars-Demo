package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class PlanetsViewModel extends CategoryViewModel {
    
    private Call<SWModelList<Planet>> mCallPlanets;

    public Planet getItem(int position) {
        return (Planet) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallPlanets = StarWarsApi.getApi().getAllPlanets(mPage);
        mCallPlanets.enqueue(new Callback<SWModelList<Planet>>() {

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

        mCount = list.count;

        for (Object object : list.results) {
            mSWModelList.add(((Planet) object));
        }

        mLiveData.setValue(mSWModelList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCallPlanets != null && mCallPlanets.isExecuted())
            mCallPlanets.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallPlanets = StarWarsApi.getApi().searchPlanets(mPage, mQuery);
        mCallPlanets.enqueue(new retrofit2.Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
