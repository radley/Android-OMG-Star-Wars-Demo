package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class StarshipsViewModel extends CategoryViewModel {
    
    private Call<SWModelList<Starship>> mCallStarships;

    public Starship getItem(int position) {
        return (Starship)mSWModelList.get(position);
    }
    
    protected void loadByPage() {

        mCallStarships = StarWarsApi.getApi().getAllStarships(mPage);
        mCallStarships.enqueue(new Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Starship> list) {

        mCount = list.count;

        for (Object object : list.results) {
            mSWModelList.add(((Starship) object));
        }

        mLiveData.setValue(mSWModelList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCallStarships != null && mCallStarships.isExecuted())
            mCallStarships.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallStarships = StarWarsApi.getApi().searchStarships(mPage, mQuery);
        mCallStarships.enqueue(new retrofit2.Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {

                Log.d(Util.tag, "onResponse: " + mQuery);
                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {

                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
