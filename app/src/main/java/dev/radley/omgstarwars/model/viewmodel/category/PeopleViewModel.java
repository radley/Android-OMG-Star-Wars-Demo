package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class PeopleViewModel extends CategoryViewModel {

    private Call<SWModelList<People>> mCallPeople;

    public People getItem(int position) {
        return (People) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallPeople = StarWarsApi.getApi().getAllPeople(mPage);
        mCallPeople.enqueue(new Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<People> list) {

        mCount = list.count;

        for (Object object : list.results) {
            mSWModelList.add(((People) object));
        }

        mLiveData.setValue(mSWModelList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCallPeople != null && mCallPeople.isExecuted())
            mCallPeople.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallPeople = StarWarsApi.getApi().searchPeople(mPage, mQuery);
        mCallPeople.enqueue(new retrofit2.Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
