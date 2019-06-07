package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class SpeciesViewModel extends CategoryViewModel {

    private Call<SWModelList<Species>> mCallSpecies;

    public Species getItem(int position) {
        return (Species) mSWModelList.get(position);
    }

    protected void loadByPage() {

        Log.d(Util.tag, "loadSpecies()");

        mCallSpecies = StarWarsApi.getApi().getAllSpecies(mPage);
        mCallSpecies.enqueue(new Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadResponse(SWModelList<Species> list) {

        if(list != null) {
            mCount = list.count;

            for (Object object : list.results) {
                mSWModelList.add(((Species) object));
            }

            mLiveData.setValue(mSWModelList);
        }
    }

    public void search(String query) {

        mQuery = query;

        if(mCallSpecies != null && mCallSpecies.isExecuted())
            mCallSpecies.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallSpecies = StarWarsApi.getApi().searchSpecies(mPage, mQuery);
        mCallSpecies.enqueue(new retrofit2.Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
